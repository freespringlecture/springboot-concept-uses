# 테스트 유틸

### OutputCapture
> 로그를 비롯해서 Console에 찍히는 모든 것을 다 캡쳐함
> 로그 메세지가 어떻게 찍히는지 테스트 해볼 수 있음
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerWebMvcTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

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

        assertThat(outputCapture.toString())
                .contains("SuperMan")
                .contains("Flash");
    }
}
```
### TestPropertyValues
### TestRestTemplate
### ConfigFileApplicationContextInitializer