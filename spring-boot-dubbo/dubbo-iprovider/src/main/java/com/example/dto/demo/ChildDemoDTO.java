package com.example.dto.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-06 15:29
 */
@Getter
@Setter
public class ChildDemoDTO implements Serializable {

    private Integer id;

    @NotNull(message = "name不能为空")
    private String name;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
    private Date birthdayDate;

    private Integer age;

}
