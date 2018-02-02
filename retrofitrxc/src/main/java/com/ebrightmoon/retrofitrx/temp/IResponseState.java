package com.ebrightmoon.retrofitrx.temp;

import java.util.List;


/**
 *
 */
public interface IResponseState {
    //HTTP请求成功状态码
    int httpSuccess();
    //AccessToken错误或已过期
    int accessTokenExpired();
    //RefreshToken错误或已过期
    int refreshTokenExpired();
    //帐号在其它手机已登录
    int otherPhoneLogin();
    //时间戳过期
    int timestampError();
    //缺少授权信息,没有AccessToken
    int noAccessToken();
    //签名错误
    int signError();
    //其他错误
    List<Integer> otherError();
}
