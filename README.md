# 내장 웹 서버 응용 2부: HTTPS와 HTTP2
https://opentutorials.org/course/228/4894  
https://gist.github.com/keesun/f93f0b83d7232137283450e08a53c4fd  

## HTTPS 설정하기
- 키스토어 만들기
- HTTP는 못쓰네?

## HTTP 커넥터는 코딩으로 설정하기 
https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors

## HTTP2 설정
- server.http2.enable
- 사용하는 서블릿 컨테이너 마다 다름

### keystore 인증서 생성
> 터미널에서 아래의 명령어로 keystore 파일 생성
```
keytool -genkey 
  -alias tomcat
  -storetype PKCS12 
  -keyalg RSA 
  -keysize 2048 
  -keystore keystore.p12 
  -validity 4000
```
  
> 인증서 alias를 tomcat이라고 주고 `server.ssl.keyAlias=tomcat`이라고 주지 않으면 아래와 같은 에러가 발생  
```bash
curl: (35) error:14077410:SSL routines:SSL23_GET_SERVER_HELLO:sslv3 alert handshake failure
```

### application.properties 에 생성된 keystore 인증서 설정
> classpath에 keystore.p12 파일이 있으면 server.ssl.key-store=classpath:keystore.p12
```
server.ssl.key-store=keystore.p12
server.ssl.key-store-password=123456
server.ssl.keyStoreType=PKCS12
server.ssl.keyAlias=spring
```
  
> 위 와 같이 설정하면 자동으로 HTTPS 가 설정되는데 공식인증기관에서 발급된 인증서가 아니라 안전하지 않다고 경고함  
> HTTP 커넥터가 하나인데 거기에 HTTPS를 설정해서 더이상 HTTP는 사용할 수 없음  
  
### curl http2 지원되도록 설치
```bash
# install cURL with nghttp2 support
brew install curl --with-nghttp2

# link the formula to replace the system cURL
brew link curl --force
```

```bash
curl -I -k --http2 https://localhost:8080/hello
```

### HTTP Connector 추가하기
https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors/src/main/java/sample/tomcat/multiconnector/SampleTomcatTwoConnectorsApplication.java
```java
   @Bean
    public ServletWebServerFactory serverFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8080);
        return connector;
    }
```

#### application.properties
> 기존 HTTPS 가 적용된 포트는 8443으로 변경
```
server.port=8443
```

### HTTP2 활성화
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-web-servers.html#howto-configure-http2-tomcat
> HTTP2를 설정하려면 반드시 HTTPS를 먼저 설정을 한다음 해야된다  
```
server.http2.enabled=true
```
  
> Undertow의 경우 HTTPS만 설정되어있으면 추가 설정하지 않아도 HTTP2가 활성화됨  
  
### TOMCAT 에서 HTTP2 설정
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-web-servers.html#howto-configure-http2-tomcat
> Tomcat 8.5에서는 설정이 너무 번거로움 Tomcat 9 이상에서는 별 다른 설정이 필요없음  
> 위에 exclude tomcat 설정을 해제 하고 이렇게 셋팅하면 된다  

- Intellij 설정
> 아래 위치들의 Java 버전을 변경  
1. File - Project Structure - Project
2. File - Project Structure - Modules - Dependencies