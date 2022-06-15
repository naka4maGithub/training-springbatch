package com.example.importandexportjsotocsvjobs.job;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.nio.file.Paths;

@Configuration
public class ImportTUserFromJsonJob {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final DataSource dataSource;

  private final String SQL_INSERT_T_USER = "insert into T_USER(USER_ID, FIRST_NAME, FAMILY_NAME, AGE, WORK_STATUS, UPDATE_DATE) values (:userId, :firstName, :familyName, :age, :workStatus, :updateDate)";

  public ImportTUserFromJsonJob(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , DataSource dataSource) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
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
            .reader(sampleJsonReader(null))
            .writer(sampleJsonWriter())
            .build();
  }

  @StepScope
  @Bean
  public JsonItemReader<TUser> sampleJsonReader(@Value("#{jobParameters['jsonFilePath']}") String jsonFilePath) {
    return new JsonItemReaderBuilder<TUser>()
            .name("sampleJsonReader")
            .resource(new FileSystemResource(Paths.get(jsonFilePath)))
            .jsonObjectReader(new JacksonJsonObjectReader<>(TUser.class))
            .build();
  }

  @Bean
  public JdbcBatchItemWriter<TUser> sampleJsonWriter() {
    return new JdbcBatchItemWriterBuilder<TUser>()
            .sql(SQL_INSERT_T_USER)
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TUser>())
            .dataSource(dataSource)
            .build();
  }

}
