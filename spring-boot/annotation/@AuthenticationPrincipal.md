## @AuthenticationPrincipal

#### SecurityContextHolder

지금까지의 프로젝트에서 토큰에 들어있는 username 값을 가져오기 위해서 `SecurityContextHolder`를 사용하는 facade를 직접 받아왔었다. 

```java
private String getUserEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        throw UnAuthorizedException.NOT_ACCESSIBLE;
    }
    return authentication.getName();
}
```

+ 인프런 강의 중에 다음과 같이 정리하였다고 한다.

  ```
  인증된 사용자 정보인 Principal을 Authentication에서 관리하고
  Authentication을 SecurityContext가 관리하고
  SecurityContext는 SecurityContextHolder가 관리한다.
  ```

+ 기본적으로 `ThreadLocal`(한 스레드 내에서 사용하는 공용 저장소)을 사용하기 때문에 `Authentication`을 한 스레드 내에서 공유할 수 있다.

<br>

#### Principal

로그인한 사용자의 정보를 받아오는 방법은 이외에도 controller에 java에서 제공하는 `Principal`(주도자) 객체를 받는 방법이 있다고 한다. 하지만, 참조할 수 있는 정보는 name 밖에 없으며, 한 번 더 조회해야 엔티티를 불러올 수 있다는 단점이 있다.

```java
public class controller {
    
    @GetMapping
    public Response getResponse(Principal principal) {
        ...
        principle.getName();
    }
}
```

<br>

#### @AuthenticationPrincipal

이와 달리 `@AuthenticationPrincipal`을 사용하면 UserDetailsService에서 return한 객체를 파라미터로 직접 받아 사용할 수 있다.

```java
public class controller {
    
    @GetMapping
    public Response getResponse(@AuthenticationPrincipal AuthDetails authDetails) {
        ...
        authDetails. ...
    } //클라이언트는 헤더에 토큰을 넣으면 된다. 
}
```

+ Spring Security의 AuthenticationPrincipalArgumentResolver 클래스를 활용하여 SecurityContextHolder에 접근해서 값을 돌려준다.

  1. SecurityContextHolder에 들어가 있는 Authentication 인스턴스를 가져온다.

     ```
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     ```

  2. authentication.getPrincipal()를 받아낸 후

  3. AuthenticationPrincipal 어노테이션에 사용한 parameter를 얻어내 expression 메소드를 통해 String으로 사용자가 인증에 사용하는 클래스의 이름을 알아낸다.

+ 파라미터와 어노테이션 타입에 적용 가능하며 런타임 시 작동한다.
+ 엔티티를 불러오기 위해서는 이 어노테이션 역시 한 번 더 조회해야 한다. 어댑터 클래스를 사용하면 한 번에 슥삭 할 수 있다.

+ spEL을 사용하여 엔티티를 직접 가져올 수도 있다.

  ```
  @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
  ```

  