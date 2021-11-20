## CORS

CORS란, 교차 출처 자원 공유(Cross-Origin Resource Sharing)의 줄임말로, 브라우저의 SOP 정책을 지키기 위한 것이다. SOP에서 몇 가지 예외 조항을 두고, 이에 해당하는 것은 허용하도록 한다.

+ Origin은 도메인을 의미한다. 

  > `naver.com`, `google.com`, `localhost:8888`

+ SOP(Same Origin Policy) : 같은 Origin에 있는 것끼리만 자원을 주고 받을 수 있도록 하는 정책

  > 같은 도메인 : 같은 스킴(`http`, `https`), 같은 프로토콜, 같은 포트

<br>

브라우저에서 SOP 정책을 지키기 위해서 preflight reuest를 보낸다.

+ cross-origin 요청을 전송하기 전에, `OPTIONS` 메소드로 preflight를 전송한다.
+ 이때 Response로 `Access-Control-Allow-Origin`과 `Access-Control-Allow-Methods`가 넘어오는데, 이는 서버에서 어떤 Origin과 어떤 Method를 허용하는지 브라우저에 알려준다. 
+ 설정해 놓지 않은 url로 요청이 들어오면 "preflight가 실패했어!"라고 알린다.

<br>

CSRF나 XSS같은 방법으로 정보를 탈취하지 못하도록 막기 위해서 CORS가 필요하다.

+ CSRF(Cross-site request forgery) : 웹사이트 취약점 공격(사이트 간 요청 위조)의 하나로, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)을 특정 웹사이트에 요청하게 하는 것이다. 
+ XSS(Cross-site scripting) : 웹 애플리케이션에서 많이 나타나는 취약점의 하나로, 관리자가 아닌 사람이 악성 스크립트를 삽입할 수 있도록 하는 것이다. 주로 다른 웹사이트와 정보를 교환하는 식으로 작동하여 '사이트 간 스크립팅'이라고 한다.

<br>

Spring boot에서 CORS를 설정하는 방법은 두 가지가 있다.

1. Controller마다 @CrossOrigin을 붙이기

   ```java
   @CrossOrigin(origins = "http://localhost:3000")
   @RestController
   public class TestController {
       
       @GetMapping
       public void test() {}
       
   }
   ```

2. config 클래스에서 설정하기

   ```
   @Configuration
   public class WebConfig implements WebMvcConfigurer {
   
   	@Override
   	public void addCorsMappings(CorsRegistry registry) {
   		registry.addMapping("/**")
   				.allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
   				.allowedOrigins("http://localhost:3000");
   	}
   
   }
   ```

   + `WebMvcConfigurer`에는 Spring MVC에서 유용하게 사용되는 기능들이 선언되어 있어서, 웹과 관련된 처리를 하기 위해서는 구현하는 것이 좋다.
   + `registry.addMapping`을 이용해서 CORS를 적용할 URL 패턴을 정의할 수 있다.
   + `allowedMethods`를 이용해서 허용할 HTTP method를 지정할 수 있다. "*"는 모든 메소드를 허락하는 것이다.
   + `allowedOrigins` 메소드를 이용해서 자원 공유를 허락할 출처를 지정할 수 있다. "*"는 모든 Origin을 허락하는 것이다.