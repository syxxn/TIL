## @Transactional 전파 레벨

@Transactional을 클래스 또는 메소드 레벨에 명시하면 해당 메소드 호출시 지정된 트랜잭션이 작동하게 된다. 단, 해당 클래스의 Bean을 다른 클래스의 Bean에서 호출할 때만 인지하고 작동하게 된다.

<br>

```java
@Transactional(propagation=Propagation.@)
```



### Propagation.REQUIRED

+ default 값
+ 부모 트랜잭션 내에서 실행하면, 부모 트랜잭션이 없을 경우 새로운 트랜잭션 생성한다.
+ 호출한 곳에서 이미 트랜잭션이 설정되어 있다면 기존의 트랜잭션 내에서 로직을 실행한다.
+ 예외가 발생하면 호출한 곳에도 롤백이 전파된다.

<br>

### Propagation.REQUIRES_NEW

+ 매번 새로운 트랜잭션을 시작한다.
+ 호출한 곳에서 이미 트랜잭션이 설정되어 기존의 트랜잭션은 메소드가 종료할 때까지 잠시 대기 상태로 두고 자식의 트랜잭션을 실행한다.
  자식 트랜잭션 안에서 예외가 발생해도 호출한 곳에는 롤백이 전파되지 않는다.

<br>

### Propagation.MANDATORY

+ 부모 트랜잭션에서 실행되며, 부모 트랜잭션이 없을 경우 Exception이 발생한다.

<br>

### Propagation.SUPPORT

+ 부모 트랜잭션이 존재하면 부모 트랜잭션으로 동작하고, 없을경우 non-transactional하게 동작한다.