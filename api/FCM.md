## FCM

FCM이 알림과 관련된 건 전부터 들었지만, 어떻게 작동하는지 자세하게 몰라 찾아보았다.

<br>

#### FCM이란,

기존에는 Android, iOS, Web 등의 플랫폼에서 푸시 알림을 보내기 위해서는 각 플랫폼 환경(APNS, GCM)별로 개발해야 했다.

FCM(Firebase Cloud Messaging)이란, 무료로 사용할 수 있는 메시지 및 알림을 위한 교차 플랫폼 메시징 솔루션이다.

> 교차 플랫폼 메시징 솔루션이란?
>
> + 플랫폼 : 소프트웨어가 구동 가능한 하드웨어 아키텍처나 소프트웨어 프레임워크의 종류
>
> 플랫폼에 종속되지 않고 Anroid, iOS, 웹 애플리케이션 등에서 사용할 수 있는 기술이다.

+ 모든 사용자에게 알림 메시지를 전송할 수도 있고, 그룹을 지어 메시지를 전송할 수도 있다.
+ FCM은 요금 정책에 구분 없이 무료로 사용할 수 있다.

<br>

#### 주요 특징

+ 메시지 타입

  + 알림 메시지/데이터 메시지 (보통 혼용해서 사용한다)

    > 알림 메시지는 백그라운드일 때 : 앱 화면이 안 보일 때
    >
    > 데이터 메시지는 포그라운드일 때 : 앱 화면을 보고 있을 때

  + 푸시 알림은 `알림 메시지`를 이용하고, 알림 메시지를 클릭하여 특정 페이지를 이동하거나 이벤트는 `데이터 메시지`를 통해 이루어진다.

+ 타켓팅

  + 단일 기기/기기 그룹/주제 구독

+ 업 스트림 메시지

  + 클라이언트에서 서버로 보내는 메시지를 말한다.

    > Stream : 일련의 연속성을 갖는 흐름
    >
    > 업 스트림 메시지 : 앱 -> 서버
    >
    > 다운 스트림 메시지 : 서버 -> 앱

<br>

#### 작동 원리

+ FCM에서는 크게 송신자, FCM Backend Server, 수신자로 구분된다.
  + 보통 송신자는 앱서버, 수신자는 모바일이 된다.
+ 저장해둔 앱 디바이스 토큰과 메시지를 FCM Backend Server에 POST로 요청한다.

+ 실제 기기로 메시지를 전달하는 것은 FCM Backend에서 처리한다.

예를 들어, A가 B한테 채팅을 보낸다고 하자. 채팅 전송 로직 안에 FCM Backend Server를 호출하는 로직을 추가한다. 그러면, A가 B한테 채팅을 보냄과 동시에 FCM Backend 로직이 실행되어 B에게 알림이 갈 것이다.
