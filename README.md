# 5부: 스프링 부트 운영
스프링 부트는 애플리케이션 운영 환경에서 유용한 기능을 제공합니다  
스프링 부트가 제공하는 엔드포인트와 메트릭스 그리고 그 데이터를 활용하는 모니터링 기능에 대해 학습합니다  

## 스프링 부트 Actuator 1부: 소개
> 스프링 부트는 Actuator라는 모듈을 제공한다  
> Actuator을 사용하면 스프링 애플리케이션 운영중에 주시할 수있는 여러가지 유용한 기능들을 제공한다  
> 그런 정보들을 Endpoint 들을 통해 제공해줌  

### Actuator 의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 애플리케이션의 각종 정보를 확인할 수 있는 Endpoints
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints
> Actuator 의존성을 추가해주면 Actuator Endpoints 정보들이 활성화됨  
  
- 다양한 Endpoints 제공
- JMX또는HTTP를통해접근가능함
- shutdown을 제외한 모든 Endpoint는 기본적으로 ​활성화​ 상태
- 활성화 옵션 조정
  - `management.endpoints.enabled-by-default=false`
  - `management.endpoint.info.enabled=true`

#### Endpoints 정보
- `auditevents`: 인증정보(인증정보 획득, 실패) 이벤트
- `beans`: 등록된 빈들
- `conditions`: 어떤 자동 설정이 어떤 조건에 의해서 적용됐는지 안됐는지 정보
- `configprops`: 여기 보이는 것들이 application.properties에 정의가 가능한 것들
- `env`: Spring의 Environment를 Exposes 시켜주고 Environment안에 있는 properties를 보여줌
- `flyway`: flyway migration 정보를 보여줌
- `health`: 이 애플리케이션이 현재 잘 구동 중인지 health 정보를 보여줌
- `httptrace`: 최근 100개의 HTTP 요청과 응답 소요시간등을 보여줌
- `info`: 이 애플리케이션에 관련된 임의의 정보들을 보여줌
- `logger`: 어떤 패키지가 어떤 logging 레벨을 가지고 있는지 또는 그런 logging 레벨들을 운영중에 수정할 수도 있음
- `liquibase`: flyway와 비슷한 데이터베이스 migration liquibase가 의존성이 있는 경우에만 보여줌
- `metrics`: 애플리케이션에 핵심이 되는 정보들 이 애플리케이션이 사용하는 메모리, CPU가 어느정도되는지
  그런 정보들을 여러 모니터링 애플리케이션 사용할 수 있는 공통의 포멧으로 만들어서 제공해줌
  다른 모니터링 툴과 연동해서 사용할 수 있음 그래서 다른 모니터링 툴에서 알림을 받거나하는 등으로 활용가능
  아주 유용한 정보를 담고있는 Endpoint 정보
- `mappings`: 컨트롤러 맵핑정보를 보여줌
- `scheduledtasks`: scheduled 애노테이션으로 정의해놓은 scheduled tasks 정보를 보여줌
- `sessions`: Spring Session 관련된 Endpoint
- `shutdown`: 애플리케이션을 끌 수 있는 Endpoint 비활성화 되어있음 하지만 공개가 되어있어 동작함
- `threaddump`: threaddump를 뜰 수 있음

#### WebApplication Endpoints 정보
- `heapdump`: heapdump(현재 상태 메모리에 무엇이 들어있는지)를 뜰 수 있음
- `jolokia`: JMX beans를 HTTP View에서도 볼 수 있음
  JMX beans는 Java Management beans이라고 하며 JMX 표준에 준하는 bean을 만들면
  애플리케이션 밖에서 그 빈의 Operation들을 호출할 수 있음
  기본적으로 위의 모든 Endpoints 들은 JMX `Mbeans`로 등록되므로 전부 `Mbeans`로 노출이 됨
  그 외에도 추가로 JMX bean에 충족하는 빈에 애플리케이션을 만들어서 등록할 수 있음
  그런 것들도 jolokia를 통해서 HTTP를 통해서 접근이 가능해짐
- `logfile`: logfile에 해당하는 것들도 볼 수 있음
- `prometheus`: metrics 정보를 Prometheus server에서 캡쳐해갈 수 있는 형태로 변환해줌

### Actuator 접근
http://localhost:8080/actuator  
  
> 위와같이 HTTP로 접근하면 Spring HATEOAS(Hypermedia as the Engine of Application State)를 통해 접근가능한 링크 정보를 리턴해줌  
> http를 통해 접근할 경우 공개된 정보는 `health`, `info` 두가지 밖에 없음  
> 활성화와 Endpoints를 공개하는 것은 따로 설정이 되어있음  
> 활성화 여부는 Endpoint enabled 로 관리  
> 기본적으로 shutdown 빼고는 전부 `enabled=true`로 설정되어있음  
> 활성화와 공개여부를 따로 관리하는데 JMX는 거의 대부분 Exposeing되어있지만 Web은 보안적인 issue로 거의 Exposeing되어있지 않음  
> Web에서도 전부 보려면 Property로 설정해주면 볼 수 있음  