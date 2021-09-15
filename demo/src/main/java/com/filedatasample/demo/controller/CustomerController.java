package com.filedatasample.demo.controller;

import com.filedatasample.demo.dto.CustomerDataDTO;
import com.filedatasample.demo.exception.CustomerException;
import com.filedatasample.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;


@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //API to save customer data to a file
    @PostMapping(value = "/customerfiledata")
    public ResponseEntity<String> saveCustomerFileData(@RequestHeader(name = "file-type", required = true) String fileType,@RequestBody CustomerDataDTO customerDataDTO) throws CustomerException, NoSuchAlgorithmException {
        return customerService.saveCustomer(customerDataDTO,fileType);
    }

   //API to update customer data
    @PutMapping(value = "/customerfiledata/{customerId}")
    public ResponseEntity<String> updateCustomerFileData(@RequestHeader(name = "file-name", required = true) String fileName,@RequestBody CustomerDataDTO customerDataDTO, @PathVariable Integer customerId) throws CustomerException, NoSuchAlgorithmException {
        return customerService.updateCustomer(customerDataDTO, customerId,fileName);
    }

   //API to view customer data
    @GetMapping(value = "/customerfiledata")
    public String getAllCustomerData(@RequestHeader(name = "file-name", required = true) String fileName) {
        return customerService.getAllCustomerData(fileName);
    }


}
