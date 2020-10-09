# Controller Package

> 완벽한 비유는 아니지만 Controller는 main이라고 생각하셈
>
> 들어오자마자 실행되는 것

### RequiredArgsConstructor ★

: final(상수 선언)이나 @NonNull인 필드 값만 파라미터로 받는 생성자 생성

: **빈에 주입된 클래스를 불러오겠다(재활용)**

+ 생성된 모든 필드의 get 메소드를 생성해 줌
+ final이 없는 필드는 생성자에 포함되지 않음

```java
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController{
	private final UserService Impl service;
}
```



### @RequestMapping ★

: 요청 URL을 어떤 메서드가 처리할 지 여부를 결정하는 것

> 이 어노테이션은 클래스와 메소드 레벨에서 모두 사용할 수 있다. 
>
> 대부분의 경우, 메소드 레벨에서는HTTP 메소드 특정 변형 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 또는 @PatchMapping 중 하나를 사용하는 것을 선호 한다

+ 어떤 경로를 한 메서드에 처리하고 싶다면, 배열로 경로 목록을 지정하면 됨

```java
public class Ne{
	@RequestMapping({'/main','/index'})
    //보통 URI를 설정 해주는데, 괄호 안에 꼭 "문자열"
	public String list(ModeMap model){
	}
}
```



### @RestController ★

: Rest 방식의 Data 자체를 넘겨주는 것

: 정보를 보내는 방식을 rest로 하겠다고 선언하고, 해당 클래스를 컨트롤러 클래스로 빈에 등록시킴

+ 주용도는 JSON 형태로 객체 데이터를 반환하는 것
+ URI가 원하는 리소스를 의미함

```java
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController{
	private final UserService Impl service;
}
```





____



> 메소드 레벨에서 사용하는, HTTP Method에 매칭되는 어노테이션
>
> 이 전에는 @RequestMapping 어노테이션 하나만을 사용하여 method를 바꿔주는 형태로 http method를 매핑했었는데, 이는 코드가 길어지고, 직관적이지 않다는 단점이 있었다.
>
> 다음의 어노테이션들은 이 단점을 보충하여 명시적이고 효율적으로 코드를 짜기 위해 생겼다

### @PostMapping

: Post의 HTTP(S) request를 처리함

```java
@PostMapping("/message/{messageId}")
    public void readMessage(@PathVariable Integer messageId) {
        messageService.readMessage(messageId);
    }
```



### @PutMapping

: Put의 HTTP(S) request를 처리함

```java
@PutMapping
public TokenResponse refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken){
        return authService.refreshToken(refreshToken);
}
```



### @GetMapping

: @GetMapping 어노테이션이 있는 메소드는 주어진 URI 표현식과 일치하는 HTTP 요청을 처리함

```java
@GetMapping("/{boardId}")
public BoardContentResponse getBoardContent(@PathVariable Integer boardId) {
    return boardService.getBoardContent(boardId);
}
```



### @DeleteMapping

: Delete의 HTTP(S) request를 처리함

```java
@DeleteMapping("/{boardId}")
    public void deleteBoardContent(@PathVariable Integer boardId) {
        boardService.deleteBoard(boardId);
    }
```



### @PathVariable

: @RequestMapping 값으로 {템플릿 변수}를 사용하면, @PathVariable을 이용해서 {템플릿 변수}와 동일한 이름을 갖는 파라미터를 추가하면 됨



____

 

> @MessageMapping으로 설정한 주소로 요청을 받고, @SendTo 를 통해 클라이언트에게 변화된 값을 브로드캐스팅한다.

### @MessageMapping

: @RequestMapping 역할을 하는 @MessageMapping

### @SendTo

: 목적지 구별해주는 @SendTo

: 1 : n으로 메세지 뿌릴 때 사용하는 구조이며 보통 경로가 /topic 으로 시작

### @DestinationVariable

```java
@MessageMapping("/send/{studentId}/{adminId}")
    @SendTo({"/receive/{studentId}/{adminId}", "/receive/admin"})
    public MessageResponse sendToAdmin(@DestinationVariable Integer studentId,
                                       @DestinationVariable Integer adminId,
                                       MessageRequest messageRequest) {
        return messageService.chat(studentId, adminId, messageRequest);
    }
```



____



### @RequestBody

: HTTP 요청의 Body 내용을 자바 객체로 매핑하는 역할 (body 안에 데이터를 받겠다)

+ JSON 형태로 데이터를 받겠다

  > 클래스에 선언된 형태로 받아오고 싶을 때, (클래스명 변수명)으로 선언

+ POST 방식으로 전달된 HTTP 요청 데이터의 Body를 통으로 읽어오게 함

```java
@PutMapping("/email/verify")
    public void verifyEmail(@RequestBody @Valid VerifyCodeRequest verifyCodeRequest) {
        userService.verifyEmail(verifyCodeRequest);
    }
```



### @Valid

: 클라이언트의 입력 데이터가 dto 클래스로 캡슐화되어 넘어올 때, 유효성을 체크하라는 어노테이션이다.

> 앞에서 dto 클래스에 작성한 어노테이션을 기준으로 유효성을 체크합니다



```java
@PostMapping
    public TokenResponse signIn(@RequestBody @Valid AccountRequest request) {
        return authService.signIn(request);
    }
```



### @Email

: 이메일 주소가 유효한지 검사한다. null은 유효하다고 판단한다

> 이메일 양식이어야 함

```java
@PostMapping("/email/verify")
    public void sendEmail(@RequestParam("email") @Email String email) {
        userService.sendEmail(email);
    }
```



### @RequestParam

: 단일 데이터를 받을 때 사용함

`@RequestParam("가져올 데이터의 이름")[데이터 타입][가져온 데이터를 담을 변수]`

```java
@PostMapping
    public void writeBoard(@RequestParam String title,
                           @RequestParam String content,
                           MultiparjavatFile[] files) {
        boardService.writeBoard(title, content, files);
    }
```

