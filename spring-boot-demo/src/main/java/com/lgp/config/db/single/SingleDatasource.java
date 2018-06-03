package com.lgp.config.db.single;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 14:59
 */
@Configuration
@Profile("singleDatasource")
@MapperScan("com.lgp.dao")
public class SingleDatasource {
}
