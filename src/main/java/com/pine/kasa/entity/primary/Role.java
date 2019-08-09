package com.pine.kasa.entity.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
@Table(name = "cc_role")
@JsonIgnoreProperties(value={"handler"})
@Data
public class Role {

	/*id*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/*角色名*/
	@Column(name = "name")
	private String name;

	/*角色值*/
	@Column(name = "value")
	private Integer value;

	/*角色描述*/
	@Column(name = "remark")
	private String remark;

	/*角色类型*/
	@Column(name = "type")
	private Integer type;

	/*创建时间*/
	@Column(name = "create_time")
	private Date createTime;

	/*更新时间*/
	@Column(name = "update_time")
	private Date updateTime = new Date();

	/*状态(0:删除，1:正常)*/
	@Column(name = "status")
	private Integer status = 1;
}
