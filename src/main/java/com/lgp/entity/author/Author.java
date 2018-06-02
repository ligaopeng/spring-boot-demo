package com.lgp.entity.author;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-22 22:51
 */
@Component
@ConfigurationProperties(prefix = "author")//使用注解处理器生成自己的元数据, prefix :前缀， locations={"classpath:com.lgp.config/author.properties"}
public class Author implements Serializable{

    private String name;

    private Integer age;

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
