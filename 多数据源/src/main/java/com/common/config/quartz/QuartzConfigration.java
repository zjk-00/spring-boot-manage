package com.common.config.quartz;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** 
* 类说明: 配置Quartz
* 
* @author  zjk
* 2018年6月28日 下午2:57:28 
*/

@Configuration
@EnableScheduling
public class QuartzConfigration {
	
	@Resource
	private DataSource dataSource;
	
	/**
	 * 定义quartz调度工厂
	 * 
	 * 2018年6月29日 上午11:59:08
	 * @return
	 * @throws IOException
	 */
	@Bean(name="scheduler")
    public SchedulerFactoryBean schedulerFactory() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 加载quartz数据源配置
        factory.setDataSource(dataSource);

        factory.setQuartzProperties(quartzProperties());
        
        factory.setSchedulerName("RenrenScheduler");
        //延时启动
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动   默认为true
        factory.setAutoStartup(true);
        
        // 自定义Job Factory，用于Spring注入
        //factory.setJobFactory(myJobFactory);

        return factory;
    }
	
	/**
     * 加载quartz数据源配置
     * 
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        //propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
	
}
