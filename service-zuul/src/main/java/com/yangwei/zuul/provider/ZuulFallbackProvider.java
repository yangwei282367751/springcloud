package com.yangwei.zuul.provider;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author 杨威
 */
@Component
public class ZuulFallbackProvider implements FallbackProvider{

    private Logger logger = Logger.getLogger(ZuulFallbackProvider.class.toString());

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        logger.warning(String.format("route:%s,exceptionType:%s,stackTrace:%s", route, cause.getClass().getName(), Arrays.toString(cause.getStackTrace())));
        String message;
        if (cause instanceof HystrixTimeoutException) {
            message = "zuul Timeout";
        } else {
            message = "Service exception";
        }
        return fallbackResponse(message);
    }
    private ClientHttpResponse fallbackResponse(String message) {
        return new ClientHttpResponse() {

            @NonNull
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.OK;
            }

            @NonNull
            @Override
            public int getRawStatusCode() {
                return 200;
            }

            @NonNull
            @Override
            public String getStatusText() {
                return "OK";
            }

            @Override
            public void close() {

            }

            @NonNull
            @Override
            public InputStream getBody() {
                String bodyText = String.format("{\"httpCode\": 9999,\"message\": \"Service unavailable:%s\"}", message);
                return new ByteArrayInputStream(bodyText.getBytes());
            }

            @NonNull
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
