package com.example.springbatchguide.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobParametersIncrementerBatch {

    private static final String JOB_NAME = "jobParametersIncrementerBatchJob";
    private static final String STEP_NAME = "jobParametersIncrementerBatchStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean(JOB_NAME)
    public Job jobParametersIncrementerBatchJob() {
        return this.jobBuilderFactory.get(JOB_NAME)
                .start(jobParametersIncrementerBatchStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }


    @Bean(STEP_NAME)
    public Step jobParametersIncrementerBatchStep() {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet(((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
