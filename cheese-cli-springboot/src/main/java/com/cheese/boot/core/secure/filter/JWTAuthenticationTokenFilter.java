package com.cheese.boot.core.secure.filter;

import com.cheese.boot.core.secure.tool.SecureUtil;
import com.cheese.core.tool.util.Func;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt token过滤器 当前过滤器在UsernamePasswordAuthenticationFilter前
 *
 * @author sobann
 */
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = SecureUtil.obtainToken(servletRequest);
        if (Func.isNotBlank(token)) {
            //从token中获取身份信息 保存到上下文
            Authentication authentication = SecureUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
