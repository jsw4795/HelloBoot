package com.mycompany.helloboot.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/*
 * 스프링 시큐리티의 로그인 기능에서 핵심적인 클래스이다.
 * UserSecurityService 는 UserDetailsService를 구현해야 하고, 
 * 이는 loadUserByUsername(String) 메서드의 구현을 강제한다.
 */

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
		if(_siteUser.isEmpty())
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		
		SiteUser siteUser = _siteUser.get();
		
		// 유저의 권한을 저장해서 넘겨줄 리스트
		List<GrantedAuthority> authorities = new ArrayList<>();
		// username이 admin인 경우에만 ADMIN 권한을 추가하고, 아닌 경우 USER 권한을 추가함
		if("admin".equals(username))
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		else
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		
		// 아이디, 비밀번호, 권한을 담은 User 객체를 리턴한다.
		// (스프링 시큐리티가 입력된 비밀번호와, User 객체의 비밀번호가 일치하는지 확인해 준다.
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
	
}
