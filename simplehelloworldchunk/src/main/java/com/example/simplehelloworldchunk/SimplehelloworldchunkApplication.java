package com.example.simplehelloworldchunk;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SimplehelloworldchunkApplication {

  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(SimplehelloworldchunkApplication.class, args)));
  }

}
