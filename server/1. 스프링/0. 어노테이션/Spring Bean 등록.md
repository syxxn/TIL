# Spring Bean 등록



## Bean 등록



### @Bean

개발자가 컨트롤할 수 없는 외부 라이브러리 Bean으로 등록하고 싶은 경우 사용

(메소드로 return 되는 객체를 Bean으로 등록)



### @Component

개발자가 직접 컨트롤할 수 있는 클래스(직접 만든)를 Bean으로 등록하고 싶은 경우

(선언된 Class를 Bean으로 등록)

> @Controller, @Service, @Repository 등은 @Component를 비즈니스 레이어에 따라 명칭을 달리 지정해준 것이다.



## Bean 주입



### @Recource

이름으로 참조할 Bean을 검색하여 주입한다. (Java 표준)



### @Autowired

타입으로 참조할 Bean을 찾아 주입한다. (spring 표준)