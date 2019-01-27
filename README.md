# 스프링 데이터 7부: 데이터베이스 초기화
## JPA를 사용한 데이터베이스 초기화
- spring.jpa.hibernate.ddl-auto 옵션  
> 작성해둔 Entity 정보를 바탕으로 스키마를 생성  
  > 데이터가 유지되므로 주로 `spring.jpa.hibernate.ddl-auto=update` 로 개발시에는 개발을 함  
  > update 단점은 컬럼이 변경되어도 그 컬럼은 남겨두고 새로변경된 컬럼만 추가하므로 운영시에 지저분해짐  
  - update: 기존의 스키마는 놔두고 추가된 사항만 변경함  
  - create-drop: 처음에 스키마를 만들고 Application 종료시 스키마 drop
  - create: 처음에 지우고 스키마를 새로 만듬
  - validate: 현재 Entity 맵핑이 릴레이션 DB에 맵핑할 수 있는 상황인지 맵핑이 되는지를 검증

- spring.jpa.generate-ddl  
> DDL에 변경을 허용하기 위한 프로퍼티  
> 기본적으로 false로 되어있어 true로 설정 해줘야 동작함  

- spring.jpa.show-sql  
> 기본적으로 false로 되어있어 true로 설정 해주면 console에 hibernate 로그를 보여줌  

### 운영용 설정
```
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.generate-ddl=false
```

#### Test에서는 아래의 두 옵션을 주석처리
```
#spring.jpa.hibernate.ddl-auto=validate
#spring.jpa.generate-ddl=false
```

## SQL 스크립트를 사용한 데이터베이스 초기화
> 테스트할때마다 Application이 구동될 때 마다 스크립트를 통해 데이터베이스를 초기화함  
> 스크립트를 만든상태에서는 ddl-auto, generate-ddl 옵션을 줘도 스크립트로 먼저 스키마 초기화를 하므로 에러가 발생하지 않음  
> 스크립트 실행 순서는 schema.sql -> data.sql 순서이므로 초기데이터를 스크립트로 넣을 수 있음  
- schema.sql 또는 schema-${platform}.sql  
- data.sql 또는 data-${platform}.sql
- ${platform} 값은 spring.datasource.platform 으로 설정 가능
> 아래외 같이 옵션을주고 postgresql-schema.sql로 주면 platform별로 스크립트를 작성할 수 있음  
```
spring.datasource.platform=postgresql
```

### 테스트 코드
1. 테스트에서 데이터베이스 초기화 SQL을 만든다
```sql
drop table account if exists
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table account (id bigint not null, email varchar(255), password varchar(255), username varchar(255), primary key (id))
```

2. src/resources 경로에 schema.sql 파일을 생성하고 SQL을 붙여넣는다