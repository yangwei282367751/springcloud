package com.yangwei.zuul.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 杨威
 */
@Configuration
public class HystrixConfig {

    @Bean(name = "hystrixRegistrationBean")
    public ServletRegistrationBean<HystrixMetricsStreamServlet> servletRegistrationBean() {
        ServletRegistrationBean<HystrixMetricsStreamServlet> registration = new ServletRegistrationBean<>(
                new HystrixMetricsStreamServlet(), "/hystrix.stream");
        registration.setName("hystrixServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean(name = "hystrixForTurbineRegistrationBean")
    public ServletRegistrationBean<HystrixMetricsStreamServlet> servletTurbineRegistrationBean() {
        ServletRegistrationBean<HystrixMetricsStreamServlet> registration = new ServletRegistrationBean<>(
                new HystrixMetricsStreamServlet(), "/actuator/hystrix.stream");
        registration.setName("hystrixForTurbineServlet");
        registration.setLoadOnStartup(1);
        return registration;
    }
}
