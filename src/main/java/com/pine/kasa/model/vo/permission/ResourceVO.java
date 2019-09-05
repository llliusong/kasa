package com.pine.kasa.model.vo.permission;

import lombok.Data;

/**
 * @Author: pine
 * @CreateDate: 2018-09-11 20:23
 */
@Data
public class ResourceVO {

    private Integer id;

    private String value;

    private Integer parentId;

    private String name;
}
