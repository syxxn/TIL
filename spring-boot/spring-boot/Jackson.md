## Jackson

데이터를 주고 받을 때, plain/text 형식으로 보낼 수도 있지만, 보통 데이터 구조를 표현하는 방식인 XML/JSON 형태로 많이 보낸다. Jackson 라이브러리는 json 뿐만 아니라 xml, yaml, csv 등 다양한 형식의 데이터 구조를 처리해주는 라이브러리이다.

스트림 방식이므로 속도가 빠르며 유연하고, JSON의 약점 중 하나인 문서화와 데이터 validation 문제를 해결할 수 있다.

```java
# 만약 Jackson을 사용하지 않는다면,

String json = "{\n" +
				"\t\"id\": " + user.getId() +
				",\t\"nickname\": \"" + user.getNickname() + '\"' +
				"\n}"
```

항상 위처럼 할 수 없어서 GSON(Google이 만든 것) 또는 SimpleJSON과 같은 것을 사용한다.

현재도 이 라이브러리를 많이 사용하고 있지만, Jackson을 사용하는 이유는 무엇일까?

<br>

#### Jackson과 기존 클래스와의 차이

+ Spring 3.0 이후부터 Jackson 라이브러리를 통해 자동화 처리가 가능하게 되어
+ 덕분에 직접 key, value를 설정하는 수고를 덜게 되었다.

<br>

#### Jackson 동작 원리

+ @RequestBody 또는 @ResponseBody에서 Spring은 MessageConverter APi를 통해 컨트롤러가 주고받는 객체를 가로챈다.

  > MessageConverter의 종류
  >
  > + StringHttpMessageConverter
  > + FormHttpMessageConverter
  > + ByteArrayMessageConverter
  > + MarshallingHttpMessageConverter
  > + MappingJacksonHttpMessageConverter

+ Jackson은 JSON 데이터를 출력하기 위한 MappingJacksonHttpMessageConverter를 제공한다.

  > + 지원 미디어 타입은 application/json이다.
  > + Jackson의 ObjectMapper가 대부분의 자바 타입을 무난히 JSON으로 변환해준다.

+ 컨트롤러가 리턴하는 객체를 다시 뜯어(자바 리플렉션 사용) Jackson의 ObjectMapper API로 JSON 객체를 만들고 난 후 출력한다.

  > Java Reflection이란,
  >
  > 구체적인 클래스 타입을 알지 못해도, 그 클래스의 메소드, 타입, 변수들을 접근할 수 있도록 하는 자바 API이다.
  >
  > 자바 클래스 파일은 바이트 코드로 컴파일 되어 static 영역에 위치한다. 때문에 클래스 이름만 알고 있다면 내부 정보를 가져올 수 있는 것이다.
  >
  > 리플렉션은 컴파일 시간이 아닌 실행 시간에 동적으로 특정 클래스의 정보를 추출해낼 수 있는 프로그래밍 기법이다.

<br>

#### Jackson을 사용하기 위해 알아야 하는 기본 지식

+ Jackson의 프로퍼티는 getter, setter의 명명 규칙으로 정해진다.

+ @Getter와 @Setter가 필요하다.

  > 라고 말해놓고 @Setter를 사용하지 않는 이유는?
  >
  > + 객체 생성 이후 수정된 부분을 추적하기 어렵다.
  > + @NoArgsconstructor, @AllArgsConstructor, @Builder로 대체하여 불변성을 보장한 후 사용하는 것이 좋다.
  > + @Setter는 DTO에서도 DAO에서도 지양하는 것을 권장한다.

<br>

#### Jackson 어노테이션

+ @JsonIgnore : VO의 멤버변수 제외처리하기
+ @JsonProperty : json으로 변환할 시에 사용할 key 이름이다.
+ @JsonInclude : 값 존재 유무에 따라 직렬화시 동작을 지정한다.(default는 ALWAYS)
+ @JsonFormat : 직렬화시 날짜, 시간의 형식을 지정
+ ...

