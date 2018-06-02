package com.lgp.config.quartz;

import com.lgp.dao.master.quartz.QuartzDao;
import com.lgp.entity.quartz.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 19:22
 */
@Slf4j
@Configuration
@Profile("schedulerListener")
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private QuartzDao quartzDao;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("ScheduleJobMapper ---->:"+ quartzDao.toString());
        try {
            // 这里从数据库中获取任务信息数据
            List<ScheduleJob> jobList = quartzDao.listQuartzConf();
            for (ScheduleJob job : jobList) {
                QuartzManager.addJob(job,schedulerFactoryBean);
            }
        } catch (Exception e) {
            log.error("get job list error : {}", e);
        }
    }

}
