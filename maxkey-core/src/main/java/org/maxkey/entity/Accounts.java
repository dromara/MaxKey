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

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
   ID                   varchar(40)                    not null,
   UID                  varchar(40)                    null,
   APPID                varchar(40)	                   null,
   USERNAME            	varchar(60)                    null,
   PASSWORD             varchar(60)                    null,
   STATUS	            char(1)                        null
   constraint PK_ROLES primary key clustered (ID)
 */
@Entity
@Table(name = "MXK_ACCOUNTS")
@Setter
@Getter
public class Accounts extends JpaBaseEntity implements Serializable {
    private static final long serialVersionUID = 6829592256223630307L;

    public static final String DEFAULT_PASSWORD_SUFFIX = UserInfo.DEFAULT_PASSWORD_SUFFIX;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
    private String id;
    @Column
    private String userId;
    @Column
    private String username;
    @Column
    private String displayName;
    @Column
    private String appId;
    @Column
    private String appName;

    @Length(max = 60)
    @Column
    private String relatedUsername;
    @Column
    private String relatedPassword;
    @Column
    private String createType;
    @Column
    private String strategyId;
    @Column
    private String strategyName;
    @Column
    private int status;

    @Column
    private String instId;

    private String instName;

    UserInfo userInfo;

    @JsonIgnore
    private HashMap<String, OrganizationsCast> orgCast = new HashMap<String, OrganizationsCast>();

    public Accounts() {
        super();
    }

    public Accounts(String id) {
        this.id = id;
    }

    public Accounts(String userId, String appId) {
        this.userId = userId;
        this.appId = appId;
    }

    public Accounts(String userId, String appId, String password) {
        this.userId = userId;
        this.appId = appId;
        this.relatedPassword = password;
    }

    public void setOrgCast(List<OrganizationsCast> listOrgCast) {
        for (OrganizationsCast cast : listOrgCast) {
            this.orgCast.put(cast.getProvider(), cast);
        }
    }

    @Override
    public String toString() {
        return "AppAccounts [uid=" + userId + ", username=" + username + ", displayName=" + displayName + ", appId="
                + appId + ", appName=" + appName + ", relatedUsername=" + relatedUsername + ", relatedPassword="
                + relatedPassword + "]";
    }

}
