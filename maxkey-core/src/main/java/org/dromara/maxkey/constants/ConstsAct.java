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
 

package org.dromara.maxkey.constants;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ConstsAct {

    public static final String CREATE 			= "create";

    public static final String DELETE 			= "delete";

    public static final String UPDATE 			= "update";

    public static final String CHANGE_PASSWORD 	= "change_password";
    
    public static final String FORGOT_PASSWORD 	= "forgot_password";

    public static final String ADD_MEMBER 		= "add_member";

    public static final String DELETE_MEMBER 	= "delete_member";
    
    public static final String ENABLE 			= "enable";
    
    public static final String DISABLE 			= "disable";
    
    public static final String INACTIVE 		= "inactive";
    
    public static final String LOCK 			= "lock";
    
    public static final String UNLOCK 			= "unlock";

    public static final String VIEW 			= "view";
    
    public static final ConcurrentMap<Integer,String> statusActon ;
    
    static {
    	statusActon = new ConcurrentHashMap<>();
    	statusActon.put(ConstsStatus.ACTIVE, ENABLE);
    	statusActon.put(ConstsStatus.INACTIVE, INACTIVE);
    	statusActon.put(ConstsStatus.DISABLED, DISABLE);
    	statusActon.put(ConstsStatus.LOCK, LOCK);
    	statusActon.put(ConstsStatus.UNLOCK, UNLOCK);
    	statusActon.put(ConstsStatus.DELETE, DELETE);
    }

}
