# 4. 자동 설정 만들기
> SpringBoot 프로젝트 정의 패턴
- Xxx-Spring-Boot-Autoconfigure 모듈: 자동 설정
- Xxx-Spring-Boot-Starter 모듈: 필요한 의존성 정의

## xxx-spring-boot-starter 프로젝트 생성
> xxx-spring-boot-starter 예제 프로젝트는 위의 사항을 통합하여 진행
> Java 버전은 1.8에서 동작함

1. 의존성 추가
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure-processor</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.0.3.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
2. Holoman model 클래스 생성
3. `@Configuration` 파일 HolomanConfiguration 생성
4. `src/main/resource/META-INF에 spring.factories` 파일 만들기
5. `spring.factories` 안에 자동 설정 파일 추가
6. `mvn install` or Intellij에서 Maven 텝에 `Lifecycle` - `install` 클릭
   > 다른 Maven 프로젝트에서도 쓸 수 있도록 Local Maven 저장소에 설치를 함
7. 다른 프로젝트에서 생성된 프로젝트 의존성 추가
8. Runner로 불러와서 사용

## xxx-spring-boot-autoconfigure 프로젝트 생성

### HolomanRunner 정의
```java
@Component
public class HolomanRunner implements ApplicationRunner {

  @Autowired
  Holoman holoman;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println(holoman);
  }
}
```

### Maven Repositories에 정의
> stater 프로젝트를 install 해서 생성된 모듈을 dependency에 추가하면 autoconfigure로 자동 적용됨 
```xml
<dependency>
    <groupId>me.freelife</groupId>
    <artifactId>springboot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## @ConfigurationProperties
- 빈 재정의 문제점
> `@SpringBootApplication` 에서 `@ComponentScan`으로 먼저 등록된 빈이 있으면 나중에 등록되는 빈은 무시됨

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
            SpringApplication application = new SpringApplication(Application.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
    }

    @Bean
    public Holoman holoman() {
        Holoman holoman = new Holoman();
        holoman.setName("freelifeBlack");
        holoman.setHowLong(60);
        return holoman;
    }
}
```

### 덮어쓰기 방지하기
- @ConditionalOnMissingBean
### 빈 재정의 수고 덜기
- @ConfigurationProperties(“holoman”)
- @EnableConfigurationProperties(HolomanProperties)
- 프로퍼티 키값 자동 완성
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

### @ConditionalOnMissingBean
> 재정의 한 빈이 동작되도록 AutoConfiguration 프로젝트를 개선
- xxx-spring-boot-starter 프로젝트에 빈 설정에 `@ConditionalOnMissingBean` 설정
```java
@Configuration
public class HolomanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Holoman holoman() {
        Holoman holoman = new Holoman();
        holoman.setHowLong(5);
        holoman.setName("freelife");
        return holoman;
    }
}
```

### application.properties에 정의 해서 쓰기
> 아래와 같이 설정하면 빈을 재정의 할 필요 없이 Properties만 재정의 하면됨  
> 재정의된 빈이 있으면 재정의된 빈을 우선으로 적용함  

#### xxx-spring-boot-starter 프로젝트 설정
1. HolomanProperties 파일 생성
```java
@ConfigurationProperties("holoman")
public class HolomanProperties {

    private String name;
    private int howLong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }
}
```

2. Configuration 파일에 `@EnableConfigurationProperties` 설정 추가
- HolomanProperties 를 주입받아 처리하도록 수정
```java
@Configuration
@EnableConfigurationProperties(HolomanProperties.class)
public class HolomanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Holoman holoman(HolomanProperties properties) {
        Holoman holoman = new Holoman();
        holoman.setHowLong(properties.getHowLong());
        holoman.setName(properties.getName());
        return holoman;
    }
}
```