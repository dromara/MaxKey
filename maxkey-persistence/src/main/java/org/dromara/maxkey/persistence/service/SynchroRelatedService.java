/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.persistence.service;

import java.util.List;

import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.maxkey.entity.Synchronizers;
import org.dromara.mybatis.jpa.service.IJpaService;

public interface SynchroRelatedService  extends IJpaService<SynchroRelated>{

    public int updateSyncTime(SynchroRelated synchroRelated);
    
    public List<SynchroRelated> findOrgs(Synchronizers synchronizer) ;
    
    public SynchroRelated findByOriginId(Synchronizers synchronizer,String originId,String classType) ;
    
     /**
     * 根据 同步器 + originId + classType 查询同步关系, 如果存在则更新, 不存在则插入
     * @param synchronizer 同步器
     * @param synchroRelated 同步关系
     * @param classType 对象类型
     */
    public void updateSynchroRelated(Synchronizers synchronizer,SynchroRelated synchroRelated,String classType) ;
}
