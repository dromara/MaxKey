package org.maxkey.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

@Entity
@Table(name = "MXK_LOCALIZATION")
public class Localization  extends JpaBaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -142504964446659847L;
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "snowflakeid")
	private String id;
	
	@Column
	private String property;

	@Column
	private String langZh;
	
	@Column
	private String langEn;
	
	@Column
	private String description;
	
	@Column
	private int status;
	
	@Column
	private String instId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}



	public String getLangZh() {
		return langZh;
	}

	public void setLangZh(String langZh) {
		this.langZh = langZh;
	}

	public String getLangEn() {
		return langEn;
	}

	public void setLangEn(String langEn) {
		this.langEn = langEn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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
		StringBuilder builder = new StringBuilder();
		builder.append("Localization [id=");
		builder.append(id);
		builder.append(", property=");
		builder.append(property);
		builder.append(", langZh=");
		builder.append(langZh);
		builder.append(", langEn=");
		builder.append(langEn);
		builder.append(", description=");
		builder.append(description);
		builder.append(", status=");
		builder.append(status);
		builder.append(", instId=");
		builder.append(instId);
		builder.append("]");
		return builder.toString();
	}

}
