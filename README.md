# 2. 스프링 부트 시작하기
> Spring Initializr로 생성하면 아래의 사항들이 자동으로 설정됨

1. 스프링 부트 프로젝트 생성
2. 스프링 부트 pom.xml 설정
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-introducing-spring-boot
3. 패키지 생성
4. Application 클래스 생성
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
5. maven build
> maven 기반의 java 프로젝트라서 `target/spring-boot-getting-started-1.0-SNAPSHOT.jar` 파일이 생성됨
```bash
mvn package
```

6. jar 파일 실행
> jar 파일을 실행하면 웹 어플리케이션이 동작함
```bash
java -jar target/spring-boot-getting-started-1.0-SNAPSHOT.jar
```