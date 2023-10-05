package com.mycompany.helloboot.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mycompany.helloboot.answer.Answer;
import com.mycompany.helloboot.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

// 이번 프로젝트에서는 편의상 엔티티를 바로 사용하지만,
// 실제 프로젝트를 진행할 때는 DTO 클래스를 만들어서 사용하는 것이 권장된다.
// (서비스를 통해서 한번 걸러주는 역할을 하는듯)

@Getter
// 앤티티에는 setter 메서드를 작성하지 않는게 권장되나, 복잡도를 낮추기 위해 사용
@Setter
@Entity // 엔티티란 데이터베이스 테이블과 매핑되는 자바 클래스다 (이 클래스가 엔티티라고 선언)
public class Question {
	
	// 프라이머리키(고유키)로 설정
	@Id
	// 1씩 자동으로 증가 (strategy = ~... 이 컬럼만의 독립적인 시퀀스를 생성한다)
	// strategy를 안쓰면 이 @GeneratedValue를 사용하는 모든 객체에서 생성할 떄 마다 1씩 증가된 값이 사용됨
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 컬럼이다(길이는 200으로 설정)
	@Column(length = 200)
	private String subject;
	
	// 컬럼이다(글자 수 제한 없음)
	@Column(columnDefinition = "TEXT")
	private String content;
	
	// createDate처럼 카멜 표기법으로 적으면, 실제 DB에 컬럼명으로는 create_date로 저장됨
	private LocalDateTime createDate;
	
	// 하나의 엔티티에서 여러개의 엔티티를 참조한다. 때문에 데이터 타입은 리스트다
	// mappedBy - 참조한 엔티티의 속성명 (Answer에서 question 속성명에서 Question을 참조하고있다)
	// cascade - cascade 속성을 설정한다 (여기서는 부모를 지우면 자녀도 다 지워지는 걸로)
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	// 엔티티 클래스는 @Column이 없어도 컬럼으로 인식한다
	// 컬럼으로 쓰기 싫을 때만 @Transient 를 사용한다.
	
	// 여러개의 엔티티에서 하나의 엔티티를 참조한다.
	// 참조 당하는 테이블의 id 값이 저장된다.
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	// @manyToMany 로 설정하면 테이블을 새로 만들어서 id를 연결해서 관리해준다
	@ManyToMany
	Set<SiteUser> voter;
	
}
