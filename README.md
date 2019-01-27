# 스프링 부트 Actuator 3부: 스프링 부트 어드민
https://github.com/codecentric/spring-boot-admin  
> 스프링 진영에서 제공하는 프로젝트가 아니고 제3자가 오픈소스로 제공하는 애플리케이션  
> 설정한 스프링 부트 Actuator 정보를 UI에서 확인할 수있는 애플리케이션 툴  

## 어드민 서버 역할 애플리케이션 생성
### 의존성 추가
```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.1.2</version>
</dependency>
```

#### @EnableAdminServer 어노테이션 추가
```java
@SpringBootApplication
@EnableAdminServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

## 어드민 클라이언트 설정
> 설정하는 방법은 두가지가 있음  
> 아래와 같이 client 라이브러리를 통해서 설정하는 방법과 스프링 클라우드를 사용해서 디스커버리 되도록 하는 방법  
> 이런 정보들이 밖으로 노출되면 매우 위험하므로 SpringSecurity를 적용해서 Endpoints를 Admin만 접근이 가능하도록 해야함  
### 어드민 클라이언트 라이브러리 적용
> 추가하면서 jolokia 의존성이 따라서 들어오므로 Mbean을 HTTP를 통해서 볼수있게 됨  

### 의존성 추가
```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.1.2</version>
</dependency>
```

#### properties 설정
> 어드민 서버 주소를 설정
```
spring.boot.admin.client.url​=​http://localhost:8080
management.endpoints.web.exposure.include​=​*
```

#### client 서버포트 변경
```
server.port=18080
```