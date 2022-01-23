package com.example.batchsample;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Scheduler { //스케쥴러를 이용해 주기적인 작업 실행가능
    private final JobLauncher jobLauncher;
    private final PrintJobConfiguration printJobConfiguration;

    @Scheduled(initialDelay = 5000, fixedRate=1000 * 20)// 5초 지연 후 5초 간격으로 작업
    public void PrintJobSchedule() {
        JobExecution execution;
        try {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("start printJob");
            execution = jobLauncher.run(printJobConfiguration.printJob(), simpleJobParam());
            System.out.println("end printJob with status : " + execution.getStatus());
            System.out.println("Current Thread: " + Thread.currentThread().getName());
            System.out.println("--------------------------------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //같은 이름의 batch는 생길 수 없기 때문에 param에 시간을 넣는다.
    private JobParameters simpleJobParam() {
        return new JobParametersBuilder()
                .addString("time", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
    }
}
