/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.dromara.maxkey.synchronizer.workweixin;

import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.http.HttpRequestAdapter;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinDepts;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinDeptsResponse;
import org.dromara.maxkey.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.*;

@Service
public class WorkweixinOrganizationService extends AbstractSynchronizerService implements ISynchronizerService {
    static final Logger _logger = LoggerFactory.getLogger(WorkweixinOrganizationService.class);

    String access_token;
    @Autowired
    private SyncJobConfigFieldService syncJobConfigFieldService;
    private static final Integer ORG_TYPE = 2;
    static String DEPTS_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s";
    static long ROOT_DEPT_ID = 1;

    public void sync() {
        _logger.info("Sync Workweixin Organizations ...");

        try {
            WorkWeixinDeptsResponse rsp = requestDepartmentList(access_token);

            // 需要对企业微信部门列表进行一次重排，保证父节点在前，子节点在后
            List<WorkWeixinDepts> deptWxListAfterLevelSort = sortDepartments(rsp.getDepartment());

            // 关键字段不能依赖映射关系,否则映射数据有问题会导致功能异常
            // 先拿出字段映射关系
            Map<String, String> fieldMap = getFieldMap(Long.parseLong(synchronizer.getId()));
            // 从映射里面拿到企业微信Id映射后的本地组织的字段 用于判断本地的组织是否存在
            String targetIdField = getLocalFieldMappingByWx(fieldMap, "id");

            for (WorkWeixinDepts deptWxCur : deptWxListAfterLevelSort) {
                _logger.debug("sync workweixin dept : {} {} {}", deptWxCur.getId(), deptWxCur.getName(), deptWxCur.getParentid());
                //root
                if (deptWxCur.getId() == ROOT_DEPT_ID) {
                    // 当前根节点
                    Organizations rootOrganization = organizationsService.get(Organizations.ROOT_ORG_ID);
                    if (rootOrganization == null) {
                        _logger.error("根组织不存在(ID: {}), 无法同步企业微信根部门", Organizations.ROOT_ORG_ID);
                        throw new RuntimeException("根组织不存在, 同步失败! 请先确保系统中存在根组织(ID: " + Organizations.ROOT_ORG_ID + ")");
                    }
                    // 构建同步关系
                    SynchroRelated rootSynchroRelated = buildSynchroRelated(rootOrganization, deptWxCur);
                    // 更新同步关系
                    synchroRelatedService.updateSynchroRelated(
                            this.synchronizer, rootSynchroRelated, Organizations.CLASS_TYPE);
                    // 是否更新根节点的编码待确认, 这里先更新名称
                    rootOrganization.setOrgName(deptWxCur.getName());
                    organizationsService.update(rootOrganization);
                } else {
                    // 现在不是根组织
                    //synchro Related 查询当前部门是否有同步记录 这里只是查有没有关系, 不是查组织 
                    SynchroRelated synchroRelated =
                            synchroRelatedService.findByOriginId(
                                    this.synchronizer, deptWxCur.getId() + "", Organizations.CLASS_TYPE);
                    //Parent 查询当前部门父部门是否有同步记录 这里只是查有没有关系, 不是查组织 
                    SynchroRelated synchroRelatedParent =
                            synchroRelatedService.findByOriginId(
                                    this.synchronizer, deptWxCur.getParentid() + "", Organizations.CLASS_TYPE);

                    // 根据字段映射构建当前组织的实体
                    Organizations orgCurrent = buildOrgByFiledMap(deptWxCur, synchroRelatedParent, fieldMap);
                    // 这里需要修正一下层级关系, 防止因为映射关系错误导致的层级错乱
                    String deptWxParentId = String.valueOf(deptWxCur.getParentid());
                    // 进入到这个节点的应该都是有上级的, 现在只需要根据上级Id查询上级的组织档案

                    Organizations parentOrg = findOrganizationByField(targetIdField, deptWxParentId);
                    // 这里父级不应该为 null
                    if (parentOrg == null) {
                        throw new RuntimeException("无法找到上级组织, 同步失败! 企业微信父部门Id: " + deptWxParentId);
                    }

                    orgCurrent.setParentId(parentOrg.getId());
                    orgCurrent.setParentCode(parentOrg.getOrgCode());
                    orgCurrent.setParentName(parentOrg.getOrgName());

                    if (ObjectUtils.isEmpty(orgCurrent.getFullName())) {
                        // 兜底设置一下组织全称
                        orgCurrent.setFullName(orgCurrent.getOrgName());
                    }


                    if (synchroRelated == null) {
                        // 当前部门还没有同步过
                        orgCurrent.setId(orgCurrent.generateId());
                        organizationsService.insert(orgCurrent);
                        _logger.debug("Organizations : " + orgCurrent);

                        synchroRelated = buildSynchroRelated(orgCurrent, deptWxCur);
                    } else {
                        // 部门曾经同步过, 但是不能保证没被删除过, 所以还需要判定一次
                        Organizations currentOrg = findOrganizationByField(targetIdField, String.valueOf(deptWxCur.getId()));
                        if (currentOrg == null) {
                            // 当前部门已经被删除, 那就需要重新写入一次
                            orgCurrent.setId(synchroRelated.getObjectId());
                            organizationsService.insert(orgCurrent);
                        } else {
                            // 组织存在, 执行更新操作
                            orgCurrent.setId(synchroRelated.getObjectId());
                            organizationsService.update(orgCurrent);
                        }
                    }

                    synchroRelatedService.updateSynchroRelated(
                            this.synchronizer, synchroRelated, Organizations.CLASS_TYPE);
                }
            }

        } catch (Exception e) {
            _logger.error("同步企业微信组织失败", e);
            throw new RuntimeException("同步企业微信组织失败: " + e.getMessage(), e);
        }

    }

    /**
     * 构建同步关系
     *
     * @param organization 组织实体
     * @param dept         企业微信部门实体
     * @return 同步关系
     */
    public SynchroRelated buildSynchroRelated(Organizations organization, WorkWeixinDepts dept) {
        return new SynchroRelated(
                organization.getId(), // objectId 系统内组织ID
                organization.getOrgName(), // objectName 系统内组织名称
                organization.getOrgName(), // objectDisplayName 系统内组织显示名称
                Organizations.CLASS_TYPE, // objectType 对象类型
                synchronizer.getId(), // syncId 同步器ID
                synchronizer.getName(), // syncName 同步器名称
                dept.getId() + "", // originId 企业微信部门ID
                dept.getName(), // originName 企业微信部门名称
                "",
                dept.getParentid() + "", // originId3 父部门ID
                synchronizer.getInstId());
    }

    public WorkWeixinDeptsResponse requestDepartmentList(String access_token) {
        HttpRequestAdapter request = new HttpRequestAdapter();
        String responseBody = request.get(String.format(DEPTS_URL, access_token));
        WorkWeixinDeptsResponse deptsResponse = JsonUtils.gsonStringToObject(responseBody, WorkWeixinDeptsResponse.class);

        _logger.trace("response : " + responseBody);
        for (WorkWeixinDepts dept : deptsResponse.getDepartment()) {
            _logger.debug("WorkWeixinDepts : " + dept);
        }
        return deptsResponse;
    }

    public Organizations buildOrganization(WorkWeixinDepts dept, SynchroRelated synchroRelatedParent) {

        Organizations org = new Organizations();
        org.setOrgName(dept.getName());
        org.setOrgCode(dept.getId() + "");
        org.setParentId(synchroRelatedParent.getObjectId());
        org.setParentName(synchroRelatedParent.getObjectName());
        org.setSortIndex(dept.getOrder());
        org.setInstId(this.synchronizer.getInstId());
        org.setStatus(ConstsStatus.ACTIVE);
        org.setDescription("WorkWeixin");
        return org;
    }

    /**
     * 从字段映射中获取企业微信字段映射后的本地字段
     * @param fieldMap    字段映射
     * @param expectField 企业微信字段
     * @return 本地字段
     */
    public String getLocalFieldMappingByWx(Map<String, String> fieldMap, String expectField) {
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String orgProperty = entry.getKey();
            String sourceProperty = entry.getValue();
            if (sourceProperty.equals(expectField)) {
                return orgProperty;
            }
        }
        throw new RuntimeException(String.format(
                "未找到企业微信字段'%s'映射后的本地字段，请检查同步器(ID: %s)的字段映射配置",
                expectField, this.synchronizer.getId()));
    }

    /**
     * 根据字段映射构建组织实体
     *
     * @param dept                 企业微信部门实体
     * @param synchroRelatedParent 父部门同步关系
     * @param fieldMap             同步器配置的字段映射
     * @return 组织实体
     */
    public Organizations buildOrgByFiledMap(WorkWeixinDepts dept, SynchroRelated synchroRelatedParent, Map<String, String> fieldMap) {
        Organizations org = new Organizations();


        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String orgProperty = entry.getKey();
            String sourceProperty = entry.getValue();
            try {
                Object sourceValue = null;

                if (hasField(dept.getClass(), sourceProperty)) {
                    sourceValue = getFieldValue(dept, sourceProperty);
                } else if (synchroRelatedParent != null && hasField(SynchroRelated.class, sourceProperty)) {
                    sourceValue = getFieldValue(synchroRelatedParent, sourceProperty);
                }
                if (sourceValue != null) {
                    setFieldValue(org, orgProperty, sourceValue);
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        org.setInstId(this.synchronizer.getInstId());
        org.setStatus(ConstsStatus.ACTIVE);
        org.setDescription("WorkWeixin");
        org.setType("department");
        return org;

    }

    public Map<String, String> getFieldMap(Long jobId) {
        Map<String, String> filedMap = new HashMap<>();
        //根据job id查询属性映射表
        List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
        //获取组织属性映射
        for (SyncJobConfigField element : syncJobConfigFieldList) {
            if (Integer.parseInt(element.getObjectType()) == ORG_TYPE) {
                filedMap.put(element.getTargetField(), element.getSourceField());
            }
        }
        return filedMap;
    }

    /**
     * 验证字段名是否合法，防止SQL注入
     *
     * @param fieldName 字段名
     * @throws IllegalArgumentException 如果字段名不合法
     */
    private void validateFieldName(String fieldName) {
        if (fieldName == null || fieldName.trim().isEmpty()) {
            throw new IllegalArgumentException("字段名不能为空");
        }
        // 只允许字母、数字、下划线，且必须以字母或下划线开头
        if (!fieldName.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
            throw new IllegalArgumentException("非法的字段名: " + fieldName + ", 字段名只能包含字母、数字和下划线，且必须以字母或下划线开头");
        }
    }

    /**
     * 根据指定字段查询组织
     *
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @return 查询到的组织，如果不存在返回null
     */
    private Organizations findOrganizationByField(String fieldName, String fieldValue) {
        // 验证字段名防止SQL注入
        validateFieldName(fieldName);

        return organizationsService.findOne(
                fieldName + " = ? AND instId = ?",
                new Object[]{fieldValue, this.synchronizer.getInstId()},
                new int[]{Types.VARCHAR, Types.VARCHAR}
        );
    }

    /**
     * 对部门列表进行排序，确保父节点在前，子节点在后
     * 使用拓扑排序算法，按照层级顺序遍历部门树
     *
     * @param departments 原始部门列表
     * @return 排序后的部门列表
     */
    private List<WorkWeixinDepts> sortDepartments(List<WorkWeixinDepts> departments) {
        if (departments == null || departments.isEmpty()) {
            return departments;
        }

        // 构建部门ID到部门对象的映射
        Map<Long, WorkWeixinDepts> deptMap = new HashMap<>();
        // 构建父ID到子部门列表的映射
        Map<Long, List<WorkWeixinDepts>> parentToChildrenMap = new HashMap<>();

        for (WorkWeixinDepts dept : departments) {
            deptMap.put(dept.getId(), dept);
            parentToChildrenMap.computeIfAbsent(dept.getParentid(), k -> new ArrayList<>()).add(dept);
        }

        // 结果列表
        List<WorkWeixinDepts> sortedList = new ArrayList<>();

        // 从根节点开始遍历
        List<Long> queue = new ArrayList<>();

        // 找到所有根节点（没有父节点的部门，或者父节点不在列表中的部门）
        for (WorkWeixinDepts dept : departments) {
            if (!deptMap.containsKey(dept.getParentid())) {
                queue.add(dept.getId());
            }
        }

        // 遍历
        while (!queue.isEmpty()) {
            Long currentId = queue.remove(0);
            WorkWeixinDepts currentDept = deptMap.get(currentId);

            if (currentDept != null) {
                sortedList.add(currentDept);

                // 将当前部门的所有子部门加入队列
                List<WorkWeixinDepts> children = parentToChildrenMap.get(currentId);
                if (children != null) {
                    for (WorkWeixinDepts child : children) {
                        queue.add(child.getId());
                    }
                }
            }
        }

        return sortedList;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public SyncJobConfigFieldService getSyncJobConfigFieldService() {
        return syncJobConfigFieldService;
    }

    public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
        this.syncJobConfigFieldService = syncJobConfigFieldService;
    }
}
