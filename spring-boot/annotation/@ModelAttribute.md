## @ModelAttribute

@RequestParam과 @ModelAttribute는 쿼리 스트링으로 요청을 보낼 때 사용한다는 공통점이 있다.

+ Query String을 하나하나 받을 땐 @RequestParam을 사용하고, 객체로 받을 때는 @ModelAttribute를 사용한다.

  > 파라미터의 개수가 적을 때는 @RequestParam으로 명시하는 것이 깔끔하지만, 갯수가 많을 때는 하나의 VO 객체로 묶어서 관리하는 것이 훨씬 간편하다.

+ @RequestParam은 파라미터 타입이 잘못 들어오면 400 Bad Request를 날리지만, @ModelAttribute는 타입 변환(바인딩)에 실패하더라도 작업이 계속 진행된다.

<br>

+ @RequestParam과 동일하게 어노테이션을 생략할 수 있다. 하지만, 생략하는 것보다 명시하는 것을 권장한다.

+ @ModelAttribute를 통한 객체 전달 시 @JsonProperty 어노테이션을 사용하더라도 key값이 변환되지 않는다.

  > 당연히 되는 줄 알고 열심히 적어놨었는데 하나도 안 먹혀서 필터링 역할을 못하고 있었다ㅜㅜ
  
  > @JsonProperty가 안되는 건 당연한 것이었다!
  >
  > @JsonProperty는 jackson 라이브러리에서 제공하는 어노테이션이다. jackson은 json 형태를 java object로 매핑해주는 역할을 한다.
  >
  > @ModelAttribute는 객체를 쿼리스트링으로 받는 것이다. json이 아니라!!