## Spring IoC

자바 기반으로 애플리케이션을 개발하던 초기에는 자바 객체를 생성하고 객체 간의 의존관계를 연결시키는 등의 제어권을 개발자가 직접 가지고 있었다고 한다.

```java
<과거>

public class A {
	private B b;
	
	public A() {
		this.b = new B();
	}
	
	public void getB() {
		return b;
	}
}
```

```java
<현재>

@RequiredArgsConstructor
@Component
public class A {
	private final B b;
	
	public void getB() {
		return b;
	}
}
```

<br>

아래 코드와 같이 개발자가 코드에 new 키워드를 선언하지 않아도 컨테이너가 객체의 생성부터 생명주기의 관리까지 모든 제어권을 갖고 있는 것을 IoC(Inversion of Contol)이라고 한다. 객체 간의 의존도를 줄이기 때문에 유연한 코드를 작성할 수 있다.

<br>

#### 구현체

+ BeanFactory

  IoC 컨테이너의 기능을 정의하고 있는 인터페이스로서, Bean의 생성 및 의존성 주입, 생명주기 관리 등의 기능을 제공한다. 대표적으로 getBean() 메소드를 제공한다.

+ ApplicationContext

  BeanFactory 인터페이스를 상속받는 ApplicationContext는 BeanFactory가 제공하는 기능 외에 AOP, 메시지 처리 등의 기능을 제공한다. 특별한 경우를 제외하고는 더 많은 기능을 제공하는 ApplicationContext를 사용하는 것이 바람직하다고 한다.

> SpringBoot main 클래스에 붙어있는 `@SpringBootApplication`은 기본적으로 `@ComponentScan`과 `@Configuration`을 가지고 있기 때문에 이 자체가 설정 파일이다. 따라서 개발자가 직접 ApplicationContext를 설정하지 않아도 된다.

<br>

#### 설정 메타 정보

설정 메타 정보란, 컨테이너가 관리하는 객체인 빈을 어떻게 만들고 동작하게 할 것인가에 대한 정보를 말한다. Spring Container는 자바 코드, XML, Groovy 등 다양한 형식의 설정 정보를 받아들일 수 있도록 유연하게 설계되어 있다.

+ 어노테이션 기반 자바 코드 설정

  일반적으로 사용하고 있는 클래스 단에 @Configuration을 붙이고 필드 또는 메소드에 @Bean을 붙이는 방식이다. 1개 이상의 빈을 제공하는 클래스의 경우 @Configuration을 반드시 명시해야 한다.

+ XML 기반의 스프링 빈 설정

  어노테이션 기반으로 메타 정보를 설정할 때와 키워드는 비슷하다.

  하지만 최근에는 잘 사용하지 않는 방법이라고 한다.