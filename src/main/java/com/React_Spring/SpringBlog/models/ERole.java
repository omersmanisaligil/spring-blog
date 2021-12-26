package com.React_Spring.SpringBlog.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public static ERole findERole(String roleName){
	    List<ERole> roleList = new ArrayList<>(Arrays.asList(ROLE_ADMIN,ROLE_USER,ROLE_AUTHOR));
	    ERole result = null;
	    for(ERole eRole:roleList){
	        if(eRole.getRoleName().equals(roleName)){
	            result = eRole;
	            break;
	        }
	    }
	    return result;
	}
}
