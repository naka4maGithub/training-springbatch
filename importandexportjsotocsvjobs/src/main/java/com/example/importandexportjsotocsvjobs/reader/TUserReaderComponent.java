package com.example.importandexportjsotocsvjobs.reader;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TUserReaderComponent {

  private final DataSource dataSource;

  public TUserReaderComponent(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean("tUserJdbcPagingItemReader")
  public JdbcPagingItemReader<TUser> tUserJdbcPagingItemReader() {
    return  new JdbcPagingItemReaderBuilder<TUser>()
            .name("tUserJdbcPagingItemReader")
            .dataSource(dataSource)
            .queryProvider(tUserPagingQueryProvider())
            .beanRowMapper(TUser.class)
            .build();
  }

  @Bean
  public MySqlPagingQueryProvider tUserPagingQueryProvider() {
    MySqlPagingQueryProvider pagingQueryProvider = new MySqlPagingQueryProvider();
    pagingQueryProvider.setSelectClause("select *");
    pagingQueryProvider.setFromClause("from T_USER");
    pagingQueryProvider.setSortKeys(makeSortKey());

    return pagingQueryProvider;
  }

  private Map<String , Order> makeSortKey() {
    HashMap<String, Order> sortKey = new HashMap<>();
    sortKey.put("USER_ID", Order.ASCENDING);

    return  sortKey;
  }

}
