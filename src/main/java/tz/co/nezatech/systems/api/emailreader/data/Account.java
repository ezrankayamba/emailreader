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
public class Account extends BaseData{
Integer id;
String name, msisdn, email, password;
int status;


public Account(Integer id, String name, String msisdn, int status, String email,String password) {
	super();
	this.id = id;
	this.name = name;
	this.msisdn = msisdn;
	this.status = status;
	this.email=email;
	this.password=password;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getMsisdn() {
	return msisdn;
}
public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
} 


}
