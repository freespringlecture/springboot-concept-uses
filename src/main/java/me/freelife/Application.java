package me.freelife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // SpringApplication app = new SpringApplication(Application.class);
        //배너 설정
        //TXT 파일이 있으면 TXT 파일이 우선 적용됨
        /*
        app.setBanner((environment, sourceClass, out) -> {
            out.println("=========================");
            out.println("FREELIFE");
            out.println("=========================");
        });
        */

        //배너 사용안함
        //app.setBannerMode(Banner.Mode.OFF);
        //app.run(args);

        //빌더 패턴
        new SpringApplicationBuilder()
                .sources(Application.class)
                .run(args);
    }

}