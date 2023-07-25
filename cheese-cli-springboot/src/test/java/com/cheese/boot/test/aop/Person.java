package com.cheese.boot.test.aop;

/**
 * 被监听属性的模型
 *
 * @author sobann
 */
@AttributeChangeRecord({"id", "name", "age"})
public class Person {

    private Long id;

    private String name;

    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @AttributeChangeMethod
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @AttributeChangeMethod
    public void setAge(int age) {
        this.age = age;
    }
}
