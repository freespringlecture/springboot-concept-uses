# 스프링 웹 MVC 1부: 소개
## 스프링 웹 MVC
https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#spring-web

## 스프링 부트 MVC
> 아무런 설정 파일을 작성하지 않아도 스프링 부트가 제공해주는 기본 설정 때문에 스프링 웹 MVC 개발을 시작할 수 있다  
> SpringBoot AutoConfiguration 플러그인 안에 WebMvcAutoConfiguration 가 자동설정을 제공  

### hiddenHttpMethodFilter
> Spring5 에서 제공해주는 Filter Spring3.0 부터 제공해오던 필터  
> PUT, DELETE, PATCH 요청시 _method 라는 Hidden Form 파라메터로 어떤 메서드인지 값을 받아올 수 있음  
> 그 값을 받아와서 Controller에 Mapping을 해줌 @DeleteMapping 등의 handler 만들 수 있음  

### HttpPutFormContentFilter
> HTTP POST 나 Form 데이터를 보낼 수 있게 Servlet Spec에 정의가 되어있음  
> HTTP PUT 이나 PATCH 도 `application/x-wwww-form-urlencoded` 타입으로 Form 데이터를 보내오면  
> HTTP POST 요청에서 꺼내올 수 있는 것처럼 HTTP PUT 이나 PATCH 에서도 꺼내올 수 있도록 Mapping 해주는 역할  

## 스프링 부트 MVC 확장
> 스프링 부트 MVC가 제공해주는 기능을 다 사용하면서 추가적으로 더 설정을 할때 아래와 같이 설정하고  
> 추가적으로 필요한 Callback Method를 호출하여 정의하여 사용  
```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}
```

## 스프링 MVC 재정의
> 아래와 같이 `@EnableWebMvc` 를 같이 사용하면 모든 스프링 MVC 설정이 다 사라지고 재정의 해야됨  
```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}
```