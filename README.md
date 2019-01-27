# 내장 웹 서버 응용 1부: 컨테이너와 포트
## 컨테이너와 포트
### 다른 서블릿 컨테이너로 변경
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-web-servers.html
 - tomcat, jetty, undertow
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<!-- Exclude the Tomcat dependency -->
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<!-- Use Jetty instead -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

### 웹서버사용하지않기
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-externalize-configuration
> WebServlet 의존성들이 클래스패스에 있더라도 무시하고 그냥 None WebApplication으로 실행하고 끝냄
#### application.properties에 아래 설정
```
spring.main.web-application-type=none
```

#### @SpringBootApplication에 설정
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }
}
```
### 포트
#### server.port
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-use-short-command-line-arguments 
#### 랜덤포트 
> 아래와 같이 설정하면 랜덤으로 사용할 수 있는 포트 찾아서 띄워줌 
```
server.port=0
```
### ApplicationListner<ServletWebServerInitializedEvent>
> 위와 같이 지정한 포트를 Application에서 어떻게 사용할 것인가
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-discover-the-http-port-at-runtime

#### 이벤트 리스너 생성
> 웹서버가 생성이 되면 servletWebServer가 실행되고 이 이벤트 리스너 핸들러가 호출이 되고 onApplicationEvent 콜백이 실행됨  
> 웹 애플리케이션 컨텍스트 이므로 웹서버를 알 수 있고 웹서버에서 포트 정보를 가져올 수 있음  
```java
@Component
public class PortListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
      ServletWebServerApplicationContext applicationContext = event.getApplicationContext();
      applicationContext.getWebServer().getPort();
    }
}
```

### Enable HTTP Response Compression
> 응답을 압축 해서 보냄  
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#how-to-enable-http-response-compression

```
server.compression.enabled=true
```