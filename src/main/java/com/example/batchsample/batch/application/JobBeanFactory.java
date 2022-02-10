package com.example.batchsample.batch.application;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobBeanFactory {
    private final ApplicationContext context;

    public Job getJob(String jobName) {
        return context.getBean(jobName, Job.class);
    }
}

