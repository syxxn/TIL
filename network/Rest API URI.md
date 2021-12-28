## Rest API URI

Representational State Transfer : 자원을 의미로 구분하여 그 상태를 전달

+ 무상태성을 가지기 때문에 API 서버는 요청만을 단순 처리 가능하다.
+ 자원(URI), 행위(Method), 표현(Body)로 구성되어 있다.

<br>

#### URI

> URI Format에 대한 원칙은 "권장 사항"이다.
> 본인에게 맞는 원칙을 세워도 좋다 (회사에 다닌다면 회사의 규칙을 따르기)

+ URI에서 /는 계층 관계를 나타내는 데 사용한다.

+ URI의 가독성을 위하여 케밥케이스(-)를 사용한다.

+ URL만 보고 의미하는 바를 알 수 있어야 하며, 낮은 계층(2 계층정도)을 사용하기를 권장한다.

  > URI는 인터넷에 있는 자원을 나타내는 유일한 주소이고, URL은 웹 상의 자원의 위치를 말한다. 

+ 일반적으로 소유의 관계에서는 has를 생략하지만, 그 외 관계에서는 sub resource를 통해 표현한다.

  ```
  GET /users/123/bags #123 유저가 갖고 있는 가방
  GET /users/123/likes/bags #123 유저가 좋아하는 가방
  ```

+ URI Format

  ```uri
  scheme://"authority"/"path"?"query"#fragment
  
  #path
  /collection/(store)/(document)
  ```

+ 쿼리 파라미터의 key값은 카멜 케이스로 하는 것이 관례이다.

+ collection과 store는 복수로 설정하는 것이 기본이다.

  > 하지만 마이페이지 url의 경우 /mypage로 할 수 있는 것을 /users/123456로 해야하기 때문에 단수로 맞추는 경우도 있다고 한다.