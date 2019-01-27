# 스프링 시큐리티 1부: spring-boot-starter-security
https://docs.spring.io/spring-security/site/docs/current/reference/html/

## 스프링 부트 시큐리티 테스트
https://docs.spring.io/spring-security/site/docs/current/reference/html/test.html
> 다양한 테스트 Annotation과 Method를 제공해줌  
- 아래와 같이 가짜 유저 로그인 정보를 만들어서 로그인 테스트를 만들 수도 있음  
```java
mockMvc.perform(get("/hello")
        .with(user("user").password("password")))
```
- 가짜 UserDetailsService 빈을 사용해서 특정한 유저정보를 사용해서 로그인하는 것을 Mocking 할 수도 있음  

## 스프링 시큐리티
- 웹시큐리티
- 메소드 시큐리티
- 다양한인증방법지원
  - LDAP, 폼 인증, Basic 인증, OAuth, ...

## 스프링 부트 시큐리티

### 인증관련 각종 이벤트 발생
- `DefaultAuthenticationEventPublisher` 빈 등록  
> `DefaultAuthenticationEventPublisher` 가 등록되어 있어   
> 비번이 틀렸거나 유저네임이 없거나 account가 expired 되거나등 여러가지 경우에 대해서 다 이벤트를 발생시킴  
> 그 이벤트 Handler를 등록해서 유저의 상태를 변경하는 등 여러가지 일들을 할 수 있음  
> 스프링 부트 시큐리티가 아니라 스프링 시큐리티만 사용한다고 하더라도 받아서 빈으로 등록만 한다면  
> 굳이 스프링 부트 설정을 안써도 우리가 충분히 쉽게 설정할 수 있는 부분임  

- 다양한 인증 에러 핸들러 등록 가능
> 스프링 시큐리티의 `WebSecurityConfigureAdapter` 설정을 그대로 사용하고 있음  
> 기본적으로 `HttpSecurity` 정의되어있는 설정들이 적용되었다고 보면 됨  
> configure에서 http의 모든 요청을 가로채서 인증이 필요하도록 만듬  
> `formLogin` 과 `httpBasic` 를 사용하겠다고 스프링 부트가 기본적으로 제공해주는 시큐리티 기능임  
> 실제적으로 스프링 부트가 해주는게 거의 없음  

- 모든 요청에 스프링 시큐리티로 인해 인증이 필요함

### Basic Authentication
#### 인증 정보가 없을 때의 응답
```bash
MockHttpServletResponse:
           Status = 401
    Error message = Unauthorized
          Headers = {WWW-Authenticate=[Basic realm="Realm"], X-Content-Type-Options=[nosniff], X-XSS-Protection=[1; mode=block], Cache-Control=[no-cache, no-store, max-age=0, must-revalidate], Pragma=[no-cache], Expires=[0], X-Frame-Options=[DENY]}
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
```
> Basic Authentication에 대한 응답이 이렇게 오면  
> Basic Authentication을 요청하게 되고 브라우저에서 띄우는 Alert창이 팝업되어서 username과 Password를 입력하라고 하라고 알림  
> Basic Authentication은 Accept Header(이 요청이 원하는 응답의 형태)에 따라 달라짐  

#### Basic Authentication 이랑 Form 인증이 둘 다 적용이 됨  
> Accept Header를 지정하지 않으면 Form Authentication에 대한 응답으로 보내지 않고 Basic Authentication에 대한 응답으로 보냄  
> 테스트를 만들 때 Accept Header를 설정할 수 있음  
> JSON 이나 Ajax로 Call하는 경우가 아니면 보통 웹브라우저에서 text/html을 Accept Header에 담아서 요청함 그런 경우에는 Form 인증으로 넘어감  

## 스프링 부트 시큐리티 자동 설정
> 스프링 시큐리티가 적용되면 기본적으로 아래의 두가지 설정파일을 제공  

### 1. SecurityAutoConfiguration
> `DefaultAuthenticationEventPublisher` 가 있으면서  
> `SpringBootSebSecurityConfiguration`의 `WebSecurityConfigurerAdapter`가 있으면서  
> `WebSecurityConfigurerAdapter`에 대한 빈이 없으면 `defaultConfigureAdapter`을 등록  
> @Configuration만 등록하면 `WebSecurityConfigurerAdapter` 빈을 만드는 것임  

- 자동설정을 사용하지 않으면서 거의 동일하게 동작하는 WebSecurityConfigurer을 설정할 수 있음
```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
}
```

### 2. UserDetailsServiceAutoConfiguration
> 자동으로 랜덤한 user를 생성해주는 자동 설정  
> 스프링 부트 Application이 구동 될 때 기본적으로 user객체 하나를 `inMemoryUserDetailsManager`를 만들어서 제공해줌  
> 이 설정은 `AuthenticationManager`, `AuthenticationProvider`, `UserDetailsService` 가 없는 경우에만 적용됨  
> 보통 스프링 시큐리티를 적용하는 프로젝트들은 다 프로젝트만에 `UserDetailsService`를 등록하게 되어있음  
> 사실상 스프링 시큐리티에 있는 `UserDetailsServiceAutoConfiguration` 설정은 거의 쓸일이 없음  

## 스프링 부트 자동 로그인 설정
> 스프링 시큐리티가 자동으로 만들어주는 기본 Login Form이 있음  
> `text/html` 로 Root를 요청했지만 인증정보가 없기 때문에 로그인이 필요해서 Login Form으로 리다이렉트 시킴  
> Login Form 페이지에 로그인 할 수 있는 정보를 스프링 시큐리티가 자동으로 만들어줌  
> 스프링 부트가 제공하는 자동설정에 의해서 만들어진 정보임  
> 로그인 되면 인증이 적용되어 페이지를 볼 수 있음  

### 자동설정에 의한 기본 사용자 생성
> Application이 구동될 때마다 랜덤으로 유저를 생성  
> 기본 username은 user이고 Application이 구동될 때 마다 password는 다시 생성됨  
  - Username: user  
  - Password: 애플리케이션을 실행할 때 마다 랜덤 값 생성 (콘솔에 출력 됨)  
  - `spring.security.user.name`
  - `spring.security.user.password`

## View 전용 Controller 셋팅 TIP
> 별다른 Controller 설정없이 요청이 들어오면 View로 보내는 설정이 하고싶으면 아래와 같이 설정하면 된다  
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
    }
}
```
## 스프링 시큐리티 실습
### 스프링 시큐리티를 적용하지 않은 테스트
#### thymeleaf 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

#### Controller 작성
```java
@Controller
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
```

#### src/resource/templates에 index, hello, my HTML 생성
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
<h1>Welcome</h1>
<a href="/hello">hello</a>
<a href="/my">my page</a>
</body>
</html>
```

#### 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void my() throws Exception {
        mockMvc.perform(get("/my"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("my"));
    }
}
```

#### 테스트 코드에 accept 설정
> accept Header 설정에 임의로 text/html을 적용하면 form 인증을 함  
```java
@Test
public void hello() throws Exception {
    mockMvc.perform(get("/hello")
            .accept(MediaType.TEXT_HTML))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("hello"));
}
```

### 스프링 시큐리티를 적용한 테스트
> text/html을 Accept Header에 담아서 요청해서 Form Authentication 하도록 구현  

#### spring security 의존성 추가
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```

#### Spring Security test 의존성 추가
> 버전관리를 해주지 않으므로 아래와 같이 버전지정  
```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <version>${spring-security.version}</version>
    <scope>test</scope>
</dependency>
```

#### 가짜 유저 인증정보 적용
> @WithMockUser 를 적용하면 가짜 유저 인증정보를 적용하여 돌려줌  
> Method마다 등록 할 수 있고 Class에 등록해 Class의 모든 Method에 등록할 수도 있음  
> 인증정보가 있으면 테스트 통과 인증정보가 없으면 status 401에 Unauthorized 메시지를 응답함  
```java
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void hello_without_user() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void my() throws Exception {
        mockMvc.perform(get("/my"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("my"));
    }

}
```