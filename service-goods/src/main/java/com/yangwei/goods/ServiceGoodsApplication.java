package com.yangwei.goods;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 杨威
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDistributedTransaction
public class ServiceGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGoodsApplication.class, args);
    }

}
