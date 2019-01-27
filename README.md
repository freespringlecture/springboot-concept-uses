# 6. 테스트
## 테스트환경 구성
### main 패키지 테스트 코드 작성
#### SampleController.class
```java
@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/hello")
    public String hello() {
        return "hello " + sampleService.getName();
    }
}
```

#### SampleService.class
```java
@Service
public class SampleService {
    public String getName() {
        return "freelife";
    }
}
```
### test 패키지 테스트 코드 작성
#### SampleControllerTest.class
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

}
```

### MockMVC 구성
- Servlet을 Mocking 한 것이 구동됨
- Mockup이 된 Servlet에 무언가 Interaction 할려면 MockMVC라는 클라이언트를 구성해야됨
- MockMVC를 만드는 방법이 여러가지가 있지만 아래와 같이 구성하는 것이 가장 쉽게 만드는 방법임
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;
}
```

#### MOCK Test Code 작성
- Auto Import 가 꼬이면 Preferences - Editor - Auto Import 에서 제거 하시면 됩니다
- Print로 찍은 대부분의 내용을 다 확인해볼 수 있음

#### MOCK로 테스트 하는 방법
```java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello freelife"))
                .andDo(print());
    }
}
```

### RANDOM_PORT 구성
> `SpringBootTest.WebEnvironment.RANDOM_PORT`로 설정하면 실제로 내장 Tomcat이 구동되고 Servlet이 올라감
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

#### TestRestTemplate
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleControllerRestTemplate {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void hello() throws Exception {
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello freelife");
    }
}
```

#### @MockBean
- `@SpringBootTest`는 `@SpringBootApplication`을 찾아가서 모든 빈을 다등록하고
- `@MockBean`으로 등록된 사항에 대해서 Mock 빈으로 교체하므로 테스트시 비용이 크고 통합테스트에 어울린다
- `@MockBean`으로 교체된 Mock 빈은 `@Test`마다 Reset 되고 다시 적용되므로 Reset관리를 할필요가 없음
  
> main Service로 테스트를 하면 테스트 코드가 너무 커지는 문제가 있어  
> `@MockBean`으로 main의 Service를 가로채서 가상의 Test Service를 만들어줌  
> 정말 간편하게 테스트 할 수 있게 해줌  
```java
    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("mavel");
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello mavel");
    }
```

#### WebTestClient
- Spring5의 WebFlux에 추가된 RestClient 중 하나로 Asynchronous(비동기식) 방식
- 기존에 RestClient는 Synchronous(동기식)
- 사용하려면 webflux dependency를 추가해줘야함
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

##### Test Code 작성
```java
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("ironman");
        webTestClient.get().uri("/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello ironman");
    }
```

## 슬라이스 테스트
> 레이어 별로 잘라서 테스트하고 싶을 때
### @JsonTest
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-json-tests
  
> 예상되는 JSON 형식을 테스트 해볼 수 있음  
```java
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@JsonTest
public class MyJsonTests {

	@Autowired
	private JacksonTester<VehicleDetails> json;

	@Test
	public void testSerialize() throws Exception {
		VehicleDetails details = new VehicleDetails("Honda", "Civic");
		// Assert against a `.json` file in the same package as the test
		assertThat(this.json.write(details)).isEqualToJson("expected.json");
		// Or use JSON path based assertions
		assertThat(this.json.write(details)).hasJsonPathStringValue("@.make");
		assertThat(this.json.write(details)).extractingJsonPathStringValue("@.make")
				.isEqualTo("Honda");
	}

	@Test
	public void testDeserialize() throws Exception {
		String content = "{\"make\":\"Ford\",\"model\":\"Focus\"}";
		assertThat(this.json.parse(content))
				.isEqualTo(new VehicleDetails("Ford", "Focus"));
		assertThat(this.json.parseObject(content).getMake()).isEqualTo("Ford");
	}

}
```

### @WebMvcTest
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests

- 빈 하나만 테스트하므로 굉장히 가벼움
- 딱 테스트 할 Controller와 필수 웹과 관련된 모듈들만 빈으로 등록되고 나머진 빈으로 등록되지 않음
- `@Controller`, `@ControllerAdvice`, `@JsonComponent`, `Converter`, `GenericConverter`, `Filter`, `WebMvcConfigurer`, `HandlerMethodArgumentResolver` 만 빈 으로 등록됨
- 사용할 빈이 있다면 `@MockBean`으로 만들어서 다 채워줘야 함 그래야 Controller이 필요한 빈을 주입받을 수 있다
- `@WebMvcTest`는 `MockMvc`로 테스트 해야함

#### Test Code 작성
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerWebMvcTest {

    @MockBean
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("ironman"); // MockBean으로 주입한 Mock Service

        mockMvc.perform(get("/hello")) // get 으로 /hello 요청하면
                .andExpect(status().isOk()) // status 는 200 이고
                .andExpect(content().string("hello ironman")) // content 는 hello ironman 이고
                .andDo(print()); // 해당 사항들을 print로 출력함
    }
}
```