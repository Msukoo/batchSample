package com.example.batchsample.quartz.job;

import com.example.batchsample.BatchType;
import com.example.batchsample.batch.job.PrintJobConfiguration;
import com.example.batchsample.quartz.application.QuartzJobFactoryConfig;
import org.quartz.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
public class PrintJobQuartzConfiguration extends QuartzConfiguration {

    private final static String PRINT_JOB = PrintJobConfiguration.PRINT_JOB;
    private final static String PRINT_JOB_DETAIL = PRINT_JOB + "Detail";
    private final static String PRINT_JOB_TRIGGER = PRINT_JOB + "Trigger";
    private final static String PRINT_JOB_SCHEDULE = PRINT_JOB + "Schedule";


    public PrintJobQuartzConfiguration(JobLauncher jobLauncher, JobLocator jobLocator) {
        super(jobLauncher, jobLocator);
    }

    @Bean(PRINT_JOB_DETAIL)
    @Override
    public JobDetail jobDetail() {
        BatchType batchType = BatchType.of(PRINT_JOB);

        return JobBuilder.newJob(QuartzJobFactoryConfig.class)
                .withIdentity(PRINT_JOB)
                .setJobData(putJobDataMap(batchType))
                .storeDurably()
                .build();
    }

    @Bean(PRINT_JOB_TRIGGER)
    @Override
    public Trigger jobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(50)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail())
                .withIdentity("printJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean(PRINT_JOB_SCHEDULE)
    @Override
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(jobTrigger());
        scheduler.setQuartzProperties(quartzProperties());
        scheduler.setJobDetails(jobDetail());
        return scheduler;
    }
}
