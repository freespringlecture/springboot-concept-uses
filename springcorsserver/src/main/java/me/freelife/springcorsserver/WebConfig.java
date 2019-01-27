package me.freelife.springcorsserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/hello") // hello를 registry에 등록 /** 하면 전부다 허용
                .allowedOrigins("http://localhost:18080");
    }
}
