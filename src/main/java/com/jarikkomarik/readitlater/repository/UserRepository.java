package com.jarikkomarik.readitlater.repository;

import com.jarikkomarik.readitlater.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
