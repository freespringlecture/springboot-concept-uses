# 5. 내장 웹 서버 이해
- SpringBoot는 웹 서버가 아니다
- SpringBoots는 Spring을 쉽게 사용하기 위한 도구
- embed tomcat으로 내부에서 톰캣을 사용할 수 있게 설정해놓은 것이다
- Java 코드로 톰캣을 만들 수 있음
```java
public class Application {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        Context context = tomcat.addContext("/","/");

        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();
                writer.println("<html><head><title>");
                writer.println("Hey, Tomcat");
                writer.println("</title></head>");
                writer.println("<body><h1>Hello Tomcat</h1></body>");
                writer.println("</html>");
            }
        };

        String servletName = "helloServlet";
        tomcat.addServlet("/", servletName, servlet);
        context.addServletMappingDecoded("/hello", servletName);

        tomcat.start();
        tomcat.getServer().await();

    }

}
```

## ServletWebServerFactoryAutoConfiguration
> Servlet 컨테이너를 만듬(서블릿 웹 서버 생성)
- TomcatServletWebServerFactoryCustomizer (서버 커스터마이징)

## DispatcherServletAutoConfiguration
- DispatcherServlet을 만들고 Servlet컨테이너에 등록