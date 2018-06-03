package com.lgp.config.quartz;

import com.lgp.entity.quartz.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-13 18:22
 */
@Slf4j
public class QuartzManager {

    public static void addJob(ScheduleJob job, SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!ScheduleJob.JOB_STATUS.equals(job.getJobStatus())) {
            log.error("任务名称 = [" + job.getJobName() + "]---------------状态无效，不启动");
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Class jobClass = Class.forName(job.getBeanClass());
        Object jobObject = jobClass.newInstance();
        if (jobObject == null) {
            log.error("任务名称 = [" + job.getJobName() + "]---------------启动失败");
            return;
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
