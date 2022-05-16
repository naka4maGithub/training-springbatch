package com.example.simplehelloworldtasklet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@ContextConfiguration(classes = {SimplehelloworldtaskletApplication.class})
class SimplehelloworldtaskletApplicationTests {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  @DisplayName("Hello Worldをコンソールに表示するジョブのテスト")
  public void testJob() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Assertions.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
  }

}
