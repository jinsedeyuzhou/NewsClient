package com.ebrightmoon.retrofitrx.retrofit;


import android.content.Context;

import com.ebrightmoon.retrofitrx.body.UploadProgressRequestBody;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.common.AppConfig;
import com.ebrightmoon.retrofitrx.common.GsonUtil;
import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.core.ApiTransformer;
import com.ebrightmoon.retrofitrx.func.ApiDownloadFunc;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.interceptor.HeadersInterceptor;
import com.ebrightmoon.retrofitrx.interceptor.LoggingInterceptor;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.mode.MediaTypes;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.subscriber.ApiCallbackSubscriber;
import com.ebrightmoon.retrofitrx.subscriber.DownCallbackSubscriber;
import com.ebrightmoon.retrofitrx.temp.AndroidScheduler;

import org.json.JSONObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wyy on 2017/9/6.
 */

public class AppClient {

    private List<MultipartBody.Part> multipartBodyParts;
    private Map<String, RequestBody> params;
    private static Context mContext;
    private final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient.Builder okHttpBuilder;
    private Retrofit retrofit;
    private ApiService apiService;
    private Retrofit.Builder retrofitBuilder;
    public static String baseUrl = AppConfig.BASE_URL;

    private AppClient(Context context, String url, Map<String, String> headers) {
        if (context != null)
            mContext = context;
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addNetworkInterceptor(new LoggingInterceptor());

//        headers.put("","");  设置全局header
        if (headers != null)
            okHttpBuilder.addInterceptor(new HeadersInterceptor(headers));

        if (url != null)
            baseUrl = url;


        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofitBuilder = new Retrofit.Builder();

        retrofit = retrofitBuilder
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

    }


    private static AppClient instance;

    public static AppClient getInstance(Context context) {
        if (instance == null) {
            synchronized (AppClient.class) {
                if (instance == null) {
                    instance = new AppClient(context);
                }
            }
        }

        return instance;
    }

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


    public static AppClient getInstance(Context context, String url) {
        if (context == null) {
            mContext = context;
        }
        return instance = new AppClient(context, url);
    }

    public static AppClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context == null) {
            mContext = context;
        }

        return instance = new AppClient(context, url, headers);
    }

    private AppClient() {
        this(null, baseUrl, null);
    }

    private AppClient(Context context) {

        this(context, baseUrl, null);
    }

    private AppClient(Context context, String url) {

        this(context, url, null);
    }

    public ApiService CreateApiService() {
        return apiService = create(ApiService.class);
    }


    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


    /**
     * Get 返回数据  无模型无校验
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void get(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().get(url, params)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }

    /**
     * Post 返回数据  无模型无校验
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void post(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().post(url, body)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }

    /**
     * Json 返回数据  无模型无校验
     *
     * @param url
     * @param jsonObject
     * @param callback
     * @param <T>
     */
    public <T> void json(String url, String jsonObject, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),jsonObject);
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().post(url, body)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }


    /**
     * Api通用Get  返回模型中data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executeGet(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().executeGet(url, params, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

    }

    /**
     * Api通用post  返回模型中T  data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executePost(String url, Map<String, String> params, ACallback<T> callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().executePost(url, params, body)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);
    }

    public <T> T execute(Observable<T> observable, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        observable.compose(ApiTransformer.<T>norTransformer())
                .subscribe(disposableObserver);
        return null;
    }


    /**
     * Api通用put  返回模型中 T data数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void executePut(String url, Map<String, String> params, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().executePut(url, params, params)
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
        CreateApiService().executeGet(url, params, params)
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
        CreateApiService().executePost(url, params, body)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }

    /**
     * Api通用put  返回ResponseResult  数据
     *
     * @param url
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void putResponseResult(String url, Map<String, String> params, ACallback<ResponseResult<T>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<T>>(callback);
        CreateApiService().executePut(url, params, params)
                .compose(ApiTransformer.<ResponseResult<T>>RRTransformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }


    /**
     * 上传文件不带参数校验 返回数据 可以控制文件上传进度
     *
     * @param url
     * @param <T>
     */
    public <T> void uploadFiles(String url, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().uploadFiles(url, multipartBodyParts)
                .compose(ApiTransformer.<T>Transformer(getSubType(callback)))
                .subscribe(disposableObserver);
    }


    /**
     * 上传文件不带参数校验  返回数据模型 ResponseResult中 T数据
     *
     * @param url
     * @param <T>
     *
     *     用例
     *          AppHttpClient.getInstance(mContext, "http://feichetls.91bihu.com/").addFile("android",getUploadFile(mContext,"test.png")).uploadFiles("api/HttpFileUpload/FileUpload",new ACallback<Object>() {
    @Override
    public void onSuccess(Object data) {
    T.showShort(mContext,data.getMessage());
    }

    @Override
    public void onFail(int errCode, String errMsg) {

    }
    });
     */
    public <T> void uploadFilesV(String url, ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().uploadFiles(url, multipartBodyParts)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);

    }


    /**
     * 上传文件 无头文件 返回 返回ResponseResult  数据 不能监控文件上传进度
     *
     * @param url
     * @param <T>
     *     用例
     *       rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
    .subscribe(granted -> {
    if (granted) { // Always true pre-M
    // I can control the camera now
    //手机密码
    Map<String, File> params = new HashMap<>();
    params.put("android", getUploadFile(mContext, "android.png"));
    AppHttpClient.getInstance(mContext, "http://feichetls.91bihu.com/").uploadFiles("api/HttpFileUpload/FileUpload", params, new ACallback<Object>() {
    @Override
    public void onSuccess(Object data) {
    T.showShort(mContext,data.getMessage());
    }

    @Override
    public void onFail(int errCode, String errMsg) {

    }
    });
    //                                AppHttpClient.getInstance().addFile("android",getUploadFile(mContext,"test.png"));


    } else {
    // Oups permission denied
    }
    });
     *
     *
     */
    public <T> void uploadFiles(String url, Map<String, File> files, ACallback<T> callback) {
        params = new HashMap<>();
        for (Map.Entry<String, File> entry : files.entrySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue());
            params.put("file\"; filename=\"" + entry.getValue() + "", requestBody);
        }
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<T>(callback);
        CreateApiService().uploadFiles(url, params)
                .map(new ApiResultFunc<T>(getSubType(callback)))
                .compose(ApiTransformer.<T>apiTransformer())
                .subscribe(disposableObserver);


    }

    /**
     * 下载文件
     *
     * @param url
     * @param params
     */
    public <T> void downloadFile(String url, Map<String, String> params, Context context, ACallback<T> callback) {
        DisposableObserver disposableObserver = new DownCallbackSubscriber(callback);
        CreateApiService().downFile(url, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(new ApiDownloadFunc(context))
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidScheduler.mainThread())
                .toObservable()
                .retryWhen(new ApiRetryFunc(0, 60))
                .subscribe(disposableObserver);


    }


    /**
     * @param params
     * @param callback
     */
    public void getMobileCode(HashMap<String, String> params, ACallback<ResponseResult<String>> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber<ResponseResult<String>>(callback);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), GsonUtil.gson().toJson(params));
        CreateApiService().getMobileCode(params, body)
                .compose(ApiTransformer.<ResponseResult<String>>norTransformer())
                .subscribe(disposableObserver);
    }


    /**
     * @param fileMap
     * @return
     */
    public AppClient addFiles(Map<String, File> fileMap) {
        if (fileMap == null) {
            return this;
        }
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            addFile(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * @param key
     * @param file
     * @return
     */
    public AppClient addFile(String key, File file) {
        return addFile(key, file, null);
    }

    /**
     * 返回ResponseResult  数据
     *
     * @param key
     * @param file
     * @param callback
     * @return
     */
    public AppClient addFile(String key, File file, UCallback callback) {
        if (key == null || file == null) {
            return this;
        }
        if (multipartBodyParts == null) {
            multipartBodyParts = new ArrayList<>();
        }
        RequestBody requestBody = RequestBody.create(MediaTypes.APPLICATION_OCTET_STREAM_TYPE, file);
        if (callback != null) {
            UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, callback);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), uploadProgressRequestBody);
            multipartBodyParts.add(part);
        } else {
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
            multipartBodyParts.add(part);
        }
        return this;
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
