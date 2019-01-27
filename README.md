# 스프링 부트 소개
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-introducing-spring-boot
- 제품 수준의 스프링 기반의 독립적인 애플리케이션을 빠르고 쉽게 만들 수 있게 도와줌
- 스프링의 최적화된 컨벤션(opinionated view)을 제공
  - 스프링 프레임워크를 설정해주는 컨벤션
  - third-party 라이브러리에 해당하는 기본설정도 제공(Tomcat 등)
## 스프링 부트의 주된 목표
- 모든 스프링 개발을 할 때 더 빠르고 더 폭넓은 사용성을 제공
- 이미 컨벤션으로 정해진 설정들을 제공해주므로 일일히 설정을 할 필요가 없음
  하지만 원하는대로 요구사항에 맞게 얼마든지 설정을 쉽고 빠르게 바꿀수 있음
- 비지니스 로직에 필요한 기능들을 구현하는데 필요한 기능뿐 아니라 
  여러가지 지원 기능들
  (embedded servers, security, metrics, health checks, and externalized configuration)도 제공을 해줌
- XML 설정을 더 이상 사용하지 않고 코드 제너레이션을 하지 않는다 
  코드 제너레이션 하지 않으므로 더 쉽고 명확하고 더 커스터마이징 하기가 쉬움

## 시스템 사양
- Java8 이상 사용가능

## 서블릿 컨테이너
- 서블릿 버전 3.1
- Tomcat 8.5
- Jetty 9.4
- Undertow 1.4