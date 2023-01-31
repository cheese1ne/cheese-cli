package com.cheese.boot.core.secure.evaluator;


import com.cheese.boot.core.secure.domain.ISysPermission;
import com.cheese.boot.modules.auth.service.ISysPermissionService;
import com.cheese.boot.modules.auth.service.ISysRoleService;
import com.cheese.core.tool.util.Func;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 自定义权限表达式hasPermission
 * 示例：在UserController通过@PreAuthorize("@customPermissionEvaluator.hasPermission(authentication,'/api/user','read')") 完成调用
 *
 * @author soban
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final ISysPermissionService permissionService;
    private final ISysRoleService roleService;

    public CustomPermissionEvaluator(ISysPermissionService permissionService, ISysRoleService roleService){
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        //获取loadUserByUsername的结果
        User user = (User) authentication.getPrincipal();
        //获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Long roleId = roleService.selectByName(roleName).getId();
            //得到角色所有的权限
            List<ISysPermission> sysPermissions = permissionService.listByRoleId(roleId);
            //如果有权限，放行
            for (ISysPermission permission : sysPermissions) {
                List<String> permissions = permission.getPermissions();
                if (Objects.equals(targetUrl, permission.getUrl()) && permissions.contains(Func.toStr(targetPermission))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
