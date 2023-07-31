package com.jarikkomarik.readitlater.repository;

import com.jarikkomarik.readitlater.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
}
