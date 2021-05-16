---
title:      "도커 이미지 빌드 & 최적화 하기"
subtitle:   "spring boot app"
date:       2021-05-13
author:     "Park Ji Hoon"
header-img: "img/post_docker/docker-logo.png"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - docker

---
> Spring Boot App 을 Docker Image 로 build 하는데 고려해야하는 사항들을 정리했다.
> spring boot project source : https://gitlab.com/marketing-blog/marketing-blog-dashboard-server
> [%] 의 표시 : 필자의 의견  

## Intro
- 목표 : Spring Boot App 을 성공적으로 docker image 화 시킴과 동시에 최적의 이미지로 빌드하기 위한 지식을 탐구했다.

#### Docker Image 생성 구조
기존 이미지 (Base Image)를 그대로 가져다가 (각 Image 들은 Layer로 구분) 추가하려는 app만 추가하여 새로운 Image를 만든다. (App 에 해당하는 Layer를 추가)
![image create](/img/post_docker/create-image.png)
`이 과정에서 중요한 명령어는 'COPY', 'RUN' 이다.`


#### Spring boot Image build
spring boot app 을 image 로 만드는 방법은 두가지가 있다.
- spring-boot-maven-plugin 에서 제공하는 build-image 명령어로 image build
- Dockerfile 을 활용한 정통적인 Image Build
하지만 첫번째 방법은 Base Image 를 어떤걸로 설정할지 (용량이 크다.), container의 생성후 추가적인 후속 조치 등을 설정 할 수 없기 때문에 두번째 `Dockerfile`을 활용한 Image Build 방식이 주로 사용된다.


#### Dockerfile : 기본 문법
> ref : https://docs.docker.com/engine/reference/builder/`
```dockerfile
#기본적인 Flow

# 1. Base Image 설정 ( + 만든사람 표시)
FROM       openjdk:8-jdk-alpine
MAINTAINER ggoowlgns@gmail.com

# 2. A 설치 : 필요한 linux package 실행 (예제는 Base Image 가 ubuntu 기반)
RUN apt-get -y install A

# 3. 기본 dir 설정
WORKDIR /usr/src/app

# 3. 소스 복사 (arg1 : Dockerfile의 위치를 기준으로 하는 소스,
                arg2 : Image 내부의 위치)
COPY . /src

# 4. build
RUN     mvn package

# 5. 실행 : container 실행시에 돌아감
CMD  ["java", "-jar", "/src/target/app.jar]
```

**BUILD 관련**
- COPY
  - build 시에 file 을 복사하여 image로 이동
- ADD
  - 위 COPY와 유사하나 두가지 추가 기능이 존재한다.
    - Auto-extraction : 복사하는 대상의 파일이 압축 파일이면(tar, 등) 해당 파일의 압축을 해제하고 복사한다.
    - Remote-URL : wget 등을 통하여 원격지의 파일을 복사 대상으로 지정할 수 있다.
`[%] Spring Boot 같은 경우는 실행을 위해서는 jar 만 필요하므로 build machine 에서 spring project 를 build 한 후에 output.jar 만 image 로 'COPY'하는게 좋아 보인다. (maven 에서 build caching으로 효율적인 운영이 가능하겠고, image에 추가되는 src가 줄어드므로 image build 연산 시간도 축소가 가능 할 것 같다.)`
- WORKDIR
  - image 의 기본 directory 설정 : 명령어 (RUN 등)은 각 줄마다 초기화 된다.
- RUN
  - 명령어를 그대로 실행. 내부적으로 /bin/sh -c 뒤에 명령어를​ 실행하는 방식이다.
  - 주로 package 설치

- ARG
  - Dockerfile 내에서 변수 실행
  - `ARG <이름> [=기본값]`

- SHELL
  - 기본 쉘 설정 : 이후부터 실행하는 RUN, CMD, ENTRYPOINT 등에서 설정한 shell로 실행
  - `SHELL ["쉘의 경로", "파라미터"]`


**Container 생성&실행 관련**
- CMD
  - 여러 개의 CMD가​ 존재할 경우 가장 마지막 CMD만 실행된다. 한꺼번에 여러 개의 명령어를 실행하고 싶은 경우에는 run.sh를 만들어서 쓰자
  - `#중요 : 사용자가 docker run 시에 명령어 입력시 dockerfile의 cmd 는 실행이 되지 않는다.`

- ENTRYPOINT
  - CMD 와 매우 유사하다.
  - `CMD 와의 차이로 docker run 시에 명령어의 인자만 교체가 된다.`

- `[%] CMD, ENTRYPOINT 잘 사용하기`
  - 1. 컨테이너가 수행될 때 변경되지 않을 실행 명령은 CMD 보다는 ENTRYPOINT 로 정의
  - 2. 메인 프로세스에 대한 default 옵션 값을 정의 할때는 CMD를 활용하는게 좋다.
  - 3. 둘다 사용할 때 shell 형태 보다는 list format : ["args1", "args2" , ..] 로 작성하는게 좋다.

- EXPOSE
  - container의 listen port 설정
  - `EXPOSE <포트 번호>`

- VOLUME
  - 외부 file system을 mount 할 때 사용 : 파일 저장등을 하기 위해서는 container 외부로 마운트를 하고 해당 위치에 저장을 하는게 적절하다. (container 은 어느때고 날라 갈 수 있다는 염두를 하고 있어야 한다.)
  - `VOLUME ["/마운트 포인트"]`

- ENV
  - image 에 환경변수 설정
  - `ENV [key] [value]`
  - Example
    ```
    ENV DIRPATH /first
    ENV DIRNAME second
    WORKDIR $DIRPATH/$DIRNAME
    RUN ["pwd"]
    ```

- USER
  - 실행하는 user setting : 보안 이슈등을 고려하여 설정하는게 좋다. (default : root)
  - Example
    ```
    RUN ["adduser", "asa"]
    RUN ["whoami"]
    USER asa
    RUN ["whoami"]
    ```
#### Dockerfile : Spring boot 
> https://spring.io/guides/gs/spring-boot-docker/

**기본 Dockerfile**
```dockerfile
FROM openjdk:8-jdk-alpine
# 1. user, group 추가
RUN addgroup -S spring && adduser -S spring -G spring

# 2. 위에서 만든 user, group 으로 전환
USER spring:spring

# 3. spring boot build시에 생성되는 jar 파일 위치 등록
ARG JAR_FILE=target/*.jar

# 4. app.jar 로 이동
COPY ${JAR_FILE} app.jar

# 5. 실행
ENTRYPOINT ["java","-jar","/app.jar"]
```
- TIP 
  - 사용자 권한으로 실행하면 위험을 완화하는데 도움이 된다. (사용자를 루트 -> 사용자로 전환)
  - but, mvn packge로 build 한 jar 를 통 COPY는 layer 를 추가하면서 `fat jar` 를 통째로 추가하는 상황이다 (BAD) : caching을 효율적으로 한다고 볼 수 없다.


##### Spring Boot Docker Image 최적화 하기
- 위와 같이 build 한 하나의 .jar 를 통째로 copy 하여 image 를 만들면 아주 조금의 Code fix 가 있어도 통째로 file 을 upload 하기 때문에 용량이 커진다.
- build 한 jar 파일 내부를 보면
  - ![](/img/post_docker/inside-spring-boot-jar.png)
  - 개발자가 수정하는 부분은 classes 내부의 Code 인데, 3rd party lib 까지도 묶어서 image 로 push 를 하고 있었기 때문에 용량이 커지고 느려지는 상황이다.
  - 즉 레이어를 재활용(Caching)하기 위해서는 `Application Layer` 와 `3rd party library Layer`를 분리하여 운용해야 레이어 재사용률을 확장시킬수 있다.
  - [%] COPY 하는 Layer의 순서에 따라 재활용 하는 레이어가 달라진다.
    : `Dockerfile 순서에서 위->아래 로 쌓는다 : 아래로 내려갈수록 더 자주 바뀌는 소스를 넣자.`
    즉, 여기서는 `3rd party library` 부터 COPY하고 그 다음에 `Application Layer`를 COPY 하자

- build jar unpack 하기
  - spring 공식 문서에는 cmd shell 명령어를 통해서 unpack 하는 방식이 소개됨
    - ref : https://spring.io/guides/topicals/spring-boot-docker
    - ```dockerfile
        FROM openjdk:8-jdk-alpine as build
        WORKDIR /workspace/app

        COPY mvnw .
        COPY .mvn .mvn
        COPY pom.xml .
        COPY src src

        RUN ./mvnw install -DskipTests
        RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

        FROM openjdk:8-jdk-alpine
        VOLUME /tmp
        ARG DEPENDENCY=/workspace/app/target/dependency
        COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
        COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
        COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
        ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]
      ```
    - `하지만 이보다 pom으로 unpack, 분리 하는 방법이 더 깔끔해 보여서 채택했다.`

  - **pom.xml 에 Unpack plugin 추가**
    - pom.xml
      - ```xml
        <?xml version="1.0" encoding="UTF-8"?>
            <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
                <parent>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-parent</artifactId>
                    <version>2.4.0</version>
                    <relativePath/> <!-- lookup parent from repository -->
                </parent>
                <groupId>com.jhpark.marketing</groupId>
                <artifactId>marketing-blog</artifactId>
                <version>0.0.1</version>
                <name>blog</name>
                <description>Marketing Blog</description>

                <properties>
                    <java.version>1.8</java.version>
                    <jar.unpack.app.dir>${project.build.directory}/unpack-app</jar.unpack.app.dir>
                    <jar.unpack.lib.dir>${project.build.directory}/unpack-lib</jar.unpack.lib.dir>
                </properties>

                <dependencies>

                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-freemarker</artifactId>
                    </dependency>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                    </dependency>

                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-devtools</artifactId>
                        <scope>runtime</scope>
                        <optional>true</optional>
                    </dependency>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-test</artifactId>
                        <scope>test</scope>
                    </dependency>

                </dependencies>

                <build>
                    <finalName>${project.artifactId}-${project.version}</finalName>
                    <plugins>
                        <plugin>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-maven-plugin</artifactId>
            <!--				<configuration>-->
            <!--					<executable>true</executable>-->
            <!--				</configuration>-->
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-dependency-plugin</artifactId>
                            <version>3.1.1</version>
                            <executions>
                                <execution>
                                    <id>unpack</id>
                                    <phase>package</phase>
                                    <goals>
                                        <goal>unpack</goal>
                                    </goals>
                                    <configuration>
                                        <artifactItems>
                                            <artifactItem>
                                                <groupId>${project.groupId}</groupId>
                                                <artifactId>${project.artifactId}</artifactId>
                                                <version>${project.version}</version>
                                                <destFileName>${project.build.finalName}</destFileName>
                                            </artifactItem>
                                        </artifactItems>
                                        <outputDirectory>${jar.unpack.app.dir}</outputDirectory>
                                    </configuration>
                                </execution>
                            </executions>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-antrun-plugin</artifactId>
                            <executions>
                                <execution>
                                    <id>move-lib</id>
                                    <phase>package</phase>
                                    <configuration>
                                        <target>
                                            <move todir="${jar.unpack.lib.dir}">
                                                <fileset dir="${jar.unpack.app.dir}/BOOT-INF/lib"/>
                                            </move>
                                        </target>
                                    </configuration>
                                    <goals>
                                        <goal>run</goal>
                                    </goals>
                                </execution>
                            </executions>
                        </plugin>

                    </plugins>
                </build>
            </project>
        ```
        -> output
        ```
        target
        ├── _config.yml
        ├── unpack-app
        │   ├── BOOT-INF
        │   │   └── classes (개발자가 작성한 source code)
        │   ├── META-INF
        │   └── org
        └── unpack-lib
            ├── 각종 3rd party lib 들 (BOOT-INF/lib 에 있던 jar 들)
            └── ...
        ```
    - Dockerfile :최종 결과물
        ```dockerfile
        FROM openjdk:8-jre-alpine
        # 1. user, group 추가
        RUN addgroup -S spring && adduser -S spring -G spring

        # 2. 위에서 만든 user, group 으로 전환
        USER spring:spring

        # 3. spring boot build시에 생성되는 jar 파일 위치 등록
        ARG APP_NAME=market-blog
        ARG APP_DIR=target/unpack-app/
        ARG LIB_DIR=target/unpack-lib/

        # 4. workdir 생성,설정
        RUN ["mkdir", "-p", "/home/spring/${APP_NAME}"]
        WORKDIR /home/ggoowlgns/${APP_NAME}

        # 4. src들 이동
        COPY ${LIB_DIR} BOOT-INF/lib
        COPY ${APP_DIR} .

        # 5. 실행
        ENV PROFILE=local
        ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}","org.springframework.boot.loader.JarLauncher"]
        ```
        ENV 는 run 시에 overwrite 이 가능하다.
         - $ docker run -p 5000:8099 -e "PROFILE=prod" ggoowlgns/blog:0.0.1




#### 논외 정리
- 논외 : JAVA OPTION 을 외부로 빼기
    ```dockerfile
    [Dockerfile]
    FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    ARG JAR_FILE=target/*.jar
    COPY ${JAR_FILE} app.jar
    ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]

    [run]
    $ docker run -p 8080:8080 -e "JAVA_OPTS=-Ddebug -Xmx128m" myorg/myapp
    ```


## 참고 자료들
- https://parkgaebung.tistory.com/44 [ADD, COPY 차이]
- https://bluese05.tistory.com/77 [CMD, ENTRYPOINT 차이]

- [Docker 문법 정리]
  - https://cultivo-hy.github.io/docker/image/usage/2019/03/14/Docker%EC%A0%95%EB%A6%AC/ 
  - https://freedeveloper.tistory.com/189?category=808752


- [spring boot image 최적화]
  - https://spring.io/guides/gs/spring-boot-docker/
  - https://spring.io/guides/topicals/spring-boot-docker
  - https://www.reimaginer.me/entry/optimize-spring-boot-dockerfile-on-maven 
  - https://perfectacle.github.io/2019/04/16/spring-boot-docker-image-optimization/