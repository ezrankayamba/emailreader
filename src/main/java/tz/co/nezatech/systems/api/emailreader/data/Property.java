/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tz.co.nezatech.systems.api.emailreader.data;

/**
 *
 * @author godfred.nkayamba
 */
public class Property extends BaseData{
	String name, value;

	public Property() {
		super();
	}

	public Property(Integer id, String key, String value) {
		super();
		this.name = key;
		this.value = value;
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String key) {
		this.name = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
