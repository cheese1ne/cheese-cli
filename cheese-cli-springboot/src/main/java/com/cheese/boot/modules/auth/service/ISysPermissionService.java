package com.cheese.boot.modules.auth.service;

import com.cheese.boot.core.secure.domain.ISysPermission;

import java.util.List;

/**
 * 权限查询接口
 *
 * @author sobann
 */
public interface ISysPermissionService {

    /**
     * 根据角色id获取系统权限
     *
     * @param roleId
     * @return
     */
    List<ISysPermission> listByRoleId(Long roleId);
}
