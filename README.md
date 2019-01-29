# 스프링 데이터 6부: Spring-Data-JPA 연동

## h2를 test 의존성으로 추가
> TEST는 h2를 사용  
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

## PostgreSQL 의존성 추가
> Application을 실행할때는 PostgreSQL을 사용  
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

## 스프링 데이터 JPA 사용하기
- @Entity 클래스 만들기
- Repository 만들기

## 스프링 데이터 리파지토리 테스트 만들기
> 슬라이스 테스트란 Reopository와 Repository와 관련된 빈 들만 등록을 해서 테스트를 만드는 것  
- H2 DB를 테스트 의존성에 추가하기  

### @DataJpaTest (슬라이스 테스트) 작성
> @DataJpaTest를 사용하지 않고 @SpringBootTest로 테스트 하면 integration 테스트 임  
> Application에 있는 @SpringBootApplication을 찾아서 모든 빈들을 다 등록하고 application.properties가 적용되고 postgreSQL 사용하게됨  
> @SpringBootTest(properties = "spring.datasource.url=''") 로 다른 데이터베이스를 설정해서 사용이 가능하긴 하지만  
> 테스트 할때는 In-memory 데이터베이스로 테스트 하기를 권장함  
> 슬라이스 테스트를 할 때는 반드시 In-memory 데이터베이스가 필요 하므로 H2를 test 의존성으로 추가  

### 이전시간에 했던 PostgreSQL Docker 시작
```bash
docker start postgres_boot
docker ps
```

### PostgreSQL Wanning 해결법
> 드라버가 createClob()라는 Method를 지원하지않아서 Wanning이 발생  
  
| 항목 | 내용                                                         |
| ---- | ------------------------------------------------------------ |
| 경고 | org.postgresql.jdbc.PgConnection.createClob() is not yet implemented |
| 해결 | spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true |

### DatabaseMetaData 확인 테스트 코드
```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {

        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getUserName());
        }
    }
}
```

#### Entity Account 클래스 작성
> equals & hashcode도 추가  
```java
@Entity
public class Account {

    @Id
    @GeneratedValue //Repository를 통해 저장을 할 때 ID를 자동으로 생성
    private Long id;
    private String username;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
```

#### JpaRepository 인터페이스 작성
```java
//JpaRepository< Entity의 타입, ID의 타입>
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
```

#### 테스트 코드 작성
```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        Account account = new Account();
        account.setUsername("freelife");
        account.setPassword("pass");

        // 새로운 account 등록
        Account newAccount = accountRepository.save(account);
        // 새로운 account가 등록 됐는지 확인
        assertThat(newAccount).isNotNull();

        // 새로운 account의 username으로 조회해서 데이터가 있는지 확인
        Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotNull();

        // 없는 username으로 조회해서 데이터가 없는지 확인
        Account nonExistingAccount = accountRepository.findByUsername("ironman");
        assertThat(nonExistingAccount).isNull();
    }
}
```

#### JpaRepository Optional로 변경
```java
//JpaRepository< Entity의 타입, ID의 타입>
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
```

#### Optional로 테스트 코드 변경
```java
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        Account account = new Account();
        account.setUsername("freelife");
        account.setPassword("pass");

        // 새로운 account 등록
        Account newAccount = accountRepository.save(account);
        // 새로운 account가 등록 됐는지 확인
        assertThat(newAccount).isNotNull();

        // 새로운 account의 username으로 조회해서 데이터가 있는지 확인
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotEmpty();

        // 없는 username으로 조회해서 데이터가 없는지 확인
        Optional<Account> nonExistingAccount = accountRepository.findByUsername("ironman");
        assertThat(nonExistingAccount).isEmpty();
    }
```

### native Query사용법
> 아래와 같은 형식으로 nativeQuery로 사용해도 되지만 JPA에서 제공하는 Query Languge를 사용해서 JPQL로 작성하는 것을 권장  
```java
//JpaRepository< Entity의 타입, ID의 타입>
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(nativeQuery = true, value = "select * from account where username = '{0}]")
    Account findByUsername(String username);
}
```