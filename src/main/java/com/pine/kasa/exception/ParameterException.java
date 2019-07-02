package com.pine.kasa.exception;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-09-04 17:42
 */
public class ParameterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParameterException() {
    }

    public ParameterException(String message) {
        super(message);
    }

}
