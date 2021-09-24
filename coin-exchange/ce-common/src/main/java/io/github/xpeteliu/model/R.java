package io.github.xpeteliu.model;

import io.github.xpeteliu.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    public static final int SUCCESS = CommonConstant.SUCCESS;

    public static final int FAILURE = CommonConstant.FAILURE;

    private int code;

    private T data;

    private String msg;

    public static <T> R<T> success() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> success(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> success(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> success(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> failure() {
        return restResult(null, FAILURE, null);
    }

    public static <T> R<T> failure(String msg) {
        return restResult(null, FAILURE, msg);
    }

    public static <T> R<T> failure(T data) {
        return restResult(data, FAILURE, null);
    }

    public static <T> R<T> failure(T data, String msg) {
        return restResult(data, FAILURE, msg);
    }

    public static <T> R<T> failure(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
