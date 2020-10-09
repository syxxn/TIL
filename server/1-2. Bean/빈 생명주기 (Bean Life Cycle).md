# 빈 생명주기 (Bean Life Cycle)

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

