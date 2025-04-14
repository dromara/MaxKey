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
 

package org.dromara.maxkey.authn.provider.scancode;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM5:42
 */
public class ScanCodeState {

    String sessionId;

    String ticket;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long confirmKey;

    String state;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getConfirmKey() {
        return confirmKey;
    }

    public void setConfirmKey(Long confirmKey) {
        this.confirmKey = confirmKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
