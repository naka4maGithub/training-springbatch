package com.example.simplehelloworldtasklet;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  private String url = "jdbc:mysql://localhost/springbatch_db";
  private String username = "springbatch";
  private String password = "springbatch";
  private String driverClassName = "com.mysql.cj.jdbc.Driver";

  @Bean
  public DataSource createDataSource() {
    return DataSourceBuilder
            .create()
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build();
  }

}
