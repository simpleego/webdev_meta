package com.simple.springex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringExApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExApplication.class, args);
        System.out.println("hello world");
    }

}
