# 스프링 웹 MVC 8부: HtmlUnit
## HTML 템플릿 뷰 테스트를 보다 전문적으로 하자
> HTML을 단위테스트 하기 위한 Tool  
  
- http://htmlunit.sourceforge.net/
- http://htmlunit.sourceforge.net/gettingStarted.html

### 의존성추가
> scope이 test이므로 test할때만 사용함
```xml
<dependency>
  <groupId>​org.seleniumhq.selenium​</groupId>
  <artifactId>​htmlunit-driver​</artifactId>
  <scope>​test​</scope>
</dependency>
<dependency>
  <groupId>​net.sourceforge.htmlunit​</groupId>
  <artifactId>​htmlunit​</artifactId>
  <scope>​test​</scope>
</dependency>
```

### 테스트 가능한 기능
- Form submit
- 특정 브라우저인척 하기
- 특정 문서의 엘리먼트를 가져와서 값을 비교하거나 할수있음

#### 테스트 코드
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    WebClient webClient;

    @Test
    public void hello() throws Exception {
        HtmlPage page = webClient.getPage("/hello"); // /hello 로 요청시
        HtmlHeading1 h1 = page.getFirstByXPath("//h1"); //h1 제일 앞에있는 것 하나만 가져옴
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("ironman"); // 대소문자인것을 무시하고 문자열이 같은지 비교
    }
}
```