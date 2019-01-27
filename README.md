# 스프링 웹 MVC 3부: ViewResolve
https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-multiple-representations
> Accept Header 에 따라 응답이 달라진다  
> 가장 비교하기 좋은 대상은 Accept Header 이지만 Accept Header를 제공하지 않는 경우도 있음  
> Accept Header가 없는 경우 `/path?format=pdf` 와 같은 형식으로 알 수 있음  
1. 어떠한 요청이 들어오면 응답을 만들어 낼 수 있는 모든 View를 찾아냄
2. View의 타입을 Accept Header랑 비교를 해서 최종적으로 선택을 하고 리턴함

## HttpMessageConvertersAutoConfiguration
> HTTP MessageConverter는 `HttpMessageConvertersAutoConfiguration` 으로 적용됨  
> JSON으로 내보내는 것은 지원하지만 XML로 내보내려는 경우에는 XmlMapper.class 가 classpath에 없으므로 아래의 로직은 Converting 이 안됨  
```java
@Test
public void createUser_XML() throws Exception {
    String userJson = "{\"username\":\"freelife\", \"password\":\"123\"}";
    //요청을 만드는 단계
    mockMvc.perform(post("/users/create")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_XML)
            .content(userJson))
            //응답 확인단계
                .andExpect(status().isOk())
                .andExpect(xpath("/User/name")
                        .string("freelife"))
                .andExpect(xpath("/User/password")
                        .string("123"));

}
```

### ​jackson-dataformat-xml​ 추가
> ​jackson-dataformat-xml​ dependency를 추가하면 XML로 내보낼 수 있음
```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.9.8</version>
</dependency>
```