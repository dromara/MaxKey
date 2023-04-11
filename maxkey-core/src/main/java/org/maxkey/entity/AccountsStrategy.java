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
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Entity
@Table(name = "MXK_ACCOUNTS_STRATEGY")
@Setter
@Getter
public class AccountsStrategy extends JpaBaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8743329570694948718L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
    private String id;
    @Column
    private String name;
    @Column
    private String appId;
    private byte[] appIcon;
    private String appIconBase64;
    @Column
    private String appName;
    @Column
    private String mapping;
    @Column
    String filters;
    @Column
    String orgIdsList;
    @Column
    String suffixes;
    @Column
    String createType;
    @Column
    String status;
    @Column
    String description;
    @Column
    String createdBy;
    @Column
    String createdDate;
    @Column
    String modifiedBy;
    @Column
    String modifiedDate;

    @Column
    private String instId;

    private String instName;

    public AccountsStrategy() {
        super();
    }


    public void transIconBase64() {
        if (this.appIcon != null) {
            this.appIconBase64 = "data:image/png;base64," +
                    Base64.getEncoder().encodeToString(appIcon);
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AccountsStrategy [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", appId=");
        builder.append(appId);
        builder.append(", appName=");
        builder.append(appName);
        builder.append(", mapping=");
        builder.append(mapping);
        builder.append(", filters=");
        builder.append(filters);
        builder.append(", orgIdsList=");
        builder.append(orgIdsList);
        builder.append(", status=");
        builder.append(status);
        builder.append(", description=");
        builder.append(description);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", modifiedBy=");
        builder.append(modifiedBy);
        builder.append(", modifiedDate=");
        builder.append(modifiedDate);
        builder.append("]");
        return builder.toString();
    }

}
