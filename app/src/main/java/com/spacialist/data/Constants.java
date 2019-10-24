package com.spacialist.data;

import java.math.BigDecimal;

public class Constants {
    //EXTRAs passed to next INTENTS
    public static final String USER_ID = "USER_ID";
    public static final String USER_DISPLAY_NAME = "USER_DISPLAY_NAME";
    public static final String USER_PASS = "USER_PASS";
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    public static final String USER_FULL_NAME = "USER_FULL_NAME";
    public static final String USER_PHONE_NUM = "USER_PHONE_NUM";
    public static final String USER_ADDRESS = "USER_ADDRESS";
    public static final String USER_EMAIL = "USER_EMAIL";

    // Calendar or DATETIME Selection
    public static final String SELECTED_DATE_TIME = "SELECTED_DATE_TIME";
    public static final String SELECTED_LOCATION_LATITUDE = "SELECTED_LOCATION_LATITUDE";
    public static final String SELECTED_LOCATION_LONGITUDE = "SELECTED_LOCATION_LONGITUDE";

    public static final String SELECTED_DATE_STRING = "SELECTED_DATE_STRING";
    public static final String SELECTED_TIME_STRING = "SELECTED_TIME_STRING";

    //Business Selection
    public static final String SELECTED_BUSINESS_NAME = "SELECTED_BUSINESS_NAME";
    public static final String SELECTED_BUSINESS_ID = "SELECTED_BUSINESS_ID";
    public static final String SELECTED_BUSINESS_ADDRESS = "SELECTED_BUSINESS_ADDRESS";
    public static final String SELECTED_BUSINESS_LOGO_URI = "SELECTED_BUSINESS_LOGO_URI";

    // Business Spa List
    public static final String SEARCH_BUSINESS_LIST_RESULTS = "SEARCH_BUSINESS_LIST_RESULTS";
    public static final String SEARCH_SERVICES_LIST_RESULTS = "SEARCH_SERVICES_LIST_RESULTS";

    // Services Selection
    public static final String SELECTED_SERVICE_ID = "SELECTED_SERVICE_ID";
    public static final String SELECTED_SERVICE_NAME = "SELECTED_SERVICE_NAME";
    public static final String SELECTED_SERVICE_DURATION = "SELECTED_SERVICE_DURATION";
    public static final String SELECTED_SERVICE_DESCRIPTION = "SELECTED_SERVICE_DESCRIPTION";
    public static final String SELECTED_SERVICE_PRICE = "SELECTED_SERVICE_PRICE";
    public static final String AUTO_ASSIGNED_STAFF_NAME = "AUTO_ASSIGNED_STAFF_NAME";
    public static final String AUTO_ASSIGNED_STAFF_ID = "AUTO_ASSIGNED_STAFF_ID";
    public static final String AUTO_ASSIGNED_STAFF_SERVICE_ID = "AUTO_ASSIGNED_STAFF_SERVICE_ID";
    public static final String COMPUTED_HR_DURATION = "COMPUTED_HR_DURATION";
    public static final String COMPUTED_MIN_DURATION = "COMPUTED_MIN_DURATION";
    public static final String SELECTED_ASSIGNED_STAFF_NAME = "SELECTED_ASSIGNED_STAFF_NAME";
    public static final String SELECTED_ASSIGNED_STAFF_ID = "SELECTED_ASSIGNED_STAFF_ID";
    public static final String SELECTED_ASSIGNED_STAFF_SERVICE_ID = "SELECTED_ASSIGNED_STAFF_SERVICE_ID";
    public static final String COMPUTED_UNIT_PRICE = "COMPUTED_UNIT_PRICE";
    public static final String COMPUTED_VAT_PERCENTAGE = "COMPUTED_VAT_PERCENTAGE";

    // HISTORY
    public static final String HISTORY_PARCEL = "HISTORY_PARCEL";
    public static final String HISTORY_BOOKING_ID = "HISTORY_BOOKING_ID";
    public static final String HISTORY_APPOINTMENT_ID = "HISTORY_APPOINTMENT_ID";
    public static final String HISTORY_BUSINESS_NAME = "HISTORY_BUSINESS_NAME";
    public static final String HISTORY_BUSINESS_ADDRESS = "HISTORY_BUSINESS_ADDRESS";
    public static final String HISTORY_TRANSACTION_DATE = "HISTORY_TRANSACTION_DATE";
    public static final String HISTORY_APPOINTMENT_DATE = "HISTORY_APPOINTMENT_DATE";
    public static final String HISTORY_APPOINTMENT_START_TIME = "HISTORY_APPOINTMENT_START_TIME";
    public static final String HISTORY_APPOINTMENT_END_TIME = "HISTORY_APPOINTMENT_END_TIME";
    public static final String HISTORY_BUSINESS_DTI_NO = "HISTORY_BUSINESS_DTI_NO";
    public static final String HISTORY_SERVICE_NAME = "HISTORY_SERVICE_NAME";
    public static final String HISTORY_BUSINESS_STAFF_NAME = "HISTORY_BUSINESS_STAFF_NAME";
    public static final String HISTORY_SERVICE_AMOUNT = "HISTORY_SERVICE_AMOUNT";
    public static final String HISTORY_TRANSACTION_PAYMENT_ID = "HISTORY_TRANSACTION_PAYMENT_ID";
    public static final String HISTORY_APPOINTMENT_STATUS = "HISTORY_APPOINTMENT_STATUS";

    //STAFF PARCEL
    public static final String STAFF_PARCEL = "STAFF_PARCEL";

    //SERVICES MENU
    public static final String SERVICES_MENU = "SERVICES_MENU";

    public static final String DATE_TIME_FORMAT = "dd/MMM/yyyy hh:mma";
    public static final String DATE_TIME_FORMAT_DDMMMYYYY = "dd/MMM/yyyy";
    public static final String DATE_TIME_FORMAT_DB = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_TIME_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "hh:mm:ss";
    public static final String TIME_FORMAT_AMPM = "hh:mma";
    public static final String PESO_SIGN = "Ρ";
    public static final String BOOKING_FEE_TEN_PESOS = "10.00";

    public static final String COMPUTED_TOTAL_FEES_IN_PHP = "COMPUTED_TOTAL_FEES_IN_PHP";
    public static final String COMPUTED_TOTAL_FEES_IN_USD = "COMPUTED_TOTAL_FEES_IN_USD";

    //PAYMENT Confirmation
    public static final String PAYMENT_DETAILS = "PAYMENT_DETAILS";
    public static final String PAYMENT_PROOF = "PAYMENT_PROOF";


    public static final String HOST = "http://localhost/";
    public static final String APP = "spacialist";
    public static final String ENDPOINT_LOGIN = "login";

    public static final String ENDPOINT_SEARCH = "search";
    public static final String TAG_SUCCESS = "SUCCESS";
    public static final String TAG_FAILED = "FAILED";

    public static final String DAY_OF_THE_WEEK = "DAY_OF_THE_WEEK";
    public static final String DAY = "DAY";
    public static final String MONTH_STRING = "MONTH_STRING";

    public static final int REQUEST_CODE_CALENDAR = 100;

    public static final String BACKSLASH = "/";
    public static final String COLON = ":";
    //Json
    public static final String JSON_USER_DAO = "userDao";
    public static final int INTERVAL = 30;

    // CURRENCY Converter
    public static final double PESO_TO_USD = 51.94;
    public static final BigDecimal VAT_12_PERCENT = new BigDecimal(0.12);
    public static final BigDecimal VAT_112_PERCENT = new BigDecimal(1.12);


    //=============URLS=============
    //SERVER
    public static final String IP = "<SERVER_IP>";
    public static final String APP_NAME = "spacialist/";

    //Resister USER API
    public static final String REGISTER_API_PATH = IP + APP_NAME + "userController/createUser.php";
    public static final String UPDATE_USER_PROFILE_API_PATH = IP + APP_NAME + "userController/updateUser.php";

    //Login API getUser by username and password
    public static final String LOGIN_API_PATH = IP + APP_NAME + "userController/read_OneUser.php?username=%s&password=%s";
    //get All Businesses
    public static final String READ_ALL_BUSINESS = IP + APP_NAME + "businessController/readAllBusiness.php";
    public static final String SEARCH_BUSINESS_BY_TIME = IP + APP_NAME + "businessController/searchBusinessByTime.php?selected_time=%S";

    //get All Services By Business ID
    public static final String READ_ALL_SERVICES_BY_BUS_ID = IP + APP_NAME + "serviceController/readAllServices.php?bus_id=%s";
    public static final String SEARCH_SERVICES_BY_BUS_ID_AND_TIME_AND_DATE = IP + APP_NAME + "serviceController/readAllServicesByStaff.php?bus_id=%s&selected_time=%s&selected_date=%s";
    public static final String CREATE_TRANSACTION_APPOINTMENT_SCHEDULE = IP + APP_NAME + "transactionController/createTransaction.php";
    public static final String CREATE_NOTIFICATION_FOR_APPOINTMENT = IP + APP_NAME + "notificationController/createNotification.php";
    public static final String SEND_FIREBASE_NOTIFICATION_FOR_APPOINTMENT = IP + APP_NAME + "notificationController/sendFirebaseNotification.php";

    //Retrieve HISTORY
    public static final String RETRIEVE_USER_ACTIVITIES = IP + APP_NAME + "historyController/readAllHistorybyUserId.php?user_id=%s";

    public static final String DEFAULT_SPA_LOGO = IP + "myspa/spacialist/img/blank.png";

    public static final String PAYPAL_CLIENT_ID = "<PAYPAL_CLIENT_ID>";
    public static final String FIREBASE_API_SERVER_KEY = "<FIREBASE_API_SERVER_KEY>";

    public static final String SUCCESS = "User was created.";
    public static final String FAILURE = "User registration failed.";

    public static final String UPDATE_SUCCESS = "User was updated.";
    public static final String UPDATE_FAILED = "User profile update updated.";
    //=============URLS=============

    public static final String TRANSACTION_SUCCESS = "Transaction successful.";
    public static final String TRANSACTION_FAILURE = "Transaction failed.";

    public static final String NOTIFICATION_SUCCESS = "Notification created.";
    public static final String NOTIFICATION_FAILURE = "Notification failed.";
    //format Service Name with client CLIENT_NAME
    public static final String APPOINTMENT_NAME = "%s with client %s";

    //Appointment with Username of client for service name with Staff name
    public static final String APPOINTMENT_DESC = "Appointment with client %s for service %s with staff %s";

    public static final String APPOINTMENT_CONFIRMATION_TITLE = "Appointment Confirmation";
    //Appointment confirmation with Heaven Spa on 09/Sep/2019 4:30pm for foot massage";
    public static final String APPOINTMENT_CONFIRMATION_MESSAGE = "Appointment with %s on %s for %s";

    public static final String FIREBASE_SEND_NOTIF_SUCCESS = "Firebase Notification sent.";
    public static final String FIREBASE_SEND_NOTIF_FAILED = "Unable to send firebase notification. Data is incomplete.";
}
