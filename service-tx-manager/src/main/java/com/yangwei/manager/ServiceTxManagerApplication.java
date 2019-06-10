package com.yangwei.manager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 杨威
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagerServer
public class ServiceTxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTxManagerApplication.class, args);
    }

}
