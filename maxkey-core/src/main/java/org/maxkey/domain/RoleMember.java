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
 

package org.maxkey.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
   ID                   varchar(40)                    not null,
   APPROLEID            varchar(40)                    null,
   UID	                varchar(40)	                   null
   constraint PK_ROLES primary key clustered (ID)
 */
@Table(name = "MXK_ROLE_MEMBER")
public class RoleMember extends UserInfo implements Serializable {
    private static final long serialVersionUID = -8059639972590554760L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    String id;
    @Column
    private String roleId;
    private String roleName;
    @Column
    private String memberId;
    private String memberName;
    @Column
    private String type;// User or Roles

    public RoleMember() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RoleMember(String roleId, String memberId, String type) {
        super();
        this.roleId = roleId;
        this.memberId = memberId;
        this.type = type;
    }

    
    public RoleMember(String roleId, String roleName, String memberId, String memberName, String type) {
        super();
        this.roleId = roleId;
        this.roleName = roleName;
        this.memberId = memberId;
        this.memberName = memberName;
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoleMember [id=" + id + ", roleId=" + roleId + ", roleName=" + roleName + ", memberId=" + memberId
                + ", memberName=" + memberName + ", type=" + type + "]";
    }

}
