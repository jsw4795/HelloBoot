package com.mycompany.helloboot.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// 엔티티 클래스를 만들면 DB에 자동으로 테이블이랑 컬럼이 추가된다.(편하긴하네)

@Getter
@Setter
@Entity
public class SiteUser { // 스프링 시큐리티에 이미 User라는 클래스가 있어서 구분하기 쉽게 SiteUser로 함
	
	@Id
	// 자동으로 생성 (프라이머리 키)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 컬럼 제약사항 설정 (유니크)
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
}
