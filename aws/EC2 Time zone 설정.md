## EC2 Time zone 설정

AWS EC2에 서버를 올려뒀는데, 지역이 서울임에도 불구하고, UTC 시간으로 반환됐다.

~~처음에는 서버에서 값을 잘못 반환해서인 줄 알고, ZoneId를 설정해서 해결하려고 했는데 이게 아니더라고..~~

<br>

#### TimeZone 설정

```
$ date
//날짜 시간 UTC 연도

$ sudo rm /etc/localtime
$ sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime

$date
//날짜 시간 KST 연도
```

위의 명령어 실행을 모두 마쳤다면, 인스턴스를 재실행해준다.

> ln : link의 약어로서, 리눅스 파일시스템에서 링크 파일을 만드는 명령어이다.
>
> `ln [옵션] 원본파일 대상파일(대상 디렉토리)`
>
> + -s : 심볼릭 링크파일 생성
>
>   심폴릭 링크 파일 : '바로가기' 같은 것으로, 원본 파일을 가리키도록만 한 것

date 명령어를 실행했을 때 시간이 KST라고 나오는데도, 서버에서 시간을 가져오면 UTC로 반환이 된다.

<br>

#### Bean 생명주기를 이용한 Timezone 설정

```java
@EnableJpaAuditing
@SpringBootApplication
public class BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

	}

}
```

main함수가 있는 클래스에 TimeZone 설정하는 메소드를 추가하였다.

`@PostConstruct`는 Bean이 완전히 초기화 된 후, 단 한 번만  호출되는 메소드이다.

> 오 이렇게 하니까 해결됐다 !