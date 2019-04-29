package org.maxkey.domain.xml;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;

/**
 * xml can not include array , MultipartFile
 * @author Crystal.Sea
 *
 */
@XmlRootElement
public class UserInfoXML extends JpaBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6942731467730249291L;

	/**
	 * 
	 */
	public UserInfoXML() {
		super();
		// TODO Auto-generated constructor stub
	}



}
