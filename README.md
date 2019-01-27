# 프로파일
- 특정한 프로파일에서만 특정한 빈을 등록 하고 싶을 때
- 애플리케이션의 동작을 특정 프로파일일 경우 빈 설정을 다르게 하고 동작을 다르게 하고 싶을 때

## @Profile 애노테이션은 어디에?
> config package를 생성하고 각 Configuration Class를 만든다음 아래와 같이 지정
- @Configuration
```java
@Profile("test")
@Configuration
public class TestConfiguration {

    @Bean
    public String hello() {
        return "hello test";
    }
}
```

## 어떤 프로파일을 활성화 할 것인가?
- spring.profiles.active
> properties에 직접 설정해서 사용  
> 우선순위에 영향을 받음  
```java
spring.profiles.active=test
```
### Command Line 에서 직접 설정시 우선순위가 더 높아서 변경됨
```bash
mvn clean package -DskipTests
java -jar target/XXX.jar --spring.profiles.active=prod
```

### program arguments 에 직접지정 하여 테스트
```
--spring.profiles.acive=test
```

## 별도의 프로파일용 프로퍼티 파일로 분리하여 관리
> `spring.profiles.acive`에 따라 해당 프로퍼티 파일이 더 우선순위를 가지므로 덮어씌움  
> `application-{profile}.properties`  
- application.properties(메인, 공통 프로퍼티)
- applciation-test.properties
- application-prod.properties

## 어떤 프로파일을 추가할 것인가?
> 추가할 프로파일을 설정할 수 있음  
- spring.profiles.include
```
spring.profiles.include=proddb
```

- application-proddb.properties 파일을 추가후 프로퍼티를 설정하면 해당 프로퍼티 값을 가져올 수 있음
```
freelife.db-name=prod db
```