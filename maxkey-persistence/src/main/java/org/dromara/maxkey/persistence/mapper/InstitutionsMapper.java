/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.persistence.mapper;
import org.apache.ibatis.annotations.Select;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.Institutions;
import org.dromara.mybatis.jpa.IJpaMapper;

public interface InstitutionsMapper extends IJpaMapper<Institutions> {

	@Select("select * from  mxk_institutions where  (id = #{value} or domain = #{value} or consoledomain = #{value} ) and status = " + ConstsStatus.ACTIVE)
	public Institutions findByDomain(String domain);
}
