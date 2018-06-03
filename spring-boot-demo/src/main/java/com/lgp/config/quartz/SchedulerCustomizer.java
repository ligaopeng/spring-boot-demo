package com.lgp.config.quartz;

import com.lgp.config.db.quartzdb.QuartzDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-13 19:49
 */
@Configuration
@EnableScheduling
@Import({QuartzAutoConfiguration.class, QuartzDatasource.class})
@Profile("schedulerCustomizer")
public class SchedulerCustomizer implements SchedulerFactoryBeanCustomizer {

    //注入主数据源
    @Autowired
    private DataSource quartzDatasource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待10秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(10);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(quartzDatasource);
        //设置上下文spring bean name
        //schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        //schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
    }
}
