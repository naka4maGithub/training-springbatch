package com.example.importandexportjsotocsvjobs.job;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportTUserFromDbJobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final JdbcPagingItemReader<TUser> tUserJdbcPagingItemReader;

  private final JsonFileItemWriter<TUser> tUserJsonFileItemWriter;

  public ExportTUserFromDbJobConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , @Qualifier("tUserJdbcPagingItemReader") JdbcPagingItemReader<TUser> tUserJdbcPagingItemReader
          , @Qualifier("tUserJsonWriter") JsonFileItemWriter<TUser> tUserJsonFileItemWriter) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.tUserJdbcPagingItemReader = tUserJdbcPagingItemReader;
    this.tUserJsonFileItemWriter = tUserJsonFileItemWriter;
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
            .<TUser, TUser>chunk(100)
            .reader(tUserJdbcPagingItemReader)
            .writer(tUserJsonFileItemWriter)
            .build();
  }

}
