package com.filedatasample.demo.dto;

import lombok.Data;

@Data
public class CustomerDataDTO {
    private String customerId;
    private String name;
    private String address;
    private Double salary;
    private Integer age;
}
