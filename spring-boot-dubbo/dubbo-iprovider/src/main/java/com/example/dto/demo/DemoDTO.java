package com.example.dto.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-04 21:12
 */
@Getter
@Setter
public class DemoDTO implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    @JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss", timezone = "GMT+8")
    private Date birthdayDate;

    private BigDecimal amount;

    @NotNull(message = "sum不能为空")
    private Long sum;

    @Valid
    private List<ChildDemoDTO> childDemoDTOList;

}
