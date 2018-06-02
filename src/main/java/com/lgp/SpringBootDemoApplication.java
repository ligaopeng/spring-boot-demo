package com.lgp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 执行顺序：filter > interceptor > aspect
 * @SpringBootApplication(exclude= {DataSourceAutoConfiguration}) 关闭特定的自动配置
 */
@SpringBootApplication(exclude = {QuartzAutoConfiguration.class, RabbitAutoConfiguration.class})
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		System.setProperty("rocketmq.client.log.loadconfig", "false");//集成rocketmq,log4j2一大坑
        SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	/**
	 * 查看当前的连接池和事务管理器
	 * @param dataSource
	 * @param platformTransactionManager
	 * @return
	 */
	@Bean
	public Object testBean(DataSource dataSource, PlatformTransactionManager platformTransactionManager) {
		System.out.println("current dataSource: " + dataSource.getClass().getName());
		System.out.println("current platformTransactionManager: " + platformTransactionManager.getClass().getName());
		return new Object();
	}


	@Bean
	public DataLoader dataLoader() {
		return new DataLoader();
	}

	@Slf4j
	static class DataLoader implements CommandLineRunner {
		@Override
		public void run(String... strings) {
			log.info("Loading data...");
		}
	}
}
