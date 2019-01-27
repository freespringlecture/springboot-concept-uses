# 3. 자동 설정 이해
## @SpringBootApplication
> `@SpringBootApplication`은 세개의 어노테이션이 합쳐져 있음  
> `@EnableAutoConfiguration`으로 인해 여러가지 설정이 자동으로 되고 웹 애플리케이션이 동작함  
- `@SpringBootConfiguration`
- `@ComponentScan`
- `@EnableAutoConfiguration`
  
> 빈은 사실 두 단계로 나눠서 읽힘  
- 1단계 : `@ComponentScan`
- 2단계 : `@EnableAutoConfiguration`

- `@EnableAutoConfiguration` 없이 애플리케이션을 구동할 수 있음
> 단 아래와 같이 설정하면 웹 서버로서의 동작은 안함  
```java
@Configuration
@ComponentScan
public class SpringinitApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
```

## @ComponentScan
> 아래의 어노테이션으로 지정된 클래스들은 모두 빈으로 등록됨  
- @Component
- @Configuration @Repository @Service @Controller @RestController

## @EnableAutoConfiguration 
- spring-boot-autoconfigure 하위의 spring.factories
> 아래의 파일의 meta에 설정에 필요한 자바 설정파일들을 아래의 키 값 밑에 설정되어있는 클래스들을 참조하여 모두 읽어들임  
> 수 많은 자동 설정 파일들이 조건에 따라 적용되어 수많은 빈들이 생성이 되고  
> 내장 톰캣을 사용해서 서블릿 컨테이너에서 웹 애플리케이션 하나가 동작하게 됨  
`org.springframework.boot.autoconfigure.EnableAutoConfiguration`

## @ConditionalOnXxxYyyZzz
> 전부 다 읽어드리는 것은 아님  
> 조건에 따라 어떤 빈은 등록하기도 하고 등록 안하기도 함  
