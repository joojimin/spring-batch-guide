package com.example.springbatchguide.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ChunkContextJobParameterBatch {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job chunkContextJobParameterJob() {
        return this.jobBuilderFactory.get("chunkContextJobParamterJob")
                .start(chunkContextJobParameterStep())
                .build();
    }

    @Bean
    public Step chunkContextJobParameterStep() {
        return this.stepBuilderFactory.get("chunkContextJobParameterStep")
                .tasklet(((contribution, chunkContext) -> {
                    String name = (String) chunkContext.getStepContext().getJobParameters().get("name");

                    System.out.printf("Hello, %s!%n", name);
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
