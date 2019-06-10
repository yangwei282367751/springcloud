package com.yangwei.order.redlock;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 杨威
 */
@Data
@ConditionalOnProperty("redisson.password")
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    private int timeout = 3000;

    private String address;

    private String password;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}
