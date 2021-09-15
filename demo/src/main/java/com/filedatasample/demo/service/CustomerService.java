package com.filedatasample.demo.service;

import com.filedatasample.demo.dto.CustomerDataDTO;
import com.filedatasample.demo.exception.CustomerException;
import org.springframework.http.ResponseEntity;
import java.security.NoSuchAlgorithmException;


public interface CustomerService {

    public ResponseEntity<String> saveCustomer(CustomerDataDTO customerDataDTO, String fileType) throws CustomerException, NoSuchAlgorithmException;

    ResponseEntity<String> updateCustomer(CustomerDataDTO customerDataDTO, Integer customerId, String fileName) throws CustomerException, NoSuchAlgorithmException;

    String getAllCustomerData(String fileName);
}
