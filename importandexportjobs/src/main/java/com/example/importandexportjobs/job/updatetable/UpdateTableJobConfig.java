package com.example.importandexportjobs.job.updatetable;

import com.example.importandexportjobs.entity.TUser;
import com.example.importandexportjobs.job.updatetable.processor.UpdateTableProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UpdateTableJobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final DataSource dataSource;

  private final UpdateTableProcessor updateTableProcessor;

  public UpdateTableJobConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , DataSource dataSource
          , UpdateTableProcessor updateTableProcessor) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
    this.updateTableProcessor = updateTableProcessor;
  }

  @Bean
  public Job updateTableJob() {
    return jobBuilderFactory.get("updateTableJob")
            .incrementer(new RunIdIncrementer())
            .start(updateTableStep())
            .build();
  }

  @Bean
  public Step updateTableStep() {
    return stepBuilderFactory.get("updateTableStep")
            .<TUser, TUser>chunk(1)
            .reader(updateTableJobTUserReader(dataSource))
            .processor(updateTableProcessor)
            .writer(updateTableWriter(dataSource))
            .build();
  }

  @Bean
  public JdbcPagingItemReader<TUser> updateTableJobTUserReader(DataSource dataSource) {
    return new JdbcPagingItemReaderBuilder<TUser>()
            .name("updateTableJobTUserReader")
            .dataSource(dataSource)
            .queryProvider(queryProvider())
            .beanRowMapper(TUser.class)
            .pageSize(1000)
            .fetchSize(1000)
            .build();
  }

  @Bean
  public JdbcBatchItemWriter<TUser> updateTableWriter(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<TUser>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .dataSource(dataSource)
            .sql("update T_USER set WORK_STATUS = :workStatus where USER_ID = :userId")
            .build();
  }

  @Bean
  public MySqlPagingQueryProvider queryProvider() {
    MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
    mySqlPagingQueryProvider.setSelectClause("select *");
    mySqlPagingQueryProvider.setFromClause("from T_USER");

    Map<String, Order> sortKey = new HashMap<>();
    sortKey.put("USER_ID", Order.ASCENDING);
    mySqlPagingQueryProvider.setSortKeys(sortKey);

    return mySqlPagingQueryProvider;
  }

}
