package com.lgp.controller.demo;

import com.lgp.entity.author.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-22 22:01
 */
@RestController
@RequestMapping(value = "demo")
public class DemoController {

    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    @Autowired
    Author author;

    @GetMapping(value = "index")
    public String index() {
        return "hello world ! ! !----" + bookAuthor + "--" + bookName;
    }

    @GetMapping(value = "getAuthor")
    public String getAuthor() {

        return author.getName() + "----" + author.getAge();
    }
}
