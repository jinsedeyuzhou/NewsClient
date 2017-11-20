package com.study.newsclient.https.retrofit;




import com.study.newsclient.base.AppConfig;
import com.study.newsclient.https.convert.GsonConverterFactory;
import com.study.newsclient.https.interceptor.LoggingInterceptor;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wyy on 2017/9/6.
 */

public class AppClient {

    private  final int DEFAULT_TIMEOUT = 5;
    private  OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;
    private ApiService apiService;
    private final Retrofit.Builder retrofitBuilder;

    private AppClient() {
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addNetworkInterceptor(new LoggingInterceptor());

        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofitBuilder = new Retrofit.Builder();

        retrofit =retrofitBuilder
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
                .build();

        apiService=retrofit.create(ApiService.class);
    }

    private static AppClient instance;

    public static AppClient getInstance() {
        if (instance == null) {
            synchronized (AppClient.class) {
                if (instance == null) {
                    instance = new AppClient();
                }
            }
        }

        return instance;
    }


    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


}
