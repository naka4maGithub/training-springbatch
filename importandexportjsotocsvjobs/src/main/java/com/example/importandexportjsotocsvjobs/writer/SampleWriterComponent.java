package com.example.importandexportjsotocsvjobs.writer;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SampleWriterComponent {

  private final DataSource dataSource;

  private final String SQL_INSERT_T_USER = "insert into T_USER(USER_ID, FIRST_NAME, FAMILY_NAME, AGE, WORK_STATUS, UPDATE_DATE) values (:userId, :firstName, :familyName, :age, :workStatus, :updateDate)";

  public SampleWriterComponent(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean("sampleJsonWriter")
  public JdbcBatchItemWriter<TUser> sampleJsonWriter() {
    return new JdbcBatchItemWriterBuilder<TUser>()
            .sql(SQL_INSERT_T_USER)
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TUser>())
            .dataSource(dataSource)
            .build();
  }
}
