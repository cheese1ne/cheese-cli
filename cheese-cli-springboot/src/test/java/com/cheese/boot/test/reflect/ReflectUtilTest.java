package com.cheese.boot.test.reflect;

import org.springframework.util.ReflectionUtils;

/**
 * @author sobann
 */
public class ReflectUtilTest {

    public static void main(String[] args) {
        Person person = new Person("sobann", 18);
        ReflectionUtils.doWithFields(Person.class, field -> {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(person);
            System.out.println("name = " + name + ", value = " + value);
        });
    }

    public static class Person {
        private String name;
        private int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
