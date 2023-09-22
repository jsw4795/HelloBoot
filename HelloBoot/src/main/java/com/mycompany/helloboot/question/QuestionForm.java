package com.mycompany.helloboot.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// form 태그에서 넘어갈때 각 항목의 제한사항을 검사하는 클래스

@Getter
@Setter
public class QuestionForm {
	
	@NotEmpty(message = "제목은 필수항목입니다.")
	@Size(max = 200)
	private String subject;
	
	@NotEmpty(message = "내용은 필수항목입니다.")
	private String content;
}
