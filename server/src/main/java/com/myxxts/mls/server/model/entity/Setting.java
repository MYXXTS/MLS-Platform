package com.myxxts.mls.server.model.entity;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document (collection = "system")
@NoArgsConstructor
public class Setting {

  @Id
  private ObjectId id = new ObjectId();

  private String name;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private HashMap<String, String> properties;

}
