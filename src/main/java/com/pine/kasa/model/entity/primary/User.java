package com.pine.kasa.model.entity.primary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "cc_user")
public class User {
    /**
     * 主键，user_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号(登录账户)
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别(0:未知/1:女/2:男)
     * {@link com.pine.kasa.enums.user.UserSexEnum}
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态(0:已删除/1:正常)
     */
    private Integer status;
}