package com.cheese.boot.core.secure.handler;

import com.cheese.boot.common.constant.NormalCharConstant;
import com.cheese.boot.core.secure.props.CheeseUserDetailsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 用户认证的具体实现需要手动定义
 * 此实现需要完成 密码匹配、加载权限、角色集合
 *
 * @author sobann
 */
@Slf4j
@Component
public class UserDetailsServiceHandler implements UserDetailsService {

    private final CheeseUserDetailsProperties cheeseUserDetailsProperties;

    public UserDetailsServiceHandler(ObjectProvider<CheeseUserDetailsProperties> cheeseUserDetailsPropertiesProvider) {
        this.cheeseUserDetailsProperties = Optional.ofNullable(cheeseUserDetailsPropertiesProvider.getIfAvailable()).orElse(new CheeseUserDetailsProperties());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "username不能为空");
        List<String> userDetails = cheeseUserDetailsProperties.getUserDetails();
        for (String userDetail : userDetails) {
            String[] split = userDetail.split(NormalCharConstant.COMMA);
            if (split[0].equals(username)) {
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
                //密码在储存时候实际上就应该是秘文，经过前端rsa加密后再经过后端BCrypt加密入库
                return new User(username, split[1], authorities);
            }
        }
        return null;
    }
}
