## SecurityConfig.java

```java
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            	.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl());

        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .anyRequest().authenticated();
    }
}
```

+ `.cors()` : cors 허용. cors 설정은 다른 파일에 해 두었다.

  ```java
  @EnableWebMvc
  @Configuration
  public class WebMvcConfig implements WebMvcConfigurer {
  
      @Override
      public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
                  .allowedOrigins("*")
                  .allowedMethods("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE")
                  .allowedHeaders("*");
      }
  
  }
  ```

+ `.and()` : .and()는 메소드 체이닝을 돕기 위한 키워드이다. 하나의 config 설정이 완료되면 다음 메소드를 호출할 수 있도록 한다.

+ `.csrf().disable()` : REST API는 HTTP 형식을 따르기 때문에 stateless하다. 엔드포인트에 의존하는 REST와 같은 API는 CSRF를 받을 가능성이 없기 때문에 대부분의 애플리케이션에서 비활성화하고 있다.

+ `.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` : JWT 같은 토큰 방식을 사용할 때 세션을 생성하지도, 기존 것을 사용하지도 않겠다고 설정한다.

+ `.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPointImpl())` : 토큰을 인증할 수 없을 때 어떻게 처리할 것인지 설정한 것이다. 현재는 400 에러를 띄우도록 설정되어 있다.

  ```java
  @Component
  public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  
      @Override
      public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Check the token");
      }
  
  }
  ```

+ `.authorizeRequests()` : 요청에 대한 권한을 지정할 수 있다.
  + HttpServletRequest란, Http 프로토콜의 request 정보를 서블릿에 전달하기 위한 목적으로 사용하는 것이다. 
  + 스프링부트는 내부적으로 내장 톰캣(Servlet Container)를 가지고 있다. 
  
+ `.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()` : 다음 구문을 통해 preflight는 인증과정을 거치지 않도록 한다.

+ `.anyRequest().authenticated()` : 설정하지 않은 모든 요청은 인증이 되어야 한다.