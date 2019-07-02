package com.pine.kasa.controller;

import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.enums.ResultCodeEnum;
import com.pine.kasa.exception.BusinessException;
import com.pine.kasa.exception.DingTalkException;
import com.pine.kasa.exception.ParameterException;
import com.pine.kasa.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BaseController 异常捕获,需要时 在controller层继承该类即可
 *
 * @author pine
 * @create 2017-08-30 上午11:18
 **/
public class BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            if (exception instanceof MissingServletRequestParameterException) {//参数异常
                CommonUtils.out(response, request, ServiceResult.result(ResultCodeEnum.PARAMETER_ERROR.getCode(), exception.getMessage()));
            } else {
                logger.error("全局异常捕获 Exception:", exception);
                CommonUtils.out(response, request, ServiceResult.systemError());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @ExceptionHandler(value = LoginException.class)
    public void loginExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 LoginException:", exception.getMessage());
            CommonUtils.out(response, request, ServiceResult.failure(ResultCodeEnum.NOT_LOGIN));
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @ExceptionHandler(value = ParameterException.class)
    public void parameterExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 ParameterException:", exception.getMessage());
            CommonUtils.out(response, request, ServiceResult.result(ResultCodeEnum.PARAMETER_ERROR.getCode(), exception.getMessage()));
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }


    @ExceptionHandler(value = DingTalkException.class)
    public void dingTalkExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.error("全局异常捕获 DingTalkException:", exception);
            CommonUtils.out(response, request, ServiceResult.result(ResultCodeEnum.DINGTALK_ERROR.getCode(), exception.getMessage()));
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }

    @ExceptionHandler(value = BusinessException.class)
    public void businessExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 BusinessException:", exception.getMessage());
            BusinessException e = (BusinessException) exception;
            if (e == null) {
                CommonUtils.out(response, request, ServiceResult.result(e.getCode().getCode(), e.getCode().getDesc()));
            } else {
                CommonUtils.out(response, request, ServiceResult.result(e.getCode().getCode(), exception.getMessage()));
            }
//            SendMsgUtils.sendRobotMessage("全局异常捕获 Exception:" + ExceptionUtils.getStackTrace(exception));
        } catch (Exception e) {
            logger.error("Exception", e);
        }
    }


}
