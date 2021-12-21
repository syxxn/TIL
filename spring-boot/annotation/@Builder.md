# @Builder

@Builder를 사용하는 이유는 직관적인 코드를 짜기 위함이다.

생성자로 한번에 설정해줄 수도 있지만, 빌더 패턴을 사용하면 각 인자가 어떤 의미인지 알기 쉽다.

```java
Application.builder()
	.account(account)
    	.post(post)
    	.isAccepted(false)
    	.build();
```

```java
Application.ApplicationBuilder builder = Application.builder();
builder.account(account);
builder.post(post);
builder.isAccepted(true);
Application application = builder.build(); 
```

<br>

@Builder를 붙이면 클래스 내부에 보이지 않는 Builder 클래스가 만들어진다. 

@Builder 어노테이션을 사용하지 않고, 빌더 패턴을 사용하기 위해서는 아래처럼 일일이 만들어 주어야 한다.

```java
public class Application {

    private Account account;
    private Post post;
    private Boolean isAccepted;

    public static class Builder {
        // Required parameters(필수 인자)
        private final Account account;
        private final Post post;

        // Optional parameters - initialized to default values(선택적 인자는 기본값으로 초기화)
        pirvate boolean isAccepted = false;

        public Builder(Account account, Post post) {
            this.account = account;
            this.post = post;
        }

        public Builder isAccepted(boolean value) {
            isAccepted = value;
            return this;    // 이렇게 하면 . 으로 체인을 이어갈 수 있다.
        }
        public Application build() {
            return new Application(this); //생성자 호출
        }
    }

    private NutritionFacts(Builder builder) {
        account = builder.account;
        post = builder.post;
        isAccepted = builder.isAccepted;
    }
}
```

@Builder를 사용할 때는 @NoArgsConstructor와 @AllArgsConstructor를 사용해야 한다.

> builder().build()를 사용하면 Builder 클래스 내부에 값이 들어가게 되는데, 이 값을 원래의 클래스로 올려 보내주기 위해서 @AllArgsConstructor가 필요하다.

> @Builder를 사용할 때에는 @AllArgsConstructor만 붙여도 되지만, test에서 ObjectMapper를 사용하기 위해서는 @NoArgsConstructor도 붙여야 한다.

<br>

```java
@Builder
public class Application {
    
    @Builder.Default
    private boolean isAccepted = false;
    
}
```

위처럼 설정하면 builder에서 값을 넣어주지 않아도 기본적으로 설정해 두었던 값이 들어간다.

<br>

+ Class가 타겟인 경우 @Builder @NoArgsConstructor랑만 사용하면, 컴파일 오류가 발생한다.

  + 빌더는 생성자가 없는 경우 모든 멤버변수를 파라미터로 받는 기본 생성자를 생성함.
  + 생성자가 있는 경우에는 따로 생성자를 생성하지 않음.

  > 생성자를 직접 만들어 주거나 @AllArgsContstructor를 붙여주어야 한다

+ 클래스에 @Builder를 붙이는 것 보다 필요한 필드만 사용한 생성자를 만들어 생성자에 @Builder를 붙이는 것이 바람직하다.

  > 이 경우엔 @AllArgsConstructor가 필요 없다.
  
  ```
  @Getter
  public class User {
  	
  	...
  	
  	@Builder
  	public User(String email, String name) {
  		this.email = email;
  		this.name = name;
  	}
  
  }
  ```

  > id같은 경우엔 대부분 데이터베이스의 AI 특성을 사용하여 따로 값을 입력받지는 않는다. 
  
  > createdAt 같은 경우는 @CreationTimestamp 같은 어노테이션을 사용하면, 따로 값을 입력받을 수 없다.
  
  또는 소수의 필드만 제외하는 경우에는 `@Builder(access = AccessLevel.NONE)`을 붙여주면 된다.