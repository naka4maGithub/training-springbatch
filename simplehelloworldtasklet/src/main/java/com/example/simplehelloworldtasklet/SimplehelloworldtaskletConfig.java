package com.example.simplehelloworldtasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SimplehelloworldtaskletConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  public SimplehelloworldtaskletConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job simpleHelloWorldTaskletJob() {
    return jobBuilderFactory.get("simpleHelloWorldTaskletJob")
            .incrementer(new RunIdIncrementer())
            .start(simpleHelloWorldTaskletStep())
            .build();
  }

  @Bean
  public Step simpleHelloWorldTaskletStep() {
    return stepBuilderFactory.get("simpleHelloWorldTaskletStep")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("Hello World");
              return RepeatStatus.FINISHED;
            })
            .build();
  }

}
