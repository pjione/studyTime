package com.studytime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class StudytimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudytimeApplication.class, args);
    }

}
