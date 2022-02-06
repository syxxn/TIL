## OSIV

Open Session in View로서, 영속성 컨텍스트를 뷰까지 열어두는 기능이다. 덕분에 뷰에서도 지연 로딩을 사용할 수가 있다.

JPA에서는 OEIV(Open EntityManager in View), hibernate에서는 OSIV라고 하지만, 관례상 둘 다 OSIV로 칭한다.

```java
spring.jpa.open-in-view=true
```

스프링에서는 OSIV가 기본적으로 true로 설정되어 있다.

![image info](https://3513843782-files.gitbook.io/~/files/v0/b/gitbook-28427.appspot.com/o/assets%2F-LxjHkZu4T9MzJ5fEMNe%2Fsync%2Fcb2a3996efdd4796447ebbcf545eec8838afd6f7.png?generation=1622890518451162&alt=media)

이렇게 하면 너무 오랫동안 DB 커넥션을 물고 있기 때문에 실시간 트래픽이 중요한 애플리케이션에서는 커넥션이 모자랄 수도 있다.

장점은 지연 로딩을 적극적으로 활용할 수 있다는 것이다.

```java
spring.jpa.open-in-view=false
```

![image info](https://3513843782-files.gitbook.io/~/files/v0/b/gitbook-28427.appspot.com/o/assets%2F-LxjHkZu4T9MzJ5fEMNe%2Fsync%2Fb5fcd162871d14ca6672d60297b2f33a4dcaf5ef.png?generation=1622890511236652&alt=media)

요청한 데이터를 반환한 다음엔 DB 커넥션을 종료한다.

따라서 db와 관련된 모든 값을 트랜잭션(Service) 안으로 넣어줘야 한다.

<br>

admin 서비스처럼 커넥션이 많이 일어나지 않는 경우는 OSIV를 켜도 괜찮지만, 트래픽이 많은 client 서비스는 OSIV를 꺼야 한다.

