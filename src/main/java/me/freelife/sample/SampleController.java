package me.freelife.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    Logger log = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    SampleService sampleService;

    @GetMapping
    public String hello() {
        log.info("SuperMan");
        System.out.println("Flash");
        return "hello " + sampleService.getName();
    }
}
