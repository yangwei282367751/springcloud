package com.yangwei.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 杨威
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOauth2Application.class, args);
    }

}
