package com.example.importandexportjsotocsvjobs.writer;

import com.example.importandexportjsotocsvjobs.entity.TUser;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class SampleJsonWriterComponent {

  @Bean("tUserJsonWriter")
  public JsonFileItemWriter<TUser> tUserJsonWriter() {
    return new JsonFileItemWriterBuilder<TUser>()
            .name("tUserJsonWriter")
            .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
            .resource(new FileSystemResource("D:\\__work\\exportTUserFromDbJob\\T_USER.json"))
            .encoding(StandardCharsets.UTF_8.name())
            .append(false)
            .shouldDeleteIfExists(true)
            .build();
  }

}
