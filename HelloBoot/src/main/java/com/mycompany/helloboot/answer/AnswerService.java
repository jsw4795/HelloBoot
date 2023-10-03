package com.mycompany.helloboot.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mycompany.helloboot.question.Question;
import com.mycompany.helloboot.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	
	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
	}

}
