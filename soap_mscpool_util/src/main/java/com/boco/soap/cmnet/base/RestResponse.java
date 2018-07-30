package com.boco.soap.cmnet.base;

public class RestResponse<T> {
    private boolean success = true;
    private String message;
    private T data;

    public RestResponse() {
    }

    public RestResponse(String message, T data) {
        this(true,message,data);
    }

    public RestResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RestResponse(boolean success, String message, T data) {
        this(success,message);
        this.data=data;
    }

    public static <W> RestResponse<W> ok(W data,String message){
        return new RestResponse(true,message,data);
    }

    public static <W> RestResponse<W> error(String message){
        return new RestResponse(false,message);
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
