package com.cheese.boot.modules.auth.service;

import com.cheese.boot.core.secure.domain.ISysRole;

/**
 * 角色查询接口
 *
 * @author sobann
 */
public interface ISysRoleService {

    /**
     * 根据角色名获取系统角色
     *
     * @param name
     * @return
     */
    ISysRole selectByName(String name);
}
