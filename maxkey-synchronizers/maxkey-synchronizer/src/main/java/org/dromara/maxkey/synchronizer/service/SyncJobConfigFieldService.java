package org.dromara.maxkey.synchronizer.service;

import org.dromara.maxkey.entity.SyncJobConfigField;
import org.dromara.maxkey.entity.UserInfo;
import org.dromara.maxkey.persistence.mapper.SyncJobConfigFieldMapper;
import org.dromara.mybatis.jpa.JpaService;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

@Service
public class SyncJobConfigFieldService extends JpaService<SyncJobConfigField> {
    public SyncJobConfigFieldService() {
        super(SyncJobConfigFieldMapper.class);
    }
    @Override
    public SyncJobConfigFieldMapper getMapper() {
        return (SyncJobConfigFieldMapper)super.getMapper();
    }

    public List<SyncJobConfigField> findByJobIdAndObjectType(Long jobId, String objectType) {

        return getMapper().findByJobIdAndObjectType(jobId,objectType);
    }

    public void deleteFieldMapById(Long id){
       super.deleteBatch(String.valueOf(id));
    }

    public List<SyncJobConfigField> findByJobId(Long jobId) {
        List<SyncJobConfigField> list = find(" jobid = ?",
                new Object[]{jobId},
                new int[]{Types.BIGINT});
        return list;

    }


}
