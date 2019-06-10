package com.yangwei.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.yangwei.common.entity.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * zuul统一处理返回结果
 * @author 杨威
 */
@Component
public class ResponseFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        InputStream stream = context.getResponseDataStream();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(body,Object.class);
            String response = mapper.writeValueAsString(ResponseEntity.success(object));
            context.setResponseBody(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
