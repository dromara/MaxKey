package org.dromara.maxkey.synchronizer.service.impl;

import org.dromara.maxkey.entity.SyncJobConfigField;

import org.dromara.maxkey.persistence.mapper.SyncJobConfigFieldMapper;
import org.dromara.maxkey.synchronizer.service.SyncJobConfigFieldService;
import org.dromara.mybatis.jpa.service.impl.JpaServiceImpl;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SyncJobConfigFieldServiceImpl extends JpaServiceImpl<SyncJobConfigFieldMapper,SyncJobConfigField>  implements SyncJobConfigFieldService {

    public List<SyncJobConfigField> findByJobIdAndObjectType(Long jobId, String objectType) {

        return getMapper().findByJobIdAndObjectType(jobId,objectType);
    }

    public void deleteFieldMapById(Long id){
        ArrayList<String> ids = new ArrayList<>();
        ids.add(String.valueOf(id));
        super.deleteBatch(ids);
    }

    public List<SyncJobConfigField> findByJobId(Long jobId) {
        List<SyncJobConfigField> list = find(" jobid = ?",
                new Object[]{jobId},
                new int[]{Types.BIGINT});
        return list;

    }


}
