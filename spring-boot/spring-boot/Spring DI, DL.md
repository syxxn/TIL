## Spring DI, DL

IoC의 구현방식은 DI와 DL로 분류된다.

![image](https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory&fname=http%3A%2F%2Fcfile10.uf.tistory.com%2Fimage%2F252FCF3B5231689B17B553)

먼저, 자바 또는 Spring에서 말하는 '의존성'이란, 하나의 객체 내에서 필요에 의해 다른 객체를 사용하는 것을 말한다. 의존성(=결합도)이 높을 수록 유지보수가 어려워진다.

<br>

### DI(Dependency Inversion)

일반적으로 Bean 설정 파일을 바탕으로 의존관계를 확인하며, 컨테이너가 흐름의 주체가 되어 애플리케이션 코드에 의존 관계를 주입한다.

인터페이스로 DI를 사용하면 구현 클래스가 변경되어도 인터페이스를 의존하기 때문에 결합도가 낮으며, 유지보수와 확장에 유리하다.

개발자들은 단지 빈 설정파일(Configuration)에서 의존관계가 필요하다는 정보만 추가하면 된다.

```java
# DI를 사용하지 않는 경우
class BurgerChef {
    private BurgerRecipe burgerRecipe;

    public BurgerChef() {
        this.burgerRecipe = new BurgerRecipe();
    }
}
```

```java
# DI를 사용하는 경우

class BurgerChef {
    private BurgerRecipe burgerRecipe;

    public BurgerChef(BurgerRecipe bugerRecipe) {
        this.burgerRecipe = bugerRecipe;
    }
}

new BurgerChef(new HamBurgerRecipe());
// 외부에서 의존성을 주입하기 때문에 BurgetChef가 어떤 객체와 의존을 갖고 있는지 알 수 없다.
```

<br>

DI는 생성자 방식(Constructor Injection), 수정자 방식(Setter Injection), @Autowired 등으로 구현된다.

#### 생성자 방식

+ 인자를 갖는 생성자를 사용하여 의존성을 주입하는 방식이다.
+ 생성자가 파라미터를 지정함으로써 객체가 필요로하는 값을 알 수 있다.
+ 의존 관계를 모두 주입 해야만 객체 생성이 가능하므로 NPE를 방지할 수 있다.
+ 불변성을 보장할 수 있고, 빈을 생성하는 시점에 순환 참조를 확인하기 때문에 순환 참조를 컴파일 단계에서 찾아낼 수 있다.
+ 생성자 인자가 많아지면 코드가 길어지게 되며, 의존성을 주입하는 것이 번거롭다.

#### 수정자 방식

+ 필요한 의존성이 포함되지 않은 생성자로 객체를 생성한 후, setter 메소드를 사용하여 의존성을 주입한다.
+ 객체가 생성된 후에 의존성을 삽입하는 방식으로 구현 시 좀 더 유연할 수 있다.
+ 구현체가 null이라면 메소드에서 NPE가 발생할 수 있기 때문에 의존성이 할당되기 전까지 객체를 사용할 수 없다.

#### @Autowired

+ 변수 선언부에 @Autowired를 붙인다.

+ 어노테이션만 붙이면 되므로 의존성을 주입하기 쉽다. 따라서 하나의 클래스가 많은 책임을 갖게 될 가능성이 높다.

+ 리플렉션을 통해 수행된다.

  > 구체적인 클래스 타입을 알지 못해도 그 클래스의 메소드, 타입을 사용할 수 있음.

+ 생성자 주입에 비해 의존 관계를 한 눈에 파악하기 어렵다.

+ 불변성을 보장할 수 없고, 순환 참조가 발생할 수 있다.

<br>

결합도를 낮추기 위해서는 객체 자체보다 인터페이스를 사용하는 것이 더 좋다. 단, 반드시 하나의 구현 클래스를 가져야 한다. 두 개 이상의 구현 클래스를 갖는 경우에는 @Qualifier 또는 @Resource를 사용해야 한다고 한다.

<br>

### DL(Dependency Lookup)

Bean에 접근하기 위해 IoC 컨테이너에서 제공하는 API를 이용하여 사용하고자 하는 Bean을 검색하여 참조하는 방법을 DL이라고 한다. 

DI와 달리 객체를 외부에서 주입 받는 것이 아니라 컨테이너를 직접 사용하여 검색해서 가져오는 것이다. 인터페이스를 구현한 구체적인 클래스 객체에 대한 결정과 해당 객체에 대한 생명 주기는 IoC 컨테이너에서 관리한다.

<br>

#### ApplicationContext getBean(T t)

ApplicationContext를 통해서 필요로 하는 Bean을 `context.getBean(A.class)`와 같이 검색할 수 있다. 

하지만, ApplicationContext를 이용하면 일반 애플리케이션 코드에 스프링의 API가 사용되는 것이 문제점이다.

Spring의 가장 큰 장점은 환경과 기술에 종속되지 않는 POJO 방식으로 개발을 지원하는 것이기 때문이다.

#### ObjectFactory<> getObject()

ObjectFactory는 ApplicationContext를 대신하여 중간에서 getBean()을 호출해주는 팩토리 객체이다.

row level의 API를 사용하지 않아도 된다는 장점이 있지만, 이 또한 Spring의 API이기 때문에 애플리케이션 코드가 Spring에 의존하는 문제는 해결하지 못했다.

#### Provider

여러 방법들이 있지만, Spring에서 DL를 하는 경우에 권장하는 방법은 Provider를 사용하는 것이다.

Provider 인터페이스는 Spring API가 아니라 JavaEE6에 추가된 표준 인터페이스이기 때문에 ObjectFactory보다 호환성이 좋으며, 별도로 빈을 등록해주어야 하는 코드가 없어 사용에 용이하다. 하지만 여러 타입의 빈이 있다면 get() 호출 시에 NoUniqueBeanDefinitionException이 발생할 수 있따.
