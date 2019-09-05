package com.pine.kasa.exception;

import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获
 *
 * @author pine
 * @create 2017-08-30 上午11:18
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public ServiceResult defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            if (exception instanceof MissingServletRequestParameterException) {
                return ServiceResult.result(ResultCodeEnum.PARAMETER_ERROR.getCode(), exception.getMessage());
            } else {
                logger.error("全局异常捕获 Exception:", exception);
                return ServiceResult.systemError();
            }
        } catch (Exception e) {
            logger.error("Exception", e);
            return ServiceResult.failure(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @ExceptionHandler(value = LoginException.class)
    public ServiceResult loginExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 LoginException:", exception.getMessage());
            return ServiceResult.failure(ResultCodeEnum.NOT_LOGIN);
        } catch (Exception e) {
            logger.error("Exception", e);
            return ServiceResult.systemError();
        }
    }

    @ExceptionHandler(value = ParameterException.class)
    public ServiceResult parameterExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 ParameterException:", exception.getMessage());
            return ServiceResult.result(ResultCodeEnum.PARAMETER_ERROR.getCode(), exception.getMessage());
        } catch (Exception e) {
            logger.error("Exception", e);
            return ServiceResult.systemError();
        }
    }

    @ExceptionHandler(value = BusinessException.class)
    public ServiceResult businessExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try {
            logger.debug("全局异常捕获 BusinessException:", exception.getMessage());
            BusinessException e = (BusinessException) exception;
            return ServiceResult.success(e.getCode());
        } catch (Exception e) {
            logger.error("Exception", e);
            return ServiceResult.systemError();
        }
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ServiceResult httpRequestMethodNotSupportedException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        return ServiceResult.success(ResultCodeEnum.METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ServiceResult notFoundPage404(HttpServletResponse response) {
        return ServiceResult.success(ResultCodeEnum.NOT_FOUUND_PAGE);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ServiceResult httpMediaTypeNotSupportedException(HttpServletResponse response) {
        return ServiceResult.success(ResultCodeEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    @RequestMapping(value = "/403", produces = {"application/json;charset=UTF-8"})
    public ServiceResult forbidden403(HttpServletResponse response) {
        return ServiceResult.success(ResultCodeEnum.PERMISSION_DENIED);
    }
}
