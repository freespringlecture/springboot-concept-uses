# 스프링 데이터 2부: 인메모리 데이터베이스

## 지원하는 인-메모리 데이터베이스
- H2 (추천, 콘솔 때문에...)
- HSQL
- Derby

## Spring-JDBC가 클래스패스에 있으면 자동 설정이 필요한 빈을 설정 해줌
> SpringBoot AutoConfiguration 에서 가장 중요한 아래의 두개의 항목을 자동설정 해줌  
- DataSource
- JdbcTemplate  
> Resource 반납 처리가 잘되어 있고 예외를 던질 때 에러 계층 구조를 잘만들어놔서 좀 더 가독성이 높은 에러 메세지를 확인 할 수 있음  
> 기본적인 jdbc API 사용하는 것 보다 훨씬 더 좋음  

## H2를 사용하는 테스트 코드 작성
> jdbc 와 h2 dependency를 추가하고 서버를 구동하면  
> 기본적으로 h2를 사용하는 jdbc 설정이 되고 Application 이 동작함  
  
- Intellij 에서 생성시 web, jdbc, h2 선택 후 생성

## H2 In-memory 조회 방법
### Intellij 에서 조회
1. Database - Data Source - H2 로 생성
2. URL을 In-memory 로 변경
3. Database는 testdb

### H2 Console 에서 조회
> spring-boot-devtools를 추가 하거나  
> spring.h2.console.enabled=true 만 추가  
1. /h2-console로 접속 (이 path도 바꿀 수 있음)
2. URL: jdbc:h2:mem:testdb 로 접속
3. 데이터 조회

## 인-메모리 데이터베이스 기본 연결 정보 확인하는 방법
> SpringBoot jdbc - DataSourceProperties에 기본 정보로 등록되어 있음  
- URL: “testdb”
- username: “sa”
- password: “”

## 실습 코드
> jdbc  
```sql
 CREATE TABLE USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))
 INSERT INTO USER VALUES (1, ‘freelife’)
```