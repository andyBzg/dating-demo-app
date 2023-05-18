package org.crazymages.datingdemoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DatingDemoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatingDemoAppApplication.class, args);
    }

}
