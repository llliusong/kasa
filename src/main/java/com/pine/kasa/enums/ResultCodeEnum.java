package com.pine.kasa.enums;

/**
 * ResultCodeEnum
 *
 * {@link org.springframework.http.HttpStatus}
 *
 * @author pine
 * @create 2017-07-19 下午2:02
 **/
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 权限不足
     */
    PERMISSION_DENIED(403, "您当前没有权限!"),

    NOT_FOUUND_PAGE(404, "没有找到访问资源"),

    METHOD_NOT_SUPPORTED(405, "method 方法不支持"),

    UNSUPPORTED_MEDIA_TYPE(415, "不支持媒体类型"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "服务器繁忙,请稍后重试"),

    /**
     * 未登录,登录超时
     */
    NOT_LOGIN(1001, "not login"),

    /**
     * 请退出钉钉重试
     */
    TRY_AGIN(1002, "登录失效,请退出钉钉重试"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(4002, "参数错误"),


    DINGTALK_ERROR(5000, "钉钉API错误!");

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
