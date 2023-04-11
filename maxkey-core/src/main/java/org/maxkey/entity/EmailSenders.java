/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
@Table(name = "MXK_EMAIL_SENDERS")
@Setter
@Getter
public class EmailSenders extends JpaBaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3689854324601731505L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
    private String id;

    @Column
    private String account;

    @Column
    private String credentials;

    @Column
    private String smtpHost;

    @Column
    private Integer port;

    @Column
    private int sslSwitch;

    @Column
    private String sender;

    @Column
    private String encoding;

    @Column
    private String protocol;

    @Column
    private int status;

    @Column
    private String instId;

    private String instName;

    @Column
    private String description;

    @Column
    private String createdBy;

    @Column
    private String createdDate;

    @Column
    private String modifiedBy;

    @Column
    private String modifiedDate;

    public EmailSenders() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmailSenders [id=");
        builder.append(id);
        builder.append(", account=");
        builder.append(account);
        builder.append(", credentials=");
        builder.append(credentials);
        builder.append(", smtpHost=");
        builder.append(smtpHost);
        builder.append(", port=");
        builder.append(port);
        builder.append(", sslSwitch=");
        builder.append(sslSwitch);
        builder.append(", sender=");
        builder.append(sender);
        builder.append(", status=");
        builder.append(status);
        builder.append(", instId=");
        builder.append(instId);
        builder.append(", instName=");
        builder.append(instName);
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
