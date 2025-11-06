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

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinUsers;
import org.dromara.maxkey.synchronizer.workweixin.entity.WorkWeixinUsersResponse;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.*;

@Service
public class WorkweixinUsersService extends AbstractSynchronizerService implements ISynchronizerService {
    final static Logger _logger = LoggerFactory.getLogger(WorkweixinUsersService.class);

    @Autowired
    public SyncJobConfigFieldService syncJobConfigFieldService;
    private static final Integer USER_TYPE = 1;
    String access_token;

    static String USERS_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=0";

    public void sync() {
        _logger.info("Sync Workweixin Users...");
        try {
            // 获取前面已经拉取下来的微信部门同步记录
            List<SynchroRelated> synchroRelatedOrgList = synchroRelatedService.findOrgs(this.synchronizer);
            Map<String, String> fieldMap = getFieldMap(Long.parseLong(synchronizer.getId()));

            // 拿到微信用户Id映射后的MaxKey字段名称
            String wxUserIdInMaxkeyField = getWxUserMappingField(fieldMap, "userid");

            for (SynchroRelated relatedOrg : synchroRelatedOrgList) {
                // 根据微信部门ID，拉取微信用户列表, 这里拉取的是直属员工
                HttpRequestAdapter request = new HttpRequestAdapter();
                String responseBody = request.get(String.format(USERS_URL, access_token, relatedOrg.getOriginId()));
                WorkWeixinUsersResponse usersResponse = JsonUtils.gsonStringToObject(responseBody, WorkWeixinUsersResponse.class);
                _logger.trace("response : " + responseBody);


                for (WorkWeixinUsers wxUser : usersResponse.getUserlist()) {
                    // 依次处理每个员工
                    // 根据员工信息，构建MaxKey用户信息
                    UserInfo maxkeyUserNew = buildUserInfoByFiledMap(wxUser, fieldMap);

                    // 一个企业微信可能属于多个部门, 所以需要从 wxUser 中获取主部门
                    String mainDepartment = String.valueOf(wxUser.getMain_department());
                    // 从 synchroRelatedOrgList 中找到对应的 MaxKey 部门ID
                    synchroRelatedOrgList.stream().filter(syncInfo -> StringUtils.equals(syncInfo.getOriginId(), mainDepartment))
                            .findFirst()
                            .ifPresent(orgRelInfo -> {
                                maxkeyUserNew.setDepartmentId(orgRelInfo.getObjectId());
                                maxkeyUserNew.setDepartment(orgRelInfo.getObjectName());
                            });


                    // 根据企业微信用户Id映射字段获取用户信息
                    UserInfo existingUser = getUserByWxUserIdMappingField(wxUserIdInMaxkeyField, wxUser.getUserid());
                    // 加载一下历史的同步记录, 查看这个用户是否历史同步过
                    SynchroRelated userSyncRecord = synchroRelatedService.findByOriginId(synchronizer, wxUser.getUserid(), UserInfo.CLASS_TYPE);

                    // 现在需要进行一些场景判断 , 大致应该是分成了4种情况
                    // 1. 有同步记录, 用户存在  -> 更新用户信息
                    // 2. 有同步记录, 用户不存在  -> 重新创建用户
                    // 3. 无同步记录, 用户存在  -> 说明是手工创建的用户, 手动创建的用户应当是不能直接关联, 因为无法确认 这个用户和企业微信同步过来的用户是同一个人
                    // 4. 无同步记录, 用户不存在  -> 正常创建用户

                    //

                    if (userSyncRecord != null) {
                        // 说明之前 已经同步过, 但是同步过也不代表用户一定存在, 可能被删除了
                        if (existingUser != null) {
                            _logger.info("[同步微信用户] 用户 {} {} 已存在, 进行信息更新", wxUser.getUserid(), wxUser.getName());
                            // 用户存在, 那么就是这个用户, 不需要重新创建了, 但是用户的信息需要更新一下, fieldMap key 中的字段是需要更新的, 但是以防配置有问题 部分字段还是需要排除的
                            updateExistingUserInfo(fieldMap, maxkeyUserNew, existingUser);
                            // 直接更新到数据库就完事了
                            userInfoService.update(existingUser);
                            continue;


                        } else {
                            _logger.info("[同步微信用户] 用户 {} {} 被删除了, 重新创建用户", wxUser.getUserid(), wxUser.getName());
                            // 用户不存在, 说明被删除了, 那么就重新创建一个用户 仍然使用原用户的Id  objectId maxkey 里面的用户ID
                            maxkeyUserNew.setId(userSyncRecord.getObjectId());
                        }
                    } else {
                        // 没有同步记录不代表用户不存在, 可能是以前手工创建的用户,需要判断一下
                        if (existingUser != null) {
                            _logger.warn("[同步微信用户] 用户 {} {} 无法确认和本地的用户是否是同一人 跳过同步", wxUser.getUserid(), wxUser.getName());
                            // 手工创建的用户应当是不能直接关联, 因为无法确认 这个用户和企业微信同步过来的用户是同一个人
                            continue;
                        }
                        _logger.info("[同步微信用户] 用户 {} {} 不存在, 正常创建用户", wxUser.getUserid(), wxUser.getName());
                        // 到这里应该是正常创建用户的流程
                        maxkeyUserNew.setId(maxkeyUserNew.generateId()); // 使用一个新的Id

                    }


                    _logger.debug("userInfo : {}", maxkeyUserNew);
                    // 设置密码
                    maxkeyUserNew.setPassword(maxkeyUserNew.getUsername() + UserInfo.DEFAULT_PASSWORD_SUFFIX);
                    // 按照 username + instId 保持唯一 , username 是判重字段
                    userInfoService.saveOrUpdate(maxkeyUserNew);

                    SynchroRelated synchroRelated = new SynchroRelated(
                            maxkeyUserNew.getId(),          // objectId: maxkey 里面的用户ID
                            maxkeyUserNew.getUsername(),    // objectName: maxkey 里面的用户名
                            maxkeyUserNew.getDisplayName(), // displayName: maxkey 里面的显示名称
                            UserInfo.CLASS_TYPE,            // objectType: 对象类型 是用户
                            synchronizer.getId(),           // jobId: 同步器ID
                            synchronizer.getName(),         // jobName: 同步器名称
                            wxUser.getUserid(),             // originId: 企业微信用户ID
                            wxUser.getName(),               // originName: 企业微信用户名
                            wxUser.getUserid(),             // originId2: 企业微信用户ID
                            "",                             // originId3: 暂无
                            synchronizer.getInstId());      // instId: 机构ID

                    synchroRelatedService.updateSynchroRelated(
                            this.synchronizer, synchroRelated, UserInfo.CLASS_TYPE);

                    socialsAssociate(synchroRelated, "workweixin");
                }
            }

        } catch (Exception e) {
            _logger.error("[同步微信用户] 同步用户失败", e);
        }

    }

    private static void updateExistingUserInfo(Map<String, String> fieldMap, UserInfo maxkeyUserNew, UserInfo existingUser) {
        fieldMap.keySet().forEach(fieldName -> {
            // 排除这些字段不更新
            if (!StringUtils.equalsAny(fieldName, "id", "password", "instId", "userType")) {
                try {
                    Object newValue = getFieldValue(maxkeyUserNew, fieldName);
                    setFieldValue(existingUser, fieldName, newValue);
                } catch (NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException e) {
                    _logger.error("update existingUser error: fieldName: {}, error: {}", fieldName, e.getMessage());
                }
            }
        });
        // 额外设置一下 部门
        existingUser.setDepartmentId(maxkeyUserNew.getDepartmentId());
        existingUser.setDepartment(maxkeyUserNew.getDepartment());
    }

    public void postSync(UserInfo userInfo) {

    }

    public UserInfo buildUserInfo(WorkWeixinUsers user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(user.getUserid());//账号
        userInfo.setNickName(user.getAlias());//名字
        userInfo.setDisplayName(user.getName());//名字

        userInfo.setMobile(user.getMobile());//手机
        userInfo.setEmail(user.getEmail());
        userInfo.setGender(Integer.parseInt(user.getGender()));

        userInfo.setWorkPhoneNumber(user.getTelephone());//工作电话
        userInfo.setDepartmentId(user.getMain_department() + "");
        userInfo.setJobTitle(user.getPosition());//职务
        userInfo.setWorkAddressFormatted(user.getAddress());//工作地点

        //激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
        if (user.getStatus() == 1) {
            userInfo.setStatus(ConstsStatus.ACTIVE);
        } else {
            userInfo.setStatus(ConstsStatus.INACTIVE);
        }
        userInfo.setInstId(this.synchronizer.getInstId());
        return userInfo;
    }

    public UserInfo buildUserInfoByFiledMap(WorkWeixinUsers user, Map<String, String> fieldMap) {
        UserInfo userInfo = new UserInfo();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {

            String userInfoProperty = entry.getKey();
            String sourceProperty = entry.getValue();

            try {
                Object sourceValue = null;
                if (sourceProperty.equals("status")) {
                    userInfo.setStatus(user.getStatus() == 1 ? ConstsStatus.ACTIVE : ConstsStatus.INACTIVE);
                    continue;
                }
                if (hasField(user.getClass(), sourceProperty)) {
                    sourceValue = getFieldValue(user, sourceProperty);
                }
                if (sourceValue != null) {
                    setFieldValue(userInfo, userInfoProperty, sourceValue);
                }

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                _logger.error("buildUserInfoByFiledMap error: sourceProperty: {}, error: {}", sourceProperty, e.getMessage());
            }
        }

        userInfo.setUserType("EMPLOYEE");
        userInfo.setUserState("RESIDENT");
        userInfo.setInstId(this.synchronizer.getInstId());
        return userInfo;
    }

    public Map<String, String> getFieldMap(Long jobId) {
        Map<String, String> userFieldMap = new HashMap<>();
        //根据job id查询属性映射表
        List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
        //获取用户属性映射
        for (SyncJobConfigField element : syncJobConfigFieldList) {
            if (Integer.parseInt(element.getObjectType()) == USER_TYPE.intValue()) {
                userFieldMap.put(element.getTargetField(), element.getSourceField());
            }
        }
        return userFieldMap;
    }

    /**
     * 从字段映射中获取企业微信字段映射后的MaxKey字段名称
     *
     * @param fieldMap 字段映射
     * @param wxField  企业微信字段名称
     * @return MaxKey字段名称
     */
    public String getWxUserMappingField(Map<String, String> fieldMap, String wxField) {
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String userInfoProperty = entry.getKey();
            String sourceProperty = entry.getValue();
            if (sourceProperty.equals(wxField)) {
                return userInfoProperty;
            }
        }
        throw new RuntimeException(String.format("未找到企业微信字段'%s'映射后的本地字段，请检查同步器(ID: %s)的字段映射配置", wxField, this.synchronizer.getId()));
    }


    /**
     * 根据企业微信用户Id映射字段获取用户信息
     *
     * @param fieldName  字段映射中和企业微信用户Id对应的MaxKey的字段名称
     * @param fieldValue 企业微信用户Id
     * @return 用户信息
     */
    public UserInfo getUserByWxUserIdMappingField(String fieldName, String fieldValue) {
        return userInfoService.findOne(fieldName + " = ? and instId = ?",
                new Object[]{fieldValue, this.synchronizer.getInstId()},
                new int[]{Types.VARCHAR, Types.VARCHAR}
        );
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
