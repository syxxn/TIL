# JPA

+ JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다
+ JPA를 사용하면, SQL과 데이터 중심의 설게에서 객체 중심의 설계로 패러다임을 전환할 수 있다
+ JPA를 사용하면 개발 생산성을 크게 높일 수 있다.



build.gradle에 라이브러리 추가

`implementation 'org.springframerok.boot:spring-boot-starter-data-jpa'`



`spring-boot-starter-data-jpa`는 내부에 jdbc 관련 라이브러리를 포함한다. 따라서 jdbc는 제거해도 된다.



스프링 부트에 JPA 설정 추가 `resources/application.properties`

```
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.show-sql=true -- JPA가 생성하는 SQL을 출력한다
spring.jpa.hibernate.ddl-auto=none --JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 `none`을 사용하면 해당 기능을 끈다
```



### Spring Data JPA

> Spring Data JPA는 JPA를 편리하게 사용하도록 도와주는 기술이다. 

**실무에서 관계형 데이터베이스를 사용한다면 Spring Data JPA는 선택이 아니라 필수이다.**



스프링 데이터 JPA는 

+ 인터페이스를 통한 기본적인 CRUD를 제공한다
+ `findByName()` , `findByEmail()`처럼 메소드 이름만으로 조회 기능 제공한다
+ 페이징 기능을 자동으로 제공한다



실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 동적 쿼리는 `Querydsl`이라는 라이브러리를 사용하면 된다. Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다. 

이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, 앞서 학습한 스프링 JdbcTemplate을 사용하면 도니다.