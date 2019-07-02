package com.pine.kasa.exception;

/**
 * 登录异常
 * <p>
 * Created by pine on  2018/3/22 下午8:50.
 */
public class LoginException extends RuntimeException {

    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

}
