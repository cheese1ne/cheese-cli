package com.cheese.boot.modules.auth.service;

import com.cheese.boot.core.secure.domain.ISysRole;

/**
 * 角色查询接口
 *
 * @author sobann
 */
public interface ISysRoleService {

    ISysRole selectByName(String name);
}
