package com.mycompany.helloboot.question;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycompany.helloboot.DataNotFoundException;

import lombok.RequiredArgsConstructor;

// 컨트롤러에서 리포지토리에 직접 접근하지 않고,
// 서비스를 통해서 접근하기 위해 만듦

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public List<Question> getList() {
		return this.questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		// 레포지토리를 이용해서 아이디를 이용해서 질문을 하나 찾아옴 (리턴타입 Optional)
		Optional<Question> oq = this.questionRepository.findById(id);
		// id가 일치하는 데이터가 존재하면 Question 객체로 리턴
		if(oq.isPresent())
			return oq.get();
		// 존재하지 않으면 직접 만든 예외를 발생시킨다.
		else
			throw new DataNotFoundException("question not found");
	}
}
