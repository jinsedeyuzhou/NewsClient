package com.ebrightmoon.retrofitrx.retrofit;

import android.support.annotation.MainThread;

import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.interceptor.LoggingInterceptor;
import com.ebrightmoon.retrofitrx.retrofit.ApiService;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wyy on 2017/9/6.
 */

public class AppClient {

    private final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;
    private ApiService apiService;
    private final Retrofit.Builder retrofitBuilder;

    private AppClient() {
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addNetworkInterceptor(new LoggingInterceptor());

        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofitBuilder = new Retrofit.Builder();

        retrofit = retrofitBuilder
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .build();

        apiService = retrofit.create(ApiService.class);
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


    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        Observable<ResponseBody> weathers = apiService.getWeathers("");
        weathers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }


}
