package com.study.newsclient.https.response;

/**
 */
public class ResponseCode {
    //HTTP请求成功状态码
    public static final int HTTP_SUCCESS = 0;
    //AccessToken错误或已过期
    public static final int ACCESS_TOKEN_EXPIRED = 10001;
    //RefreshToken错误或已过期
    public static final int REFRESH_TOKEN_EXPIRED = 10002;
    //帐号在其它手机已登录
    public static final int OTHER_PHONE_LOGIN = 10003;
    //时间戳过期
    public static final int TIMESTAMP_ERROR = 10004;
    //缺少授权信息,没有AccessToken
    public static final int NO_ACCESS_TOKEN = 10005;
    //签名错误
    public static final int SIGN_ERROR = 10006;
}
