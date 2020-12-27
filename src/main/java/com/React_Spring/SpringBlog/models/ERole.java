package com.React_Spring.SpringBlog.models;

public enum ERole {
	ROLE_USER("USER"),
	ROLE_AUTHOR("AUTHOR"),
	ROLE_ADMIN("ADMIN");
	
	private String roleName;

	private ERole(String roleName) {
		this.roleName=roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
