## OncePerRequestFilter

OncePerRequestFilter는 요청 당 한 번만 거치도록 하는 필터이다.

이 필터를 사용하여 JwtFilter를 구현했다.

처음에는 `SecurityConfig`에 `permitAll()`로 설정한 것과 설정하지 않은 것의 차이를 보려고 디버깅을 돌렸는데, 

이게 웬걸.. 'Once..'라던 필터를 두 번씩 거치고 있었다. 

> get 요청을 chrome 환경에서 테스트 했을 때 두 번씩 거친 것이며, postman을 사용하여 요청을 보냈을 때는 정상적으로 한 번만 거쳤다.

`request.getRequestURI()`를 확인 해보니 `/favicon.ico`에서 요청을 한 번씩 더 보내고 있었다.

<br>찾아보니 `/favicon.ico`는 필터를 거치지 않게끔 하는 글이 많았다.

> swagger 관련 url도 필터 거치지 않게끔 한다고 한다.

특정 url이 필터를 거치지 않게끔 하는 방법은 두 가지가 있다.

<br>

#### shouldNotFilter(HttpServletRequest request)

`OncePerRequestFilter`의 메소드  `boolean shouldNotFilter(HttpServletRequest request)`를 오버라이딩하면 된다.

```java
@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    return "/favicon.ico".equals(path);
}
```

> https://www.baeldung.com/spring-exclude-filter#shouldnotfilter-method
>
> 특정 필터에서 제외하고 싶은 경우엔 위의 링크를 참고하여 설정하면 된다.

<br>

#### configure(WebSecurity web)

`WebSecurityConfigurerAdapter` 의 `void configure(WebSecurity web)` 를 오버라이딩 하면 된다.

```java
@Override
public voidconfigure(WebSecurityweb) {
	web.ignoring().antMatchers("/favicon.ico");
}
```

> https://gracelove91.tistory.com/41
>
> 모든 필터에서 제외하고 싶은 경우엔 위와 같은 방법으로 설정하면 된다.