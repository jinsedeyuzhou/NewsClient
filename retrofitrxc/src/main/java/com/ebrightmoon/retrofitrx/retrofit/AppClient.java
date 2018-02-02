package com.ebrightmoon.retrofitrx.retrofit;


import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.common.GsonUtil;
import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.core.ApiTransformer;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.interceptor.HeadersInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.LoggingInterceptor;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
        Map<String, String> headers = new HashMap<>();
//        headers.put("","");  设置全局header
        okHttpBuilder.addInterceptor(new HeadersInterceptor(headers));


        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofitBuilder = new Retrofit.Builder();

        retrofit = retrofitBuilder
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppConfig.BASE_URL)
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

    public <T> void get(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        apiService.get(url, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

    }

    public <T> void post(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        apiService.post(url, body)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);
    }


    /**
     * Api通用Get  返回data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executeGet(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        apiService.executeGet(url, params, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

    }

    /**
     * Api通用post  返回data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executePost(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        apiService.executePost(url, params, body)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);
    }

    /**
     * Api通用put  返回data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executePut(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        apiService.executePut(url, params, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);
    }


    /**
     * Api通用Get  返回ResponseResult数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void getResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        apiService.executeGet(url, params, params)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);

    }

    /**
     * Api通用post  返回ResponseResult数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void postResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        apiService.executePost(url, params, body)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }

    /**
     * Api通用put  返回ResponseResult数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void putResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        apiService.executePut(url, params, params)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }


    public void getMobileCode(HashMap<String, String> params, ACallback<ResponseResult<String>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<String>>(callback);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        apiService.getMobileCode(params, body)
                .compose(ApiTransformer.<ResponseResult<String>>norTransformer())
                .subscribe(disposableObserver);
    }


    public void getWeather(String url, Observer<String> observer) {
        apiService.getWeather(url).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }


    /**
     * 获取第一级type
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }

    /**
     * 获取次一级type(如果有)
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getSubType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            if (type instanceof ParameterizedType) {
                finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                finalNeedType = type;
            }
        }
        return finalNeedType;
    }


}
