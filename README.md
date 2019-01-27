# 스프링 웹 MVC 4부: 정적 리소스 지원
## 정적 리소스 맵핑 `/**`
### 기본 리소스 위치
- classpath:/static
- classpath:/public
- classpath:/resources/
- classpath:/META-INF/resources
> ex) “/hello.html” => /static/hello.html

### 리소스 위치 변경
> 기본적으로 resource들은 root(/) 부터 Mapping이 되어있음
#### spring.mvc.static-path-pattern: 맵핑 설정 변경 가능
```
spring.mvc.static-path-pattern=/static/**
```
#### spring.mvc.static-locations: 리소스 찾을 위치 변경 가능

### Last-Modified 헤더를 보고 304 응답을 보냄
> Request Headers의 if-Modified-Since 이후에 바뀌었으면 클라이언트에 새로운 Resource를 보냄  
> 바뀌지 않았으면 클라이언트에 304의 응답을 보내고 새로운 Resource를 보내지 않아서 응답이 훨씬 빨라짐  
> 이런식의 캐싱이 동작함  

### ResourceHttpRequestHandler가 처리함원
> WebMvcConfigurer의 addRersourceHandlers로 커스터마이징 할 수 있음  
> SpringBoot가 제공하는 Resource Handler는 그대로 놔두면서 내가 원하는 Resource Handler만 따로 추가  
> 기본적으로 SpringBoot가 제공하는 Resource Handler과 캐싱 전략이 다르므로 별도로 캐싱 처리를 해줘야됨  
1. addResourceHandler 설정
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**") // /m 으로 시작하는 요청이 오면
                .addResourceLocations("classpath:/m/") // classpath 기준으로 m 디렉토리 밑에서 제공
                .setCachePeriod(20); // 20초
    }
}
```

2. classpath에 m 디렉토리 추가