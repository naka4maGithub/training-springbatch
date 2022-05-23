package com.example.simplehelloworldchunk.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class SimpleHelloWorldProcessor implements ItemProcessor<String, String> {

  @Override
  public String process(String item) throws Exception {
    return item;
  }

}
