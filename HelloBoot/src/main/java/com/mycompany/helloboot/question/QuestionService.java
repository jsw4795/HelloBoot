package com.mycompany.helloboot.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mycompany.helloboot.DataNotFoundException;
import com.mycompany.helloboot.user.SiteUser;

import lombok.RequiredArgsConstructor;

// 컨트롤러에서 리포지토리에 직접 접근하지 않고,
// 서비스를 통해서 접근하기 위해 만듦

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public Page<Question> getList(int page) {
		// "createDate"의 내림차순으로 정렬하는 Sort객체를 추가한다.
		// (여러가지 정렬 조건이 있을 수 있기 때문에 List를 사용한다.
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		// page는 보여줄 페이지 번호이다.
		// 한 페이지에 10개의 데이터를 보여준다.
		// 해당 페이지의 데이터만 조회하도록 쿼리가 변경된다. (처리시간 단축효과)
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.questionRepository.findAll(pageable);
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
	
	public void create(String subject, String content, SiteUser user) {
		Question q = new Question();
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}
	
}
