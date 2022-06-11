package com.example.importandexportjobs;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class ImportandexportjobsApplication {

  public static void main(String[] args) {
    System.exit(SpringApplication.exit(SpringApplication.run(ImportandexportjobsApplication.class, args)));
  }

}
