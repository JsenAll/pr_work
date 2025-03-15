package com.org.pr_work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class PrWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrWorkApplication.class, args);
    }

}
