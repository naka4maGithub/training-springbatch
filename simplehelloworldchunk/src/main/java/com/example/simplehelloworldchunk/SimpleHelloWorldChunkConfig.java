package com.example.simplehelloworldchunk;

import com.example.simplehelloworldchunk.processor.SimpleHelloWorldProcessor;
import com.example.simplehelloworldchunk.reader.SimpleHelloWorldReader;
import com.example.simplehelloworldchunk.writer.SimpleHelloWorldWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleHelloWorldChunkConfig {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  private final SimpleHelloWorldReader simpleHelloWorldReader;

  private final SimpleHelloWorldProcessor simpleHelloWorldProcessor;

  private final SimpleHelloWorldWriter simpleHelloWorldWriter;

  public SimpleHelloWorldChunkConfig(JobBuilderFactory jobBuilderFactory
          , StepBuilderFactory stepBuilderFactory
          , SimpleHelloWorldReader simpleHelloWorldReader
          , SimpleHelloWorldProcessor simpleHelloWorldProcessor
          , SimpleHelloWorldWriter simpleHelloWorldWriter) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.simpleHelloWorldReader = simpleHelloWorldReader;
    this.simpleHelloWorldProcessor = simpleHelloWorldProcessor;
    this.simpleHelloWorldWriter = simpleHelloWorldWriter;
  }

  @Bean
  public Job simpleHelloWorldChunkJob() {
    return jobBuilderFactory.get("simpleHelloWorldChunkJob")
            .incrementer(new RunIdIncrementer())
            .start(simpleHelloWorldChunkStep())
            .build();
  }

  @Bean
  public Step simpleHelloWorldChunkStep() {
    return stepBuilderFactory.get("simpleHelloWorldChunkStep")
            .<String, String>chunk(1)
            .reader(simpleHelloWorldReader)
            .processor(simpleHelloWorldProcessor)
            .writer(simpleHelloWorldWriter)
            .build();
  }


}
