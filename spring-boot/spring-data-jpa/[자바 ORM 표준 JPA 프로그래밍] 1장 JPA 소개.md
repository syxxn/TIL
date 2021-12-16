## [자바 ORM 표준 JPA 프로그래밍] 1장 JPA 소개

+ JPA는 실행 시점에 자동으로 SQL을 만들어서 실행한다.

#### SQL을 직접 다루면 발생하는 문제점

+ 진정한 의미의 계층 분할이 어렵다.

  > DAO를 열어서 어떤 SQL이 실행되고, 어떤 객체들이 함께 조회되는지 확인해야 한다.

+ 엔티티를 신뢰할 수 없다.

  > SQL에 의존적으로 개발해야 하기 때문이다.

+ SQL에 의존적인 개발을 피하기 어렵다.

<br>

#### JPA가 제공하는 API

+ 저장 

  ```java
  jpa.persist(member);
  ```

+ 조회

  ```java
  Member member = jpa.find(Member.class, memberId);
  ```

+ 수정

  JPA는 별도의 수정 메소드를 제공하지 않는다. 

  객체를 조회해서 값을 변경만 하면, 트랜잭션을 커밋할 때 데이터베이스에 적절한 UPDATE SQL이 전달된다.

  ```java
  Member member = jpa.find(Member.class, memberId);
  member.setName("이름");
  ```

+ 연관된 객체 조회

  ```java
  Member member = jpa.find(Member.class, memberId);
  Team team = member.getTeam();
  ```

<br>

#### 패러다임의 불일치

+ 객체와 관계형 데이터베이스가 서로 지향하는 목적이 달라 기능과 표현하는 방법이 다른 것을 말한다.

+ 만약 Item 클래스를 상속받은 Album 클래스가 있다고 하자. 

  > ```java
  > jpa.persist(album);
  > ```
  >
  > 위의 명령어를 실행하면 JPA는 `INSERT INTO ITEM ...`, `INSERT INTO ALBUM ...` 명령어를 따로 실행하여 저장한다. 
  >
  > ```java
  > Album album = jpa.find(Album.class, albumId);
  > ```
  >
  > 위의 명령어를 실행하면, JPA는 ITEM과 ALBUM 두 테이블을 조인해서 조회 명령어를 실행한다.

+ 객체 그래프 탐색이란, 참조관계를 get메소드를 사용하여 탐색하는 것이다.

  ```
  member.getTeam().getOffice()
  ```

+ 지연로딩이란, 실제 객체를 사용하는 시점까지 데이터베이스 조회를 미루는 것을 말한다.

<br>

#### JPA

+ 자바 진영의 ORM(Object Relational Mapping) 기술 표준(인터페이스 느낌)으로서, 애플리케이션과 JDBC 사이에서 동작한다.
+ 가장 대중적인 ORM 프레임워크는 Hibernate(JPA의 구현체)이다.
+ JPA를 제대로 이해하지 못하고 사용하면 N+1 문제가 발생할 수 있다.
  + N+1 문제는, 예를 들어 회원 100명을 조회하는 SQL을 실행시킨 후에 각 회원의 주문 내역을 따로 조회하는 것을 말한다.