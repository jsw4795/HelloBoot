package com.mycompany;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// 레포지토리로 만들기 위해 JpaRepository<[대상 엔티티 타입], [이 엔티티의 PK 속성 타입]>를 상속함
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	// 잠깐 써보니까 DAO라고 생각하면 될듯
	
	// 기본으로 제공하지 않는 메소드기 때문에 만들어서 사용한다
	// 이건 리턴값을 바로 받는 객체로 써도 되네..? findById()는 Oprional로 받고 .get()으로 빼서 쓰던데
	Question findBySubject(String subject);
	
	// subject와 content 컬럼 두 가지를 AND 로 검색한다
	Question findBySubjectAndContent(String subject, String content);
	
	// JPA가 자동으로 메서드 이름을 분석해서 쿼리를 만들고 실행해준다.
	// 즉, findBy + 찾을 엔티티 속성명의 이름으로 메소드를 만들기만 하면 알아서 해준다 짱인데?
	
	// 여기서 해본거 말고 내가 알고있는 SQL 문법을 사용해서 검색하는 메소드를 만들 수 있다
	// 리턴값이 여러개일 때는 List<> 를 사용해야 한다
}
