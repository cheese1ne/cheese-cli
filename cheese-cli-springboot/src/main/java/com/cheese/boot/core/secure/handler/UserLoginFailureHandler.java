package com.cheese.boot.core.secure.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 *
 * @author sobann
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public UserLoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        log.error("login failed, the reason is :{}", exception.getMessage());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (exception instanceof UsernameNotFoundException) {
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString("用户名不存在!"));
        } else if (exception instanceof LockedException) {
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString("用户被冻结!"));
        } else if (exception instanceof BadCredentialsException) {
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString("用户名或密码不正确!"));
        } else {
            //未知的其他错误
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString("登录失败!"));
        }

        //TODO:登录失败需要进行的业务：如短信提醒账号持有者等
    }
}
