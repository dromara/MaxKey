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
 

/**
 * 
 */
package org.dromara.maxkey.entity.apps;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Crystal.Sea
 *
 */
@Entity
@Table(name = "MXK_APPS_JWT_DETAILS") 
public class AppsJwtDetails  extends Apps  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1717427271305620545L;

	@Id
	@Column
	@GeneratedValue
	protected String id;
	
	@Column
	private String subject;
	
	@Column
	private String issuer;
	
	@Column
	private String audience;
	/**
	 * 
	 */
	@Column
	private String redirectUri;
	//
	@Column
	private String tokenType;
	@Column
	private String jwtName;
	@Column
	private String algorithm;
	@Column
	private String algorithmKey;
	@Column
	private String encryptionMethod;
	@Column
	private String signature;
	@Column
	private String signatureKey;
	@Column
	private Integer expires;
	@Column
	private String instId;

	private String instName;	
	
	public AppsJwtDetails() {
		super();
	}


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getIssuer() {
		return issuer;
	}


	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}


	public String getAudience() {
		return audience;
	}


	public void setAudience(String audience) {
		this.audience = audience;
	}


	public String getRedirectUri() {
        return redirectUri;
    }


    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }


    public String getTokenType() {
        return tokenType;
    }


    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }




    public String getJwtName() {
		return jwtName;
	}


	public void setJwtName(String jwtName) {
		this.jwtName = jwtName;
	}


	public String getAlgorithm() {
        return algorithm;
    }


    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }


    public String getAlgorithmKey() {
        return algorithmKey;
    }


    public void setAlgorithmKey(String algorithmKey) {
        this.algorithmKey = algorithmKey;
    }


    public String getEncryptionMethod() {
		return encryptionMethod;
	}


	public void setEncryptionMethod(String encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getSignatureKey() {
		return signatureKey;
	}


	public void setSignatureKey(String signatureKey) {
		this.signatureKey = signatureKey;
	}


	public Integer getExpires() {
        return expires;
    }


    public void setExpires(Integer expires) {
        this.expires = expires;
    }


    public String getInstId() {
		return instId;
	}


	public void setInstId(String instId) {
		this.instId = instId;
	}


	public String getInstName() {
		return instName;
	}


	public void setInstName(String instName) {
		this.instName = instName;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppsJwtDetails [id=");
		builder.append(id);
		builder.append(", redirectUri=");
		builder.append(redirectUri);
		builder.append(", tokenType=");
		builder.append(tokenType);
		builder.append(", jwtName=");
		builder.append(jwtName);
		builder.append(", algorithm=");
		builder.append(algorithm);
		builder.append(", algorithmKey=");
		builder.append(algorithmKey);
		builder.append(", expires=");
		builder.append(expires);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
