/**
 * 
 */
package org.maxkey.domain;

/**
 * @author Administrator
 *
 */
public class ExtraAttr {
	
	String attr;
	String value;
	
	/**
	 * @param attr
	 * @param value
	 */
	public ExtraAttr(String attr, String value) {
		super();
		this.attr = attr;
		this.value = value;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ExtraAttr [attr=" + attr + ", value=" + value + "]";
	}
	
}
