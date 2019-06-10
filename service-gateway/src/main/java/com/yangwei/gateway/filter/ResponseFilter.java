package com.yangwei.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangwei.common.entity.ResponseEntity;
import lombok.Data;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultClientResponse;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.springframework.cloud.gateway.filter.WebClientWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

/**
 * @author 杨威
 */
@Component
public class ResponseFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(response){
            @Override
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);
                ResponseAdapter responseAdapter = new ResponseAdapter(body, httpHeaders);
                DefaultClientResponse clientResponse = new DefaultClientResponse(responseAdapter, ExchangeStrategies.withDefaults());
                //TODO: flux or mono
                Mono modifiedBody = clientResponse.bodyToMono(String.class).flatMap(originalBody -> {
                    String result = "";
                    if (response.getStatusCode() != null) {
                        if (response.getStatusCode().is2xxSuccessful()) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                Object object = mapper.readValue(originalBody,Object.class);
                                result = mapper.writeValueAsString(ResponseEntity.success(object));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    return Mono.just(result);
                });

                BodyInserter bodyInserter = fromPublisher(modifiedBody, String.class);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                        exchange.getResponse().getHeaders());
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            Flux<DataBuffer> messageBody = outputMessage.getBody();
                            //TODO: if (inputStream instanceof Mono) {
                            HttpHeaders headers = getDelegate().getHeaders();
                            if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                                messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                            }
                            //TODO: use isStreamingMediaType?
                            return getDelegate().writeWith(messageBody);
                        }));

            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    @Data
    public class ResponseAdapter implements ClientHttpResponse {

        private final Flux flux;
        private final HttpHeaders headers;

        ResponseAdapter(Publisher<? extends DataBuffer> body, HttpHeaders headers) {
            this.headers = headers;
            if (body instanceof Flux) {
                flux = (Flux<DataBuffer>) body;
            } else {
                flux = ((Mono) body).flux();
            }
        }

        @NonNull
        @Override
        public Flux<DataBuffer> getBody() {
            return flux;
        }

        @NonNull
        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        @NonNull
        @Override
        public HttpStatus getStatusCode() {
            return null;
        }

        @Override
        public int getRawStatusCode() {
            return 0;
        }

        @NonNull
        @Override
        public MultiValueMap<String, ResponseCookie> getCookies() {
            return null;
        }
    }

}
