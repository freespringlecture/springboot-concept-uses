# 스프링 데이터 8부: 데이터베이스 마이그레이션

## Flyway 
https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup  
> Flyway와 Liquibase가 대표적이지만 Flyway에 대해서만 알아봄  
https://flywaydb.org/  
- 스키마나 데이터 변경시 버전관리 하듯이 관리할 수 있음  
- Flyway는 기본적으로 SQL을 사용함  
- 다른 Migration 툴에서는 Rollback 까지 지원함 이용가치가 많음  
- flyway-database-migrations-on-startup

### 의존성 추가
- org.flywaydb:flyway-core

### 마이그레이션 디렉토리
- db/migration 또는 db/migration/{vendor}
- spring.flyway.locations로 변경 가능

### 마이그레이션 파일 이름
- V숫자__이름.sql
- V는 꼭 대문자로
- 숫자는 순차적으로 (타임스탬프 권장)
- 숫자와 이름 사이에 언더바 ​두 개​
- 이름은 가능한 서술적으로

## migration 실습
1. /src/resources 에 db/migration 폴더 생성 (db.migration 으로 생성하면 에러남)
2. V1__init.sql 파일 생성
3. schema.sql의 SQL을 V1_init.sql 파일로 복사해서 붙여넣기 한 다음 schema.sql 파일 제거
4. V1__init.sql SQL 교정
```sql
drop table if exists account;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table account (id bigint not null, email varchar(255), password varchar(255), username varchar(255), primary key (id));
```
5. 서버 기동 후 Schema 정상적으로 생성되는 지 확인
6. Account Entity에 active boolean 값 추가
```java
@Entity
public class Account {

    @Id
    @GeneratedValue //Repository를 통해 저장을 할 때 ID를 자동으로 생성
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean active;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return active == account.active &&
                Objects.equals(id, account.id) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(email, account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, active);
    }
}
```

7. 새로운 migration 스크립트 파일 V2__add_active.sql 추가
> 한번 적용이 된 migration 스크립트는 절대로 다시 건드리면 안됨  
```sql
ALTER TABLE account ADD COLUMN active BOOLEAN;
```

## 컬럼명 변경 시 
1. 새로운 migration 스크립트 추가해서 새로운 컬럼을 추가
2. 기존 컬럼 데이터를 새로운 컬럼으로 이동
3. 기존 컬럼 삭제 