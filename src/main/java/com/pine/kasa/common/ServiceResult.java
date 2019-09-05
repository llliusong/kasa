package com.pine.kasa.common;

import com.pine.kasa.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * service层返回对象列表封装
 *
 * @param <T>
 */
public class ServiceResult<T> implements Serializable {

    private boolean success = false;

    private Integer code;

    private String message;

    private T result;


    private ServiceResult() {
    }

    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.result = result;
        item.code = ResultCodeEnum.SUCCESS.getCode();
        item.message = ResultCodeEnum.SUCCESS.getMessage();
        return item;
    }

    public static <T> ServiceResult<T> success(String msg) {
        return resultSuccess(ResultCodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ServiceResult<T> success(String msg, T result) {
        return resultSuccess(ResultCodeEnum.SUCCESS.getCode(), msg, result);
    }

    public static <T> ServiceResult<T> success(ResultCodeEnum codeEnum) {
        return resultSuccess(codeEnum.getCode(), codeEnum.getMessage());
    }

    public static <T> ServiceResult<T> resultSuccess(Integer code, String message) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.code = code;
        item.message = message;
        return item;
    }

    public static <T> ServiceResult<T> resultSuccess(Integer code, String message, T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.code = code;
        item.message = message;
        item.result = result;
        return item;
    }

    public static <T> ServiceResult<T> result(Integer errorCode, String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;
        return item;
    }

    public static <T> ServiceResult<T> failure(ResultCodeEnum errorCode) {
        return result(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> ServiceResult<T> failure(String msg) {
        return result(ResultCodeEnum.SYSTEM_ERROR.getCode(), msg);
    }

    public static <T> ServiceResult<T> systemError() {
        return result(ResultCodeEnum.SYSTEM_ERROR.getCode(), ResultCodeEnum.SYSTEM_ERROR.getMessage());
    }

    public boolean hasResult() {
        return result != null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
