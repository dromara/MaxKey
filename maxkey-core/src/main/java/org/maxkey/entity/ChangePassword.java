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


package org.maxkey.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Setter
@Getter
@NoArgsConstructor
public class ChangePassword extends JpaBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2362608803392892403L;

    private String id;
    private String userId;
    private String username;
    private String email;
    private String mobile;
    private String windowsAccount;
    private String employeeNumber;
    private String displayName;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private String decipherable;
    private String instId;
    private int passwordSetType;
    private String passwordLastSetTime;

    public ChangePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ChangePassword(UserInfo userInfo) {
        this.setId(userInfo.getId());
        this.setUserId(userInfo.getId());
        this.setUsername(userInfo.getUsername());
        this.setWindowsAccount(userInfo.getWindowsAccount());
        this.setMobile(userInfo.getMobile());
        this.setEmail(userInfo.getEmail());
        this.setEmployeeNumber(userInfo.getEmployeeNumber());
        this.setDecipherable(userInfo.getDecipherable());
        this.setPassword(userInfo.getPassword());
        this.setInstId(userInfo.getInstId());
    }

    public void clearPassword() {
        this.password = "";
        this.decipherable = "";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ChangePassword [id=");
        builder.append(id);
        builder.append(", uid=");
        builder.append(userId);
        builder.append(", username=");
        builder.append(username);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", oldPassword=");
        builder.append(oldPassword);
        builder.append(", password=");
        builder.append(password);
        builder.append(", confirmPassword=");
        builder.append(confirmPassword);
        builder.append(", decipherable=");
        builder.append(decipherable);
        builder.append("]");
        return builder.toString();
    }

}
