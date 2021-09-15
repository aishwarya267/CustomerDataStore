package com.filedatasample.demo.constants;

public class ResponseMessages {

    public static final String SAVE_SUCCESS = "Customer record is saved successfully";
    public static final String UPDATE_SUCCESS = "Customer record is updated successfully";
    public static final String RECORD_SAVE_FAILURE = "Customer record cannot be saved";
    public static final String RECORD_UPDATE_FAILURE = "Customer record cannot be updated";
    public static final String RECORD_FETCH_FAILURE = "Customer record cannot be fetched";
    public static final String DataProcessMicroservicePathForSave = "/fileData/fileDataSave";
    public static final String DataProcessMicroservicePathForUpdate = "/fileData/fileDataUpdate";
    public static final String DataProcessMicroservicePathForFetch = "/fileData/fileDataFetch";


    private ResponseMessages() {
    }
}
