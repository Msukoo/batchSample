package com.example.batchsample;

import com.example.batchsample.batch.job.PrintJobConfiguration;
import com.example.batchsample.batch.job.EchoMinsuJobConfiguration;

import java.util.Arrays;

public enum BatchType {
    PRINT(PrintJobConfiguration.PRINT_JOB, PrintJobConfiguration.INDEX_NAME, PrintJobConfiguration.PRINT_JOB),
    ECHO_MINSU(EchoMinsuJobConfiguration.ECHO_MINSU_JOB, EchoMinsuJobConfiguration.INDEX_NAME, EchoMinsuJobConfiguration.ECHO_MINSU_JOB),
    SIMPLE_JOB("simpleJob", "simple", "simpleJob");

    private final String type;
    private final String indexName;
    private final String job;


    BatchType(String type, String indexName, String job) {
        this.type = type;
        this.indexName = indexName;
        this.job = job;
    }

    public static BatchType of(String type) {
        return Arrays.stream(values())
                .filter(x -> x.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant com.etoos.latex.batch.domain.BatchType." + type));
    }

    public String getJobName() {
        return this.job;
    }

    public String getIndexName() {
        return this.indexName;
    }
}

