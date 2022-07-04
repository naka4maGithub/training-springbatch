package com.example.importandexportjsotocsvjobs.reader;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Paths;

@Configuration
public class SampleReaderComponent {

  @StepScope
  @Bean("sampleJsonReader")
  public JsonItemReader<TUser> sampleJsonReader(@Value("#{jobParameters['jsonFilePath']}") String jsonFilePath) {
    return new JsonItemReaderBuilder<TUser>()
            .name("sampleJsonReader")
            .resource(new FileSystemResource(Paths.get(jsonFilePath)))
            .jsonObjectReader(new JacksonJsonObjectReader<>(TUser.class))
            .build();
  }
}
