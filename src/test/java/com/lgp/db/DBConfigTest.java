package com.lgp.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.lgp.SpringBootDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-09 11:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class DBConfigTest {

    @Resource
    private DruidDataSource masterDataSource;

    @Resource
    private DruidDataSource clusterDataSource;

    @Test
    public void testDataSourceOne() throws SQLException {
        System.out.println("master" + masterDataSource.getUrl());
        System.out.println("cluster" + clusterDataSource.getUrl());
    }

}
