package com.cheese.boot.test.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生命周期方法
 * 1.构造方法调用 construct
 * 2.构造方法调用 User, 注入属性(如果是通过传参的构造方法，则1与2调换位置)
 * 3.上下文感知 ApplicationContextAware.setApplicationContext
 * 4.Bean处理器初始化调用使@PostConstruct生效 InitDestroyAnnotationBeanPostProcessor
 * 5.InitializingBean.afterPropertiesSet生效
 * 6.@Bean#initMethod()生效
 * 7.Bean处理器被系统钩子触发，调用使@PreDestroy生效
 * 8.DisposableBean.destroy()生效
 * 9.@Bean#destroyMethod()生效
 *
 * @author sobann
 */
public class LifeCycle implements InitializingBean, ApplicationContextAware, DisposableBean {

    @Autowired
    private User user;

    /**
     * 使用构造方法注入属性的方式，构造方法的调用顺序为User->LifeCycle
     */
    public LifeCycle(/*User user*/) {
        System.out.println("LifeCycle对象被创建了");
    }

    /**
     * 实现的 Aware 回调接口
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Aware接口起作用，setApplicationContext被调用了，此时user=" + user);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct注解起作用，postConstruct方法被调用了");
    }

    /**
     * 实现 InitializingBean 接口
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean接口起作用，afterPropertiesSet方法被调用了");
    }

    /**
     * 通过 {@link Bean#initMethod()}来指定
     *
     * @throws Exception
     */
    public void initMethod() throws Exception {
        System.out.println("@Bean#initMethod()起作用，initMethod方法被调用了");
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("@PreDestroy注解起作用，preDestroy方法被调用了");
    }

    /**
     * 通过 {@link Bean#destroyMethod()}来指定
     *
     * @throws Exception
     */
    public void destroyMethod() throws Exception {
        System.out.println("@Bean#destroyMethod()起作用，destroyMethod方法被调用了");
    }

    /**
     * 实现 DisposableBean 注解
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean接口起作用，destroy方法被调用了");
    }
}
