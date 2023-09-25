package com.mycompany;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.mycompany.helloboot.HelloBootApplication;
import com.mycompany.helloboot.question.QuestionService;


// 테스트코드
@SpringBootTest
// 어플리케이션 클래스를 연결해줌 (자동으로 못찾아서 추가)
@ContextConfiguration(classes = HelloBootApplication.class)
class HelloBootApplicationTests {

	// 스프링이 대신 객체를 생성해서 변수에(?) 주입해준다
	// @Autowired보다는 생성자를 통한 객체 주입방식이 권장된다.
	// 지금은 테스트이므로 이렇게 함
	@Autowired
	private QuestionService questionService;
	
	
	
	@Test
	void testJpa() {
		for(int i = 0; i <= 300; i++) {
			String subject = "테스트 데이터 입니다 : " + i;
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}
}
	
	
	
	
	
//	// 테스트코드에서 DB관련 동장 한 번 하고 접속이 끊기는 것을 방지
//	// 메소드가 종료될 때까지 DB 세션이 유지된다
//	@Transactional
//	@Test
//	void testJpa() {
//		// 기본 테스트 코드에서는 findById를 실행하고 DB와 연결이 끊긴다.
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		// Question 객체에서 Answer들 List를 가져오는 메소드
//		List<Answer> answerList = q.getAnswerList();
//		
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
//	}
	
	
	// 질문에 대한 답변을 만들어서 DB에 저장
//	@Test
//	void testJpa() {
//		// 2번 질문에 답변을 만들기 위해 id가 2인 질문 가져오기
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		Answer a = new Answer();
//		// Answer에 속성값 설정
//		a.setContent("네 자동으로 생성됩니다.");
//		// 어느 질문에 대한 답변인지를 알기위해 Question 객체를 가진다
//		a.setQuestion(q);
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);
//	}
	
	// 데이터 가져와서 수정해서 DB UPDATE 테스트
//	@Test
//	void testJpa() {
//		// id가 1인거를 가져와서
//		Optional<Question> oq = this.questionRepository.findById(1);
//		// 가져온 거가 존재하면 테스트 패스
//		assertTrue(oq.isPresent());
//		// Question 변수에 Optional에 있는 객체 저장
//		Question q = oq.get();
//		// q 객체 제목 변경
//		q.setSubject("수정된 제목");
//		// 변경된 객체 DB에 저장
//		this.questionRepository.save(q);
//	}
	
	
	
	// Question 테이블에서 subject 컬럼의 내용으로 조회해서 내용 테스트
//	@Test
//	void testJpa() {
//		// subject 내용으로 찾는 메소드는 기본 제공되지 않기 때문에 QuestionRepository 클래스에서 만들어서 사용
//		// 만들 때 이건 리턴타입을 Question 으로 만듦
//		Question q = questionRepository.findBySubject("ssb가 무엇인가요?");
//		assertEquals(1, q.getId());
//	}	
	
	
	// id 값으로 찾아서 값이 일치하는지 테스트
//	@Test
//	void testJpa() {
//		// findById의 리턴타입은 Optional<> 이다
//		Optional<Question> oq = this.questionRepository.findById(1);
//		// isEmpty()의 반대
//		if(oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("ssb가 무엇인가요?", q.getSubject());
//		}
//	}	
	
	
	
	
	// DB에 Question 테이블에 있는 데이터 전체를 가져와서 '데이터 개수', '데이터 일치 여부' 검사
//	@Test
//	void testJpa() {
//		// repository에서 제공하는 findAll() 메서드 : 데이터 전체 조회
//		List<Question> all = this.questionRepository.findAll();
//		// 테스트를 위한 JUnit에서 제공하는 메소드 : (기대값, 실제값) 일치하면 성공
//		assertEquals(2, all.size());
//		
//		Question q = all.get(0);
//		assertEquals("ssb가 무엇인가요?", q.getSubject());
//	}
	
	// Question 객체를 만들어서 DB에 저장하는 테스트 코드
//	@Test
//    void testJpa() {        
//        Question q1 = new Question();
//        q1.setSubject("sbb가 무엇인가요?");
//        q1.setContent("sbb에 대해서 알고 싶습니다.");
//        q1.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(q1);  // 첫번째 질문 저장
//
//        Question q2 = new Question();
//        q2.setSubject("스프링부트 모델 질문입니다.");
//        q2.setContent("id는 자동으로 생성되나요?");
//        q2.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(q2);  // 두번째 질문 저장
//    }
