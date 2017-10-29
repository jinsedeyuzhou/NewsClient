package com.study.newsclient.https.retrofit;


import com.study.newsclient.https.response.ResponseCode;

/**
 * Created by wyy on 2017/9/12.
 */

public class ApiException extends RuntimeException {

    private int code;
    private String message;

    public ApiException(int code) {
        this(getApiExceptionMessage(code));
    }

    public ApiException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ApiException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiException setCode(int code) {
        this.code = code;
        return this;
    }

    public String getDisplayMessage() {
        return message + "(code:" + code + ")";
    }

    public static String getApiExceptionMessage(int code) {
        String message="";
        switch (code) {
            case ResponseCode.HTTP_SUCCESS:

                break;
            case ResponseCode.ACCESS_TOKEN_EXPIRED:
                break;
            default:


        }
        return message;
    }
}
