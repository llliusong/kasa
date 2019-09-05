package com.pine.kasa.model.entity.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Table(name = "cc_role_resource")
@JsonIgnoreProperties(value = {"handler"})
@Data
public class RoleResource {

    /*id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /*role_id(角色id)*/
    @Column(name = "role_id")
    private Integer roleId;

    /*esource_id(资源id)*/
    @Column(name = "resource_id")
    private Integer resourceId;

    /*创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /*更新时间*/
    @Column(name = "update_time")
    private Date updateTime;

    /*状态(0:删除，1:正常)*/
    @Column(name = "status")
    private Integer status = 1;
}
