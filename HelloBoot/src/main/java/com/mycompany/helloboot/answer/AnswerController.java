package com.mycompany.helloboot.answer;

import java.security.Principal;

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

import com.mycompany.helloboot.question.Question;
import com.mycompany.helloboot.question.QuestionService;
import com.mycompany.helloboot.user.SiteUser;
import com.mycompany.helloboot.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;

	// 로그인된 상태에서만 접근 허가 (아니면 로그인 페이지로)
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	/*
	 * @RequestParam 데이터타입 [input 태그의 name 속성] --> form 에서 name이 일치하는 input의
	 * 내용(content)을 매개변수로 가져온다.
	 * (@Valid 사용해서 검사하면서 @RequestParam대신 AnswerForm을 사용해서 데이터를 가져옴)
	 * 
	 * Principal 객체를 매개변수로 가져오면서 시큐리티에서 현재 접속된 사용자를 알 수 있다
	 * (principal.getName() 으로 로그인된 사용자의 아이디를 알 수 있다
	 */
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult, Principal principal) {

		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			// question_detail.html은 Question 객체를 필요로 해서 Model을 이용해서 넘겨줌
			model.addAttribute("question", question);
			return "question_detail";
		}
		Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);

		return "redirect:/question/detail/" + id + "#answer_" + answer.getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		answerForm.setContent(answer.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@PathVariable("id") Integer id, @Valid AnswerForm answerForm, 
							BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors())
			return "answer_form";
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		this.answerService.modify(answer, answerForm.getContent());
		// 답변의 id 가 아니라 질문의 id로 가야 하므로
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(@PathVariable("id") Integer id, Principal principal) {
		Answer answer = this.answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		this.answerService.delete(answer);
		
		return "redirect:/question/detail/" + answer.getQuestion().getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
	}

}
