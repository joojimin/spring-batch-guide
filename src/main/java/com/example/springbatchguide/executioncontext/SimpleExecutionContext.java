package com.example.springbatchguide.executioncontext;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleExecutionContext {

    private static final String JOB_NAME = "simpleExecutionContextJob";
    private static final String STEP_NAME = "simpleExecutionContextStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean(name = JOB_NAME)
    public Job simpleExecutionContextJob() {
        return this.jobBuilderFactory.get(JOB_NAME)
                .start(simpleExecutionContextStep())
                .build();
    }

    @Bean(name = STEP_NAME)
    public Step simpleExecutionContextStep() {
        return this.stepBuilderFactory.get(STEP_NAME)
                .tasklet(new HelloWorld())
                .build();
    }

    private static class HelloWorld implements Tasklet {

        private static final String HELLO_WORLD = "Hello, %s";

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            String name = (String) chunkContext.getStepContext()
                    .getJobParameters()
                    .get("name");
            ExecutionContext executionContext = chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext();
            executionContext.put("user.name", name);
            System.out.println(String.format(HELLO_WORLD, name));
            return RepeatStatus.FINISHED;
        }
    }
}
