## Entity에 @NoArgsConstructor를 넣어야 하는 이유는?

전부터 Entity 클래스에 `@NoArgsConstructor`를 써 왔고, 써야 한다고 해서 계속 사용해왔다.

하지만, 왜 필요한 것인지 제대로 이해하고 싶어서 정리하게 되었다.

<br>

`@Entity` 어노테이션이 붙은 클래스에는 `@NoArgsContructor`가 필수적으로 있어야 하며, **접근 레벨이 public, protected**여야 한다(public보다는 protected를 활용하는 것이 안정성 측면에서 더 낫다). 그 이유는 **Entity Proxy 조회** 때문이라고 한다.

<br>

### Proxy Server

먼저, Proxy Server에 대해 정리해보자면,

'클라이언트가 **자신을 통해서 다른 네트워크 서비스에 간접적으로 접속**할 수 있게 해 주는 컴퓨터 시스템이나 응용 프로그램'을 말한다고 한다.

보안 상의 목적으로 설치되는 경우가 많으나, 단순히 보안 상의 이유만으로 설치되는 것은 아니며, 프록시 서버에 요청된 내용들을 **캐시**를 이용하여 저장한다고 한다. 캐시를 이용하기 때문에 같은 내용을 여러 번 참조하는 경우, 빠른 속도로 불러올 수 있다.

따로 설정해주지 않으면 사용하지 않는 것 같다.

<br>

### Entity Proxy

그렇다면, Entity Proxy는 무엇을 말하는 것일까?

만약 아래와 같은 관계가 있다고 가정하자.

```java
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    ...
    
    @ManyToOne(fetch = FetchType.LAZY) //ManyTo_는 기본 FetchType이 EAGER이기 때문에 따로 설정해주어야 한다.
    @JoinColumn(name = "school_id")
    private School school;
}

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School {
    ...
}
```

만약 Student 정보를 불러올 때 연관 관계에 있는 School 정보를 전부 불러와야 한다면 성능 상의 문제가 발생한다. 하지만, FetchType을 LAZY로 설정해주면 Student 객체에서 School 객체에 접근하여 값을 가져오는 경우, 이 때 **HibernateProxy** 객체 형태로 조회하는 것이다. 

영속성 컨텍스트에 생성되어 있는 경우엔 엔티티를 반환하고, 없는 경우에는 DB를 조회하여 엔티티 객체를 생성하고, 프록시 객체의 참조 변수에 할당한다. 

즉, **Proxy 객체를 직접 바꾸는 것이 아니라 Proxy 객체의 참조를 바꾸어 실제 Entity로 통하게끔** 하는 것이다.

추가적으로, Proxy 객체가 SQL이 실행되는 시점은 `student.getSchool().getName()`처럼 실제 Entity의 값을 사용할 때라고 한다.

<br>

`FetchType.EAGER`인 경우에는 `@NoArgsConstructor(access=AccessLevel.PRIVATE)`이어도 오류가 발생하지 않는다. Proxy 객체를 만들지 않고 즉시 호출하기 때문이다.

<br>

### 참고

+ https://erjuer.tistory.com/105

+ https://erjuer.tistory.com/106
+ https://kha0213.github.io/jpa/jpa-proxy/
+ https://github.com/syxxn/TIL/blob/master/spring-boot/spring-data-jpa/Proxy%20Object.md
+ https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/proxy/package-summary.html
+ https://dev-gorany.tistory.com/317