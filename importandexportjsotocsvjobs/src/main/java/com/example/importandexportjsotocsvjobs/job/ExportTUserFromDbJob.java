package com.example.importandexportjsotocsvjobs.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties
public class ExportTUserFromDbJob {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  public ExportTUserFromDbJob(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job exportTUserFromDbJob() {
    return jobBuilderFactory.get("exportTUserFromDbJob")
            .incrementer(new RunIdIncrementer())
            .start(exportTUserFromDbStep())
            .build();
  }

  @Bean
  public Step exportTUserFromDbStep() {
    return stepBuilderFactory.get("exportTUserFromDbStep")
            .tasklet(null)
            .build();
  }


}
