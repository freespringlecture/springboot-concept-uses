package me.freelife;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(SampleRunner.class);

    @Autowired
    private String hello;

    @Autowired
    FreelifeProperties freelifeProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("=======================");
        logger.debug(hello);
        logger.debug(freelifeProperties.getName());
        logger.debug(freelifeProperties.getFullName());
        logger.debug(String.valueOf(freelifeProperties.getAge()));
        logger.debug(freelifeProperties.getDbName());
        logger.debug(String.valueOf(freelifeProperties.getSessionTimeout()));
        logger.debug(String.valueOf(freelifeProperties.getReadTimeout()));
        logger.debug("=======================");
    }
}
