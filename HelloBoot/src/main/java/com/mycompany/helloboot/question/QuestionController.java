package com.mycompany.helloboot.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

/* 스프링의 의존성 주입(Dependency Injection) 방식 3가지
1. 속성에 @Autowired 애너테이션을 적용하여 객체를 주입하는 방식
2. 생성자를 작성하여 객체를 주입하는 방식 (권장하는 방식)
3. Setter 메서드를 작성하여 객체를 주입하는 방식 (메서드에 @Autowired 애너테이션 적용이 필요하다.)
*/

// 이 클래스의 맵핑 앞에 "/question"이 붙는다고 생각하면 된다. (URL 프리픽스)
@RequestMapping("/question")
// lombok의 애너테이션이다
// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어준다.
// 스프링 의존선 주입 규칙에 의해 questionRepository 객체가 자동으로 주입된다.
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;
	
	@GetMapping("/list")
	// Model 객체가 컨트롤러 메소드에 매개변수로 지정되기만 하면 스프링부트가 자동으로 Model 객체를 생성
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		// model에 "qustionList"라는 이름으로 DB에서 검색한 데이터 저장
		// 이것은 HTML에서 타임리프 템플릿으로 불러올 것이다.
		model.addAttribute("questionList", questionList);
		// src/main/resources/templates 에 있는 question_list.html을 찾아서 리턴한다
		return "question_list";
	}
	
	// ~/detail/숫자 가 오면 실행되는 메소드다
	// QuestionService를 이용해서 질문객체를 하나 얻어서 Model에 저장한 후 question_detail.html을 리턴한다.
	@GetMapping(value = "/detail/{id}")
	// 변화하는 값을 얻을 때에는 getMapping 애너테이션에 있는
	// {변수명}의 변수명과 일치시켜서 @PathVariable("변수명") 을 사용해야 한다.
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		// question이라는 이름으로 받아온 Question 객체 전달
		model.addAttribute("question", question);
		return "question_detail";
	}
}
