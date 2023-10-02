package com.mycompany.helloboot.user;

import lombok.Getter;

// 이번 프로젝트에서는 권한에 따른 기능 제한은 하지 않는다.

@Getter
public enum UserRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	UserRole(String value){
		this.value = value;
	}
	
	private String value;
}
