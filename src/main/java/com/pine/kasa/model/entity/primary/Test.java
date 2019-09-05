package com.pine.kasa.model.entity.primary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pine on  2019-07-02 15:44.
 */
@Table(name = "cc_audit_record")
@Data
public class Test {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公司id
     */
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 帖子id
     */
    @Column(name = "feed_id")
    private Integer feedId;

    /**
     * 评论id(如果为评论，此id不可为空)
     */
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "data_type")
    private Integer dataType;

    /**
     * 审核人id
     */
    @Column(name = "auditor_id")
    private Integer auditorId;

    @Column(name = "audit_action")
    private Integer auditAction;

    /**
     * 备注
     */
    private String remark;

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
}
