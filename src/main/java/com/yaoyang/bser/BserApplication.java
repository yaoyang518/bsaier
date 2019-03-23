package com.yaoyang.bser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BserApplication.class, args);
    }
}
