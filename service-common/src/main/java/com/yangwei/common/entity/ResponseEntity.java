package com.yangwei.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @param <T>
 * @author 杨威
 */
@Data
public class ResponseEntity<T> implements Serializable {

    private Integer httpCode;

    private String message;

    private T data;

    private ResponseEntity() {

    }

    private ResponseEntity(HttpCode httpCode) {
        this.httpCode = httpCode.getHttpCode();
        this.message = httpCode.getMessage();
    }

    private ResponseEntity(HttpCode httpCode, T data) {
        this.httpCode = httpCode.getHttpCode();
        this.message = httpCode.getMessage();
        this.data = data;
    }

    private ResponseEntity(Integer httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    private ResponseEntity(Integer httpCode, String message, T data) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<T> build(Integer httpCode, String message) {
        return new ResponseEntity<>(httpCode, message);
    }

    public static <T> ResponseEntity<T> build(Integer httpCode, String message, T data) {
        return new ResponseEntity<>(httpCode, message, data);
    }

    public static<T> ResponseEntity<T> success(T data){
        return  new ResponseEntity<>(HttpCode.SUCCESS, data);
    }

    public static<T> ResponseEntity<T> error(){
        return  new ResponseEntity<>(HttpCode.ERROR);
    }

    public static<T> ResponseEntity<T> unauthorized(){
        return  new ResponseEntity<>(HttpCode.UNAUTHORIZED);
    }

    private enum HttpCode {

        // 成功
        SUCCESS(200, "请求成功"),
        // 失败
        ERROR(400, "请求失败"),
        // 未认证（签名错误）
        UNAUTHORIZED(401, "尚未认证"),
        // 服务器内部错误
        INTERNAL_SERVER_ERROR(500, "服务器内部错误");

        private Integer httpCode;

        private String message;

        HttpCode(Integer httpCode, String message) {
            this.httpCode = httpCode;
            this.message = message;
        }

        public Integer getHttpCode(){
            return httpCode;
        }

        public String getMessage(){
            return message;
        }
    }
}
