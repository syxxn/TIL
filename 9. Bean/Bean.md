# Bean

static은 인스턴스를 한번만 생성할 수 있고, static 선언을 하지 않았다면 객체를 생성할 때마다 사용할 수 있다.

이때 객체를 계속해서 생성하다보면 무리가 갈 수 있는데, 이것을 막기 위해서 Bean을 사용한다



### Component

+ 빈에 들어가 있는 걸 구분하고, 빈에 등록하기 위해 사용한다

  `@Service` ,`@Repository`, `@RestController` -> 빈 생성 -> DI 

  > Bean은 **개별적(메소드)으로 주입하기 위해** 사용하는 것이고, 
  >
  > Component는 주입하고 싶은 **객체가 클래스 단위**일 때 사용한다

+ `@Component`라는 어노테이션이 붙은 클래스들은 Spring의 contrainer가 알아서 Spring Bean 객체로 등록하고 생성한다



____



## Bean

: 자주 사용하는 객체를 `Singleton 객체`로 생성해놓고 어디서든 불러서 쓸 수 있는 것(콩)

> 컨테이너 안에 이 빈, 저 빈이 있음
>
> **재사용이 가능하게 만들어진 Component**

> **Spring Bean**
>
> : Spring Framework의 Container에 의해 등록, 생성, 조회, 관계설정이 되는 객체이다
>
> 일반 Java Object와 동일하지만 IoC 방식으로 관리된다



#### <Java Bean 규약>

1. 기본 생성자가 존재해야 한다

2. 모든 멤버변수의 접근제어자는 private이다

3. 멤버변수마다 getter/setter가 존재해야 한다

4. 외부에서 멤버변수에 접근하기 위해서는 메소드로만 접근할 수 있다

5. Serializable(직렬화)가 가능해야 한다

   > 시스템 내부에서 사용하는 객체 혹은 데이터를 외부의 시스템에서도 사용할 수 있도록 변환시키는 것



**Bean Factory**

: Bean 객체를 생성하고 관리하는 인터페이스로, 디자인패턴의 일종인 팩토리패턴을 구현한 것이다.

구동될 때, Bean 객체를 생성하는 것이 아니라, **클라이언트의 요청이 있을 때 (getBean()) 객체를 생성**한다



#### 빈 생명주기 (Bean Life Cycle)

: 언제 어떻게 인스턴스화되는지, 그것이 살아갈 때까지 어떤 작업을 수행하는지, 언제 어떻게 파괴되는지를 나타냄

`객체 생성 - 초기화 - 사용 - 소멸`

1. 스프링 컨테이너를 초기화 할 때, 가장 먼저 빈 객체를 생성함

2. 빈 객체 생성 후 < property > 태그로 지정한 의존을 설정함(의존 주입도 이 단계에서 수행!)

3. 모든 의존 설정이 완료되면, 빈 객체 초기화를 함

   > 빈 객체를 초기화하기 위해 빈 객체의 지정한 메소드를 호출

4. 스프링 컨테이너를 종료하면, 스프링 컨테이너는 빈 객체를 소멸시킴

   > 빈 객체의 소멸을 처리하기 위해 빈 객체의 지정한 메소드를 호출



+ **빈 객체의 초기화**

빈 객체가 `InitializingBean`인터페이스를 상속 받았다면, 스프링 컨테이너에서 빈 객체 생성 후, 초기화 과정이 필요한 경우 `InitializingBean`에 정의되어 있는 **afterPropertiesSet()** 메서드를 사용한다

+ **빈 객체의 소멸**

  빈 객체를 소멸해야 할 때는 `DisposableBean` 인터페이스를 상속받고, **destroy()** 메소드를 이용한다

```java
public class SimpleClass implements InitializingBean, DisposableBean{
 
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BEAN 생성 및 초기화 : afterPropertiesSet() 호출됨");
    }
 
    @Override
    public void destroy() throws Exception {
        System.out.println("BEAN 생성 소멸 : destroy 호출됨");
    }
 
    // 중략...
}
```

