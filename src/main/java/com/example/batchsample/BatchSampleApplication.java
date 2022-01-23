package com.example.batchsample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // chron 스케쥴링 설정
@EnableBatchProcessing  // (필수)이 어노테이션으로 Batch 활성화를 설정한다.
public class BatchSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchSampleApplication.class, args);
    }

}
