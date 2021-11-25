package com.example.springbatchguide.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloWorldBatchWithValidator {

    private static final String JOB_NAME = "helloWorldBatchWithValidatorJob";
    private static final String STEP_NAME = "helloWorldBatchWithValidatorStep";
    private static final String VALIDATOR_NAME = "helloWorldBatchValidator";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean(name = VALIDATOR_NAME)
    public JobParametersValidator validator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();

        validator.setRequiredKeys(new String[]{"fileName", "version"});
        validator.setOptionalKeys(new String[]{"name"});

        return validator;
    }


    @Bean(name = JOB_NAME)
    public Job helloWorldBatchWithValidatorJob() {
        return this.jobBuilderFactory.get(JOB_NAME)
                .validator(validator())
                .start(helloWorldBatchWithValidatorStep(null, null))
                .build();
    }

    @Bean
    @JobScope
    public Step helloWorldBatchWithValidatorStep(@Value("#{jobParameters['name']}") String name,
                                                 @Value("#{jobParameters['fileName']}") String fileName) {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet((contribution, chunkContext) -> {

                    System.out.println("Hello, " + name);
                    System.out.println("fileName= " + fileName);

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
