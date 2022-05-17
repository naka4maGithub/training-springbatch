package com.example.simplehelloworldchunk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@ContextConfiguration(classes = {SimplehelloworldchunkApplication.class})
class SimplehelloworldchunkApplicationTests {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void test() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
  }

}
