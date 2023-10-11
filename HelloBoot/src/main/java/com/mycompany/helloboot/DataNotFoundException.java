package com.mycompany.helloboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 이 애너테이션으로 인해 DataNotFoundException 발생시, 404 오류가 나타남
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
	
	// 직렬화 역직렬화 할 때 필요한건데 오류를 방지하기 위해 그냥 1L로 설정한듯
	private static final long serialVersionUID = 1L;
	public DataNotFoundException(String message) {
		super(message);
	}
}
