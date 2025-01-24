package com.myxxts.mls.server.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myxxts.mls.server.model.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

  Optional<User> findByUsername (String username);

  Optional<User> findByEmail (String email);

  Optional<User> findByUsernameOrEmail (String username, String email);

  boolean existsByUsername (String username);
}
