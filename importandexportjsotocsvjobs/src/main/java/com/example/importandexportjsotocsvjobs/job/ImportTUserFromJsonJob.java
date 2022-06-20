package com.example.importandexportjsotocsvjobs.job;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportTUserFromJsonJob {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final JsonItemReader<TUser> sampleJsonReader;

  private final JdbcBatchItemWriter<TUser> sampleJsonWriter;

  public ImportTUserFromJsonJob(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , @Qualifier("sampleJsonReader") JsonItemReader<TUser> sampleJsonReader
          , @Qualifier("sampleJsonWriter") JdbcBatchItemWriter<TUser> sampleJsonWriter) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.sampleJsonReader = sampleJsonReader;
    this.sampleJsonWriter = sampleJsonWriter;
  }

  @Bean
  public Job importTUserFromJonJob() {
    return jobBuilderFactory.get("importTUserFromJonJob")
            .start(importTUserFromJobStep())
            .build();
  }

  @Bean
  public Step importTUserFromJobStep() {
    return stepBuilderFactory.get("importTUserFromJonStep")
            .<TUser, TUser>chunk(1)
            .reader(sampleJsonReader)
            .writer(sampleJsonWriter)
            .build();
  }
}
