package com.example.blog;

public class Result<T>{
    private int code;
    private String message;
    private T data;
    public static <T> Result<T> success(T data){
        Result<T> r=new Result<>();
        r.setCode(200);
        r.setMessage("success");
        r.setData(data);
        return r;
    }
    public static <T> Result<T> success(){
        return success(null);
    }
    public static <T> Result<T> error(String message){
        Result<T> r=new Result<>();
        r.setCode(500);
        r.setMessage(message);
        return r;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
