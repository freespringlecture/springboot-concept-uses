# 스프링 데이터 3부: MySQL
> DBCP란 Database Connection Pool  
> DBCP에서 Database Connection을 만드는 과정이 많은 과정이 일어나는 작업임  
> 그래서 Connection을 미리 여러개 만들어 놓고 필요할때 마다 그때그때 가져다가 쓰도록 하는 개념  
> Connection 유지 갯수 및 시간 등 여러가지 설정을 할 수가 있음  
> DBCP가 Application 성능에 아주 핵심적인 역할을 하므로 DBCP에 bug가 있으면 Application에 아주 심각한 문제가 발생함  
> DBCP에 대해서 충분히 알고 사용을 해야 함  

## HikariCP 주요 설정 정보
- https://github.com/brettwooldridge/HikariCP#frequently-used
- connectionTimeout: 커넥션 타임아웃
- maximumPoolSize: 커넥션 유지 갯수 Default 10  
  > Connection 객체를 몇개를 유지할 것인가 동시에 실행할 수 있는 Connection 수는 CPU Core 갯수와 같음  
  > CPU Core를 넘어간 갯수의 객체들은 모두 대기 하면서 타임슬라이싱해서 조금씩 처리함  
## 지원하는 DBCP
> SpringBoot는 기본적으로 HikariCP라는 DBCP를 사용함  
> Hikari DBCP 옵션 값들은 HikariConfig에 정의 되어있음  
> ex) `spring.datasource.hikari.maximum-pool-size=4`  
1. HikariCP​ (기본)
2. Tomcat CP
3. Commons DBCP2

## 테스트 코드 작성
1. Datasource 구현체 mysql-connector-java dependency 추가
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### application.properties 작성
```
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=freelife
spring.datasource.password=1879asdf
```

## MySQL 추가 (도커 사용)
> Docker kernal을 공유하기때문에 가상머신을 사용할때보다 훨씬 빠르게 Application을 설치할 수 있음
#### docker container stop
```bash
docker stop mysql_boot
```

#### docker container delete
```bash
docker rm mysql_boot
```

#### docker mysql install & run
```bash
docker run -p 3306:3306 --name ​mysql_boot ​-e MYSQL_ROOT_PASSWORD=​1​ -e
MYSQL_DATABASE=​springboot​ -e MYSQL_USER=​freelife -e
MYSQL_PASSWORD=​pass​ -d mysql
```

#### docker mariadb install & run
```bash
docker run -p 3306:3306 --name ​mysql_boot ​-e MYSQL_ROOT_PASSWORD=​1​ -e
MYSQL_DATABASE=​springboot​ -e MYSQL_USER=​freelife -e
MYSQL_PASSWORD=​pass​ -d mariadb
```

#### docker 인스턴스 보기
```bash
docker ps
```

#### docker container 안에서 bash 실행
```bash
docker exec -i -t mysql_boot bash
```

#### mysql 접속 및 데이터베이스 및 테이블 확인
```bash
mysql -u freelife -p

show databases;
use springboot;
show tables;
```

## MySQL용 Datasource 설정
- spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false
- spring.datasource.username=freelife
- spring.datasource.password=1879asdf


## MySQL 접속시 에러

### TIMEZONE 설정 변경
http://ggamu.com/81

#### my.cnf에 TimeZone 값 Asia/Seoul 로 변경
```
vi /usr/local/etc/my.cnf

[mysqld]
default-time-zone=Asia/Seoul
```

#### mysql 서버 재기동 안되면
```bash
sudo chown -R mysql:_mysql /usr/local/var/mysql
sudo mysql.server start
```
  
OR
  
#### JDBC TimeZone 설정
http://dogcowking.tistory.com/179
```
jdbc:mysql://ip:port/TestDB?characterEncoding=UTF-8&serverTimezone=UTC
```


### MySQL 5.* 최신 버전 사용할 때
> SSL 설정을 하지 않을때 Wanning을 없애기위에 SSL 옵션을 끔  
  
| 항목 | 내용                                                         |
| ---- | ------------------------------------------------------------ |
| 문제 | Sat Jul 21 11:17:59 PDT 2018 WARN: Establishing SSL connection without server's identity verification is not recommended. **According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set.** For compliance with existing applications not using SSL the **verifyServerCertificate property is set to 'false'**. You need either to explicitly disable SSL by setting **useSSL=false**, or set **useSSL=true and provide truststore** for server certificate verification. |
| 해결 | jdbc:mysql:/localhost:3306/springboot?**useSSL=false**       |

### MySQL 8.* 최신 버전 사용할 때
| 항목 | 내용                                                         |
| ---- | ------------------------------------------------------------ |
| 문제 | com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException : Public Key Retrieval is not allowed |
| 해결 | jdbc:mysql:/localhost:3306/springboot?useSSL=false&**allowPublicKeyRetr ieval=true** |

## MySQL 라이센스 (GPL) 주의
- MySQL 대신 MariaDB 사용 검토  
> MariaDB 는 상용 소프트웨어가 아니라 무료긴 하지만 GPL2이라서 소스공개 의무가 발생 할 수 있음  
- 소스코드 공개 의무 여부 확인  
> GPL은 소스공개 의무가 있음  