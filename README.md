# 외부 설정 1부
## 사용할 수 있는 외부 설정
- properties
- YAML
- 환경변수
- 커맨드 라인 아규먼트

## 프로퍼티 우선 순위
1. 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties
2. 테스트에 있는 @TestPropertySource
3. @SpringBootTest 애노테이션의 properties 애트리뷰트
4. 커맨드 라인 아규먼트
```bash
java -jar springinit-0.0.1-SNAPSHOT.jar --freelife.name=mavel
```
5. SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로티) 에 들어있는 프로퍼티
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config
6. ServletConfig 파라미터
7. ServletContext 파라미터
8. java:comp/env JNDI 애트리뷰트
9.  System.getProperties() 자바 시스템 프로퍼티
10. OS 환경 변수
11. RandomValuePropertySource
12. JAR 밖에 있는 특정 프로파일용 application properties
13. JAR 안에 있는 특정 프로파일용 application properties
14. JAR 밖에 있는 application properties
15. JAR 안에 있는 application properties
16. @PropertySource
17. 기본 프로퍼티 (SpringApplication.setDefaultProperties)

## Bug 발생 포인트
> test package에 main package에 작성된 property 변수가 없으면 Error가 발생함  
> 이유는 main package를 먼저 빌드해서 classpath에 넣고 test package를 빌드 할 때  
> main package의 properties 파일을 오버라이딩 하는데  
> 변수 설정이 다르면 기존 변수를 없애버리므로 오류가 발생함  
> 해결법은 똑같이 properties 를 맞춰주거나 아래의 세가지 방법으로 해결함  
#### 4순위 방법 `@SpringBootTest` Annotation에 properties 직접 지정
```java
@SpringBootTest(properties = "freelife.age=2")
```

#### 3순위 방법으로 `@TestPropertySource`에 properties 직접 지정
```java
@TestPropertySource(properties = {"freelife.name=superman", "freelife.age=23"})
```

#### 2순위 방법 `@TestPropertySource`에 추가로 생성한 `test.properties` 파일 locations 지정
> main package application.properties 파일이 classpath에 생성된 후 동일한 property 변수를 덮어씀
```java
@TestPropertySource(locations = "classpath:/test.properties")
```

### application.properties 우선 순위 (높은게 낮은걸 덮어 씁니다.)
> 아래의 네가지 위치에 application.properties가 위치할 수 있는데 위치에 따라 우선순위가 달라짐  
1. file:./config/
2. file:./
3. classpath:/config/
4. classpath:/

## application.properties 랜덤값 설정하기
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-random-values
> random으로 값을 줄때 공백이 있으면 안됨 `${random.int(0,100)}` 이렇게 줘야됨  
- ${random.*}

## 플레이스 홀더
> 위에서 사용한 변수는 재사용할 수 있음
- name = freelife
- fullName = ${name} baik

## Command Line Application 끄는법
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-command-line-args
```java
SpringApplication.setAddCommandLineProperties(false)
```