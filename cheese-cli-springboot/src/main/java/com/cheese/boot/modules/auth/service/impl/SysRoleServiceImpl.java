package com.cheese.boot.modules.auth.service.impl;

import com.cheese.boot.core.secure.domain.ISysRole;
import com.cheese.boot.modules.auth.service.ISysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色查询默认实现
 *
 * @author sobann
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {

    @Override
    public ISysRole selectByName(String name) {
        return new ISysRole() {
            @Override
            public Long getId() {
                return 50L;
            }

            @Override
            public String getName() {
                return "administrator";
            }
        };
    }
}
