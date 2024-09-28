package org.dromara.maxkey.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.mybatis.jpa.IJpaMapper;

import java.util.List;


public interface SyncJobConfigFieldMapper extends IJpaMapper<SyncJobConfigField> {
    /*@Select("SELECT * FROM sync_job_config_field WHERE job_id = #{jobId} AND object_type = #{objectType}")*/
    public List<SyncJobConfigField> findByJobIdAndObjectType(@Param("jobId") Long jobId, @Param("objectType") String objectType);

    public List<SyncJobConfigField> findByJobId(Long jobId);


    void deleteFieldMapById(Long id);

    void deleteFiledMapByjobId(Long jobId);

    void deleteByJobIdAndObjectType(@Param("jobId") Long jobId, @Param("objectType") String objectType);
}


