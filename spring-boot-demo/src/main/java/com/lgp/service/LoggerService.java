package com.lgp.service;

import com.lgp.entity.logger.LoggerEntity;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-03 22:49
 */
public interface LoggerService {

    int saveLogger(LoggerEntity loggerEntity);

    List<LoggerEntity> listLogger();
}
