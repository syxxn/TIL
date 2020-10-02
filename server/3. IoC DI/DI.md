# DI

1. Dependency Injection(의존성 주입) -> 대신 넣어준다

   > 이미 만들어 둔 것을 필요할 때 사용

   ```java
   @RestController
   @AllArgsConstructor
   @RequestMapping("/user")
   public class UserController {
       
       //컨테이너에 등록된 userServie를 컨트롤러에 주입 시킴
       //여기서 빈은 userService, 이 빈이 컨트롤러에 등록된 것
       private final UserService userService; //DI
   
       @PostMapping("/user")
       public void signUp(@RequestBody SignUpRequest signUpRequest){
           userService.signUp(signUpRequest);
       }
   }
   ```

   

2. Dependency Inversion(제어권 역전)

3. IoC(Inversion of Control)  (제어 역전의 원칙) -> 대신 해준다

   > 미리 찜해 놓은 객체를 생성하고 관계를 설정시켜주고 소멸시키는 것