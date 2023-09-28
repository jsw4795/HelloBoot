package com.mycompany.helloboot.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	// get으로 들어오면 그냥 회원가입 폼을 열어줌
	// 타임리프에서 유저생성폼을 사용할거라 get 에서도 매개변수로 전해줌
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	// post로 들어오면 서비스 객체로 db에 저장 시도
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) 
			return "signup_form";
		
		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			// BindingResult.rejectValue(필드명, 오류코드, 에러메세지) - 오류 발생시키는 메서드
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		try {
		this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1()).equals(bindingResult);
		} 
		// 유니크 제약조건을 충족하지 못하면 DataIntegrityViolationException 예외가 발생함
		catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			// BindingResult 객체에 (오류코드, 오류메세지) 인 오류를 등록
			bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
	
}
