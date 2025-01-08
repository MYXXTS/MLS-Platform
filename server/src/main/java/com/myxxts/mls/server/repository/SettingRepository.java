package com.myxxts.mls.server.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.myxxts.mls.server.model.entity.Setting;

public interface SettingRepository extends MongoRepository<Setting, ObjectId> {

  Optional<Setting> findByName (String name);

}
