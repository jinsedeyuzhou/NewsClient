package com.ebrightmoon.retrofitrx.common;

/**
 * Created by wyy on 2018/1/26.
 */

public class AppConfig {

    private AppConfig()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String BASE_URL = "http://openapi.tuling123.com/";
}
