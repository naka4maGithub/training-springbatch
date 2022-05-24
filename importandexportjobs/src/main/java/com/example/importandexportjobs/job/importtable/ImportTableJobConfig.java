package com.example.importandexportjobs.job.importtable;

import com.example.importandexportjobs.entity.TUser;
import com.example.importandexportjobs.job.importtable.mapper.TUserMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class ImportTableJobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final DataSource dataSource;

  private final TUserMapper tUserMapper;

  public ImportTableJobConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , DataSource dataSource
          , TUserMapper tUserMapper) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
    this.tUserMapper = tUserMapper;
  }

  @Bean
  public Job importTableJob() {
    return jobBuilderFactory.get("importTableJob")
            .incrementer(new RunIdIncrementer())
            .start(importTableStep())
            .build();
  }

  @Bean
  public Step importTableStep() {
    return stepBuilderFactory.get("importTableStep")
            .<TUser, TUser>chunk(2)
            .reader(tUserReader())
            .writer(tUserWriter(null))
            .build();
  }

  @Bean
  public FlatFileItemReader<TUser> tUserReader() {
    return new FlatFileItemReaderBuilder<TUser>()
            .name("tUserReader")
            .resource(new ClassPathResource("csv/sampleTUser.csv"))
            .delimited()
            .names(new String[]{"userId", "firstName", "familyName", "age", "updateDate"})
            .fieldSetMapper(tUserMapper)
            .build();
  }

  @Bean
  public JdbcBatchItemWriter<TUser> tUserWriter(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<TUser>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .dataSource(dataSource)
            .sql("insert into T_USER(USER_ID, FIRST_NAME, FAMILY_NAME, AGE, WORK_STATUS, UPDATE_DATE) values (:userId, :firstName, :familyName, :age, :workStatus, :updateDate)")
            .build();
  }

}
