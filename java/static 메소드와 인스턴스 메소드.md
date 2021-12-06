## static 메소드와 인스턴스 메소드

Entry 6.1을 개발할 때, 같이 개발하는 친구가 Exception 객체를 매번 만들지 말고 static 필드를 만들어 사용하자고 했는데 이유가 기억이 안나서 다시 찾아보았다.

```java
public class AdminNotFoundException extends RollsException {

	public static final RollsException EXCEPTION =
			new AdminNotFoundException();

	private AdminNotFoundException() {
		super(ErrorCode.ADMIN_NOT_FOUND);
	}

}
```

```java
@RequiredArgsConstructor
@Component
public class AdminFacade {
    
    private final AdminRepository adminRepository;
    
    public Admin getAdminById(String id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}
    
}
```

<br>

> + 클래스의 멤버변수 중 모든 인스턴스에 공통된 값을 유지해야 하는 것이 있는지 보고, 있다면 static을 붙인다.
> + 작성한 메소드 중에서 인스턴스 변수나 인스턴스 메소드를 사용하지 않는 메소드에 static을 붙이는 것을 고려한다.

인스턴스 메소드는 실행 시 호출 되어야 할 메소드를 찾는 과정이 추가적으로 필요하여 시간이 더 걸린다고 한다.

<br>

따라서 인스턴스 변수를 사용하지 않는(어떤 객체든 같은 값을 반환함) Exception class에서는 static 필드를 사용하는 것이 더 효율적이라고 한다.