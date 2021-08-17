package com.nimai.admin.payload;

import java.util.List;

import com.nimai.admin.model.NimaiMpRoleRights;
import com.nimai.admin.model.NimaiMpUserRole;

public class loginBeanResponse {
private Long id;
private String name;
private String username;
private String flag;
List<String> authorities;
List<String> role;
private String country;
private String enabled;
private String accountNonLocked;
private String accountNonExpired;
private String credentialsNonExpired;





public List<String> getAuthorities() {
	return authorities;
}
public void setAuthorities(List<String> authorities) {
	this.authorities = authorities;
}
public List<String> getRole() {
	return role;
}
public void setRole(List<String> role) {
	this.role = role;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getFlag() {
	return flag;
}
public void setFlag(String flag) {
	this.flag = flag;
}

public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getEnabled() {
	return enabled;
}
public void setEnabled(String enabled) {
	this.enabled = enabled;
}
public String getAccountNonLocked() {
	return accountNonLocked;
}
public void setAccountNonLocked(String accountNonLocked) {
	this.accountNonLocked = accountNonLocked;
}
public String getAccountNonExpired() {
	return accountNonExpired;
}
public void setAccountNonExpired(String accountNonExpired) {
	this.accountNonExpired = accountNonExpired;
}
public String getCredentialsNonExpired() {
	return credentialsNonExpired;
}
public void setCredentialsNonExpired(String credentialsNonExpired) {
	this.credentialsNonExpired = credentialsNonExpired;
}







}
