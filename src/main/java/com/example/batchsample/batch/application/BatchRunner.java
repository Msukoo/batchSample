package com.example.batchsample.batch.application;

import com.example.batchsample.BatchType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchRunner {
    private final JobBeanFactory jobBeanFactory;

    private final JobLauncher jobLauncher;

    public void dynamicIndexing(BatchType batchType, String updateDateTime) throws Exception {
        Job job = jobBeanFactory.getJob(batchType.getJobName());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("indexName", batchType.getIndexName())
                .addString("updateDateTime", updateDateTime)
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}

