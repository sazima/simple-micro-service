package com.imooc.response;

import java.io.Serializable;

public class Response implements Serializable {
    public static final Response USERNAME_OR_PASSWORD_ERROR = new Response(1001, "username or password is error");
    private int code;
    private String message;

    public Response(){
        this.code = 0;
        this.message = "success";
    }
    public Response(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
