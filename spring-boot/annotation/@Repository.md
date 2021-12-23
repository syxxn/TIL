## @Repository

@Repository 어노테이션을 사용하면 빈으로 등록시켜준다는 장점이 있다.

또한, SQLException 또는 JPA 관련 예외를 개발자가 빨리 인식할 수 있도록 스프링의 DataAccessException으로 변환해준다.

> SQLException
>
> 복구가 불가능한 시스템의 예외(500)이다.

> DataAccessException
>
> 런타임 예외에 속하는 클래스이며, 코드가 특정한 데이터 접근 API를 알 필요 없이 직면하는 에러를 발견하고 처리할 수 있도록 해준다. optimistic locking failure에 대해 특정한 API를 알 필요 없이 즉각적인 반응이 가능하다.
>
> + optimistic locking failure : 두 개의 데이터가 순차적으로 업데이트 될 때 뒤늦게 업데이트 한 것이 먼저 업데이트한 것을 덮어쓰지 않도록 막아주는 데 쓸 수 있는 기능

<br>

```java
//@Repository
public interface UserRepository extends CrudRepository<User, String> {}
```

JpaRepository의 구현체인 SimpleJpaRepository에 이미 @Repository가 들어가 있기 때문이다.

> CrudRepository는 JpaRepository의 할아버지쯤 된다.

