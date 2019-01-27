# 로깅 2부: 커스터마이징
## 커스텀 로그 설정 파일 사용하기
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-custom-log-configuration
  
> 위의 사항보다 더 디테일하게 로그 설정하기 위해서 로그파일을 추가하여 설정  
- Logback: logback-spring.xml
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-configure-logback-for-logging
- `logback.xml`, `logback-spring.xml` 로 로그설정파일을 추가할 수 있는데 `logback-spring.xml`파일을 추가하는 것을 추천
- 추가 Extention을 사용할 수 있음
- `logback.xml`은 너무 일찍 로딩이 되므로 추가 Extention을 사용할 수 없음
- Logback extention
  - 프로파일 `<springProfile name="프로파일">`
  - Environment 프로퍼티 `<springProperty>`

### logback-spring.xml 파일 추가 및 설정
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html#howto-configure-log4j-for-logging
#### logback-spring.xml 설정
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
	<include resource="org/springframework/boot/logging/logback/base.xml"/>
	<logger name="org.springframework.web" level="DEBUG"/>
</configuration>
```

#### logback-spring.xml profiles 설정
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#_profile_specific_configuration
```xml
<springProfile name="staging">
	<!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev | staging">
	<!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
	<!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
``` 

- Log4J2: log4j2-spring.xml
- JUL(비추): logging.properties

## 로거를 Log4j2로 변경하기
#### pom.xml에 Log4j2 설정
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```