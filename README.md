# 스프링 데이터 10부: MongoDB
> MongoDB​는 JSON 기반의 도큐먼트 데이터베이스  

### SpringData MongoDB 의존성 추가
> MongoDB Reactive라는 것도 있는데 Spring Webflux를 사용하는 경우  
> Reactive한 Stream과 Repository를 만들어 낼 수 있음  
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

## MongoDB 설치 및 실행 (도커)
```bash
docker run -p 27017:27017 --name mongo_boot -d mongo
docker exec -i -t mongo_boot bash
```
- mongo

## 스프링 데이터 몽고DB
> 아래의 빈 들을 자동으로 설정해주고 지원해줘서 손쉽게 MongoDB를 사용할 수 있음  
- MongoTemplate  
- MongoRepository

## MongoDB 실습1
#### Runner를 빈으로 추가하고 account를 insert 하는 테스트 로직 작성
```java
@SpringBootApplication
public class Application {

    @Autowired
    MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setEmail("aaa@bbb");
            account.setUsername("aaa");

            mongoTemplate.insert(account);

            System.out.println("finished");
        };
    }
}
```

#### Account 클래스 작성
```java
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;
    private String username;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
```

## MongoDB 실습2 - Repository
#### MongoRepository를 확장한 interface 추가
```java
public interface AccountRepository extends MongoRepository<Account, String> {
}
```

#### Repository를 사용하는 테스트 코드 작성
```java
@SpringBootApplication
public class Application {

    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setEmail("aaa@bbb");
            account.setUsername("aaa");
            accountRepository.insert(account);

            System.out.println("finished");
        };
    }
}
```

## 내장형 MongoDB (테스트용)을 사용한 슬라이싱 테스트
### 테스트용 embed MongoDB 의존성 추가  
> 의존성만 추가해주면 내장용 MongoDB에 대한 자동설정을 지원해주므로 사용하기 매우 쉬움  
```xml
<dependency>
    <groupId>de.flapdoodle.embed</groupId>
    <artifactId>de.flapdoodle.embed.mongo</artifactId>
    <scope>test</scope>
</dependency>
```

### @DataMongoTest
> MongoRepository에 관련된 빈 들만 전부 등록이 됨  
> 테스트 해보면 내장 MongoDB만 사용해서 테스트 하며 운영 MongoDB에는 아무런 영향을 주지 않음  
- AccountRepository에 findByEmail 추상 메서드 추가  
```java
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByEmail(String email);
}
```

### 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@DataMongoTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByEmail() {
        Account account = new Account();
        account.setUsername("freelife");
        account.setEmail("freejava1191@gmail.com");

        accountRepository.save(account);

        Optional<Account> byId = accountRepository.findById(account.getId());
        assertThat(byId).isNotEmpty();

        Optional<Account> byEmail = accountRepository.findByEmail(account.getEmail());
        assertThat(byEmail).isNotEmpty();
        assertThat(byEmail.get().getUsername()).isEqualTo("freelife");
    }
}
```