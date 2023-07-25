package com.cheese.boot.test.extend;

/**
 * 此服务只能被角色id包含14、20、22的用户调用
 * 针对@CompositeService注解可以引入解释器来解析语法达到权限控制的目的
 *
 * @author sobann
 */
@CompositeService(regex = "^role:(14|20|22)")
public class HelloService {

    public String hello() {
        return "hello";
    }
}
