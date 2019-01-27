# SpringApplication 2부
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-spring-application.html#boot-features-application-events-and-listeners

### ApplicationEvent 등록
> ApplicationContext를 만들기 전에 사용하는 리스너는 @Bean으로 등록할 수 없다  
  
#### SampleListner 작성
> ApplicationStartingEvent 는 ApplicationContext 가 만들어기 전에 발생하는 이벤트이므로 빈등록 설정을 해도 빈으로 등록할 수 없음
```java
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("=======================");
        System.out.println("Application is Starting");
        System.out.println("=======================");
    }
}
```

#### addListners()
```java
application.addListeners(new SampleListener());
```

#### 빈으로 등록 가능한 Listener 예시
> ApplicationStartedEvent 의 경우 ApplicationContext 가 만들어진 다음에 발생하는 이벤트이므로 빈으로 등록해서 서용 가능
```java
@Component
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("===================");
        System.out.println("Application Started");
        System.out.println("===================");
    }
}
```

## WebApplicationType 설정
> `SERVLET`이 없고 `REACTIVE`로 동작하지만 `SERVLET`이 있으면 무조건 `SERVLET`로 동작함  
> Spring MVC가 들어있으면 기본적으로 `SERVLET`로 동작함  
> Spring Webfulx가 들어있으면 기본적으로 `REACTIVE`로 동작함  
> `SERVLET`, `REACTIVE`둘다 없으면 NONE로 동작  

### WebApplicationType 종류
#### NONE : WebApplication 사용안함 설정
```java
application.setWebApplicationType(WebApplicationType.NONE);
```
#### SERVLET : SERVLET 사용 설정
```java
application.setWebApplicationType(WebApplicationType.SERVLET);
```
#### REACTIVE : REACTIVE 사용 설정
```java
application.setWebApplicationType(WebApplicationType.REACTIVE);
```

## Application Arguments
> `-D` 들어오는 옵션은 `JVM` 옵션 `--`로 들어오는 옵션은 Program arguments  

### TEST Code
> `--`으로 옵션을 준 bar만 Application Arguments 이므로 true로 출력됨
```java
@Component
public class SampleArguments {

    public SampleArguments(ApplicationArguments arguments) {
        System.out.println("foo: "+arguments.containsOption("foo"));
        System.out.println("bar: "+arguments.containsOption("bar"));
    }
}
```

### Console 에서 테스트
1. 먼저 Maven Clean 후 패키징
```bash
mvn clean package -DskipTests
```

2. target 폴더에서 java -jar 명령으로 Test
```bash
 java -jar springinit-0.0.1-SNAPSHOT.jar --foo --bar
```

## Application 실행한 뒤 뭔가 실행하고 싶을때
> 두가지가 있지만 ApplicationRunner 사용을 추천함  
> 둘다 `JVM` 옵션은 받지 못하고 Application Arguments 옵션(`--`)으로 준 값만 받을 수 있음  
> ApplicationRunner이 여러개이면 `@Order(1)` 로 순서를 주어 우선순위를 정할 수 있음 숫자가 낮을 수록 우선순위가 높음  

### ApplicationRunner
> 아래와 같이 ApplicationRunner 를 상속받아 코드를 작성하면 Application 실행한뒤 로직을 실행함
```java
@Component
public class SampleRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("foo: "+args.containsOption("foo"));
        System.out.println("bar: "+args.containsOption("bar"));
    }
}
```

### CommandLineRunner
```java
@Component
public class SampleCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
    }
}
```