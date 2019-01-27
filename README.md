# 로깅 1부: 스프링 부트 기본 로거 설정
https://docs.spring.io/spring/docs/5.0.0.RC3/spring-framework-reference/overview.html#overview-logging
- Spring Core가 Commons Logging으로 Logging 하도록 기본 설정되어있음
- Spring-JCL이 Commins Loggins -> SLF4j or Log4j2 로 자동으로 보내도록 설정해줌
- SpringBoot에서 Commons Logging -> SLF4j -> Logback 로 보내서 Logback에서 로그를 찍도록 자동으로 변경설정함

## 스프링 부트 로깅
- 기본적인 포맷 형식으로 로그가 출력됨
### 핵심 라이브러리 디버깅
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-logging-console-output
  
> 일부 핵심 라이브러리(embeded container, Hibernate, Spring Boot)만 디버깅 모드로 출력  
- program arguments: --debug
- JVM Option: -Ddebug
### 모든 라이브러리 디버깅
> 전부 다 디버깅 모드로 `--trace`

### 컬러출력
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-logging-color-coded-output
  
> `spring.output.ansi.enabled=always`  

### 파일출력
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-logging-file-output
- `logging.file` 또는 `logging.path`로 설정
- 10MB 마다 파일을 Rolling 이 되고 나머지는 Archive가 됨
- `logging.file.max-size`, `logging.file.max-history` 등 옵션 설정 가능
```
logging.path=logs
```

### 로그 레벨 조정
> `logging.level.패키지 = 로그레벨` 로 로그레벨 지정 가능  
- application.properties
```
logging.level.me.freelife.springinit=DEBUG
```
#### java
```java
private Logger logger = LoggerFactory.getLogger(SampleRunner.class);

logger.info(hello);
```