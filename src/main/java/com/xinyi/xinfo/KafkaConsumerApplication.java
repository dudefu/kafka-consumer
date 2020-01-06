package com.xinyi.xinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.xinyi.xinfo.runner.StartupRunner;
import com.xinyi.xinfo.runner.TaskRunner;

@SpringBootApplication
public class KafkaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);

    }

    @Bean
    public StartupRunner startupRunner(){
        return new StartupRunner();
    }

    @Bean
    public TaskRunner taskRunner(){
        return new TaskRunner();
    }

}
