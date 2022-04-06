package com.unikrew.faceoff;

public class Config {
//    public static final String BASE_URL = "https://rda-uat.abl.com.pk";
    public static final String BASE_URL = "http://10.100.102.124:8080";

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



    public static String CNIC_ACC = "cnic_acc";

    public static int errorType = 0;
    public static int successType = 1;
    public static int verifiedType = 2;
    public static int ACCOUNT_LENGTH = 16;
    public static int CNIC_LENGTH = 13;

    public static int countDownTime = 5*60*1000;



    /* Phase 2 working below */

    public static int MOBILE_NUMBER_LENGTH = 11;
    public static String CNIC_NUMBER = "cnic_number";
    public static String MOBILE_NUMBER = "mobile_number";
    public static String PORTED_MOBILE_NETWORK = "ported_mobile_network";

    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;

    /* Code for new user. */
    public static int CUSTOMER_TYPE_ID = 106501;


    /* Algorithm for encryption */
    public static String ALGO = "AES";


    /* Pagination variables below */
    public static int PAGE_NUMBER = 1;
    public static int PAGE_SIZE = 10;
    
    
    /* Attachment variables for view-Apps-Generate-Otp */
    public static String CNIC_FRONT_FILE_NAME = "CNIC FRONT";
    public static String CNIC_BACK_FILE_NAME = "CNIC BACK";
    public static int ATTACHMENT_TYPE_ID_FRONT = 1043001;
    public static int ATTACHMENT_TYPE_ID_BACK = 1043002;


    /* Account Application variables */
    public static String APP_LIST = "app_list";
    public static String ACCESS_TOKEN = "access_token";

    public static int otpLength = 6;

}




/* Dumps */
//    /* Code for getting mobile network. */
//    public static int codeTypeId = 1145;