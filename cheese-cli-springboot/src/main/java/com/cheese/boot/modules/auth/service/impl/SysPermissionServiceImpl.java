package com.cheese.boot.modules.auth.service.impl;

import com.cheese.boot.core.secure.domain.ISysPermission;
import com.cheese.boot.modules.auth.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 权限查询默认实现
 *
 * @author sobann
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Override
    public List<ISysPermission> listByRoleId(Long roleId) {
        List<ISysPermission> permissions = new ArrayList<>();
        //fixme 管理员具有查询用户的权限
        if (roleId == 50L){
            permissions.add(new ISysPermission() {
                @Override
                public String getUrl() {
                    return "/api/user";
                }

                @Override
                public List<String> getPermissions() {
                    return Collections.singletonList("read");
                }
            });
            return permissions;
        }
        return Collections.emptyList();
    }
}
