package com.ebrightmoon.retrofitrx.temp;



import com.ebrightmoon.retrofitrx.response.ResponseCode;

import java.util.List;

/**
 */
public class DefaultResponseState implements IResponseState {
    @Override
    public int httpSuccess() {
        return ResponseCode.HTTP_SUCCESS;
    }

    @Override
    public int accessTokenExpired() {
        return ResponseCode.ACCESS_TOKEN_EXPIRED;
    }

    @Override
    public int refreshTokenExpired() {
        return ResponseCode.REFRESH_TOKEN_EXPIRED;
    }

    @Override
    public int otherPhoneLogin() {
        return ResponseCode.OTHER_PHONE_LOGIN;
    }

    @Override
    public int timestampError() {
        return ResponseCode.TIMESTAMP_ERROR;
    }

    @Override
    public int noAccessToken() {
        return ResponseCode.NO_ACCESS_TOKEN;
    }

    @Override
    public int signError() {
        return ResponseCode.SIGN_ERROR;
    }

    @Override
    public List<Integer> otherError() {
        return null;
    }
}
