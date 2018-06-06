package com.example.consume.demo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dto.demo.DemoDTO;
import com.example.iprovider.demo.DemoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-04 21:47
 */
@Slf4j
@RestController
public class DemoController {

    @Reference(version = "${demo.service.version}", application = "${dubbo.application.id}", registry = "${dubbo.registry.id}")
    DemoProvider demoProvider;

    @GetMapping(value = "getDemoDTOById/{id}")
    public DemoDTO getDemoDTO(@PathVariable Integer id) {
        DemoDTO demoDTO = demoProvider.getDemoDTO(id);
        return demoDTO;
    }


    @GetMapping(value = "listDemoDTO")
    public List<DemoDTO> listDemoDTO() {
        List<DemoDTO> demoDTOList = demoProvider.listDemoDTO();
        return demoDTOList;
    }

    @PostMapping(value = "saveDemoDTO")
    public DemoDTO saveDemoDTO(@RequestBody DemoDTO demoDTO) {
        DemoDTO newDemoDTO = null;
        try {
            newDemoDTO = demoProvider.save(demoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDemoDTO;
    }

}
