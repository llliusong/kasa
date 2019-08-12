package com.pine.kasa.entity.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Data
@Table(name = "cc_resource")
@JsonIgnoreProperties(value = {"handler"})
public class Resources {

    /*id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /*value(对应的url)*/
    @Column(name = "value")
    private String value;

    @Column(name = "parent_id")
    private Integer parentId;

    /*菜单名称*/
    @Column(name = "name")
    private String name;

    /*创建时间*/
    @Column(name = "create_time")
    private Date createTime;

    /*更新时间*/
    @Column(name = "update_time")
    private Date updateTime;

    /*状态(0:删除，1:正常)*/
    @Column(name = "status")
    private Integer status = 1;

    /**
     * 资源类型(0:sort/1:sort)
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * ======
     */
    @Transient
    private Set<Role> roleSet;

    @Transient
    private Set<Resources> childSet;
}
