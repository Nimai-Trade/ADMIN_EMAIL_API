package com.nimai.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimai.admin.model.NimaiMLogin;

public class UserPrincipal implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String username;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;

	private String flag;

	private Collection<? extends GrantedAuthority> authorities;

	private Collection<?> role;
	
	private String country;

	public UserPrincipal(Long id, String name, String username, String email, String password, String flag,
			Collection<? extends GrantedAuthority> authorities, Collection<?> role,  String country) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.flag = flag;
		this.authorities = authorities;
		this.role = role;
		this.country = country;
	}

	public static UserPrincipal create(NimaiMLogin user) {

		List<GrantedAuthority> authorities = new ArrayList<>();

		Set<String> roleList = user.getEmpCode().getNimaiMpUserRoleList().stream()
				.map(role -> new String(role.getRoleId().getRoleName())).collect(Collectors.toSet());
		 
		return new UserPrincipal(user.getLoginId(), user.getEmpCode().getEmpName(), user.getEmpCode().getEmpCode(),
				user.getEmpCode().getEmpEmail(), user.getPassword(), user.getFlag(), authorities, roleList, user.getEmpCode().getCountry());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	public String getFlag() {
		return flag;
	}

	public Collection<?> getRole() {
		return role;
	}

	public String getCountry() {
		return country;
	}

}
