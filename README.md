# 스프링 데이터 5부: 스프링 데이터 JPA 소개
> ORM(Object-Relational Mapping)과 JPA (Java Persistence API)  
- 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크  
- http://hibernate.org/orm/what-is-an-orm/  
  - 대부분의 JPA Spec이 Hibernate에 기반해서 만들어짐  
  - Hibernate의 모든 기능을 JPA가 커버하지는 않음  
  - 경우에 따라서는 Hibernate에 관련된 설정을 직접 다루기도 함  
  - 그렇게 할 수 있도록 매커니즘을 제공  
- JPA: ORM을 위한 자바 (EE) 표준

## ORM에서 다루는 문제들
- 객체는 크기가 굉장히 다양하지만 테이블은 테이블과 컬럼 밖에 없고 크기가 한정적임
- 복잡한 객체의 다양한 크기들을 테이블에 맵핑을 시킬 수 있을 것인가에 대한 해결책을 제공
- 테이블은 상속이 없지만 객체들은 상속이 있음 클래스간에 상속구조를 만들 수도 있고
- 그런 상속구조를 테이블로는 어떻게 맵핑할 것인가
- Relational 에서 식별자는 굉장히 단순함
- Object에서는 Identity는 Hashcode? equals Method?
- Object Identity가 같으면 도대체 어떤 Entity가 같아야 우리는 객체가 같다고 해야되는건가?
- 테이블에서는 식별자만 같으면 같은건데 Object에서는 그게 아님 프로퍼티만 같다고 해서 같은건가? 
- ID가 null이면 어떻게 되는거지?

## SpringData JPA
> JPA 표준 스펙을 아주쉽게 사용할 수 있도록 SpringData로 추상화 시켜놓은 것  
> 아래의 구현체로는 Hibernate를 사용하고 JPA에 있는 Entity Manager로 감싸서 사용함  
> SpringData JPA가 제공하는 Annotation을 사용해서 JPA, Hibernate를 사용하게 됨 Hibernate 아래에는 JDBC가 있음  
> 결국 SpringDataJDBC의 기능을 모두 다 사용할 수 있으면서 부가적으로 JPA, Hibernate, SpringDataJPA 기능을 더 사용할 수 있음  
- Repository 빈 자동 생성
- 쿼리메소드자동구현
- @EnableJpaRepositories (스프링 부트가 자동으로 설정 해줌.)
- SDJ(SpringDataJPA) -> JPA -> Hibernate -> Datasource(JDBC)

### 스프링 데이터 JPA 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```