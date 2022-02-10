## @FeignClient

Feign이란, 넷플릭스에서 만든 선언적(소스코드 없이 어노테이션 선언만으로) HTTP 클라이언트 바인더이다. 

RestTemplate을 사용하는 방식과 달리 간편하게 웹 서비스 클라이언트를 작성할 수 있다.

동기 통신 방식에서는 주로 Feign Client 방식을 사용하고, 비동기 통신 방식엔 주로 Kafka를 사용한다고 한다.

<br>

### 사용법

build.gradle에 다음의 구문을 추가한다.

```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'
```

@EnableFeignClients를 추가해야 @FeignClient가 선언된 클래스를 찾아 구현체를 만들어 준다. 

Application 클래스에 어노테이션을 추가하지 않는 경우네는 basePackages 또는 basePackageClasses를 지정해주어야 한다.

```java
@EnableFeignClients(basePackages = "")
@Configuration
public class FeignClientsConfig {
}
```

@FeignClient를 선언한 클래스를 추가한 후, RequestMapping 메소드를 추가한다.

> 이때 추가하는 request 메소드는 일반 controller에서 메소드를 추가하듯 하면 된다.

```java
@FeignClient(name=, url=)
public interface FeignClient {
	
	@GetMapping("")
	ResponseDto execute();

}
```

+ name : 서비스 ID / 빈 이름 설정
+ url : 요청을 보낼 url

<br>

### 동기 호출이 실패한 경우

feign의 경우 2XX의 status만 정상이라고 판단하며, 이외의 status는 모두 Internal Error 500으로 처리한다.

> 단, application의 프로퍼티 파일에서 `feign.client.config.default.decode404=true`로 설정한 경우엔 404도 올바르게 반환된다.

세부적으로 에러 메시지를 해석하기 위해서는 Error Decoder를 구현해야 한다.

```java
public class ErrorDecoderImpl implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) throws FeignException {
        //구현 내용
        return FeignException.errorStatus(methodKey, response);
    }

}
```

`FeignConfig` 클래스에서 `ErrorDecoderImpl`를 @Bean으로 등록해 주어야 FeignException이 발생했을 때 동작하도록 해 준다.

```java
@EnableFeignClients(basePackages = "")
@Configuration
public class FeignClientsConfig {
    
    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
        return new ErrorDecoderImpl();
    }
    
}
```

