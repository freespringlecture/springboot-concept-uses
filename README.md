# 스프링 데이터 9부: Redis
> 캐시, 메시지 브로커, 키/밸류 스토어 등으로 사용 가능

### redis 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### Redis 설치 및 실행 (도커)
#### Redis 설치
```bash
docker run -p 6379:6379 --name redis_boot -d redis
```

#### Redis 접속
```bash
docker exec -i -t redis_boot redis-cli
```

#### key 확인
```bash
127.0.0.1:6379> keys *
(empty list or set)
```

## 스프링 데이터 Redis
- https://projects.spring.io/spring-data-redis/
### 스프링 데이터 Redis를 사용할 수 있는 두 가지 방법
- StringRedisTemplate: String에 특화되어있는 Redis Template
- RedisTemplate

### 확장 Repository 클래스
- extends CrudRepository  
> SpringData 최상위 Repository 인터페이스  

## Redis 주요 커맨드
- https://redis.io/commands
- keys*
- get {key}
- hgetall {key}
- hget {key} {column}

## 커스터마이징
> properties 설정으로 커스터마이징이 가능  
- spring.redis.*  

## Redis 실습
#### RedisRunner를 만들고 간단하게 데이터 추가 테스트
```java
@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("freelife", "flash");
        values.set("springboot","2.0");
        values.set("hello", "world");
    }
}
```

#### Account 클래스 생성 하고 @RedisHash 추가
```java
@RedisHash("accounts")
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

#### AccountRepository 추가 후 CrudRepository 인터페이스 확장
```java
public interface AccountRepository extends CrudRepository<Account, String> {
}
```

#### RedisRunner에 Repository로 처리하도록 테스트 코드 추가
```java
@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("freelife", "flash");
        values.set("springboot","2.0");
        values.set("hello", "world");

        Account account = new Account();
        account.setEmail("freejava1191@gmail.com");
        account.setUsername("freelife");

        accountRepository.save(account);

        Optional<Account> byId = accountRepository.findById(account.getId());
        System.out.println(byId.get().getUsername());
        System.out.println(byId.get().getEmail());
    }
}
```

## 해쉬 값 조회
####  해쉬값의 email 조회 
```bash
hget accounts:51d346c6-a039-409e-a914-69318dfa7cb4 email

"freejava1191@gmail.com"
```

#### 전체 해쉬값 조회
```bash
hgetall accounts:51d346c6-a039-409e-a914-69318dfa7cb4

1) "_class"
2) "me.freelife.springdataredis.account.Account"
3) "id"
4) "51d346c6-a039-409e-a914-69318dfa7cb4"
5) "username"
6) "freelife"
7) "email"
8) "freejava1191@gmail.com"
```