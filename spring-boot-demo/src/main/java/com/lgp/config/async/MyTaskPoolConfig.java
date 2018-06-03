package com.lgp.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-12 18:26
 */
@Configuration
@EnableAsync//开启异步支持
public class MyTaskPoolConfig {

    /**
     * AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
     * CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
     * DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
     * DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
     *
     * @return
     */

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);//核心线程数
        taskExecutor.setMaxPoolSize(20);//最大线程数
        taskExecutor.setQueueCapacity(200);//队列大小
        taskExecutor.setKeepAliveSeconds(60);//线程最大空闲时间s
        taskExecutor.setThreadNamePrefix("taskExecutor-");//线程池名的前缀
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//不能执行的任务将被删除
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);//设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        taskExecutor.setAwaitTerminationSeconds(60);//设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        return taskExecutor;
    }


}
