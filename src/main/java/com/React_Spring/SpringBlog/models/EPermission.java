package com.React_Spring.SpringBlog.models;

public enum EPermission {
	//permission to access admin specific api's
	ADMIN_READ("ADMIN_READ"),
	ADMIN_WRITE("ADMIN_WRITE"),
	
	//permission to create blogs and write posts
	AUTHOR_READ("AUTHOR_READ"),
	AUTHOR_WRITE("AUTHOR_WRITE"),
	
	//permission to change user data
	USER_READ("USER_READ"),
	USER_WRITE("USER_WRITE");
	
	private String permission;
	
	EPermission(String permission){
		this.setPermission(permission);
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
