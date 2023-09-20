package com.mycompany;

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
}
