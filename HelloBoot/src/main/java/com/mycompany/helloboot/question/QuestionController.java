package com.mycompany.helloboot.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.mycompany.helloboot.answer.AnswerForm;
import com.mycompany.helloboot.user.SiteUser;
import com.mycompany.helloboot.user.UserService;

import jakarta.validation.Valid;
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
	private final UserService userService;
	
	@GetMapping("/list")
	// Model 객체가 컨트롤러 메소드에 매개변수로 지정되기만 하면 스프링부트가 자동으로 Model 객체를 생성
	// get방식으로 요청된 URL에서 page값이 있으면 가져오고 아니면 0으로 설정 (첫 페이지가 0이다)
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		// model에 "paging"라는 이름으로 DB에서 검색한 데이터 저장
		// 이것은 HTML에서 타임리프 템플릿으로 불러올 것이다.
		model.addAttribute("paging", paging);
		// src/main/resources/templates 에 있는 question_list.html을 찾아서 리턴한다
		return "question_list";
	}
	
	// ~/detail/숫자 가 오면 실행되는 메소드다
	// QuestionService를 이용해서 질문객체를 하나 얻어서 Model에 저장한 후 question_detail.html을 리턴한다.
	@GetMapping(value = "/detail/{id}")
	// 변화하는 값을 얻을 때에는 getMapping 애너테이션에 있는
	// {변수명}의 변수명과 일치시켜서 @PathVariable("변수명") 을 사용해야 한다.
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		// question이라는 이름으로 받아온 Question 객체 전달
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	// 로그인이 되어있지 않으면 로그인 페이지로 이동시킨다.
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	/*
	 * question_form.html에 questionForm 이라는 오브젝트로 확인하는 코드가 들어갔기 때문에 get방식에서도 일단
	 * 오브젝트를 전달해줘야 오류가 발생하지 않는다.
	 * (매개변수로 바인딩한 객체는 Model객체로 전달하지 않아도 템플릿에서 사용 가능하다)
	 */
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	/*
	 * @Valid 를 폼 클래스 앞에 붙이면 폼클래스안에 있는 검증 기능이 동작한다.
	 * 
	 * (스프링 프레임워크의 바인딩 기능)
	 * subject와 content 항목을 지닌 폼이 전송되면 QuestionForm의 속성이 자동으로 바인딩된다. 
	 * 
	 * BindingResult 는 @Valid 로 검증이 수행된 결과를 의미하는 객체다.
	 * -- BindingResult는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다. (아니면 작동안함)
	 */	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult,
			 Principal principal) {
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		// @Valid의 검증 결과에 문제가 있다면 question_form.html을 리턴
		if(bindingResult.hasErrors())
			return "question_form";
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		// 템플릿에서 막아놓긴 했는데 이상한 방법으로 접근하는 거를 한 번 더 막는 듯
		if(!question.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		// 현재 질문의 제목과 내용이 채워져서 화면에 보이도록 QuestionForm에 담아서 보내줌
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
								Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors())
			return "question_form";
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/detail/" + id;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		this.questionService.delete(question);
		// 루트 디렉토리로 보내면 /question/list로 감
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return "redirect:/question/detail/" + id;
	}
}
