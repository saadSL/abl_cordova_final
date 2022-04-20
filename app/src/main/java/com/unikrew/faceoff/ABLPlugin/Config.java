package com.unikrew.faceoff;

import com.unikrew.faceoff.ABLPlugin.model.aasan_account_model.select_banking_mode.RegisterVerifyOtpResponse;

public class Config {
    //    public static final String BASE_URL = "https://rda-uat.abl.com.pk";
    public static final String BASE_URL = "http://10.100.102.124:8080";
    public static final String PROFILE_ID = "profile_id";
    public static final String ACCOUNT_INFO_ID = "acc_info_id";

    public static String ACCOUNT_NUMBER = "account_number";
    //for resume application
    public static String GET_CONSUMER_RESPONSE = "getConsumerAccountDetailsResponse";
    //for new application
    public static String REG_OTP_RESPONSE = "registerVerifyOtpResponse";

    public static String ASAAN_ACCOUNT_PREF = "asaanAccountPref";
    public static String RESPONSE = "response";
    public static String CONSUMER_ACC_DETAILS = "consumer_acc_details";
    // Activity request codes
    public static final int REQ_SCAN_FINGERPRINT = 22;
    public static final int EXTERNAL_STORAGE_CODE = 11;
    public static final int CONVENTIONAL_BANKING = 114201;
    public static final int ISLAMIC_BANKING = 114202;
    public static final int CURRENT_ACCOUNT = 114301;
    public static final int SAVINGS_ACCOUNT = 114302;
    public static final int ADDRESS_TYPE_ID = 106901;
    public static final String IS_RESUMED = "is_resumed";

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

    public static int countDownTime = 5 * 60 * 1000;

    public static int ASAAN_DIGITAL_ACCOUNT = 108243;
    public static int REMITTANCE_ACCOUNT = 108244;
    public static int FREELANCE_ACCOUNT = 108245;

    /* Phase 2 working below */

    public static int MOBILE_NUMBER_LENGTH = 11;
    public static String CNIC_NUMBER = "cnic_number";
    public static String MOBILE_NUMBER = "mobile_number";
    public static String PORTED_MOBILE_NETWORK = "ported_mobile_network";

    public static final int CAMERA_REQUEST = 1888;
    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int LOCATION_PERMISSION_CODE = 44;

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
    public static String MOTHER_NAME_SUGGESTION = "mother_name_suggestion";
    public static String PLACE_OF_BIRTH_SUGGESTION = "place_of_birth_suggestion";

    public static int otpLength = 6;

    public static int ACCOUNT_PURPOSE_CODE =1081;
    public static int OCCUPATION_CODE =1014;
    public static int PROFESSION_CODE =1016;


    public static int codeTypeIdForOccupation = 1014;
    public static int codeTypeIdForProfession = 1016;

    public static int TRANSACTION_ALERT_SMS = 114401;
    public static int TRANSACTION_ALERT_EMAIL = 114402;
    public static int UPI = 108613;
    public static int VDC = 108614;

    public static int SINGLE = 102101;
    public static int JOINT = 102102;
    public static int MINOR = 102103;

    public static int CNIC_BACK_TYPE_ID = 1043002;
    public static int CNIC_FRONT_TYPE_ID = 1043001;
    public static int SIGNATURE_TYPE_ID = 1043008;
    public static int LIVE_PHOTO = 1043005;
}




/* Dumps */
//    /* Code for getting mobile network. */
//    public static int codeTypeId = 1145;