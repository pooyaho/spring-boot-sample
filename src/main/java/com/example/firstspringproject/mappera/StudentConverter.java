package com.example.firstspringproject.mappera;

import com.example.firstspringproject.models.StudentDocument;
import com.example.firstspringproject.models.StudentDto;
import com.example.firstspringproject.models.StudentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentConverter {
        StudentDto sourceToDestination(StudentEntity source);
        StudentEntity destinationToSource(StudentDto destination);

        StudentDto sourceToDestinationModel(StudentDocument source);
        StudentDocument destinationToSourceModel(StudentDto destination);

}
