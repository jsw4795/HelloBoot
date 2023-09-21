package com.mycompany.helloboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@GetMapping("/index")
	@ResponseBody // URL 요청에 대해 문자열을 리턴하겠다
	public String index() {
		return "안녕하세요";
	}
	
	// 아무런 경로 지정없이 요청이 들어오는 경우를 받는 매핑이다
	@GetMapping("/")
	public String root() {
		
		// redirect:<URL> - 완전히 새로운 URL로 요청이 된다.
		// forward:<URL> - 기존 요청 값들이 유지된 상태로 URL이 전환된다. 
		// (브라우저에서 보이는 URL이 바뀌지 않는다.)
		
		// '/question/list' URL로 리다이렉트 시킨다.
		return "redirect:/question/list";
	}
}
