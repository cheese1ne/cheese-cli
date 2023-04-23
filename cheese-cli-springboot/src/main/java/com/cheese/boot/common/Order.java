package com.cheese.boot.common;

/**
 * cheese-cli 顶层排序接口 用于OOP的顺序逻辑管理
 * 函数式接口
 *
 * @author sobann
 */
@FunctionalInterface
public interface Order {

    /**
     * 顺序
     *
     * @return 顺序号
     */
    int order();
}
