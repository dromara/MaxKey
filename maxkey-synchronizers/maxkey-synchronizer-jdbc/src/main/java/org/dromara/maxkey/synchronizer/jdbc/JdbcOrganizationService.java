/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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


package org.dromara.maxkey.synchronizer.jdbc;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.DbTableMetaData;
import org.dromara.maxkey.entity.history.HistorySynchronizer;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.synchronizer.AbstractSynchronizerService;
import org.dromara.maxkey.synchronizer.ISynchronizerService;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.maxkey.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dromara.maxkey.synchronizer.utils.FieldUtil.setFieldValue;

@Service
public class JdbcOrganizationService extends AbstractSynchronizerService implements ISynchronizerService {
    static final  Logger _logger = LoggerFactory.getLogger(JdbcOrganizationService.class);
    static ArrayList<ColumnFieldMapper> mapperList = new ArrayList<>();
    @Autowired
    private SyncJobConfigFieldService syncJobConfigFieldService;

    private static final Integer ORG_TYPE = 2;

    @Override
    public void sync() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            if (StringUtils.isNotBlank(synchronizer.getOrgFilters())) {
                _logger.info("Sync Org Filters {}", synchronizer.getOrgFilters());
                conn = JdbcUtils.connect(
                        synchronizer.getProviderUrl(),
                        synchronizer.getPrincipal(),
                        synchronizer.getCredentials(),
                        synchronizer.getDriverClass());

                stmt = conn.createStatement();
                rs = stmt.executeQuery(synchronizer.getOrgFilters());
                while (rs.next()) {
                    Organizations org = buildOrganization(rs);
                    Organizations queryOrg = organizationsService.get(org.getId());
                    if (queryOrg == null) {
                        organizationsService.insert(org);
                    } else {
                        organizationsService.update(org);
                    }
                }
            }
        } catch (Exception e) {
            _logger.error("Exception ", e);
        } finally {
            JdbcUtils.release(conn, stmt, rs);
        }
    }

    public Organizations buildOrgByFieldMap(ResultSet rs) throws SQLException{
        Organizations org = new Organizations();
        DbTableMetaData meta = JdbcUtils.getMetaData(rs);
        Map<String, String> fieldMap = getFieldMap(Long.parseLong(synchronizer.getId()));
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            String column = entry.getValue();
            String field = entry.getKey();
            Object value = rs.getObject(column);

            if (value != null) {
                try {
                    setFieldValue(org,field,value);
                } catch (Exception e) {
                    _logger.error("setProperty Exception", e);
                }
            }
        }
        org.setType("department");
        org.setId(org.generateId());
        org.setInstId(synchronizer.getInstId());
        if (meta.getColumnsMap().containsKey("status")) {
            org.setStatus(rs.getInt("status"));
        } else {
            org.setStatus(ConstsStatus.ACTIVE);
        }
        _logger.debug("Organization {}", org);

        HistorySynchronizer historySynchronizer = new HistorySynchronizer();
        historySynchronizer.setId(historySynchronizer.generateId());
        historySynchronizer.setSyncId(synchronizer.getId());
        historySynchronizer.setSyncName(synchronizer.getName());
        historySynchronizer.setObjectId(org.getId());
        historySynchronizer.setObjectName(org.getOrgName());
        historySynchronizer.setObjectType(Organizations.class.getSimpleName());
        historySynchronizer.setInstId(synchronizer.getInstId());
        historySynchronizer.setResult("success");
        historySynchronizerService.insert(historySynchronizer);

        return org;
    }

    public Organizations buildOrganization(ResultSet rs) throws SQLException {
        DbTableMetaData meta = JdbcUtils.getMetaData(rs);
        Organizations org = new Organizations();

        for (ColumnFieldMapper mapper : mapperList) {
            if (meta.getColumnsMap().containsKey(mapper.getColumn())) {
                Object value = null;
                if (mapper.getType().equalsIgnoreCase("String")) {
                    value = rs.getString(mapper.getColumn());
                } else {
                    value = rs.getInt(mapper.getColumn());
                }
                if (value != null) {
                    try {
                        PropertyUtils.setSimpleProperty(org, mapper.getField(), value);
                    } catch (Exception e) {
                        _logger.error("setSimpleProperty Exception", e);
                    }
                }
            }
        }

        org.setId(org.generateId());
        org.setInstId(synchronizer.getInstId());
        if (meta.getColumnsMap().containsKey("status")) {
            org.setStatus(rs.getInt("status"));
        } else {
            org.setStatus(ConstsStatus.ACTIVE);
        }
        _logger.debug("Organization {}", org);

        HistorySynchronizer historySynchronizer = new HistorySynchronizer();
        historySynchronizer.setId(historySynchronizer.generateId());
        historySynchronizer.setSyncId(synchronizer.getId());
        historySynchronizer.setSyncName(synchronizer.getName());
        historySynchronizer.setObjectId(org.getId());
        historySynchronizer.setObjectName(org.getOrgName());
        historySynchronizer.setObjectType(Organizations.class.getSimpleName());
        historySynchronizer.setInstId(synchronizer.getInstId());
        historySynchronizer.setResult("success");
        historySynchronizerService.insert(historySynchronizer);
		
        return org;

    }


    public Map<String,String> getFieldMap(Long jobId){
        Map<String,String> filedMap = new HashMap<>();
        //根据job id查询属性映射表
        List<SyncJobConfigField> syncJobConfigFieldList = syncJobConfigFieldService.findByJobId(jobId);
        //获取用户属性映射
        for(SyncJobConfigField element:syncJobConfigFieldList){
            if(Integer.parseInt(element.getObjectType()) == ORG_TYPE.intValue()){
                filedMap.put(element.getTargetField(), element.getSourceField());
            }
        }
        return filedMap;
    }

    public SyncJobConfigFieldService getSyncJobConfigFieldService() {
        return syncJobConfigFieldService;
    }

    public void setSyncJobConfigFieldService(SyncJobConfigFieldService syncJobConfigFieldService) {
        this.syncJobConfigFieldService = syncJobConfigFieldService;
    }

    static {
        mapperList.add(new ColumnFieldMapper("id", "id", "String"));
        mapperList.add(new ColumnFieldMapper("orgcode", "orgCode", "String"));
        mapperList.add(new ColumnFieldMapper("orgname", "orgName", "String"));
        mapperList.add(new ColumnFieldMapper("fullname", "fullName", "String"));
        mapperList.add(new ColumnFieldMapper("parentid", "parentId", "String"));
        mapperList.add(new ColumnFieldMapper("parentcode", "parentCode", "String"));
        mapperList.add(new ColumnFieldMapper(" ", "parentName", "String"));

        mapperList.add(new ColumnFieldMapper("type", "type", "String"));
        mapperList.add(new ColumnFieldMapper("codepath", "codePath", "String"));
        mapperList.add(new ColumnFieldMapper("namepath", "namePath", "String"));
        mapperList.add(new ColumnFieldMapper("level", "level", "Int"));
        mapperList.add(new ColumnFieldMapper("haschild", "hasChild", "String"));
        mapperList.add(new ColumnFieldMapper("division", "division", "String"));
        mapperList.add(new ColumnFieldMapper("country", "country", "String"));
        mapperList.add(new ColumnFieldMapper("region", "region", "String"));
        mapperList.add(new ColumnFieldMapper("locality", "locality", "String"));
        mapperList.add(new ColumnFieldMapper("street", "street", "String"));
        mapperList.add(new ColumnFieldMapper("address", "address", "String"));
        mapperList.add(new ColumnFieldMapper("contact", "contact", "String"));
        mapperList.add(new ColumnFieldMapper("postalcode", "postalCode", "String"));
        mapperList.add(new ColumnFieldMapper("phone", "phone", "String"));
        mapperList.add(new ColumnFieldMapper("fax", "fax", "String"));
        mapperList.add(new ColumnFieldMapper("email", "email", "String"));
        mapperList.add(new ColumnFieldMapper("sortindex", "sortIndex", "Int"));
        mapperList.add(new ColumnFieldMapper("ldapdn", "ldapDn", "String"));
        mapperList.add(new ColumnFieldMapper("description", "description", "String"));
        mapperList.add(new ColumnFieldMapper("status", "status", "int"));
    }
}
