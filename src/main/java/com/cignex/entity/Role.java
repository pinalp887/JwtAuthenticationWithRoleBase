package com.cignex.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "role_id")
	private int roleId;

	@Column(name = "role")
	private String role;
	@ManyToMany(mappedBy = "roles")
	private Set<User> user;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Set<User> getUser() {
		return user;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(int roleId, String role, Set<User> user) {
		super();
		this.roleId = roleId;
		this.role = role;
		this.user = user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}
	
	
}
