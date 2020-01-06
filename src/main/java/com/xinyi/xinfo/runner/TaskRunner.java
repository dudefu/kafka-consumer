package com.xinyi.xinfo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

public class TaskRunner implements ApplicationRunner, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void run(ApplicationArguments args) {
        logger.info("task runner");
    }

}
