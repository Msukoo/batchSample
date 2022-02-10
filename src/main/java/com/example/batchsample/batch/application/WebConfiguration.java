package com.example.batchsample.batch.application;

import com.example.batchsample.BatchType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, BatchType>() {
            @Override
            public BatchType convert(String source) {
                return BatchType.of(source);
            }
        });
    }
}


