## AuthenticationEntryPoint

Spring Security에서 `AuthenticationException`이 나면 `AuthenticationEntryPoint`를 호출한다.

+ `AuthenticationException`은 인증 예외이다.
+ `AuthenticationEntryPoint`를 호출하면, 로그인 페이지로 이동되고,  401 오류 코드를 전달한다.

<br>

인증이 되지 않았을 경우에는 AuthenticationEntryPoint 부분에서 AuthenticationException을 발생 시키고, 인증은 되었으나 해당 요청에 대한 권한이 없을 경우에는 AccessDeniedHandler 부분에서 AccessDeniedException이 발생된다.

<br>

```java
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Check the token");
    }

}
```

> 나는 만료된 토큰일 경우 401을 띄웠고,
>
> 인증 자체를 못하거나 토큰이 잘못된 형태일 경우 400을 띄우도록 설정하였다.
>
> 