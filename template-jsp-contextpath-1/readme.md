# 스프링부트 JSP 예제

스프링부트는 jsp를 지원하지 않기 때문에 jar 빌드 시 작동이 되지 않는다.

war 빌드하여 작동해보자.

contextPath 설정 /app

클라이언트 요청 http://localhost:8081/app/test (예시)

매핑주소 /list

return 주소 list (prefix = /WEB-INF/views, suffix = .jsp)

static jsp 가져올때 주소 /app/WEB-INF/views/list.jsp (숨겨져 있어서 외부에서 볼 수 없다.)

static 리소스 가져올때 주소 /app/img/cat01.jpg

<br/>
<br/>

# build.gradle

디펜던시에 jsp용 라이브러리 추가

```groovy
dependencies {
  // (생략)
	// jsp용 라이브러리
	implementation "org.apache.tomcat.embed:tomcat-embed-jasper"
	implementation 'javax.servlet:jstl:1.2'
}
```

<br/>

war 빌드를 하려면 아래와 같이 설정

```groovy
plugins {
  // (생략)
	id 'war' // war로 빌드하려면 추가하여야한다
}
```

<br/>
<br/>

# application.yml

contextPath 프로젝트 context이름 설정
port 포트번호 설정
prefix, suffix 템플릿 파일 경로 세팅

```yaml
server:
  servlet:
    context-path: /app
  port: 8081
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
```

<br/>
<br/>

# TempController

파일 내 주석 참고

<br/>
<br/>

# 빌드

## jar로 빌드 시

gradle의 plugins에 id 'war'가 없는지 확인하고 터미널에서

```
./gradlew build
(테스트 등 사전작업을 다 하고 jar bootJar 빌드 - 추천)
```

또는

```
./gradlew bootJar
(내장톰캣 bootJar만 빌드 - 추천)

./gradlew jar
(외장톰캣 jar만 빌드)
```

<br/>
<br/>

## war로 빌드 시

gradle에 id 'war' 추가 한 뒤 터미널에서

```
./gradlew build
(테스트 등 사전작업을 다 하고 war bootWar 빌드 - 추천)
```

또는

```
./gradlew bootWar
(내장톰캣 bootWar만 빌드 - 추천)

./gradlew war
(외장톰캣 war만 빌드)
```

<br/>

# 실행

## bootJar , bootWar

터미널에서

```
java -jar demo.jar

또는

java -jar demo.war
```

<br/>
<br/>

## jar , war

톰캣 설치 후 톰캣의 webapps 폴더에 war 파일을 넣고 톰캣을 실행한다.

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

# p.s.

> [서버 내부와 외부 주소](https://i.imgur.com/RYGntJI.png)

> [jsp war 배포](https://hye0-log.tistory.com/28)
