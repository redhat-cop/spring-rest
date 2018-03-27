package com.hello;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends MongoRepository<Greeting, Long> {
  
}
