package com.cheese.boot.core.secure.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session过期的策略
 *
 * @author sobann
 */
public class ExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private final ObjectMapper objectMapper;

    public ExpiredSessionStrategy(){
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(objectMapper.writeValueAsString("您的账号已在其他ip地址登录!"));
    }
}
