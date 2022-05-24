package com.example.importandexportjobs.job.importtable.mapper;

import com.example.importandexportjobs.entity.TUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Component
public class TUserMapper implements FieldSetMapper<TUser> {

  @Override
  public TUser mapFieldSet(FieldSet fieldSet) throws BindException {
    TUser tUser = new TUser();
    int userId = fieldSet.readInt(0); // userId
    String firstName = fieldSet.readString(1); // firstName
    String familyName = fieldSet.readString(2); // familyName
    int age = fieldSet.readInt(3); // age
    String updateDateStr = fieldSet.readString(4); // updateDate

    tUser.setUserId(Long.parseLong(String.valueOf(userId)));
    tUser.setFirstName(firstName);
    tUser.setFamilyName(familyName);
    tUser.setAge(age);
    tUser.setWorkStatus(1);
    tUser.setUpdateDate(LocalDateTime.parse(updateDateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)));

    return tUser;
  }
}
