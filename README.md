# 2. 의존성 관리 응용

## SpringBoot가 버전 관리를 하는 의존성 추가 예시 
- spring-boot-starter-data-jpa dependency 추가
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## maven repository 검색
https://mvnrepository.com/

## SpringBoot가 버전 관리를 안하는 의존성 추가 예시 
- ModelMapper dependency 추가
> model을 자동으로 생성 mapping 해주는 라이브러리
```xml
<!-- https://mvnrepository.com/artifact/org.modelmapper/modelmapper -->
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>2.3.2</version>
</dependency>
```

## Spring 버전 변경
- `spring-boot-dependencies`에서 properties에서 spring 버전을 가져와서 오버라이딩 해서 지정
```xml
<spring.version>5.1.3.RELEASE</spring.version>
```