package com.filedatasample.demo.dto;

import lombok.Data;

@Data
public class UpdateRequestDTO {
    private String encryptedData;
    private String fileName;
    private String encodedKey;
}
