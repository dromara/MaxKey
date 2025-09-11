/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.entity.passkey;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import org.dromara.mybatis.jpa.entity.JpaEntity;

/**
 * 用户Passkey凭据实体类
 */
@Entity
@Table(name = "mxk_user_passkeys")
public class UserPasskey extends JpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 45)
    private String id;

    @Column(name = "user_id", length = 45, nullable = false)
    private String userId;

    @Column(name = "credential_id", length = 500, nullable = false)
    private String credentialId;

    @Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
    private String publicKey;

    @Column(name = "signature_count", nullable = false)
    private Long signatureCount = 0L;

    @Column(name = "aaguid", length = 100)
    private String aaguid;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "device_type", length = 50)
    private String deviceType;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "last_used_date")
    private Date lastUsedDate;

    @Column(name = "status", nullable = false)
    private Integer status = 1; // 1: 活跃, 0: 禁用

    @Column(name = "inst_id", length = 45, nullable = false)
    private String instId;

    // 构造函数
    public UserPasskey() {
        this.createdDate = new Date();
    }

    public UserPasskey(String id, String userId, String credentialId, String publicKey) {
        this.id = id;
        this.userId = userId;
        this.credentialId = credentialId;
        this.publicKey = publicKey;
        this.createdDate = new Date();
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getSignatureCount() {
        return signatureCount;
    }

    public void setSignatureCount(Long signatureCount) {
        this.signatureCount = signatureCount;
    }

    public String getAaguid() {
        return aaguid;
    }

    public void setAaguid(String aaguid) {
        this.aaguid = aaguid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUsedDate() {
        return lastUsedDate;
    }

    public void setLastUsedDate(Date lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    @Override
    public String toString() {
        return "UserPasskey{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", credentialId='" + credentialId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", createdDate=" + createdDate +
                ", lastUsedDate=" + lastUsedDate +
                ", status=" + status +
                ", instId='" + instId + '\'' +
                '}';
    }
}