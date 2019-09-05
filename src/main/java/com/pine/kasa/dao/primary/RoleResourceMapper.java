package com.pine.kasa.dao.primary;

import com.pine.kasa.model.entity.primary.RoleResource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by pine on 2018-03-22 18:04:48
 */
public interface RoleResourceMapper extends Mapper<RoleResource> {

    void insertBatch(@Param("param") List<RoleResource> param);
}