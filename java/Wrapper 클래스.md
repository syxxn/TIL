## Wrapper 클래스

Wrapper 클래스는 java.lang 패키지에 포함되어 있다. (import 없이 사용 가능함)

```java
public class WrapperTest {

    private boolean isB;

    private Boolean isC;

    public boolean isB() {
        return isB;
    }

    public Boolean getC() {
        return isC;
    }

}
```

<br>

#### 박싱, 언박싱

+ Boxing : primitive type -> Wrapper Object

  Unboxing : Wrapper Object -> primitive type

```java
Integer a = 100; //자동 박싱
int b = a - 10; //자동 언박싱
```

<br>

#### Boolean 값 비교

```java
Boolean b = null;
System.out.println(b == false); //NPE

# NPE 해결법
System.out.println(b1 != null && !b1);
System.out.println(Boolean.FALSE.equals(b));
```

<br>

#### 문자열 -> 기본 타입으로 형변환

Wrapper 클래스마다 있는 정적 메소드 parse_()를 호출한다.

```java
String str = "100";
int n = Integer.parseInt(str);
```

<br>

#### Jackson - boolean

jackson에서 boolean 필드를 파싱할 때 getter 메소드의 이름을 사용한다.

boolean 자료형 필드명이 'is _'를 갖고, json에서도 'is _'로 보내고 싶을 땐

1. getter명을 변경한다.

   ```java
   public boolean getIsB(){}
   ```

2. @JsonProperty를 사용한다.

   ```java
   @JsonProperty("is_b")
   private boolean isB;
   ```

3. Boolean 타입으로 선언한다.

   ```java
   private Boolean isB;
   ```

   