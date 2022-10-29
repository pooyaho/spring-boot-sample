package com.example.firstspringproject.repositories;

import com.example.firstspringproject.models.StudentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface StudentMongoRepository extends MongoRepository<StudentDocument,String>, QuerydslPredicateExecutor<StudentDocument> {

}
