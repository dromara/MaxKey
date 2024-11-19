package org.dromara.maxkey.synchronizer.service;

import org.dromara.maxkey.entity.SyncJobConfigField;

import org.dromara.mybatis.jpa.IJpaService;
import java.util.List;

public interface SyncJobConfigFieldService extends IJpaService<SyncJobConfigField> {

    public List<SyncJobConfigField> findByJobIdAndObjectType(Long jobId, String objectType) ;

    public void deleteFieldMapById(Long id);

    public List<SyncJobConfigField> findByJobId(Long jobId) ;


}
