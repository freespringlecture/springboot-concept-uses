# 스프링 데이터 12부: 정리
https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#boot-features-configure-datasource

## Open EntityManager in View
https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#boot-features-jpa-in-web-environment
> View를 렌더링 하는 것과 관련이 있음  
> 스프링 웹 MVC를 사용할 때 RestFull API를 만드는 것이 아니라 실제로 View를 렌더링 하는 과정이 있고  
> View에서 어떤 Entity의 추이적인 레퍼런스를 렌더링 할때까지  
> Hibernate의 세션 또는 JPA의 Entity 매니저를 그때까지 열어놓는 패턴  
> 기본적으로는 `spring.jpa.open-in-view` 옵션은 `true` 임  
> OpenSession인 View 패턴을 사용하게 되었음  

## JOOQ
http://kingbbode.tistory.com/36
https://blog.naver.com/jasuil/221424703048
> 데이터베이스 스키마에서 Java코드를 생성해서  
> SQL을 그 생성된 Java코드로 TypeSafe 하게 작성할 수 있는 유틸리티  
> 설정이 좀 복잡함  