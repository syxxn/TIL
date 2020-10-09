## Config Package



### @Configuration

: 클래스가 하나 이상의 `@Bean` 메소드를 제공하고 스프링 컨테이너가 <u>Bean</u> 정의를 생성하고 런타임 시 그 Bean들이 요청들을 처리할 것을 선언하게 된다.

> Bean : 가상 메모리/ 동일한 기능들을 빈에 넣어두고 필요할 때마다 꺼내씀(재활용) 
>
> --> 정보를 가져오는 속도가 빨라지며, 자원 낭비를 최소화할 수 있다

: 스프링 `IoC Container`에게 해당 클래스를 Bean 구성 Class임을 알려주는 것이다

```java
@Configuration
public class ApplicationConfig {
 
    @Bean
    public MyClass getService() {
	// Do something.
    }
	
    @Bean
    public MyClass1 getService() {
	// Do something.
    }
}
```



### @Value

: 공통 값들을 정의해 놓은 파일에 접근하여 원하는 데이터를 읽어와 사용함

> 변수와 메소드의 인수에 기본값을 할당하는 데 사용

```java
@Component
public class User {
    @Value("${custom.myname}")
    //'$'를 사용하여 프로퍼티 값을 그대로 주입해 준 것
    //SpEL(Spring Expression Language) : 객체들의 정보를 질의 하거나 조작하여 어떤 값을 표현할 수 있는 강력한 표현 언어
    private String name;

    @Value("${custom.myage}")
    private int age;

    @Value("${custom.mytel}")
    private String tel;
}
```

스프링 컨테이너 내부에서는 빈들을 모두 등록할 때 @value() 안의 내용에 맞는 값을 `application.properties`에서 찾아 넣어주게 됩니다.

### @Bean

: `Spring(IoC) Container`에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션이다

: **개발자가 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들려할 때** 사용된다

```java
@Configuration
public class ApplicationConfig{
	@Bean
	public ArrayList<String> array(){
		return new ArrayList<String>();
	}
}
```





### @Component

: `Spring(IoC) Container`에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션이다

: **개발자가 직접 작성한 Class를 Bean으로 등록하기 위한** 어노테이션이다

```java
@Component(value="mystudent")
public class Student{
	public Student(){
		System.out.println("hi");
	}
}
```



### @Order

: 스프링 빈의 순서를 정할 수 있다

```java
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringLogger implements Filter {
```



### @Override

: 해당 메소드가 부모클래스의 메소드를 상속받았다는 것을 명시적으로 선언함

```java
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityContifurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
	}
}
```



### @EnableRedisRepositories

: <u>redis</u> 디비 연결을 설정할 때 쓰이는 어노테이션

> 메모리 기반의 "키" : "값" 구조 데이터 관리 시스템이며, 모든 데이터를 메모리에 저장하고 조회하기에 빠른 read, write 속도를 보장하는 **비 관계형 데이터베이스**(임시 DB)이다.

