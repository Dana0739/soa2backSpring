package com.dana.soa2back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Soa2backApplication {
    public static void main(String[] args) {
        SpringApplication.run(Soa2backApplication.class, args);
    }
}
