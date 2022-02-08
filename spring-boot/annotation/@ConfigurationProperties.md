## @ConfigurationProperties

*.properties , *.yml 파일에 있는 property를 자바 클래스에 값을 가져와서(바인딩) 사용할 수 있게 해주는 어노테이션이다.

@Value를 사용하는 방법도 있지만, 문자열을 사용하여 바인딩하기 때문에 오타가 발생하면 값을 제대로 갖고 오지 못할 수도 있고, 여러 군데에서 사용할 경우 중복 코드가 발생할 수도 있다. 

이러한 문제점을 해결하기 위해 클래스 파일로 환경 변수를 관리할 수 있게끔 `@ConfigurationProperties` 어노테이션을 사용하였다.

```java
@Getter
@ConstructorBinding
@ConfigurationProperties("jwt")
public class JwtProperties {

    private final String secretKey;
    private final Long accessExp;
    private final Long refreshExp;

    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final Integer MICROSECOND_PER_SECOND = 1000;

    public JwtProperties(String secretKey, Long accessExp, Long refreshExp) {
        this.secretKey = secretKey;
        this.accessExp = accessExp * MICROSECOND_PER_SECOND;
        this.refreshExp = refreshExp * MICROSECOND_PER_SECOND;
    }
}
```

+ `@ConstructorBinding`은 setter 대신 생성자를 사용하여 final 필드에 대한 값을 주입해 준다. 

  setter를 사용하는 것은 싱글톤 객체의 변경 가능성이 열려 있기 때문에 좋은 방법이 아니다.

  > 바인딩은 일반적으로 하나를 다른 것으로 매핑시키는 것을 의미한다.

<br>

위의 코드만 추가 했을 때에는 `@ConfigurationProperties`에 빨간 밑줄이 그어졌다. 메시지는 아래와 같다.

```
Not registered via @EnableConfigurationProperties, marked as Spring component, or scanned via @ConfigurationPropertiesScan 
```

<br>

#### @ConfigurationPropertiesScan

그래서 다른 예제 코드들을 살펴보니 편의를 위해서인지 `@ConfigurationPropertiesScan` 어노테이션을 Application 클래스에 붙였다.

```java
@ConfigurationPropertiesScan
@SpringBootApplication
public class BasicSettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicSettingApplication.class, args);
	}

}
```

<br>

#### @EnableConfigurationProperties

하지만 Application에 어노테이션을 추가하는 것보다 config를 개별적으로 설정하는 것이 좋다고 한다.

> 하나의 컨트롤러에서 모든 요청을 받지 않듯이, 용도에 따라 config를 분리해야 찾기가 수월하기 때문이다.

그래서 다른 방법을 찾아보니, 아래처럼 설정하는 방법도 있었다.

```java
@EnableConfigurationProperties(value = {JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {
}
```

