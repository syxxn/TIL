## Validation annotation

요청의 유효성을 확인하는 어노테이션은 자바에서 지원해주는 @Valid와 스프링에서 지원해주는 @Validated가 있다.

```java
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

spring boot 2.3 이상부터는 두 어노테이션을 사용하기 위해서는 validation starter에 대한 의존성을 추가해야한다.

그룹을 설정하여 필요한 것만 유효성 검사를 하고 싶을 때는 @Validated, 요청 전체를 검사하고 싶을 때는 @Valid를 사용하면 된다. 

@Validated는 @Valid의 기능을 포함하기 때문에 @Valid를 @Validated로 대체할 수 있지만, 원하는 속성만 검사하는 용도로 사용하는 것이 정확하다.

용도 및 상황에 맞게 유효성 검사를 진행한다면, 사용자의 오류나 시스템의 오류를 최소화할 수 있다.

<br>

### Validation 어노테이션

@Valid 또는 @Validated 없이는 동작하지 않으며, 유효성 검사에 걸리면 400을 띄운다.

#### @NotNull

+ `""` 또는 `" "`는 허용하고, **null만 허용하지 않는다.**
+ Entity 컬럼에 붙여서 사용하면 `nullable=false`와 달리 유효성 검사를 해주기 때문에 더 안전하게 사용할 수 있다.
+ 기본 자료형(int, boolean ...)으로 두고 따로 set하지 않는 경우에는 0으로 초기화한다.

#### @NotEmpty

+ null과 `""`를 허용하지 않는다.

#### @NotBlank

+ **null, "", " " 모두를 허용하지 않는다.**
+ @NotNull, @NotEmpty, @NotBlank 중 강도가 가장 높으며, 값이 들어와야만 인정한다.
+ String에서만 사용할 수 있다.

#### @Email

+ 이메일 형식이 아니면 ValidationException을 띄운다.
+ 다만 ""의 경우 통과시키기 때문에 `@Pattern(regexp=)`를 사용하는 것이 더 안전하다.

#### @Size(min=, max=)

+ @Max와 @Min을 한 번에 설정할 수 있다.

#### @Positive

+ 값을 양수로 제한한다.