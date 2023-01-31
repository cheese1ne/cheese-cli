package com.cheese.boot.core.secure.filter;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证的filter 参照UsernamePasswordAuthenticationFilter
 *
 * 未使用
 *
 * @author sobann
 */
public class SimpleUsernameAuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    public SimpleUsernameAuthenticationLoginFilter() {
        super(new AntPathRequestMatcher("/api/login", HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //创建一个游客身份
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        //进行验证
        return getAuthenticationManager().authenticate(authRequest);
    }
}
