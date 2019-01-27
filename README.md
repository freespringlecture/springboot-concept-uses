# 스프링 웹 MVC 9부: ExceptionHandler
> SpringBoot에는 ErrorHandler가 이미 등록되어있음  
> ErrorHandler에 의해서 Error 메세지들이 보임  

## 기본 @ExceptionHandler 테스트 코드
> 해당 된 컨트롤러 내에서만 동작하는 ExceptionHandler 테스트 코드  
#### SampleController
```java
@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        throw new SampleException();
    }

    // AppError: App에서 만든 커스텀한 에러정보를 담고 있는 클래스가 있다면
    @ExceptionHandler(SampleException.class)
    //메서드 파라메터로 해당하는 Exception 정보를 받아 올 수 있음
    public @ResponseBody AppError sampleError(SampleException e) {
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("IDK IDK IDK");
        return appError;
    }
}
```

#### SampleException
```java
public class SampleException extends RuntimeException {
}
```

#### AppError
```java
public class AppError {

    private String message;
    private String reason;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
```

## 스프링 @MVC 예외 처리 방법 
- @ControllerAdvice  
  > ExceptionHandler 정의하면 여러 컨트롤러에서 발생하는 Exception을 처리하는 Handler가 동작하게 됨  
- @ExchangepHandler  
## 스프링 부트가 제공하는 기본 예외 처리기
- BasicErrorController  
  
> 상속받아서 메서드를 오버라이딩해서 구현하는 것을 레퍼런스에서 추천함  
> HTML로 요청하면 HTML로 응답하고 그 외에는 JSON으로 응답함  
  - HTML과 JSON 응답 지원  
- 커스터마이징 방법
  - ErrorController 구현
## 커스텀 에러 페이지
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-error-handling-custom-error-pages
> 상태코드 값에 따라 다른 에러페이지 보여주기  

### 에러 페이지 명
> 경로: src/main/resources/public/error/  
> 경로에 상태코드와 동일한 에러페이지를 만들어야 동작함 아니면 첫번째 숫자만 명시해서 만들어도 됨  
- 404.html
- 5xx.html
- ErrorViewResolver 구현