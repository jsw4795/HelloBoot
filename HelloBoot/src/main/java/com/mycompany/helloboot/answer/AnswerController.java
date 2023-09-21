package com.mycompany.helloboot.answer;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.helloboot.question.Question;
import com.mycompany.helloboot.question.QuestionService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

	private final QuestionService questionService;
	private final AnswerService answerService;

	@PostMapping("/create/{id}")
	// @RequestParam 데이터타입 [input 태그의 name 속성]
	// --> form 에서 name이 일치하는 input의 내용(content)을 매개변수로 가져온다.
	public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
		if (content.trim().length() > 0) {
			Question question = this.questionService.getQuestion(id);
			// TODO : 답변을 DB에 저장한다
			this.answerService.create(question, content);
		}
		return "redirect:/question/detail/" + id;
	}

}
