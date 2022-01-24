## Spring Event

### Event란?

Spring의 Bean과 Bean 사이의 데이터를 전달하는 방법 중 하나이다. 일반적으로는 DI를 받아 데이터를 전달하지만, Event는 ApplicationContext로 넘긴 데이터를 Listener에서 받아서 처리한다.

### Event 발행과 실행

Event Publisher는 이벤트를 발행하고, Event Listener는 이벤트를 실행하는 것이다.

@EventListener는 이벤트가 발행된 시점에 실행되게 하고, @TransactionalEventListener는 이벤트가 발행된 메소드의 트랜잭션이 종료된 후(기본으로 commit) 실행되도록 한다. 이외에도 엔티티 라이프 사이클 중 특정 시점에 원하는 로직을 처리하도록 하는 @EntityListener도 있다.

### 예제 코드

https://github.com/jobda-keychain/keychain

위의 프로젝트에서 event를 사용한 이유는

1. 내가 원하는 값을 넘겨주기 위해서
2. 원하는 메소드에서만 실행시키기 위해서
3. try-catch의 복잡한 설정 없이 트랜잭션이 성공했을 때만 실행시키기 위해서

```java
# LogEvent.class

@Getter
@AllArgsConstructor
public class LogEvent {

    private final String clientIpAddress;
    private final MethodType methodType;

}
```

> event를 실행시킬 때 필요한 정보를 담기 위한 클래스이다.

```java
# LogEventHandler.class
    
@RequiredArgsConstructor
@Component
public class LogEventHandler {

    private final LogService logService;

    @Transactional
    @TransactionalEventListener
    public void saveRequestLog(LogEvent logEvent) {
        logService.saveRequestLog(logEvent.getMethodType(), logEvent.getClientIpAddress());
    }

}
```

+ event가 실행될 때 호출하는 메소드에서 트랜잭션에 관여하기 때문에 `@Transactional`을 추가하였다.

+ `@TransactionalEventListener`를 붙여 메소드의 트랜잭션이 종료할 때만 실행되도록 했다.

  > 성공한 요청을 저장하기 위한 이벤트이기 때문에 트랜잭션 종료 후 실행시키도록 했다.

```java
# EnvironmentService.class
    
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EnvironmentService {
    
    private final ApplicationEventPublisher eventPublisher;
    
    @Transactional
    public void addEnvironment(String clientIpAddress, AddEnvironmentRequest request) {
        Platform platform = getPlatform(request.getPlatform());

        existsSameName(platform, request.getName());

        Environment environment = Environment.createEnvironment(request.getName(), request.getServerDomain(), request.getClientDomain(), platform);
        environmentRepository.save(environment);
        eventPublisher.publishEvent(new LogEvent(clientIpAddress, MethodType.ADD_ENVIRONMENT));
    }
}
    
```

+ `ApplicationEventPublisher` 빈을 주입받고 `publishEvent()`에 적절한 매개변수를 보내면 해당 이벤트를 발행한다.
  + `@TransactionalEventListener`를 사용하기 위해서는 이벤트를 발행하는 메소드에도 `@Transactional`이 붙어있어야 한다.
  + `@TransactionalEventListener`를 사용했기 때문에 발행 시점은 크게 상관 없다.