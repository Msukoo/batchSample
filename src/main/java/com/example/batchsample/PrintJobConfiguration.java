package com.example.batchsample;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration // spring batch의 모든 job은 이 어노테이션으로 등록해서 사용
public class PrintJobConfiguration {
    private static final String PRINT_JOB = "printJob";
    private static final String PRINT_JOB_STEP = PRINT_JOB + "Step";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job printJob() throws Exception {
        return jobBuilderFactory.get(PRINT_JOB) //job 이름으로 bean 불러오기
                .start(printJobStep()) //step실행
                .build();
    }

    @Bean
    public Step printJobStep() {
        return stepBuilderFactory.get(PRINT_JOB_STEP)
                //contribution - 현재 단계 실행을 업데이트하기 위해 다시 전달되는 변경 가능한 상태
                // chunkContext - 호출 간에는 공유되지만 재시작 간에는 공유되지 않는 속성
                .tasklet((contribution, chunkContext) -> {
                    startStepBatch();
                    endStepBatch(); // reader, writer와 동일 depth
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private void startStepBatch() {
        System.out.println("batchSample - startBatch!!!");
    }

    private void endStepBatch() {
        System.out.println("batchSample - endBatch!!!");
    }

}
