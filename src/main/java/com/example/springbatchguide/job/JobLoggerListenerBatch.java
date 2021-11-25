package com.example.springbatchguide.job;

import com.example.springbatchguide.component.JobLoggerListener;
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
public class JobLoggerListenerBatch {

    private static final String JOB_NAME = "jobLoggerListenerBatchJob";
    private static final String STEP_NAME = "jobLoggerListenerBatchStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean(JOB_NAME)
    public Job jobLoggerListenerBatchJob() {
        return this.jobBuilderFactory.get(JOB_NAME)
                .start(jobLoggerListenerBatchStep())
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .build();
    }


    @Bean(STEP_NAME)
    public Step jobLoggerListenerBatchStep() {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("tasklet execute");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
