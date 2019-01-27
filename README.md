# 스프링 웹 MVC 5부: 웹JAR
> 각종 플러그인들을 JAR파일로 추가할 수 있음  
### jQuery dependency 추가
```xml
<dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>jquery</artifactId>
    <version>3.3.1</version>
</dependency>
```
### jQuery webjars 사용
```html
<script ​src=​"/webjars/jquery/3.3.1/dist/jquery.min.js"​></script>
<script>
    $(function() {
        console.log("ready!");
    });
</script>
```

## 웹JAR 맵핑 “​/webjars/**​”
> webjars 플러그인들의 버전 생략하고 사용하려면  
> resource chaning 이라는 SpringFrameWrok 에 포함된 기능으로 동작  
> Resource Handler 와 Resource Transformer를 체이닝 하는 하는 기술   

### webjars-locator-core 의존성 추가
```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator-core</artifactId>
    <version>0.36</version>
</dependency>
```