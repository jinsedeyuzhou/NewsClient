package com.study.newsclient.restful.config;


public class AppConfig {
    public final static String PACKAGE="com.study.newsclient";
    public final static String ERROR400 = "网络错误";
    public final static String ERROR500 = "网络错误";
    public final static String ERROR408 = "请求超时";

    /**
     * 请求地址
     */
    public static final String BASE_URL = "http://bdp.deeporiginalx.com/v2";

    /**
     * 注册游客身份
     */
    public static final String URL_REGISTER_VISITOR=BASE_URL+"/au/sin/g";

    /**
     * apk自动更新
     */
    public static final String URL_APK_UPDATE=BASE_URL+"/version/query?";








}
