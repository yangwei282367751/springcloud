package com.yangwei.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * zuul实现路由网关
 * @author 杨威
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class ServiceZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }

}

