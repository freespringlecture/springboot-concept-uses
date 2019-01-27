# 스프링 REST 클라이언트 1부: RestTemplate과 WebClient
## RestTemplate
- Blocking I/O 기반의 Synchronous API
- RestTemplateAutoConfiguration
- 프로젝트에 spring-web 모듈이 있다면 RestTemplate​Builder​를 빈으로 등록
- https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#rest-client-access

## WebClient
- Non-Blocking I/O 기반의 Asynchronous API
- WebClientAutoConfiguration
- 프로젝트에 spring-webflux 모듈이 있다면 WebClient.​Builder​를 빈으로 등록
- https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client


## RestTemplate 실습
> spring-web 모듈이 있으면 RestTemplate를 사용할 수 있음  
> RestTemplate가 클래스패스에 있으면 RestTemplateAutoConfiguration 사용할 수 있고 RestTemplateBuilder가 빈으로 등록됨  
> RestTemplateBuilder를 주입받아서 사용하는 테스트코드  

#### web만 의존성 추가해서 프로젝트 생성

#### SampleController 생성
```java
@RestController
public class SampleController {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        Thread.sleep(5000l);
        return "hello";
    }

    @GetMapping("/world")
    public String world() throws InterruptedException {
        Thread.sleep(3000l);
        return "world";
    }
}
```

#### RestRunner 생성
> Blocking I/O 기반의 Syncronous(동기식) 방식 API 처리 테스트 8초정도 소요  
  
```java
@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RestTemplate restTemplate = restTemplateBuilder.build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // TODO /hello
        String helloResult = restTemplate.getForObject("http://localhost:8080/hello", String.class);
        System.out.println(helloResult);

        // TODO /world
        String worldResult = restTemplate.getForObject("http://localhost:8080/world", String.class);
        System.out.println(worldResult);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
```

## WebClient 실습
> Webflux의 Non-Blocking I/O 기반의 Asynchronous API 처리 테스트  
> Stream API는 Subscribe 하기 전에는 Stream이 흐르지 않음 그냥 담아만 놓은 것과 같음  
> Subscribe로 칸막이를 열어줘야 동작함  
> 다양한 호출을 하기위해 추천함  
#### RestRunner WebClient로 로직 변경  
```java
@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    WebClient.Builder builder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = builder.build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Stream API는 Subscribe 하기 전에는 Stream이 흐르지 않음 그냥 담아만 놓은 것 뿐임
        // 아무동작을 하지 않고 단지 Mono만 만들어 둠
        Mono<String> helloMono = webClient.get().uri("http://localhost:8080/hello") // get으로 /hello 요청
                .retrieve() // 응답값을 가져옴
                .bodyToMono(String.class);// Mono Type으로 변경

        // hello subscribe 결과값은 String
        // 비동기방식 이라 subscribe하고 Callback이 오면 로직을 수행
        helloMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) { // stopwatch가 실행중이면 종료
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });


        Mono<String> worldMono = webClient.get().uri("http://localhost:8080/world")
                .retrieve()
                .bodyToMono(String.class);

        worldMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });
    }
}
```