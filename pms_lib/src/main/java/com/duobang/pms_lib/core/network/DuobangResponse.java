package com.duobang.pms_lib.core.network;

public class DuobangResponse<T> {

    /**
     *  code表示结果 1.success 2.failure（正常请求成功失败）
     *             3.error(异常等不可测错误)
     */
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
