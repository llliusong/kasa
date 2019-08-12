package com.pine.kasa.service;

import com.pine.kasa.common.ServiceResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShiroService {
    Map<String, String> loadFilterChainDefinitions();

    Set<String> findPermissionUrlByUserId(Integer userId);

    Set<String> findPermissionUrlByRoleId(List<Integer> roleId);

    void updatePermission(Integer roleId, String companyId);

    ServiceResult getUserResource(String userName);
}
