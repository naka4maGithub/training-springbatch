package com.example.simplehelloworldchunk.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class SimpleHelloWorldWriter implements ItemWriter<String> {

  @Override
  public void write(List<? extends String> items) throws Exception {
    System.out.println(items);
  }

}
