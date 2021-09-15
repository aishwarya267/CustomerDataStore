package com.filedatasample.demo.service.impl;


import com.filedatasample.demo.constants.ResponseMessages;
import com.filedatasample.demo.dto.CustomerDataDTO;
import com.filedatasample.demo.dto.RequestDTO;
import com.filedatasample.demo.util.EncryptDecryptManager;
import com.filedatasample.demo.exception.CustomerException;
import com.filedatasample.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${fileDataProcessService.name}")
    private String hostServer;


    @Override
    public ResponseEntity<String> saveCustomer(CustomerDataDTO customerDataDTO, String fileType) throws NoSuchAlgorithmException {
        log.info("Started saving Json data to be stored in file");


        //encrypt data to be saved
        // create new key and get base64 encoded version of the key
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        EncryptDecryptManager encryptDecryptManager = new EncryptDecryptManager();
        String encryptedData = encryptDecryptManager.encrypt(customerDataDTO.toString(), encodedKey);


        // RestTemplate to call the microservice to save it to the file format specified

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setEncryptedData(encryptedData);
        requestDTO.setFileType(fileType);
        requestDTO.setEncodedKey(encodedKey);
        ResponseEntity<String> responseEntity = null;
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            final String dataStoreMicroserviceURL = hostServer + ResponseMessages.DataProcessMicroservicePathForSave;
            final ResponseEntity<String> response = restTemplate.postForEntity(dataStoreMicroserviceURL, requestDTO, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                responseEntity = new ResponseEntity<>(ResponseMessages.SAVE_SUCCESS, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            throw new CustomerException(ResponseMessages.RECORD_SAVE_FAILURE, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<String> updateCustomer(CustomerDataDTO customerDataDTO, Integer customerId, String fileName) throws CustomerException, NoSuchAlgorithmException {
        log.info("Started updating Customer details");

        //encrypt data that needs to be updated
        // create new key and get base64 encoded version of the key
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        EncryptDecryptManager encryptDecryptManager = new EncryptDecryptManager();
        String encryptedUpdateData = encryptDecryptManager.encrypt(customerDataDTO.toString(), encodedKey);

        //RestTemplate to call the microservice to update it in the file format specified

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setEncryptedData(encryptedUpdateData);
        requestDTO.setFileType(fileName);
        requestDTO.setEncodedKey(encodedKey);
        ResponseEntity<String> responseEntity = null;
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            final String dataStoreMicroserviceURL = hostServer + ResponseMessages.DataProcessMicroservicePathForUpdate;
            HttpEntity<RequestDTO> requestUpdate = new HttpEntity<>(requestDTO, headers);
            final ResponseEntity<String> response = restTemplate.exchange(dataStoreMicroserviceURL, HttpMethod.PUT, requestUpdate, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                responseEntity = new ResponseEntity<>(ResponseMessages.UPDATE_SUCCESS, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new CustomerException(ResponseMessages.RECORD_UPDATE_FAILURE, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


    public String getAllCustomerData(String fileName) {
        log.info("Started Fetching all Customer information");
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            final String dataStoreMicroserviceURL = hostServer + ResponseMessages.DataProcessMicroservicePathForFetch;
            ResponseEntity<String> result = restTemplate.getForEntity(dataStoreMicroserviceURL, String.class);

            //Decrypt the data received from file data process microservice
            // decode the base64 encoded string
            byte[] decodedKey = Base64.getDecoder().decode(result.getBody().getBytes());
            // rebuild key using SecretKeySpec
            SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            EncryptDecryptManager decryptionManager = new EncryptDecryptManager();
            return decryptionManager.decrypt(result, originalKey.toString());
        } catch (Exception e) {
            throw new CustomerException(ResponseMessages.RECORD_FETCH_FAILURE, HttpStatus.NOT_FOUND);
        }

    }
}
