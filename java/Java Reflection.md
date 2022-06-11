## Java Reflection

리플렉션이란,

+ 구체적인 클래스 타입을 알지 못해도, 그 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API
+ Heap 영역에 로드된 Class 타입의 객체를 통해, 원하는 클래스의 인스턴스를 생성할 수 있도록 지원하고, 인스턴스의 필드와 메소드를 접근 제어자와 상관 없이 사용할 수 있도록 지원하는 API

라고 하는데 ~~아직 어렵다~~.

<br>

JavaScript, Python 같은 동적 언어는 런타임 시점에 타입을 결정한다. 하지만 C, Java 같은 정적 언어는 컴파일 시점에 타입을 결정한다. 이를 풀어서 말하면, '**컴파일 시점에 클래스의 타입이 결정되기 때문에 런타임에서 어떤 클래스 타입이 사용될지 모른다**'이다.

하지만, 프레임워크, 라이브러리를 사용할 때는 개발자가 어떤 클래스를 만들지 알 수가 없다.

**정적 언어 Java를 사용하면서, 런타입 시점에서 클래스 타입을 동적으로 바인딩 하기 위해 사용하는 기술이 바로 Reflection**이다. 리플렉션은 new 키워드를 사용해서 객체를 가져오는 것과는 다른 것이다.

<br>

대표적으로 사용하는 예는 Spring의 어노테이션이라고 한다.

Spring에서 @Controller, @Service, @Repository 등의 어노테이션만 붙이면 Bean Factory에서 알아서 해당 어노테이션이 붙은 클래스를 생성하고 관리해 준다. 개발자가 Bean Factory에 해당 클래스를 알려준 적이 없는데 가능한 이유는 리플렉션 덕분이라고 한다. **런타임에 해당 어노테이션이 붙은 클래스를 탐색하고 발견**하면, 리플렉션을 통해 **해당 클래스의 인스턴스를 생성하고 필요한 필드를 주입하여 Bean Factory에 저장**하는 식으로 사용이 된다고 한다.

<br>

위와 같은 예시 말고 리플렉션을 직접 사용할 수도 있다고 한다. [공식 문서](https://www.baeldung.com/java-reflection)에서도 사용법을 확인할 수 있다.

```java
//평소에는 getter를 사용한다면, 리플렉션을 사용할 때는 다음과 같은 느낌인갑다.

Class class = Class.forName("com.github.syxxn.Main");

Object person = new Person();
Field[] fileds = person.getClass().getDeclaredFields();
```

<br>

리플렉션을 사용할 때는 주의해야 할 것이 있다.

+ 캡슐화를 저해하기 때문에 꼭 필요한 상황에서만 사용해야 한다.
+ 컴파일 타임에 확인되지 않고 런타임 시에만 발생하는 문제를 만들 가능성이 있으며, 구체적인 동작 흐름을 파악하기 어렵다.
+ new 객체의 필드 및 메소드를 접근할 대보다 리플렉션을 사용하여 접근할 때 성능이 느릴 수 있다.

<br>

### 참고

+ https://dublin-java.tistory.com/53
+ https://dev-nomad.com/89
+ https://steady-coding.tistory.com/609
+ https://gyrfalcon.tistory.com/entry/Java-Reflection