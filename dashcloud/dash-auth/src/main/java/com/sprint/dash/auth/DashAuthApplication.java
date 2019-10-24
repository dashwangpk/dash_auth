package com.sprint.dash.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.sprint.dash"})
public class DashAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashAuthApplication.class, args);
    }

}
