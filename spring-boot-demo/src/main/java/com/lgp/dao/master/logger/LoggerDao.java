package com.lgp.dao.master.logger;

import com.lgp.entity.logger.LoggerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-03 22:37
 */
@Repository
public interface LoggerDao {

    int saveLogger(LoggerEntity loggerEntity);

    List<LoggerEntity> listLogger();

}
