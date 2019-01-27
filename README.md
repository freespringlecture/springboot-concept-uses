# 스프링 REST 클라이언트 2부: 커스터마이징

## 지역적으로 커스터마이징
> 지역적으로 선언한 webClient에 baseUrl을 설정하고 build 하고 아래쪽에서 uri 요청 시 baseUrl은 제외하고 요청  
```java
@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    WebClient.Builder builder;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = builder
                .baseUrl("http://localhost:8080")
                .build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Mono<String> helloMono = webClient.get().uri("/hello") // get으로 /hello 요청
                .retrieve() // 응답값을 가져옴
                .bodyToMono(String.class);// Mono Type으로 변경

        helloMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) { // stopwatch가 실행중이면 종료
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });

        Mono<String> worldMono = webClient.get().uri("/world")
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

#### 생성자를 통해서 클래스안에서 공유하는 webClient 설정법
```java
WebClient webClient;
public RestRunner(WebClient.Builder builder) {
    this.webClient = builder.build();
}
```

## 전역적으로 커스터마이징
- WebClientCustomizer 사용  
> baseUrl을 적용한뒤 빈으로 등록해서 전역적으로 사용  
> 모든 Builder는 baseUrl을 적용한대로 설정이되서 다른 빈들에 주입이 됨  
> `WebClientCustomizerAutoConfiguration`에서 만들어주는 Builder가 customizer에 기본적으로 들어옴  
> `WebClientBuilder` 자체를 다시 등록할 수도 있음  
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebClientCustomizer webClientCustomizer() {
        return new WebClientCustomizer() {
            @Override
            public void customize(WebClient.Builder webClientBuilder) {
                webClientBuilder.baseUrl("http://localhost:8080");
            }
        };
    }
}
```

## HTTP 클라이언트 변경
## RestTemplate
- 기본으로 java.net.HttpURLConnection 사용
- 커스터마이징
  - 로컬 커스터마이징
  - 글로벌 커스터마이징
    - RestTemplateCustomizer
    - 빈 재정의

## WebClient
- 기본으로 Reactor Netty의 HTTP 클라이언트 사용
- 커스터마이징
  - 로컬 커스터마이징
  - 글로벌 커스터마이징
    - WebClientCustomizer
    - 빈 재정의

### Apache HttpClient로 변경 실습
> RestTemplate를 Apache HttpClient로 변경해서 사용가능  

#### Apache HttpClient 의존성 추가
```xml
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
```

#### RestTemplate Apache HttpClient를 사용하도록 변경  
> 각각 원하는 구현체에 맞는Adaptor형식에 RequestFactory가 있음  
> 스프링의 PSA(Potable Service Acception)가 잘 적용이 되어있음  
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer() {
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            }
        };
    }
}
```