package com.cheese.boot.modules.auth.service;

import com.cheese.boot.core.secure.domain.ISysPermission;

import java.util.List;

/**
 * 权限查询接口
 *
 * @author sobann
 */
public interface ISysPermissionService {

    List<ISysPermission> listByRoleId(Long roleId);
}
