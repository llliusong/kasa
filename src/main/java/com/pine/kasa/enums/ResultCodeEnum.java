package com.pine.kasa.enums;

/**
 * ResultCodeEnum
 *
 * @author pine
 * @create 2017-07-19 下午2:02
 **/
public enum ResultCodeEnum {
    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(-1, "system busy"),

    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 文件名为空
     */
    FILE_NAME_EMPTY(505, "文件名为空"),

    NOT_FILE(506, "文件类型不支持"),

    FILE_TOO_BIG(507, "附件最大支持8M"),

    /**
     * 权限不足
     */
    PERMISSION_DENIED(403, "您当前没有权限!"),

    /**
     * 未登录,登录超时
     */
    NOT_LOGIN(1001, "not login"),

    /**
     * 请退出钉钉重试
     */
    TRY_AGIN(1002, "登录失效,请退出钉钉重试"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(4001, "服务器繁忙,请稍后重试!"),

    /**
     * 参数错误
     */
    PARAMETER_ERROR(4002, "参数错误"),

    NOT_AVAILABLE_MICRO_APP(4003, "当前企业未接入微应用或已停用!"),

    IMAGE_TYPE_NOT_NULL(4004, "图片类型不能为空!"),

    IMAGE_CONTENT_NOT_NULL(4005, "图片内容不能为空!"),
    FILE_CONTENT_NOT_NULL(41006, "附件内容不能为空!"),

    IMAGE_SIZE_OVER_LIMIT(4006, "上传失败:图片大小不能超过10M!"),

    IMAGE_TYPE_ERROR(4007, "文件类型错误!"),

    FEED_NOT_NULL(4008, "帖子数据不能为空!"),


    FEED_CONTENT_NOT_NULL(4009, "帖子内容不能为空!"),

    FEED_NOT_SEARCH(4010, "没有搜到该帖子，请检查feedId!"),

    FEED_HAVE_LIKE(4011, "您已经点赞了!"),

    FEED_NOT_HAVE_LIKE(4012, "您还未点赞!"),

    COMMENT_NOT_NULL(4014, "该评论已删除"),

    TOPIC_ID_NOT_NULL(4015, "该板块已删除"),

    TOPIC_HAVE_FOLLOWER(4016, "版块已经关注!"),

    TOPIC_NOT_HAVE_FOLLOWER(4017, "版块未关注!"),

    TOPIC_NOT_FOUND(4018, "未查到该版块!"),

    FEED_HAVE_FAVOURITES(4019, "已收藏该贴!"),

    FEED_NOT_HAVE_FAVOURITES(4020, "未收藏该贴!"),

    FEED_NOT_FOUND(4021, "该贴已删除!"),

    FEED_NOT_TOPIC(4022, "无版块不能匿名评论!"),

    FEED_NOT_ANONYMOUS(4023, "该版块不能匿名评论!"),

    COMMENT_NOT_FOUND(4024, "该评论未找到!"),

    REPLY_ID_NOT_NULL(4025, "回复id不能为空!"),

    REPLY_COMMENT_NOT_FOUND(4026, "回复评论未找到!"),

    TOPIC_NUM_LESS_THAN(4027, "版块数量不超过20个!"),

    BEYOND_TOP_NUM(4028, "超过置顶数量!"),

    DATA_TIME_TYPE(4029, "时间类型参数不能为空"),


    DINGTALK_ERROR(5000, "钉钉API错误!"),

    DINGTALK_GETUSERINFO(5001, "通过钉钉服务端API获取用户在当前企业的userId失败!"),

    DINGTALK_GETUSERDETAIL(5002, "通过钉钉API获取用户详情失败!"),

    TOPIC_NOT_ALLOW_CANCEL_CONCERN(5003, "该板块禁止取消关注"),

    CAN_NOT_STRING_EMOJI_NAME(5004, "版块名称不能输入特殊字符"),


    CAN_NOT_STRING_EMOJI_JJ(5004, "版块简介不能输入特殊字符"),

    STORAGE_SPACE_INSUFFICIENT(6001, "您的企业存储空间不足,请联系管理员充值!"),

    STORAGE_SPACE_VIDEO_NULL(6101, "视频内容不能为空!"),

    STORAGE_SPACE_SIZE_OVER_LIMIT(6102, "上传失败:视频大小不能超过50M!"),

    STORAGE_SPACE_UNAUTHORIZED(6103, "抱歉，您的管理员还未在 PC 端的空间管理中开启上传视频的权限哦~请联系云社区管理员开启权限!"),

    DRAF_FULL(6104, "您的草稿箱已满，请先删除草稿，再进行保存");

    private Integer code;

    private String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
