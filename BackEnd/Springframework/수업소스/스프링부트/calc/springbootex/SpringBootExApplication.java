package com.simple.springbootex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootExApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExApplication.class, args);
        System.out.println("server started!");
    }

}
