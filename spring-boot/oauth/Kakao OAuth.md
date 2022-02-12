## Kakao OAuth

open-feign을 사용하여 OAuth 코드를 작성해 보았다.

> 참고한 문서
>
> + 인가코드 받기 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code
> + 토큰 받기 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
> + 사용자 정보 가져오기 : https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info

<br>

#### 인가코드 받기

```java
@FeignClient(name = "KakaoCode", url = "https://kauth.kakao.com/oauth/authorize")
public interface KakaoCode {

    @GetMapping
    String execute(@RequestParam("client_id") String clientId,
                   @RequestParam("redirect_uri") String redirectUri,
                   @RequestParam("response_type") String responseType);

}
```

```java
# 로직
public String getCode() {
	return kakaoCode.execute(
                oAuthProperties.getClientId(), // REST API Key
                oAuthProperties.getRedirectUrl(), // 등록한 redirectUrl
                "code"
        );
}
```

애플리케이션의 `REST API Key`와 `Redirect URI`, `Response Type`을 쿼리 파라미터로 보내면 아래와 같이 리디렉트 된다.

```url
http://localhost:8484/test-oauth?code=XjMenjECSqhikxQ0n9Fv_CAopb7gAAAF-7KZXyQ
```

그럼 클라이언트에서는 로그인 api를 호출할 때 code를 파싱하여 보낸다.

<br>

#### 토큰 받기

그럼 서버에서는 받은 코드를 사용하여 '토큰 받기' api를 호출한다.

```java
@FeignClient(name = "KakaoAuth", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoAuth {

    @PostMapping
    TokenResponse execute(OAuthRequest request);

}
```

```java
@Getter
public class OAuthRequest {

    private final String grantType = "authorization_code";

    private final String clientId;

    private final String redirectUri;

    private final String code;

    private final String clientSecret;

    @Builder
    public OAuthRequest(String clientId, String redirectUri, String code, String clientSecret) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.code = code;
        this.clientSecret = clientSecret;
    }

}
```

```java
@Getter
@NoArgsConstructor
public class TokenResponse {

    private String accessToken;

}
```

> 원하는 key만 받아도 된다는 것을 처음 알았다.

```java
# 로직

public String getAccessToken(String code) {
    return kakaoAuth.execute(
                OAuthRequest.builder()
                        .code(code)
                        .clientId(oAuthProperties.getClientId())
                        .redirectUri(oAuthProperties.getRedirectUrl())
                        .clientSecret(oAuthProperties.getClientSecret()) // 토큰 발급시 보안을 강화하기 위해 추가 확인하는 코드
                        .build()
        ).getAccessToken()
}
```

<br>

#### 사용자 정보 가져오기

```java
@FeignClient(name = "KakaoInfo", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoInfo {

    @GetMapping
    InfoResponse execute(@RequestHeader("Authorization") String token);

}
```

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfoResponse {

    private Long id;

    private properties properties;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class properties {
        private String nickname;
    }

}
```

```java
public String getInfo(String accessToken) {
    return kakaoInfo.execute("Bearer " + accessToken);
}
```

