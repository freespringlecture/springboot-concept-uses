# SpringApplication 1부
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-spring-application.html#boot-features-spring-application

## Debug 모드 찍기
- Configuration - VM options 에 `-Ddebug` 입력
- Configuration - Program arguments 에 `--debug` 입력

## FailureAnalyzer
> 로그를 예쁘게 출력하게 해줌

## 배너
- 배너는 MANIF  
- resource 폴더에 banner.txt | gif | jpg | png 파일을 넣으면 배너를 출력 해줌  

### 우선순위
> banner.txt 파일이 있으면 우선 실행  

#### 다른 위치에 놓고 싶을 시
`spring.banner.location` 에 위치를 지정하면 된다

#### 배너를 끄고 싶을 때
```java
application.setBannerMode(Banner.Mode.OFF);
```

### 배너를 Configuration 에서 설정 시
```java
application.setBanner(new Banner() {
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println("=========================");
        out.println("FREELIFE");
        out.println("=========================");
    }
});
```

## SpringApplicationBuilder로 빌더 패턴 사용하기
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .run(args);
    }
}
```