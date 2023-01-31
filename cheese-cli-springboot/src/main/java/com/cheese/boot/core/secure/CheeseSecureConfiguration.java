package com.cheese.boot.core.secure;


import com.cheese.boot.core.secure.filter.JWTAuthenticationTokenFilter;
import com.cheese.boot.core.secure.props.CheeseSecureProperties;
import com.cheese.boot.core.secure.props.CheeseUserDetailsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 安全配置类，可以使用META-INF/spring.factories进行装配
 * 安全配置默认开启，可以通过配置secure.enabled = false关闭
 *
 * @author xuling
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(value = "secure.enabled", matchIfMissing = true)
@PropertySource(value = {"classpath:application.yml", "classpath:cheese-user.yml"})
@EnableConfigurationProperties({CheeseSecureProperties.class, CheeseUserDetailsProperties.class})
public class CheeseSecureConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final CheeseSecureProperties secureProperties;

    private final CheeseUserDetailsProperties cheeseUserDetailsProperties;

    private final UserDetailsService userDetailsService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    public CheeseSecureConfiguration(CheeseSecureProperties secureProperties,
                                     CheeseUserDetailsProperties cheeseUserDetailsProperties,
                                     UserDetailsService userDetailsService,
                                     AuthenticationEntryPoint authenticationEntryPoint,
                                     AccessDeniedHandler accessDeniedHandler,
                                     AuthenticationSuccessHandler authenticationSuccessHandler,
                                     AuthenticationFailureHandler authenticationFailureHandler,
                                     LogoutSuccessHandler logoutSuccessHandler) {
        this.secureProperties = secureProperties;
        this.cheeseUserDetailsProperties = cheeseUserDetailsProperties;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    /**
     * 静态资源忽略
     * /css/**
     * /js/**
     * /index.html
     * /img/**
     * /fonts/**
     * /favicon.ico
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> skipOther = secureProperties.getSkipOther();
        web.ignoring().antMatchers(StringUtils.toStringArray(skipOther));
    }

    /**
     * http访问的策略
     * 1.允许客户端在访问某些特定资源时不进行拦截
     * 2.跨域处理
     * 3.取消跨站请求伪造防护
     * 4.自定义异常处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //还有一种配置方式则是放行登录url 并自己通过web接口的方式定义，以代码逻辑的方式响应accessToken以及refreshToken等内容，这种方法屏蔽掉配置登录地址以及登录成功失败的处理器代码即可

        List<String> skipUrl = secureProperties.getSkipUrl();
        http.authorizeRequests()
                //不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(StringUtils.toStringArray(skipUrl)).permitAll()
                //其他的需要登陆后才能访问
                .anyRequest().authenticated().and()
                //异常自定义处理 authenticationEntryPoint解决匿名用户访问无权限资源时的异常 accessDeniedHandler解决认证过的用户访问无权限资源时的异常
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler).and()
                //防止iframe 造成跨域
                .headers().frameOptions().disable().and()
                //基于token验证不需要session 关闭session功能
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //session invalidSessionUrl:过期访问的url  maximumSessions:一个账号只允许拥有一个session对象 maxSessionsPreventsLogin 达到最大值时是否保留已经登录的对象(false为踢出，true会无法登录)
//        http.sessionManagement().invalidSessionUrl("/session/invalid")
//                .maximumSessions(1).maxSessionsPreventsLogin(false).expiredSessionStrategy(new ExpiredSessionStrategy())
//                .sessionRegistry(new SessionRegistryImpl());

        //配置登录地址
        http.formLogin().loginProcessingUrl("/api/login").successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler);
        //配置登出地址
        http.logout().logoutUrl("/api/logout").logoutSuccessHandler(logoutSuccessHandler);
        //开启跨域；取消跨站请求伪造防护
        http.cors().and().csrf().disable();
        //基于jwt token验证用户身份，过滤器设置在用户密码验证过滤器之前
        http.addFilterBefore(new JWTAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 跨域参数配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置的参数依次是：允许跨域的路径、允许跨域的源、允许的请求方式、预检间隔、允许头部设置、允许发送cookie
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(168000)
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    /**
     * 用户认证方式和用户密码加密方式
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ApplicationContext applicationContext = getApplicationContext();
        BCryptPasswordEncoder bCryptPasswordEncoder = applicationContext.getBean(BCryptPasswordEncoder.class);
        //用户密码过滤器认证方式userDetailsService需要自行定义
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        super.configure(auth);
    }

    /**
     * bcrypt 加密
     *
     * @return
     */
    @Bean("bCryptPasswordEncoder")
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}