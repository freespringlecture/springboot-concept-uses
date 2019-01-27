package me.freelife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner {

    @Autowired
    WebClient.Builder builder;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = builder.build();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // Stream API는 Subscribe 하기 전에는 Stream이 흐르지 않음 그냥 담아만 놓은 것 뿐임
        Mono<String> helloMono = webClient.get().uri("/hello") // get으로 /hello 요청
                .retrieve() // 응답값을 가져옴
                .bodyToMono(String.class);// Mono Type으로 변경

        // hello subscribe 결과값은 String
        helloMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) { // stopwatch가 실행중이면 종료
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });

        Mono<String> worldMono = webClient.get().uri("/world")
                .retrieve()
                .bodyToMono(String.class);

        worldMono.subscribe(s -> {
            System.out.println(s);

            if(stopWatch.isRunning()) {
                stopWatch.stop();
            }

            System.out.println(stopWatch.prettyPrint());
            stopWatch.start();
        });
    }
}