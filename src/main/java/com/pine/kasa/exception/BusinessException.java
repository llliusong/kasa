
package com.pine.kasa.exception;

import com.pine.kasa.enums.ResultCodeEnum;

/**
 * 业务异常
 *
 * @author pine
 * @create 2017-09-14 上午11:13
 **/
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResultCodeEnum code;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.PARAMETER_ERROR;
    }

    public BusinessException(ResultCodeEnum code) {
        super(code.getMessage());
        this.code = code;
    }

    public BusinessException(ResultCodeEnum code, String message) {
        super(message);
        this.code = code;
    }

    public ResultCodeEnum getCode() {
        return code;
    }

}
