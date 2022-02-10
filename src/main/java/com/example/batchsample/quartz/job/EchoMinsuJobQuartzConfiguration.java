package com.example.batchsample.quartz.job;

import com.example.batchsample.BatchType;
import com.example.batchsample.batch.job.EchoMinsuJobConfiguration;
import com.example.batchsample.quartz.application.QuartzJobFactoryConfig;
import org.quartz.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
public class EchoMinsuJobQuartzConfiguration extends QuartzConfiguration{

    private final static String ECHO_MINSU_JOB = EchoMinsuJobConfiguration.ECHO_MINSU_JOB;
    private final static String ECHO_MINSU_JOB_DETAIL = ECHO_MINSU_JOB + "Detail";
    private final static String ECHO_MINSU_JOB_TRIGGER = ECHO_MINSU_JOB + "Trigger";
    private final static String ECHO_MINSU_JOB_SCHEDULE = ECHO_MINSU_JOB + "Schedule";

    public EchoMinsuJobQuartzConfiguration(JobLauncher jobLauncher, JobLocator jobLocator) {
        super(jobLauncher, jobLocator);
    }

    @Bean(ECHO_MINSU_JOB_DETAIL)
    @Override
    public JobDetail jobDetail() {
        BatchType batchType = BatchType.of(ECHO_MINSU_JOB);

        return JobBuilder.newJob(QuartzJobFactoryConfig.class)
                .withIdentity(ECHO_MINSU_JOB)
                .setJobData(putJobDataMap(batchType))
                .storeDurably()
                .build();
    }

    @Bean(ECHO_MINSU_JOB_TRIGGER)
    @Override
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(20)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail())
                .withIdentity("echoMinsuJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean(ECHO_MINSU_JOB_SCHEDULE)
    @Override
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(jobTrigger());
        scheduler.setQuartzProperties(quartzProperties());
        scheduler.setJobDetails(jobDetail());
        return scheduler;
    }
}

