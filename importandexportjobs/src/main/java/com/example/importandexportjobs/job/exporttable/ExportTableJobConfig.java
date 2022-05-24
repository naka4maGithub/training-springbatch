package com.example.importandexportjobs.job.exporttable;

import com.example.importandexportjobs.entity.TUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExportTableJobConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final DataSource dataSource;

  public ExportTableJobConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , DataSource dataSource) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
  }

  @Bean
  public Job exportTableJob() {
    return jobBuilderFactory.get("exportTableJob")
            .incrementer(new RunIdIncrementer())
            .start(exportTableStep())
            .build();
  }

  @Bean
  public Step exportTableStep() {
    return stepBuilderFactory.get("exportTableStep")
            .<TUser, TUser>chunk(1)
            .reader(exportTableJobTUserReader(dataSource))
            .writer(exportTableJobWriter())
            .build();
  }

  @Bean
  public JdbcPagingItemReader<TUser> exportTableJobTUserReader(DataSource dataSource) {
    return new JdbcPagingItemReaderBuilder<TUser>()
            .name("updateTableJobTUserReader")
            .dataSource(dataSource)
            .queryProvider(queryProvider2())
            .beanRowMapper(TUser.class)
            .pageSize(1000)
            .fetchSize(1000)
            .build();
  }

  @Bean
  public MySqlPagingQueryProvider queryProvider2() {
    MySqlPagingQueryProvider mySqlPagingQueryProvider = new MySqlPagingQueryProvider();
    mySqlPagingQueryProvider.setSelectClause("select *");
    mySqlPagingQueryProvider.setFromClause("from T_USER");

    Map<String, Order> sortKey = new HashMap<>();
    sortKey.put("USER_ID", Order.ASCENDING);
    mySqlPagingQueryProvider.setSortKeys(sortKey);

    return mySqlPagingQueryProvider;
  }

  @Bean
  public JsonFileItemWriter<TUser> exportTableJobWriter() {
    return new JsonFileItemWriterBuilder<TUser>()
            .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
            .append(false)
            .encoding(StandardCharsets.UTF_8.name())
            .name("exportTableJobWriter")
            .resource(new FileSystemResource("D:\\__work\\exportJob\\samplejson.json"))
            .saveState(true)
            .build();
  }

}
