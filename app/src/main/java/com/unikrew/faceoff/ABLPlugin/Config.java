package com.unikrew.faceoff;

public class Config {
    public static final String BASE_URL = "https://rda-uat.abl.com.pk";

    public static String ACCOUNT_NUMBER = "account_number";

    public static String RESPONSE = "response";
    // Activity request codes
    public static final int REQ_SCAN_FINGERPRINT = 22;
    public static final int EXTERNAL_STORAGE_CODE = 11;

    public static String templateType = "WSQ";


    // Permission request codes
    public static final int CAMERA_REQ_CODE = 10;


    // Bundle keys
    public static final String KEY_RESPONSE_CODE = "ResponseCode";

    public static String CNIC_NUMBER = "";


    public static String CNIC_ACC = "cnic_acc";

    public static int errorType = 0;
    public static int successType = 1;
    public static int ACCOUNT_LENGTH = 16;
    public static int CNIC_LENGTH = 13;

    public static int countDownTime = 5*60*1000;
}
