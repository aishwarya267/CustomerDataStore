package com.filedatasample.demo.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private String encryptedData;
    private String fileType;
    private String encodedKey;
}
