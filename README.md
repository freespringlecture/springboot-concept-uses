# Spring-Boot-Devtools
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-property-defaults
  
> 설정하면 아래의 경로에 해당되는 Properties 들이 모두 변경됨 주로 Cache 관련 설정들을 꺼줌  
  
https://github.com/spring-projects/spring-boot/blob/v2.1.1.RELEASE/spring-boot-project/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java

### `spring-boot-starter-devtools` dependency 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

## Auto Restart
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-restart
  
> SpringBoot는 Class Loader를 두개를 사용함  
- Base Class Loader: 기본적으로 바뀌지 않는 기본 Class Loader
- Restart Class Loader: Application 에서 사용하는 일반적인 Class Loader
  
> Restart 시 Restart Class Loader가 제거되고 새로운 Class Loader가 작성됨  
> Base Class Loader는 새롭게 작성되지 않기 때문에 직접 껐다 켜기(cold starts) 보다 빠름  
  
- 클래스패스에 있는 파일이 변경 될 때마다 자동으로 재시작.
  - 직접 껐다 켜는거 (cold starts)보다 빠른다. 왜?
  - 릴로딩 보다는 느리다. (JRebel 같은건 아님)
    - JRebel은 클래스가로드 될 때 클래스를 다시 작성하여 다시로드하기 쉽도록함
  - 리스타트 하고 싶지 않은 리소스는? spring.devtools.restart.exclude
  https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-restart-exclude

### livereload
> Chrome 에서 서버가 재시작했을때 화면을 자동으로 갱신해주는 확장 프로그램
- livereload 서버 끄기
```
spring.devtools.liveload.enabled=false
```
### Global Configuration
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-globalsettings
> 우선순위가 가장 높은 외부설정  
> `spring-boot-devtools` plug-in 의존성이 있으면 적용  
- ~/.spring-boot-devtools.properties

### 리모트 애플리케이션
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools-remote
> 원격에 Application 구동하고 Local에서 실행(재시작, 구동, 파일변경 후 재구동등)  
> Production 용이 아님 과정이 매우 복잡함 개발용임 개발용에서 이렇게 복잡한 설정을 하고 쓰지는 않을 것 같음  
> 만약 사용한다면 그냥 devtools 의 Cache 끄는 설정만 복사해서 사용하는 것이 나아보임  