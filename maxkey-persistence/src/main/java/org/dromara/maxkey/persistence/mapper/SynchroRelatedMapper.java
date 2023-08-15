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
 

/**
 * 
 */
package org.dromara.maxkey.persistence.mapper;

import org.apache.ibatis.annotations.Update;
import org.dromara.maxkey.entity.SynchroRelated;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * @author Crystal.sea
 *
 */
public  interface SynchroRelatedMapper extends IJpaMapper<SynchroRelated> {
	@Update("update mxk_synchro_related set synctime = #{syncTime} where id= #{id} ")
	public int updateSyncTime(SynchroRelated synchroRelated);
}
