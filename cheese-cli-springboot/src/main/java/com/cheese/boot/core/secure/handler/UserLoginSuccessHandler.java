package com.cheese.boot.core.secure.handler;

import com.cheese.boot.core.secure.tool.ResponseUtil;
import com.cheese.boot.core.secure.tool.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后处理器
 *
 * @author sobann
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    public UserLoginSuccessHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.info("login success, the user authentication is :{}", authentication);
        /*
            如果是前后端未分离的项目，可以在此处设置跳转路径 前后端分离项目前端通过路由跳转，举个栗子 vue中通过 $route.push(redirect) 完成跳转
            String redirect = httpServletRequest.getParameter("redirect");
            redirect = Func.isEmpty(redirect) ? "/" : redirect;
            httpServletResponse.sendRedirect(redirect);
        */
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.ok(SecureUtil.createToken(authentication))));
    }
}
