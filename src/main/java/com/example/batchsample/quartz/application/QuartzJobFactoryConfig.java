package com.example.batchsample.quartz.application;

import com.example.batchsample.BatchType;
import lombok.Builder;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
public class QuartzJobFactoryConfig extends QuartzJobBean {
    private BatchType batchType;
    private JobLauncher jobLauncher;
    private JobLocator jobLocator;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try
        {
            Job job = jobLocator.getJob(batchType.getJobName());
            LocalDateTime indexingTime = LocalDateTime.now();
            LocalDateTime indexingTimeErasedNano = indexingTime.minusNanos(indexingTime.getNano());
            JobParameters params = new JobParametersBuilder()
                    .addString("indexName", batchType.getIndexName())
                    .addString("lastIndexingDate", DATE_TIME_FORMATTER.format(indexingTimeErasedNano))
                    .toJobParameters();

            jobLauncher.run(job, params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

