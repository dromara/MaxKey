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

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * PROTOCOLS.
 * @author Crystal.Sea
 *
 */
public final class ConstsRoles {

    public static final SimpleGrantedAuthority ROLE_ADMINISTRATORS 	= new SimpleGrantedAuthority("ROLE_ADMINISTRATORS");

    public static final SimpleGrantedAuthority ROLE_MANAGERS 		= new SimpleGrantedAuthority("ROLE_MANAGERS");
    
    public static final SimpleGrantedAuthority ROLE_USER 			= new SimpleGrantedAuthority("ROLE_USER");
    
    public static final SimpleGrantedAuthority ROLE_ALL_USER 		= new SimpleGrantedAuthority("ROLE_ALL_USER");
    
    public static final SimpleGrantedAuthority ROLE_ORDINARY_USER 	= new SimpleGrantedAuthority("ROLE_ORDINARY_USER");
    
}
