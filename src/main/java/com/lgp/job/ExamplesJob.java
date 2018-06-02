package com.lgp.job;

import com.lgp.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-13 18:59
 */
@Slf4j
public class ExamplesJob implements Job {


    @Autowired
    private AsyncService asyncService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            asyncService.doTaskOne();
        } catch (Exception  e) {
            log.error("execute service error", e);
        }
        System.out.println("examplesJob:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}
