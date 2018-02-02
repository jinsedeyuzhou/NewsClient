package com.ebrightmoon.retrofitrx.core;



import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.func.ApiDataFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.response.ResponseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ApiTransformer {

    private ApiTransformer() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static <T> ObservableTransformer<ResponseResult<T>, T> apiTransformer() {
        return new ObservableTransformer<ResponseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiDataFunc<T>())
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }

    public static <T> ObservableTransformer<ResponseResult<T>, T> mapTransformer() {
        return new ObservableTransformer<ResponseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiDataFunc<T>())
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }


    public static <T> ObservableTransformer<T, T> norTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> norTransformer(final int retryCount, final int retryDelayMillis) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }


}
