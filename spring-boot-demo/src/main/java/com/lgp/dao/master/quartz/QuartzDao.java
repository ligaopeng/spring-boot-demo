package com.lgp.dao.master.quartz;

import com.lgp.entity.quartz.ScheduleJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-13 18:28
 */
@Repository
public interface QuartzDao {

    List<ScheduleJob> listQuartzConf();

    int insertScheduleJob(ScheduleJob scheduleJob);

    int updateScheduleJob(ScheduleJob scheduleJob);

}
