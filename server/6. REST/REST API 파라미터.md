# REST API 파라미터



### URL 분석하기!

`http://a.com/topic?id=1`

1. `http://` : 프로토콜
2. `a.com` : 도메인
3. `/topic` : path
4. `id=1` : query string



### Header Parameter

header 파라미터는 보통 인증(Authentication; 특정 identity 제공)과 권한 부여(Authorization; 특정 action 허용)

 두 가지 목적으로 사용된다.



### Path Variable

> Path Parameter

`Path Variable`는 엔드포인트의 일부로, 요청을 경로로 지정하여 요청할 수도 있다. 

+ 일반적으로 우리가 **어떤 자원(데이터)의 위치를 특정해서 보여줘야 할 경우** 사용한다.

+ 어떤 **resource를 식별**하고 싶을 때 사용한다.

```
/users/123 # 아이디가 123인 사용자를 가져온다
```



### Query String

> Query Parameter

엔드포인트에서 물음표(?) 뒤에 id란 변수에 값을 담아 전달하는 방식이 Query String이다. 

자원(데이터)를 **정렬하거나 필터**해서 보여줘야 할 경우에 Query parameter를 사용한다.

```
/users # 사용자 목록을 가져온다
/users?occupation=programer # 프로그래머인 사용자 목록을 가져온다
/users/123 # 아이디가 123인 사용자를 가져온다
```



### Request body parameter

보통 POST 리퀘스트에서는 JSON 오브젝트를 리퀘스트 바디 안에 넣어 보낸다. 이것이 바로 request body 파라미터이며, 주로 JSON으로 되어 있다.

```json
{
	"days" : 2,
	"utils" : "imperial",
	"time" : 14546544
}
```

