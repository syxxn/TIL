## Hexagonal Architecture

아키텍처란, 소프트웨어 응용 프로그램을 설계하기 위한 모델 또는 패턴을 말한다.

> 클린아키텍처에서는 소프트웨어 공학에서 아키텍처란, **개발, 배포, 운영, 유지보수 되도록 만들어진 시스템의 형태**라고 정의했다.

<br>

기존의 계층형 아키텍처는 DIP(의존성 역전 원칙)를 적용해도 한계가 있다. 클린 아키텍처에서는 장기적인 애플리케이션 생명주기 관점에서 계층형 아키텍처를 지양해야 한다고 말한다.
**객체지향적인 관점에서 좋은 설계는 행동을 중심으로 설계**하며, 이 행동이 상태를 결정해야 하지만, 계층형 아키텍처는 데이터베이스 중심적인 개발이 이루어질 수 밖에 없으며, 이는 **비즈니스 로직이 영속성 계층에 강하게 결합**되는 문제가 발생한다.

> Layered Architecture : Presentation > Business Logic > Database

도메인이 인프라에 의존하게 되면서 도메인적인 관심사와 기술적인 관심사가 섞이게 된다.

> + Domain : Entity, Use Case / 비즈니스 로직
> + Infrastructure : Persistence Adapter, Web Adapter / DB, 브라우저...

<br>

### DIP

SOLID 원칙 중 하나인 DIP(의존성 역전 원칙)을 적극적으로 적용한 것이 Hexagonal Architecture이다.

보편적인 소프트웨어 공학에서는 모듈 간 의존관계는 항상 상위에서 하위로 맺어진다. (표현 계층 > 응용 계층 > 도메인 계층 > 인프라 계층) 이런 구조의 가장 큰 단점은 의존하는 하위 계층에 변경이 있을 경우 상위 계층이 영향을 받는다는 점이다. 즉, 인프라 계층에 종속적인 현상이 많이 일어나게 된다.

여기서 계층을 확실하기 분리하기 위해 의존 관계의 방향을 역전시키는 것이다. 

<img src="https://jinia-img-bucket.s3.ap-northeast-2.amazonaws.com/2fa93d4b-931d-4b22-8df0-2c6674f2fa42.png" alt="image info" style="zoom:80%;" />

하위 계층 객체의 구현과 인터페이스를 분리하면 상위 계층의 객체는 수평 의존을 하고, 하위 계층에 있던 구현이 상위의 인터페이스를 의존한다.

하위 계층(저수준 모듈)이 상위 계층(고수준 모듈)에 의존하게 되는 것을 DIP라고 한다.

<br>

### Hexagonal Architecture

Hexagonal Architecture(Port and Adapter Architecture)의 주요 특징은 구성 요소 간의 종속성이 안 쪽인 도메인 개체를 향한다.

Hexagonal Architecture은 **비즈니스 로직(도메인 모델)을 인프라에서 분리하는 것**이 핵심이다.

<img src="https://velog.velcdn.com/images%2Fnkjang%2Fpost%2F83b5027f-f98e-4b9a-a4ef-7acf88d79d33%2Fimage.png" alt="image info" style="zoom:80%;" />

#### 내부 영역 : 순수 비즈니스 로직

+ Use Case : 클라이언트가 그 시스템을 통해 하고자 하는 것을 의미한다. Inbound Port의 구현체이며, Outbound만 알고 있다. 유스 케이스 클래스 역시 외부 구성 요소에 대한 종속성이 없으며, 출력 포트를 만든다.
+ Entity : 도메인 개체에는 외부로 향하는 종속성이 없다. 도메인 개체는 상태(state)와 동작(behavior)을 모두 포함할 수 있다.

  > 사용자가 프로그램을 사용하는 분야를 도메인이라고 하고, 그 도메인에서 다루는 핵심 개체를 엔티티라고 한다.

#### 외부 영역 : 인터페이스 처리

+ Port : 외부와의 모든 통신은 전용 포트를 통해 이루어진다.
+ Adapter : 사용자의 요청을 받아들일 때 사용되는 어댑터와 도메인 모델의 처리에 사용되는 어댑터가 있다.

> Port와 Adapter의 주도권은 사용되는 쪽이 갖는 것 같다.
>
> Inbound는 요청이 들어오는 경로(API) / Outbound는 로직에서 사용하는 경로(SPI)

#### PORT

DI를 위해 추상화 되어 있는 인터페이스

+ (주도하는) Inbound Port : 외부 → 내부 /  UseCaseInterface
+ (주도되는) Outbound Port : 내부 → 외부

#### ADAPTER

포트를 통해 인프라와 실제로 연결하는 부분만 담당하는 구현체

+ (주도하는) Inbound Adapter : 외부 → Inbound Port / (@Controller)
+ (주도되는) Outbound Adapter : Outbound Port → 외부 / Outbound Port의 구현체

<br>

### Aggregate

추가적으로 **Aggregate**란, **비즈니스에서 밀접한 연관성이 있는 객체들의 군집**이다.

육각형 내부에서는 Entity를 직접 접근하는 것이 아니라 Aggregate라는 객체로 접근해야 한다.

+ 하나의 도메인 객체는 하나의 Aggregate에만 속한다.
  + 쉽게 생각하면 **비즈니스 로직에서 사용하는 DTO**
+ 하나의 Aggregate Root와 연관 객체들로 구성한다.
  + Aggregate는 응집된 경계이기 때문에 내부의 모든 객체들이 동일하거나 거의 유사한 라이프사이클을 가진다. 즉, Root가 삭제될 경우 모든 연관 데이터도 삭제되어야 한다.
  + 해당 룰을 지키기 위해 영속화를 담당하는 Repsository는 Aggregate Root만이 지닌다.
+ 모든 하위 객체들은 Aggregate Root를 통하여 접근한다.
+ 다른 Aggregate에서의 참조는 Aggregate Root의 ID를 통하여 한다.
+ 하나의 트랜잭션에서는 하나의 Aggregate만 수정한다.

<br>

### 참고

+ https://mesh.dev/20210910-dev-notes-007-hexagonal-architecture
+ https://zkdlu.tistory.com/4
+ https://velog.io/@nkjang/Hexagonal-Architecture-with-Java-and-Spring
+ https://www.jiniaslog.co.kr/article/view?articleId=1152
+ https://shinsunyoung.tistory.com/82