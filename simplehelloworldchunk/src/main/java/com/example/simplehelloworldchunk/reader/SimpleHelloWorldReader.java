package com.example.simplehelloworldchunk.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class SimpleHelloWorldReader implements ItemReader<String> {

  private String[] strings = {"Hello World", null};

  private int index = 0;

  @Override
  public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    String message = strings[index];
    index++;

    return message;
  }

}
