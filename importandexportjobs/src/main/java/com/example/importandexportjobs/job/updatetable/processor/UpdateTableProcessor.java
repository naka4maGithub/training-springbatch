package com.example.importandexportjobs.job.updatetable.processor;

import com.example.importandexportjobs.entity.TUser;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class UpdateTableProcessor implements ItemProcessor<TUser, TUser> {

  @Override
  public TUser process(TUser item) throws Exception {
    item.setWorkStatus(0);
    return item;
  }

}
