# 스프링 웹 MVC 10부: Spring HATEOAS
> Hypermedia ​A​s ​T​he ​E​ngine ​O​f A​ ​pplication ​S​tate  
> HATEOAS를 구현하기에 편리한 기능들을 제공하는 라이브러리  
> REST에서 R은 Representational  
- 서버: 현재 리소스와 ​연관된 링크 정보​를 클라이언트에게 제공한다
- 클라이언트: ​연관된 링크 정보​를 바탕으로 리소스에 접근한다
- 연관된 링크 정보
  - Rel​ation
  - H​ypertext ​Ref​erence

### spring-boot-starter-hateoas 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```
- https://spring.io/understanding/HATEOAS
- https://spring.io/guides/gs/rest-hateoas/
- https://docs.spring.io/spring-hateoas/docs/current/reference/html/

## HATEOAS가 자동설정으로 제공하는 기능
### ObjectMapper 제공
> 제공하는 리소스를 JSON으로 변환할 때 사용하는 인터페이스  
> 따로 디펜던시를 추가하지 않아도 바로 사용가능  
- spring.jackson.*  
> Properties 파일에서 ObjectMapper를 커스터마이징 할 수 있음  
- Jackson2ObjectMapperBuilder  

### LinkDiscovers 제공
> REST API로 다른쪽 서버정보를 요청해서 응답을 받았는데 그 API가 HATEOAS를 지원하면  
> getResourceByRelation 메서드로 self에 해당하는 링크 정보를 가져올 수 있는 Utility성 클래스  
- 클라이언트 쪽에서 링크 정보를 Rel 이름으로 찾을때 사용할 수 있는 XPath 확장 클래스

## HATEOAS 구현 테스트
> HATEOAS에서 제공하는 Resource는 우리가 제공하는 Resource + Link정보  
> Link를 만드는 다양한 클래스들이 존재하는데 Spring HATEOAS가 제공하는 것들임  

1. 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self").exists());
    }
}
```

2. 컨트롤러 작성
```java
@RestController
public class SampleController {

    @GetMapping("/hello")
    public Resource<Hello> hello(){
        Hello hello = new Hello();
        hello.setPrefix("Hey,");
        hello.setName("freelife");

        Resource<Hello> helloResource = new Resource<>(hello);
        //SampleController 클래스에 존재하는 hello라는 메서드에 대한 링크를 만들어서 self라는 릴레이션을 만들어서 추가함
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

        return helloResource;
    }
}
```

3. Hello 클래스 작성
```java
public class Hello {

    private String prefix;
    private String name;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return prefix + " " + name;
    }
}
```