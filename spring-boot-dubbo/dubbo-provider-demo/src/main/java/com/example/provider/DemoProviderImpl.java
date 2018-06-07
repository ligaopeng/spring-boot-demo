package com.example.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dto.demo.DemoDTO;
import com.example.iprovider.demo.DemoProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-06-04 21:23
 */
@Service(version = "${demo.provider.version}", application = "${dubbo.application.id}", protocol = "${dubbo.protocol.id}", registry = "${dubbo.registry.id}")
public class DemoProviderImpl implements DemoProvider {


    @Override
    public DemoDTO getDemoDTO(Integer id) {
        DemoDTO demoDTO = fromDemo(id);
        return demoDTO;
    }

    @Override
    public DemoDTO save(DemoDTO demoDTO) {
        demoDTO.setName(demoDTO.getName() + "，我来了！！！");
        return demoDTO;
    }

    @Override
    public List<DemoDTO> listDemoDTO() {
        List<DemoDTO> demoDTOList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DemoDTO demoDTO = fromDemo(i);
            demoDTOList.add(demoDTO);
        }
        return demoDTOList;
    }

    private DemoDTO fromDemo(Integer id) {
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setAge(25);
        demoDTO.setAmount(new BigDecimal(5000000));
        demoDTO.setBirthdayDate(new Date());
        demoDTO.setSum(59999999999999999L);
        demoDTO.setName("李高朋" + id);
        demoDTO.setId(id);
        return demoDTO;
    }
}
