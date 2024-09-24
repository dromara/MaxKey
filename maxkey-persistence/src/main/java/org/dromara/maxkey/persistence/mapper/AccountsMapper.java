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
 

/**
 * 
 */
package org.dromara.maxkey.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.AccountsStrategy;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.mybatis.jpa.IJpaMapper;

/**
 * @author Crystal.sea
 *
 */
public  interface AccountsMapper extends IJpaMapper<Accounts> {
	

    public List<UserInfo> queryUserNotInStrategy(AccountsStrategy strategy);
    
    public long deleteByStrategy(AccountsStrategy strategy);
    
    public List<Accounts> queryByAppIdAndDate(Accounts account);
    
    @Select("select * from mxk_accounts where appid=#{appId} and	relatedusername=#{relatedUsername}")
    public List<Accounts> queryByAppIdAndAccount(@Param ("appId") String appId,@Param ("relatedUsername") String relatedUsername);
    
    @Update("update mxk_accounts set status = #{status}  where id= #{id}")
    public int updateStatus(Accounts accounts);
}
