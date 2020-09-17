package com.duobang.pms_lib.network.model;

public class ErrorBody {

    /**
     * statusCode : 400
     * error : Bad Request
     * message : 该分项正在执行, 不允许删除
     */

    private int statusCode;
    private String code;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
