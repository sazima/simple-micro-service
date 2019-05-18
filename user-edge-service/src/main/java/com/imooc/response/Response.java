package com.imooc.response;

import java.io.Serializable;

public class Response implements Serializable {
    public static final Response USERNAME_OR_PASSWORD_ERROR = new Response(1001, "username or password is error");
    public static final Response MOBILE_OR_EMAIL_REQUIRED = new Response(1002, "mobile or email is required");
    public static final Response SEND_VERIFY_CODE_FAIED = new Response(1003, "send verifyCode failed");
    public static final Response VERIFYCODE_INVALID = new Response(1004, "verify code invalid");

    public static final Response SUCCESS = new Response();
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

    public static Response exception(Exception e){
        return new Response(9999, e.getMessage());
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
