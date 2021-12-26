package com.React_Spring.SpringBlog.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="PERMISSIONS")
public class Permission {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="PERMISSION_ID")
	private int permission_id;

	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private EPermission name;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="permissions")
	private Set<Role> roles=new HashSet<Role>();
	
	public int getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(int permission_id) {
		this.permission_id = permission_id;
	}

	public EPermission getName() {
		return name;
	}

	public void setName(EPermission name) {
		this.name = name;
	}
	
	
}
