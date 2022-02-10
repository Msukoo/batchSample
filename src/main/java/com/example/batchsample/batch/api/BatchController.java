package com.example.batchsample.batch.api;

import com.example.batchsample.batch.application.BatchRunner;
import com.example.batchsample.BatchType;
import com.example.batchsample.batch.job.PrintJobConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class BatchController {
    private final BatchRunner batchRunner;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/sample/simpleJob")
    public ResponseEntity<String> dynamicIndexing() throws Exception {
        BatchType batchType = BatchType.of(PrintJobConfiguration.PRINT_JOB);
        LocalDateTime indexingTime = LocalDateTime.now();
        LocalDateTime indexingTimeErasedNano = indexingTime.minusNanos(indexingTime.getNano());
        batchRunner.dynamicIndexing(batchType, DATE_TIME_FORMATTER.format(indexingTimeErasedNano));
        return ResponseEntity.ok("Batch Started");
    }
}

