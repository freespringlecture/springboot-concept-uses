# 스프링 데이터 4부: PostgreSQL 설정하기
### PostgreSQL 의존성 추가
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```
## Docker 설치
https://docs.docker.com/docker-for-mac/install/
#### 설치 후 docker logout
```bash
docker logout

docker run hello-world
```

## PostgreSQL 설치
#### Docker로 PostgreSQL 설치
```bash
docker run -p 5432:5432 -e POSTGRES_PASSWORD=1879asdf -e POSTGRES_USER=freelife -e POSTGRES_DB=springboot --name postgres_boot -d postgres
```

#### Docker container 중지
```bash
docker stop postgres_boot
```

#### Docker container 삭제
```bash
docker rm postgres_boot
```

#### Docker 프로세스 확인
```bash
docker ps
```

#### Docker PostgreSQL container 접근
```bash
docker exec -i -t postgres_boot bash
```

#### Docker container 프로세스 확인
```bash
ps aux | grep postgres
```

#### User postgres로 전환
```bash
su - postgres
```

#### PostgreSQL 접속
```bash
psql -U freelife  
```

#### 데이터베이스 조회
```bash
\list or \l
```

#### 테이블 조회
```bash
\dt
```

#### PostgreSQL 접속 끊기
```bash
\q
```

#### account 테이블 조회
```bash
SELET * FROM account;
```

## 테스트 코드 
#### application.properties 작성
```
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
spring.datasource.username=freelife
spring.datasource.password=1879asdf
```

#### PostgreSQL Runner 작성
> PostgreSQL에서는 USER가 예약어이므로 account로 테이블명 교체  
```java
@Component
public class PostgreSQLRunner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Java8에서 지원하는 try문 connection 이라는 resource를 block 안에서 사용하고 무슨 문제가 생기든 정리를 해줌
        try(Connection connection = dataSource.getConnection()) {
            System.out.println(dataSource.getClass());
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE account(ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        }

        jdbcTemplate.execute("INSERT INTO account VALUES(1, 'freelife')");
    }
}
```

## Intellij Database
> Intellij Database에서 간편하게 PostgreSQL 조회 가능  