package com.example.firstspringproject
        ;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.lang.reflect.Method;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@EnableMongoRepositories
public class FirstSpringProjectApplication {
    public static void main(String[] args) throws JsonProcessingException {
//        StudentDto studentDto = new StudentDto();
//        studentDto.setFamily("Zuckerberg");
//        studentDto.setName("Mark");
//
//        ObjectMapper objectMapper=new ObjectMapper();
//        String x = objectMapper.writeValueAsString(studentDto);
//        System.out.println(x);
//        StudentDto value = objectMapper.readValue(x, StudentDto.class);
//        System.out.println(value);

        MyClass original=new MyClass();
        MyClass myClass = (MyClass) Enhancer.create(MyClass.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("Before run");
                Object invoke = methodProxy.invoke(original,objects);
                System.out.println("After run");
//                throw new IllegalStateException();
                return invoke;
            }
        });

        myClass.print();
//        SpringApplication.run(FirstSpringProjectApplication.class, args);
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}

interface BaseClass{
    void print();
}
class MyClassProxy extends MyClass {
    @Override
    public void print() {
        System.out.println("Before print");
        super.print();
        System.out.println("After print");
    }
}

class MyClass{
    public void print(){
        System.out.println("Hello world!");
    }
}