# 1. 의존성 관리 이해
> `spring-boot-starter-parent` 를 지정하면 `spring-boot-dependencies` 에서 Dependencies에서 의존성을 정의해서 수많은 라이브러리들이 자동으로 설정됨

## parent 상속구조를 사용하지 않을 때
> 임의로 Spring Boot 버전 정의
> 의존성 외에 다른 Spring Boot에 최적화된 설정들(인코딩설정, 리소스필터링등)때문에 parent로 정의하는게 좋음
- 13.2.2 Using Spring Boot without the Parent POM
```xml
<dependencyManagement>
		<dependencies>
		<dependency>
			<!-- Import dependency management from Spring Boot -->
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-dependencies</artifactId>
			<version>2.1.1.RELEASE</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```