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
 

package org.maxkey.authz.endpoint;

import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "3-1-在线ticket文档模块")
@Controller
@RequestMapping(value={"/onlineticket"})
public class OnlineTicketEndpoint {

    @Autowired
    @Qualifier("onlineTicketServices")
    protected OnlineTicketServices onlineTicketServices;
    
    @ApiOperation(value = "在线ticket验证接口", notes = "",httpMethod="GET")
    @ResponseBody
    @RequestMapping(value="/validate") 
    public String ticketValidate(
            @RequestParam(value ="ticket",required = true) String ticket) {
        OnlineTicket onlineTicket = onlineTicketServices.get(ticket);
        return onlineTicket == null ? "" :onlineTicket.getTicketId();
    }
}
