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
 

package org.dromara.maxkey.authz.endpoint;

import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "3-1-在线ticket文档模块")
@Controller
@RequestMapping(value={"/onlineticket"})
public class OnlineSessionEndpoint {

    @Autowired
    protected SessionManager sessionManager;
    
    @Operation(summary = "在线ticket验证接口", description = "",method="GET")
    @ResponseBody
    @RequestMapping(value="/validate") 
    public String ticketValidate(
            @RequestParam(value ="ticket",required = true) String ticket) {
        Session session = sessionManager.get(ticket);
        return session == null ? "" : session.getId();
    }
}
