/**
 * 
 */
package com.giants.swagger.configuration;

/**
 * @author vencent.lu
 *
 */
public class Parameter {
	
	private Class<?> type;
	private String name;

	public Parameter() {
	}

	public Parameter(Class<?> type, String name) {
		this.type = type;
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
