## UserDetails, UserDetailsService 구현체

인증 객체의 정보를 담은 UserDetails와 UserDetailsService의 구현체 이름은 인증(Authentication)을 줄여 Auth_로 설정하였다.

<br>

### AuthDetails

UserDetails는 사용자의 정보를 담는 인터페이스이다.

AuthDetails는 UserDetails를 구현한 유저 정보를 인증 객체 형태로 가지고 있는 첫 번째 인증객체이다.

```java
@AllArgsConstructor
public class AuthDetails implements UserDetails {
    
    private final String userName

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
```

+ `getAuthorities()` : 갖고 있는 권한 리스트를 불러오는 메소드이다. 
  new ArrayList()를 하면 기본적으로 10개의 배열을 가지고 있어 메모리가 낭비된다.
  권한 설정을 따로 해주지 않는 경우엔 `Collections.emptyList()`로 반환하는 것이 메모리 낭비를 막을 수 있다.

+ `getPassword()` : 계정의 password 가져오기
  null로 반환하는 것이 안전하다.

+ `getUsername()` : 계정의 고유한 값 가져오기

  로그인할 때 사용한 username을 가져온다.

+ 다음 메소드는 따로 설정하지 않는 한 true로 설정해야 계정 사용이 가능하다.

  + `isAccountNonExpired()` : 계정이 만료되지 않았는지를 반환한다. 
  + `isAccountNonLocked()` : 계정이 잠겨있는지를 반환한다.
  + `isCredentialsNonExpired()` : 계정의 비밀번호가 만료되지 않았는지 반환한다.
  + `isEnabled()` : 계정이 사용 가능한지 반환한다.

<br>

### AuthDetailsService

UserDetailsService란 유저의 정보를 가져오는 인터페이스이다.

유저의 로그인 정보를 사용하여 실제 DB에 존재하는지 확인하고, 인증 객체를 반환한다.

```java
@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final BasicEntityRepository basicEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        basicEntityRepository.findByUserId(username)
                .orElseThrow(() -> UserInformationNotExistsException.EXCEPTION);

        return new AuthDetails(username);
    }

}
```

+ `loadUserByUsername()` : username을 사용하여 User 인증객체를 가져온다.

  이 메소드는 TokenFilter에서 인증객체를 만들 때 호출된다.