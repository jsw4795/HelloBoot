package com.mycompany.helloboot.user;

import org.springframework.data.jpa.repository.JpaRepository;

/* Repository : 엔티티에 의해 생성된 테이블에 접근하는 메서드들을 사용하기 위한 인터페이스다.
 * 
 * 생성규칙 : 인터페이스, JpaRepository를 상속받고, <엔티티, 이 엔티티의 PK의 데이터 타입>
 */
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	/*
	 * 기본으로 생성되는 메서드 (T는 리포지터리의 엔티티)
	 * 
	 * <S extends T> S save(S entity)
	 *  - 주어진 엔티티를 저장한다.
	 * Iterable<T> findAll()
	 *  - 모든 엔티티를 반환한다.
	 * Optional<T> findById(ID primaryKey)
	 *  - 주어진 ID값으로 식별되는 엔티티를 반환한다.
	 * void delete(T entity)
	 *  - 주어진 엔티티를 삭제한다.
	 * boolean existsById(ID primaryKey)
	 *  - 주어진 ID를 가진 엔티티가 있는지 여부를 반환한다.
	 * Long count()
	 *  - 엔티티 수를 반환한다.
	 *  
	 *  
	 *  새로운 메서드 추가
	 *  
	 *  findBy[엔티티속성명]([속성 데이터 타입] [속성])
	 *   - 엔티티속성명 으로 엔티티를 찾는다.
	 *   
	 *  findBy[속성명1]And[속성명2](데이터타입1 속성명1, 데이터타입2 속성명2)
	 *   - 두 가지 속성명으로 엔티티를 찾는다.
	 *   
	 *  findBy[속성명]like(String 속성명)
	 *   - 매개변수가 속성명에 포함된 엔티티를 찾는다.
	 *   ** 매개변수 는 %문자열% 이런 식으로 해야한다! (% 위치는 자유)
	 */
}
