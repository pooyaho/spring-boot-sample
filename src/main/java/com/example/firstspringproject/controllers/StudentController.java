package com.example.firstspringproject.controllers;

import com.example.firstspringproject.mappera.StudentConverter;
import com.example.firstspringproject.models.StudentDocument;
import com.example.firstspringproject.models.StudentDto;
import com.example.firstspringproject.models.StudentEntity;
import com.example.firstspringproject.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    public static ThreadLocal<StudentDto> threadLocal = new ThreadLocal<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentConverter studentConverter;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/print", method = RequestMethod.POST)
    public void printStudent(@Valid @RequestBody StudentDto dto, HttpServletRequest httpServletRequest) {
        LOGGER.info(dto.toString());
        LOGGER.trace("");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional()
    public void save(@RequestBody StudentDto dto) {

        studentService.saveToMongo(studentConverter.destinationToSourceModel(dto));
        studentService.save(studentConverter.destinationToSource(dto));


    }

    @RequestMapping(value = "/getStudent", method = RequestMethod.POST)
    public StudentDto getStudent() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFamily("Doe");
        studentDto.setName("John");
        studentDto.setId(1234L);

        return studentDto;
    }

    @RequestMapping(value = "/findByExample", method = RequestMethod.POST)
    public Iterable<StudentEntity> findByExample(@RequestBody StudentDto dto) {
        return studentService.findByExample(this.studentConverter.destinationToSource(dto));
    }

    @RequestMapping(value = "/findByExampleMongo", method = RequestMethod.POST)
    public List<StudentDocument> findByExampleMongo(@RequestBody StudentDto dto) {
        return studentService.findByExampleMongo(this.studentConverter.destinationToSourceModel(dto));
    }


}
