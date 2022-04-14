## Optional<>

Optional<>은 null 값을 더 깔끔하게 처리할 수 있도록 Java8부터 지원하는 클래스이다. 

<br>

null 값을 제대로 처리 하지 않으면 `NPE(Null Pointer Exception)`을 만나게 된다. 안정적인 실행을 위해 중간중간 null 체크를 해줘야 하는데, null 처리 할 것이 많은 경우 코드가 지저분 해질 수 있다.

```java
List<User> names = getNames();
//names.sort(); //names가 null인 경우 NPE 발생

if(names != null) { //조건문 필요
    names.sort();
}

➔
List<String> names = Optional.ofNullable(getNames())
                .orElse(new ArrayList<>()); //null인 경우 빈 리스트 객체 반환
```

<br>

위처럼 Optional의 static 메소드를 사용해서 Optional 객체를 생성할 수도 있지만, 내가 사용했던 Optional<>은 아래와 같은 느낌으로 사용했었다. 이때 Optional 객체를 더 잘 사용하고자 찾아보게 되었다.

```java
public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByStyleCode(String styleCode);
}

pubilc class AddItemService {
    private void isConflictStyleCode(String styleCode) {
        itemRepository.findByStyleCode(styleCode)
                .ifPresent(i -> {
                    throw ConflictException.ALREADY_EXISTS_STYLE_CODE;
                });
    }
}
```

<br>

### Java 8 이후 추가된 메소드들

+ Java 9에서 추가된 메소드들

  + `or()`

    중간처리 메소드로서, Optional 객체를 리턴한다. 메소드 체인 중간에서 Optional.empty()가 되었을 때 다른 Optional 객체를 만들어서 넘겨주기 위해 사용합니다.

  + `ifPresentOrElse()`

    두 개의 인자를 받아서 첫번째 인자는 Optional 객체에 값이 존재하는 경우, 두 번째 인자는 Optional.empty()인 경우 실행합니다.

  + `stream()`

    Optional 객체가 바로 Stream 객체로 전환되지 않아 불편했던 부분을 해소하기 위한 메소드이다.

+ Java 10에서 추가된 메소드

  + `orElseThrow()`

<br>

### 생성

```
Optional.of(value);
```

value 변수의 값이 null인 경우 NPE가 발생한다. 

> 반드시 값이 있어야 하는 경우에 사용한다고 하는데.. 이미 null이 아닌 것이 확인된 값인데 왜 Optional 객체로 변환하는 건지 궁금했다.
>
> 멘토님에게 질문드렸더니 'null인 경우가 없다'라고 명시적으로 표현하고 싶거나 null일 때 예외를 발생시키고 싶은 경우 사용한다고 답하셨다.

```
Optional.ofNullable(value);
```

value가 null인 경우 Optional.empty()가 리턴된다.

```
Optional.empty();
```

비어 있는 Optional 객체를 반환한다. 

➔ 객체는 미리 생성되어 있지만 내부에서 가리키는 참조가 없는 경우를 말한다.

<br>

### 중간처리

```
.filter(o -> ...)
```

filter 메소드의 인자로 받은 람다식이 거짓인 경우 Optional.empty()를 반환해서 추가로 처리가 안되도록 한다.

```
.map(o -> ...)
```

Optional 객체의 값에 어떤 수정을 추가하고 싶을 때 사용하는 메소드이다.

<br>

### 반환

```
isPresent()
```

값의 존재여부만 판단하여 boolean 값을 반환한다.

```
ifPresent(o -> ...)
```

만약 값이 존재하는 경우 람다식을 실행시킨다. 

```
get()
```

Optional 객체가 가지고 있는 value 값을 꺼내온다. 만약 Optional 객체에 값이 없다면, `NoSuchElementException`이 발생한다.

```
orElse(T other)
```

Optional 객체가 비어있다면 orElse() 메소드에 지정된 값이 기본 값으로 반환된다. 

other가 null이든 아니든 관계없이 항상 불린다.

```
orElseGet(Supplier<? extends T> other)
```

orElse()는 파라미터로 값을 받는 것과 달리 orElseGet()는 함수를 매개변수로 받는다.

orElseGet()는 해당 값이 null일 때만 불린다.

```
orElseThrow(Supplier<? extends X> exceptionSupplier)
```

Optional 객체가 비어있다면, Supplier 함수를 실행시켜 예외를 발생한다.