package com.cheese.boot.modules.auth;

import com.cheese.boot.common.tool.ApplicationContextHelper;
import com.cheese.boot.core.secure.props.CheeseUserDetailsProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录相关的前端控制器 单独维护
 *
 * @author sobann
 */
@RestController
public class UserController {

    @RequestMapping("/")
    public String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return name;
    }

    /**
     * 模拟查询所有用户信息
     * 检查当前用户是否具有/admin r权限
     *
     * @return
     */
    @PreAuthorize("@customPermissionEvaluator.hasPermission(authentication,'/api/user','read')")
    @GetMapping("/api/user")
    public List list() {
        CheeseUserDetailsProperties cheeseUserDetailsProperties = ApplicationContextHelper.getApplicationContext().getBean(CheeseUserDetailsProperties.class);
        return cheeseUserDetailsProperties.getUserDetails();
    }
}
