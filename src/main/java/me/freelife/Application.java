package me.freelife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FreelifeProperties.class)
public class Application {

    // 외부의 설정 파일의 경우 흔한 경우는 아님
    /*
    @ConfigurationProperties("server")
    @Bean
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }
    */

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}