package com.mycompany.helloboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* Spring Security는 기본적으로 로그인 되지 않은 사용자의 접근은 차단하기 때문에
   그 설정을 풀어주는 클래스다. (시큐리티 설정 클래스)*/

// 스프링 환경 설정 파일임을 의미한다.
@Configuration
// 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
// (내부적으로 SpringSecurityFilterChain이 동작하여 URL필터가 적용된다.)
@EnableWebSecurity
// QuestionController, AnswerController에서 사용한 @PreAuthorize 를 사용하기 위해 필요한 애너테이션이다.
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// 모든 페이지에서 로그인 안해도 허가
		.authorizeHttpRequests(
				(authorizeHttpRequests) -> 
				authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		// h2-console 하위의 URL에서는 csrf 기능 끄기
		.csrf((csrf) -> 
				csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
		/* clickjacking 공격을 막기 위해 X-Frame-Options을 사용하는데 기본값은 DENY 인듯
		 * h2-console 가 프레임으로 이루어져서 안보여서 설정 변경 
		 * X프레임 옵션 : DENY - 프레이밍 금지, SAMEORIGIN - 외부 사이트에 의한 프레이밍 금지, ALLOW-FROM origin - 특정 사이트에서 프레이밍 허용*/
		.headers((header) ->
				header.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
		/* .formLogin 메서드는 로그인 설정을 담당하는 부분이다
		 * 	로그인 페이지의 url 은 "/user/login이고, 로그인 성공 시 이동하는 디폴트 url 은 "/" 이다. */
		.formLogin((formLogin) -> 
				formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
		// 로그아웃 구현(?)
		.logout((logout) -> 
				logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					  .logoutSuccessUrl("/")
					  .invalidateHttpSession(true))	// 로그아웃 시 사용자 세션 삭제
		;
		return http.build();
	}
	
	/* Bean이란 IoC(제어의 역전) 컨테이너가 관리하는 자바 객체다. 
	 (개발자가 직접 new 를 이용해서 객체를 생성하지 않음)
	 
	 @Bean 은 @Configuration 이 달린 클래스에서만 사용 가능
	 (@Component(Bean으로 만들어준다고 함) 는 @Configuration 없이 사용가능)
	
	 Bean으로 만들어서 비밀번호를 사용하는 모든 클래스에 다 만드는게 아닌 하나만 만들어서 씀
	 (나중에 인코딩 방식 바꿀 때 코드 하나만 바꿔주면 됨)*/
	@Bean
	PasswordEncoder passwordEncoder() { // PasswordEncoder 는 BCryptPasswordEncoder의 부모클래스
		return new BCryptPasswordEncoder();
	}
	
	// AuthenticationManager 는 스프링 시큐리티의 인증을 담당한다.
	// 사용자 인증 시, UserSecurityService와 PasswordEncoder를 사용한다. (이 두 가지를 구현해야 한다)
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
