package com.lgp.controller.logger;

import com.lgp.dto.logger.LoggerDTO;
import com.lgp.entity.logger.LoggerEntity;
import com.lgp.mapstruct.LoggerMapper;
import com.lgp.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 16:21
 */
@RestController
@RequestMapping(value = "logger")
public class LoggerController {

    @Autowired
    LoggerService loggerService;

    @Autowired
    LoggerMapper loggerMapper;

    @GetMapping(value = "listLoggerDTO")
    public List<LoggerDTO> listLoggerDTO() {
        List<LoggerEntity> loggerEntities = loggerService.listLogger();
        List<LoggerDTO> loggerDTOS = loggerMapper.fromList(loggerEntities);
        return loggerDTOS;
    }
}
