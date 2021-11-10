package com.example.springbatchguide.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class LateBindingJobParameterBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("lateBindingJobParameterBatchJob")
    public Job job() {
        return this.jobBuilderFactory.get("lateBindingJobParameterBatchJob")
                .start(step(null))
                .build();
    }

    @Bean("lateBindingJobParameterBatchStep")
    @JobScope
    public Step step(@Value("#{jobParameters['name']}") String name) {
        return this.stepBuilderFactory.get("lateBindingJobParameterBatchStep")
                .tasklet(((contribution, chunkContext) -> {
                    System.out.printf("Hello, %s!%n", name);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
