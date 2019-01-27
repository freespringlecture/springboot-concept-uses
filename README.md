# 스프링 시큐리티 2부: 시큐리티 설정 커스터마이징
> 스프링부트가 제공하는 기본설정 두가지  
- WebSecurityConfigureAdapter 설정  
    > Form인증과 Basic Authentication을 활성화 시켜줌  
    > 모든 요청이 다 Authentication이 필요하다고 정의  
- UserDetailsService 설정  
    > 기본으로 랜덤한 유저를 생성해주는 건 `UserDetailsServiceAutoConfiguration`  

## 1단계 실습 - Web Security Configuration
> hello나 index페이지는 아무나 접근  
> my 페이지만 인증을 한 사용자가 방문하도록  
> `WebSecurityConfigurerAdapter`의 빈을 등록하면 SpringBoot가 제공하는 SecurityAutoConfiguration 은 사용이 안됨  
> 정의 하는대로 동작함  

### thymeleaf, security 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

#### Controller 작성
```java
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
```

#### src/resources/template에 index, hello, my html 생성
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title</title>
</head>
<body>
<h1>Hello Spring Boot Security</h1>
<a href="/hello">Hello</a>
<a href="/my">My</a>
</body>
</html>
```

#### SecurityConfig 생성
> index와 hello는 인증을 예외해서 접근이 가능  
> my는 accept Header에 html이 있으므로 formLogin에 걸려서 로그인 인증요청 화면으로 이동  
> html이 없는 경우에는 httpBasic에 걸림  
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // index와 hello는 인증없이 접근이 가능
                .antMatchers("/","/hello").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }
}
```

## 2단계 실습 - UserDetailsService 구현
https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-authentication-userdetailsservice
> 스프링 부트에서 자동으로 유저를 만들어주는 것이 아닌 직접 Account를 관리 하도록  
  
### jpa와 h2 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

#### Account 클래스 생성
```java
@Entity
public class Account {

    @Id @GeneratedValue
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
}
```

#### AccountRepository 생성
> JPA 설정이 되었고 h2를 추가하였으므로 자동으로 h2를 사용하도록 설정됨
```java
public interface AccountRepository extends JpaRepository<Account, Long> {
}
```

#### AccountRunner 생성
> 로그인할 테스트 유저를 임의로 생성하기 위해 Runner 클래스를 생성함
```java
@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account freelife = accountService.createAccount("freelife", "1234");
        System.out.println(freelife.getUsername() + " password: " + freelife.getPassword());
    }
}
```

### AccountService 생성
> 보통 Service에 UserDetailService 클래스를 상속 받아서 오버라이딩 해서 구현
> `UserDetailService` 빈이 등록되면 스프링 부트 시큐리티가 더이상 랜덤으로 유저를 생성하지 않음

#### `UserDetailService` interface 검증 순서
1. 오버라이딩된 loadUserByUsername 에서 입력받은 username이 들어옴
2. username에 해당하는 실제 유저정보를 확인(UserDetails)
3. 확인된 유저정보의 패스워드와 입력한 패스워드가 같으면 로그인 처리 다르면 예외 처리

#### loadUserByUsername 로직 처리 과정
1. byUsername에 실제 데이터가 없으면 UsernameNotFoundException 를 예외로 던짐
2. 있으면 account가 실제 account로 나옴
3. return은 UserDetails라는 인터페이스 구현체를 return
4. UserDetails라는 인터페이스는 서비스에 구현되어있는 유저정보들의 인터페이스
5. 가장 핵심적인 유저정보들의 중요한 정보들을 담고있는 인터페이스
6. 이 인터페이스의 기본 구현체를 스프링 시큐리티가 User라는 이름으로 제공해줌
7. 특정권한을 가진 유저임을 설정하고 우리가 가지고 있는 asccount 정보를 UserDetails로 변환하는 과정
8. 변환을 하면 스프링 시큐리티가 username과 password를 확인해서 로그인할때 입력한 사용자 정보가 유효한지 확인

```java
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        Account account = byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(account.getUsername(), account.getPassword(), authorities());
    }

    private Collection<? extends GrantedAuthority> authorities() {
        // ROLE_USER 이라는 권한을 가진 유저임을 설정
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
```

## 3단계 실습 - PasswordEncoder 설정 및 사용
https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#core-services-password-encoding

### PasswordEncoder 예외 발생
> 로직을 다 구현하고 테스트 해보면 아래와 같은 예외사항이 발생  
```bash
ava.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
```

> 스프링 시큐리티 버전이 올라가면서 다양한 인코딩을 지원  
> password 포맷이 {noop}password 여야지 아무것도 인코딩하지 않는 패스워드 인코더로 디코딩을 시도  
> 아무것도 없는 경우 위와 같이 예외가 발생  

### NoOpPasswordEncoder
> 실제로는 사용하면 안되지만 예외적으로 회피할 수 있는 NoOpPasswordEncoder로 로직 처리  
> 스프링 시큐리티가 사용할 Encoder가 더이상 기본 패스워드가 아닌 NoOpPasswordEncoder 로 변환됨  
> 패스워드 앞에 있는 prefix 값을 보고 어떤 Encoding인지 확인 한 다음에 Encoding Decoding하는 똑똑한 패스워드가 아니라  
> 일부러 그 빈을 Encoding Decoding 아무것도 하지 않는 NoOp 그런 패스워드 Encoder로 바꾼 것임  
> 이렇게는 절대로 사용하면 안됨  
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
}
```

### 권장하는 PasswordEncoder
> 아래는 스프링 시큐리티가 권장하는 PasswordEncoder으로 설정  
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
```


#### SecurityConfig에 PasswordEncoder 빈으로 등록하도록 설정 추가
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // index와 hello는 인증없이 접근이 가능
                .antMatchers("/", "/hello").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
```

#### AccountService PasswordEncoder를 사용하도록 로직 수정
> PasswordEncoder을 주입을 받아서 Encoding 된 password를 저장하도록 수정 
```java
@Autowired
private PasswordEncoder passwordEncoder;

public Account createAccount(String username, String password) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(passwordEncoder.encode(password));
    return accountRepository.save(account);
}
```

### 결과 확인
> 아래와 같이 bycrypt로 Encoding 된 것을 확인 할 수 있다
```bash
freelife password: {bcrypt}$2a$10$hPZ8YCYcvoVtNxwp/FttWuwzAxx.9SVtDfNY.TGl44IOgFLB9.SN2
```