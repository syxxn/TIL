## HTTP

텍스트 기반의 통신 규약으로 인터넷에서 데이터를 주고 받을 수 있는 프로토콜이다.

<br>

+ HTTP는 사용자가 애플리케이션을 통해 요청(request)을 하면 서버에서는 해당 요청 사항에 맞는 결과를 찾아 사용자에게 응답(response)하는 형태로 동작한다.

+ HTTP는 웹에서 이루어지는 모든 데이터 교환의 기초이며, 클라이언트-서버 프로토콜이다. 

  > 클라이언트-서버 프로토콜 : 수신자 측에 의해 요청이 초기화되는 프로토콜이다.

+ Stateless하다. HTTP 쿠키를 사용하면 상태가 있는 세션을 만들 수 있지만, 서버는 상태를 저장하지 않는다. 동일한 연결 상에서 연속하여 두 개의 요청이 전달되어도 두 요청은 연결고리가 없다.

  + 연결은 전송 계층에서 제어되므로 응용 계층 프로토콜인 HTTP에서는 전송 프로토콜을 요구하지 않는다. 

    하지만 신뢰할 수 있거나 메시지 손실이 없도록 하는 연결 기반인 TCP 표준에 의존한다.

<br>

### HTTP Method

> HTTP Method는 클라이언트 입장에서 생각해야 한다.

+ **GET** : 자료를 받아오는 경우 사용한다. 주로, Get 요청을 보낼 때는 body 값을 보내지 않고, parameter를 전송한다.

  > 비슷한 HTTP Method로는 HEAD가 있다. HEAD는 Get과 동작이 비슷하지만, Response에 Body가 존재하지 않는다.

+ **POST** : 자료를 생성하는 경우 사용한다. 주로 body로 값을 받는다.

+ **PUT** : 기존의 자료를 모두 수정하고 싶을 때 사용한다. 

+ **DELETE** : 자료의 삭제를 요청할 때 사용한다.

+ **PATCH** : 기존의 자료 중 일부만 수정하고 싶을 때 사용한다.

<br>

### Request Message

```http
#1. Request Line
POST https://rolls.entrydsm.hs.kr/admin HTTP/2
#2. Request Headers
Host: rolls.entrydsm.hs.kr
User-Agent: insomnia/7.1.1
Content-Type: application/json

#3. Request Body
{
	"type":"START_DATE",
    "date": "2021-10-20`T`12:05:55"
}
```

1. **Request Start Line** :

   `HTTP Method`와 `Request Target`, `Protocol version`으로 구성되어 있다.

   `Request Target`은 주로 URL 형태로 표현된다.

2. **Request Headers** :

   Header는 Start Line에서 표현되지 않은 더 구체적인 요구를 작성하는 공간이다.

   + `Host` : 서버의 도메인 주소

   + `User-Agent` : 사용자의 웹 브라우저 종류 & 버전 정보

     > insomnia는 크롬 확장 프로그램이다.

   + `Content-Type` : 컨텐츠의 타입과 문자열 인코딩 등을 명시한다.

3. **Blank** : 헤더와 바디 사이에는 빈 줄이 존재한다.

4. **Request Body** :

   보통 POST, PUT과 같이 서버에 데이터를 전송할 때 본문도 함께 보낸다.

![image](https://blog.kakaocdn.net/dn/mSk2H/btqx9WRK6pz/7I8R1wG4H1iCnV3HCHpib1/img.png)

<br>

### Response Status

#### 1XX 정보

데이터 처리의 상태를 나타낸다.

___

#### 2XX 성공

+ **200 OK** : 요청이 성공했음을 나타낸다. Default 성공 상태 코드이다. 새롭게 업데이트하여 사용자에게 새로운 페이지를 보여줘야 할 때 사용한다.
  + GET : 리소스를 가져옴. 
  + POST : 리소스가 명시하는 행동의 결과를 성공함.

+ **201 CREATE** : 요청이 성공적으로 처리되어 자원이 생성되었을 때 반환한다. 보통 POST에서 사용한다.
+ **204 NO CONTENT** : 요청이 성공했으나 response body가 없다. PUT이나 DELETE 요청일 때 주로 사용한다.

___

#### 3XX 리다이렉트

리다이렉트와 관련된 status code이다. 직접 통신하는 서버에서는 자주 사용하지 않지만, OAuth 등을 사용할 때 볼 수 있다.

> 리다이렉트 : 다시 지시하는 것을 말한다. 사용자가 A URL로 웹 서버에 요청을 보내면, 서버는 B로 가보라고 한다. 그럼 브라우저는 B를 띄워준다. 

___

#### 4XX 클라이언트 오류

+ **400 BAD REQUEST** : 요청이 잘못되었을 때 반환한다.

  Request Body를 받는 경우 타입이 잘못 되었거나, 정규식을 어겼거나 하는 등의 상황에서 사용한다.

+ **401 UNAUTHORIZED** : 인증되지 않은 사용자가 접근하는 경우 반환한다. 

+ **403 FORBIDDEN** : 권한이 없을 때 사용한다. 401과 비슷하지만, 401은 인증을 하면 해결되지만, 403은 재인증을 시도하더라도 지속적으로 거절한다.

+ **404 NOT FOUND** : 요청한 리소스를 찾을 수 없다는 것을 의미한다. 예를 들어 로그인을 하는데 유저의 id가 존재하지 않을 때, 상세보기를 요청한 게시글이 존재하지 않을 때 등에서 사용한다.

+ **409 CONFLICT** : 서버의 현재 상태와 요청이 충돌할 때 반환한다.
  예를 들어 회원가입을 하는데 데이터베이스에 이미 동일한 이메일이 존재할 때 사용한다.

+ 418 I'M A TEAPOT : 만우절 때 만들어진 status이다. 서버가 찻주전자이기 때문에 커피 내리기를 거절했다는 것을 의미한다.

___

#### 5XX 서버 오류

+ **500 INTERNAL SERVER ERROR** : 요청을 처리하는 과정에서 예상하지 못한 상황에 놓였을 때 반환된다. 
  예상하지 못한 상황이기 때문에 에러 핸들링을 잘 해주는 것이 중요하다.

+ **502 BAD GATEWAY** : 업스트림 서버로부터 유효하지 않은 응답을 받았을 때 반환된다.

  도메인은 존재하지만 서버가 꺼진 경우 등에서 볼 수 있다.

  > 업스트림 서버 : 서버 계층에서 상위에 위치하며, 다른 서버에 서비스를 제공하는 서버를 말한다.

<br>

https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP
