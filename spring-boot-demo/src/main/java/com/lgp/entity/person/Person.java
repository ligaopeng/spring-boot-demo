package com.lgp.entity.person;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-25 20:49
 */
public class Person implements Serializable {

    private String name;

    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
