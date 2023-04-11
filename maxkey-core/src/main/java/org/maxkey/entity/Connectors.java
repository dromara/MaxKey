/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.mybatis.jpa.persistence.JpaBaseEntity;
import org.maxkey.pretty.impl.JsonPretty;

@Entity
@Table(name = "MXK_CONNECTORS")
@Setter
@Getter
@NoArgsConstructor
public class Connectors extends JpaBaseEntity implements Serializable {

    private static final long serialVersionUID = 4660258495864814777L;
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
    String id;
    @Column
    String connName;
    @Column
    String scheduler;
    @Column
    int justInTime;
    @Column
    String providerUrl;
    @Column
    String principal;
    @Column
    String credentials;
    @Column
    String filters;
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
    String status;

    @Column
    private String instId;

    private String instName;

    public Connectors(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new JsonPretty().format(this);
    }

}
