package com.lgp.config.db.masterslave1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Random;

/**
 * 类说明
 * 自定义路由
 * @author lgp
 * @create 2018-05-09 17:14
 */
public class DataSourceRoute extends AbstractRoutingDataSource {

    protected static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private final int dataSourceNumber;

    public DataSourceRoute(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String type = DataSourceType.master.getType();
        String typeKey = DataSourceContextHolder.getJdbcType();
        logger.info("++++++++ swtich dataSource:" + type +" ++++++++ typeKey : " + typeKey);
        if (type.equals(typeKey)) {
            return DataSourceType.master.getType();
        }else{
            //从数据源随机分配
            Random random = new Random();
            int slaveDsIndex = random.nextInt(dataSourceNumber);
            return slaveDsIndex;
        }
    }
}
