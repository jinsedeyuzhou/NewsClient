package com.ebrightmoon.retrofitrx.interceptor;
<<<<<<< HEAD
=======

import android.support.annotation.NonNull;
>>>>>>> 29b4bf83535835fb7947b2b713d62280eba5fdcf

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @Description: 无缓存拦截
 */
public class NoCacheInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "max-age=60")
                .build();
    }
}
