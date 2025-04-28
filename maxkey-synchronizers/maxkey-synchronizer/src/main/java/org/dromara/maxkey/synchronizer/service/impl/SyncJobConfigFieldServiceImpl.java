/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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
