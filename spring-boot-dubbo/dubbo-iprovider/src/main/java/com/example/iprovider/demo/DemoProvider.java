package com.example.iprovider.demo;


import com.example.dto.demo.DemoDTO;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-04 21:16
 */
public interface DemoProvider {

    DemoDTO getDemoDTO(Integer id);

    DemoDTO save(DemoDTO demoDTO);

    List<DemoDTO> listDemoDTO();
}
