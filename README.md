# 스프링 웹 MVC 2부: HttpMessageConverters
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-mvc-message-converters
> SpringFrameWork에서 제공하는 인터페이스 SpringMVC 의 일부분  
> HTTP 요청 본문을 객체로 변경하거나, 객체를 HTTP 응답 본문으로 변경할 때 사용  
> {“username”:”keesun”, “password”:”123”} <-> User  
> 같이 사용되는 Spring Annotation  
- @ReuqestBody
- @ResponseBody
  - @RestController을 사용하면 생략해도됨 자동으로 설정 뷰 네임 리졸버를 안타고 바로 MessageConverter 타고 응답 본문으로 내용이 들어감
  - @Controller 이면 반드시 붙여야됨 붙이지 않으면 뷰 네임 리졸버에서 return 이름에 해당되는 뷰 네임 리졸버를 찾으려고 시도함

## 예제코드 실습
> 먼저 테스트 코드를 작성하고 테스트 하면서 로직 추가
1. 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void createUser_JSON() throws Exception {
        String userJson = "{\"username\":\"freelife\", \"password\":\"123\"}";
        //요청을 만드는 단계
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson))
                //응답 확인단계
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", is(equalTo("freelife"))))
                    .andExpect(jsonPath("$.password", is(equalTo("123"))));

    }
}
```

2. 컨트롤러 코드 작성
```java
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/users/create")
    public User create(@RequestBody User user){
        return user;
    }
}
```

3. User 객체 클래스 생성
```java
public class User {
    private Long id;
    private String username;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```