# Swarmpit 사용하기

Swarmpit 사용하는 방법을 정리하기 전에 기본적인 개념을 먼저 정리하였다.

<br>

## What is Docker?

간단하게 Docker를 짚고 가자면, Docker란 프로세스 격리 기술들을 사용해 컨테이너로 실행하고 관리하는 플랫폼이다.

컨테이너 기술은 애플리케이션을 작동시키기 위해 필요한 라이브러리나 애플리케이션 등을 하나로 모아 마치 별도의 서버인 것처럼 사용할 수 있게 만든 것이다.

Docker는 즉, **하나의 컴퓨터를 여러 대처럼 사용**할 수 있도록 **환경을 분리**해 준다.

하드웨어 스택을 가상화하는 가상머신과 달리 OS 체제에서 가상화를 실시하여 다수의 컨테이너를 OS 커널에서 직접 구동한다. 따라서 훨씬 가볍고 빠르며, 메모리를 훨씬 적게 차지한다.

- **Image** : 읽기만 가능함. 도커 컨테이너를 구성하는 파일 시스템과 실행할 애플리케이션 설정을 하나로 합친 것이다.
- **Container** : 쓰기 가능함. 도커 이미지를 기반으로 생성되며, 파일 시스템과 애플리케이션이 구체화되어 실행되는 상태이다.

Image는 하나의 프로그램 같은 느낌이며, Image를 실행한 Container는 프로세스 같은 느낌이다. 하나의 Container는 하나의 Image만 담을 수 있다.

- **Dockerfile** : 도커 이미지를 만드는 설명서. 최소한의 패키지를 설치하고 동작하기 위한 프로젝트만의 설정을 담은 파일이다.
- **Registry :** Github 같은 이미지 저장소. 예로는 Dockerhub, ECR 등이 있다.
- **Volumne** : 컨테이너 데이터 저장소. 컨테이너들은 휘발성이기 때문에 데이터를 저장할 수 없다. 그래서 Volumne을 통해 데이터를 저장한다.

<br>

## What is Docker Swarm?

Docker Swarm은 여러 호스트 서버의 컨테이너들을 쉽게 배포 및 관리할 수 있도록 하는 Orchestration이다.

Docker Swarm은 여러 컨테이너를 클러스터로 만들어 관리해준다.

- **Cluster** : 컨테이너 형태의 애플리케이션을 호스팅하는 물리/가상 환경의 노드들로 이루어진 집합을 말한다.
- **Node** : 1 Node = 1 Server
- **Service** : 기본적인 배포 단위이다.
- **Task** : 컨테이너의 배포 단위로서, 하나의 서비스로 여러 개의 Task를 실행시킬 수 있다.

**Swarmpit**이란, Docker Swarm을 관리하기 쉽게 **GUI 형태로 확인**할 수 있는 도구이다. Swarmpit의 Autoredeploy는 주기적으로 도커 이미지가 업데이트 되었는지 확인하여 해당 해시값과 현재 이미지의 해시값을 비교해서 다르면 업데이트를 진행한다.

<br>

## What is DockerHub?

Docker Hub는 Docker 이미지를 저장하는 Image Registry이다. 일종의 app store라고 생각하면 조금 쉽게 다가갈 듯 하다.

Docker Hub에는 누구나 이미지를 업로드할 수 있다.

이미지를 검색했을 때 `Official Images`(공식 이미지), `Verified Publisher`(인증된 퍼블리셔) 태그가 붙어있는 것만 사용하는 것을 권장한다.

> 프리미엄에 가입하지 않는 경우에는 Priviate 이미지는 한 계정 당 하나의 이미지만 등록할 수 있다.

<br>

## Setting

#### ./Dockerfile

```yaml
FROM openjdk:11-jre-slim
EXPOSE 8080

COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

#### ./github/workflows/docker-image.yml

```yaml
name: Github Actions

on:
  push:

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
      - name: Checkout
        uses: actions/checkout@v2

      - name: Docker meta
        id: docker_meta
        uses: crazy-max/ghaction-docker-meta@v1
        with:
          images: teichae/node
          tag-semver: |
            {{version}}
            {{major}}.{{minor}}

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v1

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
        shell: bash
        
      - name: Build
        run: ./gradlew build
        shell: bash
        
      - name: Login to DockerHub
        uses: docker/login-action@v1
        with:
          username: ${{ secrets.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}

      - name: Docker Image Build
        run: docker build -t syxxn/shavizu .

      - name: Upload to DockerHub
        run: docker push syxxn/shavizu
```

#### EC2 명령어

Docker Swarm을 사용할 때는 JDK, Git 등을 설치하지 않아도 된다. Docker, Swarmpit만 설치하면 된다.

아래 링크를 참고하여 설정해주면 된다.

[[Deploy\] Github Action과 Docker Swarm으로 배포하기](https://velog.io/@hanif/Deploy-Github-Action과-Docker-Swarm으로-배포하기?fbclid=IwAR2gfUsud4a6sgHx4FGINmSN9QJUPiZRNe4mhwkPe8YTmwcaYq_LTnP0PvM)

- Docker 설치 https://docs.docker.com/engine/install/ubuntu/#install-using-the-convenience-script

  ```yaml
  $ curl -fsSL <https://get.docker.com> -o get-docker.sh
  $ sudo sh get-docker.sh
  ```

- Docker swarm 설정

  ```yaml
  $ docker swarm init
  ```

- Swarmpit 설치

  ```yaml
  $ docker run -it --rm \\
    --name swarmpit-installer \\
    --volume /var/run/docker.sock:/var/run/docker.sock \\
  swarmpit/install:1.9
  ```

#### 보안그룹 설정

- 아웃바운드는 모든 TCP를 열어준다.

- 인바운드는 DB, 각 서버에서 사용하는 Port를 열어준다.

  또한, Swarmpit 접속을 위하여 `888` Port,

  노드간 통신을 위하여 TCP/UDP `7946` Port,

  오버레이 네트워크 간 트래픽을 위해 `4789` Port,

  클러스터 매니지먼트에서 사용할 수 있도록 2377 Port를 추가적으로 열어주어야 한다.