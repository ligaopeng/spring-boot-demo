package com.lgp.service.impl;

import com.lgp.dao.master.logger.LoggerDao;
import com.lgp.entity.logger.LoggerEntity;
import com.lgp.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-03 22:51
 */
@Service
public class LoggerServiceImpl implements LoggerService {


    @Autowired
    LoggerDao loggerDao;


    @Override
    @Transactional
    public int saveLogger(LoggerEntity loggerEntity) {
        int i = loggerDao.saveLogger(loggerEntity);
        return i;
    }

    @Override
    public List<LoggerEntity> listLogger() {
        List<LoggerEntity> loggerEntities = loggerDao.listLogger();
        return loggerEntities;
    }
}
