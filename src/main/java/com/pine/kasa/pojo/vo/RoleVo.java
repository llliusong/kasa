package com.pine.kasa.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created  in com.xfw.community.vo.
 * Description:
 *
 * @author: yupan
 * @date: 2019-04-04 16:37
 */
@Data
public class RoleVo implements Serializable {

    /**
     * 用户角色中间表id
     */
    private Integer userRoleId;

    /**
     * 板块管理员中间表id
     */
    private Integer userTopicRefId;

    /**
     * 人员名称
     */
    private String userName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 板块名称
     */
    private String topicName;

    /**
     * 板块id
     */
    private Integer topicId;

    /**
     * 授权人
     */
    private String authorizer;

    /**
     * 授权时间
     */
    private Date updateTime;

    private Integer userId;
}
