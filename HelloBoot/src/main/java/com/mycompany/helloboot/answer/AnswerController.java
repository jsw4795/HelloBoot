package com.mycompany.helloboot.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.helloboot.question.Question;
import com.mycompany.helloboot.question.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;

	@PostMapping("/create/{id}")
	/*
	 * @RequestParam 데이터타입 [input 태그의 name 속성] --> form 에서 name이 일치하는 input의
	 * 내용(content)을 매개변수로 가져온다.
	 * (@Valid 사용해서 검사하면서 @RequestParam대신 AnswerForm을 사용해서 데이터를 가져옴)
	 */
	public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm,
			BindingResult bindingResult) {

		Question question = this.questionService.getQuestion(id);
		if(bindingResult.hasErrors()) {
			// question_detail.html은 Question 객체를 필요로 해서 Model을 이용해서 넘겨줌
			model.addAttribute("question", question);
			return "question_detail";
		}
		this.answerService.create(question, answerForm.getContent());

		return "redirect:/question/detail/" + id;
	}

}
