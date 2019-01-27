# 스프링 데이터 11부: Neo4j
> Neo4j​는 노드간의 연관 관계를 영속화하는데 유리한 그래프 데이터베이스  
> 릴레이션 데이터베이스 보다 빠름  
> GraphQL을 사용  

### 의존성 추가
> 버전 마다 하위호환성이 좋지 않음  
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```

## Neo4j 설치 및 실행 (도커)
> 최신버전에서는 PORT 맵핑을 두개 해줘야함 HTTP용과 Bolt라는 프로토콜용  
```bash
 docker run -p 7474:7474 -p 7687:7687 -d --name noe4j_boot neo4j
```

### UI 브라우저 환경을 제공
http://localhost:7474/browser  
- 기본 password는 neo4j

### Properties 설정
```
spring.data.neo4j.password=1111
spring.data.neo4j.username=neo4j
```

## 스프링 데이터 Neo4J
> 최신버전에서 사용할 수 있는 것은 SessionFactory 밖에 없음  
- Neo4jTemplate (Deprecated)
- SessionFactory
- Neo4jRepository

## 스프링 데이터 Neo4J 테스트 실습1
#### Account 클래스 생성
```java
@NodeEntity
public class Account {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;

    @Relationship(type = "has")
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
```

#### Role 클래스 생성
> 유저에게 권한을 부여하는 클래스  
```java
@NodeEntity
public class Role {

    @Id @GeneratedValue
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

#### Neo4jRunner 클래스를 생성해서 테스트 코드 작성
```java
@Component
public class Neo4jRunner implements ApplicationRunner {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
//        account.setEmail("freejava1191@gmail.com");
//        account.setUsername("freelife");
        account.setEmail("flash@gmail.com");
        account.setUsername("flash");

        Role role = new Role();
        role.setName("admin"); //admin 권한 생성

        account.getRoles().add(role); //생성된 유저에 admin 권한 부여

        Session session = sessionFactory.openSession();
        session.save(account); // 저장
//        session.clear(); // 캐싱을 비워줌
        sessionFactory.close();

        System.out.println("finished");
    }
}
```

## 스프링 데이터 Neo4J 테스트 실습2 Repository 사용
#### AccountRepository 생성
```java
public interface AccountRepository extends Neo4jRepository<Account, Long> {
}
```

#### Repository를 사용하는 테스트 코드 작성
```java
@Component
public class Neo4jRunner implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setEmail("doctorstranger@gmail.com");
        account.setUsername("doctorstranger");

        Role role = new Role();
        role.setName("user"); //user 권한 생성

        account.getRoles().add(role); //생성된 유저에 권한 부여

        accountRepository.save(account);

        System.out.println("finished");
    }
}
```