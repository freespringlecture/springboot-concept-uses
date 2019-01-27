# 스프링 웹 MVC 11부: CORS

## SOP과 CORS
> 웹 브라우저가 지원하는 표준기술  
- Single-Origin Policy  
> 같은 Origin에만 보낼 수 있는 표준기술  
- Cross-Origin Resource Sharing  
> Single-Origin Policy를 우회하기 위한 표준기술  
> 서로 다른 Origin끼리 Resource를 공유할 수 있는 방법을 제공하는 표준  

### Origin
> 기본적으로는 Origin이 다르면 서로 호출을 못함  
> 아래 항목들 세가지를 조합한 것이 하나의 Origin 임  
> 이 하나의 Origin이 또다른 Origin을 서로 호출할 수 없음  
- URI 스키마 (http, https)
- hostname (whiteship.me, localhost)
- 포트 (8080, 18080)

## 스프링 MVC @CrossOrigin
> 스프링에서 사용하려면 빈 설정을 해줘야하는데 스프링 부트에서 자동으로 설정해주므로 별다른 설정을 할 필요 없이 사용가능  
- https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
- @Controller나 @RequestMapping에 추가하거나
- WebMvcConfigurer 인터페이스를 상속받아서 글로벌 설정  
> 스프링 부트가 제공하는 스프링 MVC 기능을 그대로 다 사용하면서 추가로 확장  

### 테스트
> Access-Control-Allow-Origin 어떤 오리진이 server에 접근할 수 있는지 알려주는 Header 값  
> server의 Access-Control-Allow-Origin Header 값을 Client에 보내서 매칭이 되면 요청이 가능함  

### Server
1. 단일 CrossOrigin 허용 Server 테스트 코드
```java
@SpringBootApplication
@RestController
public class Application {

    //CrossOrigin 을 허용할 주소를 설정
    @CrossOrigin(origins = "http://localhost:18080")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

2. WebMvcConfigurer 인터페이스를 상속받아서 글로벌 설정
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/hello") // hello를 registry에 등록 /** 하면 전부다 허용
                .allowedOrigins("http://localhost:18080");
    }
}
```

#### 요청 Client
1. jquery dependency 추가
```xml
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>jquery</artifactId>
    <version>3.3.1</version>
</dependency>
```

2. src/resources/static/index.html 생성
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title</title>
</head>
<body>
  <h1>CORS Client</h1>
</body>
<script src="/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script>
  $(function () {
    $.ajax("http://localhost:8080/hello")
      .done(function (msg) {
        alert(msg);
      })
      .fail(function () {
        alert("fail");
      })
  })
</script>
</html>
```

3. application.properties에 포트 설정
```
server.port=18080
```