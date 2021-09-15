package com.filedatasample.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "filedata")
public class FileData {
    private String name;
    @JsonFormat(pattern="dd MMM yyyy")
    private LocalDate dateOfBirth;
    private Double salary;
    private Integer age;
}



