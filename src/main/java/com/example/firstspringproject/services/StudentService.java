package com.example.firstspringproject.services;

import com.example.firstspringproject.models.QStudentDocument;
import com.example.firstspringproject.models.StudentDocument;
import com.example.firstspringproject.models.StudentEntity;
import com.example.firstspringproject.repositories.StudentMongoRepository;
import com.example.firstspringproject.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentService {
    public static final QStudentDocument Q = QStudentDocument.studentDocument;
    private final StudentRepository studentRepository;
    private final StudentMongoRepository studentMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentMongoRepository studentMongoRepository) {
        this.studentRepository = studentRepository;
        this.studentMongoRepository = studentMongoRepository;
    }

    public void save(StudentEntity studentEntity) {
        saveToMongo(null);
        studentRepository.save(studentEntity);
    }



    @Transactional
    public void saveToMongo(StudentDocument studentDocument) {
        studentMongoRepository.save(studentDocument);
    }

    public Iterable<StudentEntity> findByExample(StudentEntity entity) {

        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("family", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return studentRepository.findAll(Example.of(entity, customExampleMatcher));

    }

    public List<StudentDocument> findByExampleMongo(StudentDocument entity) {

        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("family", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());


        Iterable<StudentDocument> all = studentMongoRepository.findAll(Q.family.containsIgnoreCase("A"));
        System.out.println(all);
        return studentMongoRepository.findAll(Example.of(entity, customExampleMatcher));

    }

}
