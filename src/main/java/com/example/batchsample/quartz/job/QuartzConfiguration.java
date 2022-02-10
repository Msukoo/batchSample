package com.example.batchsample.quartz.job;

import com.example.batchsample.BatchType;
import com.example.batchsample.batch.job.PrintJobConfiguration;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.quartz.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import java.util.Properties;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public abstract class QuartzConfiguration {
    protected final JobLauncher jobLauncher;
    protected final JobLocator jobLocator;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }


    @Bean
    public abstract JobDetail jobDetail();

    @Bean
    public abstract Trigger jobTrigger();

    @Bean
    public abstract SchedulerFactoryBean schedulerFactoryBean() throws IOException;

    @Bean
    public Properties quartzProperties() throws IOException
    {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    protected JobDataMap putJobDataMap(BatchType batchType){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", PrintJobConfiguration.PRINT_JOB);
        jobDataMap.put("batchType", batchType);
        jobDataMap.put("jobLauncher", jobLauncher);
        jobDataMap.put("jobLocator", jobLocator);
        return jobDataMap;
    }




}

