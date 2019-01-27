# 스프링 웹 MVC 7부: Thymeleaf
> 스프링 웹 MVC로 동적 컨텐츠를 생성하는 방법  

## 스프링 부트가 자동 설정을 지원하는 템플릿 엔진
> View, Code Generation, Email Template 등에 사용  
> 기본적인 템플릿은 같은데 값들이 경우에 따라 달라지는 동적인 컨텐츠를 표현해야 하므로 사용  
- FreeMarker
- Groovy
- Thymeleaf
- Mustache

## JSP를 권장하지 않는 이유
> JSP는 자동설정을 지원하지 않는데다가 SpringBoot가 지향하는 바와 달라서 권장하지 않음  
> SpringBoot는 기본적으로 독립적으로 실행가능한 임베디드 톰캣으로 빠르고 쉽게 만들어 배포하길 바람  
> JAR JSP에 대한 의존성을 넣으면 의존성 문제가 생기는 등 여러가지 제약사항으로 잘 사용하지 않음  
- JAR 패키징 할 때는 동작하지 않고, WAR 패키징 해야 함  
  WAR로 패키징 하더라도 Embed-Tomcat으로 java -jar 실행할 수도 있고 다른 외부 Tomcat에 배포할 수도 있음
- JBOSS에서 만든 가장 최신 Servlet 엔진 Undertow는 JSP를 지원하지 않음
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-jsp-limitations

## Thymeleaf 사용하기
> Thymeleaf 를 사용하면 테스트시 본문 확인이 가능하지만 JSP는 힘듬  
> 실제 렌더링된 결과는 Servlet 엔진이 해야되는 것인데  
> Thymeleaf는 Servlet에 독립적인 엔진이기 때문에 View에 렌더링되는 결과를 확인할 수 있음  
- https://www.thymeleaf.org/
- https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html
- 의존성 추가: spring-boot-starter-thymeleaf
- 템플릿 파일 위치: /src/main/resources/​template/  
  > 템플릿 엔진 의존성이 추가 되면 모든 View는 template 폴더에서 찾게 됨  
- 예제:  
  https://github.com/thymeleaf/thymeleafexamples-stsm/blob/3.0-master/src/main/webapp/WEB-INF/templates/seedstartermng.html

### 테스트 코드
> View 이름과 Model만 확인하는 간단한 테스트  
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        // 요청 "/hello"
        // 응답
        // - 모델 name : freelife
        // - 뷰 이름 : hello
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("hello"))
                .andExpect(model().attribute("name", is("freelife")));
    }
}
```

#### Controller 코드
```java
@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "freelife");
        return "hello";
    }
}
```

#### resources/template 폴더에 hello.html 생성
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title</title>
</head>
<body>
    <h1 th:text="${name}">Name</h1>
</body>
</html>
```