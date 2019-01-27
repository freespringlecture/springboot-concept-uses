# 외부 설정 2부
## 타입-세이프 프로퍼티 @ConfigurationProperties
- `@Value`로 값을 받아오는 것 보다 추천 하지만 `@Value`는 SpEL 표현식을 사용할 수 있는 장점
- `@ConfigurationProperties`으로 여러 properties를 묶어서 읽어올 수 있음
- 빈으로 등록해서 다른 빈에 주입할 수 있음
  - `@EnableConfigurationProperties`, `@Component`, `@Bean`
- `@ConfigurationProperties`를 추가하면 META 정보를 생성해주는 플러그인 설치안내가 뜸
  - https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#configuration-metadata-annotation-processor
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
  </dependency>
  ```
  
> `@ConfigurationProperties`로 설정한 변수들은 META정보를 생성해줘서 나중에 application.properties에서 자동완성을 지원함
#### 기존방식 아래와 같이 했으나 자동으로 `@EnableConfigurationProperties` 설정이 되어있으므로 패스
```java
@SpringBootApplication
@EnableConfigurationProperties(FreelifeProperties.class)
```

#### Properties 클래스에 `@Component` 설정을 하여 빈으로 만들어주기만 하면됨
```java
@Component
@ConfigurationProperties("freelife")
```

#### 아래와 같이 값을 주입받아 가져올 수 있음
```java
@Autowired
FreelifeProperties freelifeProperties;

@Override
public void run(ApplicationArguments args) throws Exception {
    System.out.println("=======================");
    System.out.println(freelifeProperties.getFullName());
    System.out.println(freelifeProperties.getAge());
    System.out.println("=======================");
}
```

### 융통성 있는 바인딩
> 아래와 같이 입력하면 알아서 다 자동 맵핑 해줌  
- context-path (케밥)
- context_path (언드스코어)
- contextPath (캐멀)
- CONTEXTPATH
  
> SpringFramework 가 지원하는 컨버전 서비스를 통해서 기본적으로 타입을 컨버팅 해줌  
### 프로퍼티 타입 컨버전 `@DurationUnit`
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config-conversion-duration
  
> SpringBoot가 제공하는 독특한한 컨버전 타입  
> 시간정보를 받고 싶을 떄 아래의 타입을 잘 명시해주면 Annotation을 활용하지 않아도 시간정보를 잘가져옴  
- `ns` for nanoseconds
- `us` for microseconds
- `ms` for milliseconds
- `s` for seconds
- `m` for minutes
- `h` for hours
- `d` for days

### 프로퍼티 값 검증
> FailureAnalyzer Validated 검증 메세지를 출력해줌  
- Class에 `@Validated` 붙이면 검증가능
- JSR-303 (@NotNull, ...)

### 메타정보생성
### @Value
- SpEL 을 사용할 수 있지만...
- 위에 있는 기능들은 전부 사용 못합니다