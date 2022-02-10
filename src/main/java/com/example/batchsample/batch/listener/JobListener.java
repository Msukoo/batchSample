package com.example.batchsample.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobListener implements JobExecutionListener, StepExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String job = jobExecution.getJobInstance().getJobName();
//        this.jobOperator.stop(jobExecution.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info(job + " ----- beforeJob : " + timestamp.toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String job = jobExecution.getJobInstance().getJobName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info(job + " ----- afterJob : " + timestamp.toString());
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String job = stepExecution.getJobExecution().getJobInstance().getJobName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info(job + " ----- beforeStep : " + timestamp.toString());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String job = stepExecution.getJobExecution().getJobInstance().getJobName();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info(job + " ----- afterStep : " + timestamp.toString());
        return new ExitStatus(stepExecution.getStepName(), "finished");
//        return ExitStatus.COMPLETED;
    }
}

