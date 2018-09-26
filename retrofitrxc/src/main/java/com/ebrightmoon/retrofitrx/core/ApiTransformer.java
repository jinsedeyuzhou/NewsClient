package com.ebrightmoon.retrofitrx.core;



import android.support.annotation.NonNull;

import com.ebrightmoon.retrofitrx.common.HttpUtils;
import com.ebrightmoon.retrofitrx.func.ApiDataFunc;
import com.ebrightmoon.retrofitrx.func.ApiFunc;
import com.ebrightmoon.retrofitrx.func.ApiResultFunc;
import com.ebrightmoon.retrofitrx.func.ApiRetryFunc;
import com.ebrightmoon.retrofitrx.recycle.ActivityLifeCycleEvent;
import com.ebrightmoon.retrofitrx.recycle.RecycleBaseActivity;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.temp.AndroidScheduler;

import org.reactivestreams.Publisher;

import java.io.File;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * 根据不同返回数据要求定制相关转换方法
 */
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
                        .observeOn(AndroidScheduler.mainThread())
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
                        .observeOn(AndroidScheduler.mainThread())
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }

    /**
     * 未测试暂时不要用
     * @param retryCount
     * @param retryDelayMillis
     * @param event
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> norTransformer(final int retryCount, final int retryDelayMillis,final ActivityLifeCycleEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> apiResultObservable) {
                ObservableSource<ActivityLifeCycleEvent> compareLifecycleObservable =
                        RecycleBaseActivity.lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
                            @Override
                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) throws Exception {
                                return activityLifeCycleEvent.equals(event);
                            }
                        }).firstElement().toObservable().publish();
                return apiResultObservable
                        .takeUntil(compareLifecycleObservable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidScheduler.mainThread())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }


    public static  <T> ObservableTransformer<ResponseBody, T> Transformer(final Type type) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidScheduler.mainThread())
                        .map(new ApiFunc<T>(type))
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }



    public static <T> ObservableTransformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        RecycleBaseActivity.lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
                            @Override
                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) throws Exception {
                                return activityLifeCycleEvent.equals(event);
                            }
                        }).firstElement().toObservable().publish();


                return upstream.takeUntil(compareLifecycleObservable);
            }


        };
    }


    public static  <T> ObservableTransformer<ResponseBody, ResponseResult<T>> RRTransformer(final Type type) {
        return new ObservableTransformer<ResponseBody, ResponseResult<T>>() {
            @Override
            public ObservableSource<ResponseResult<T>> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidScheduler.mainThread())
                        .map(new ApiResultFunc<T>(type))
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };
    }



    public  static  <T> ObservableTransformer<T,T> downTransformer(Type type) {
        return new ObservableTransformer<T,T>(){

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
              return   upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .toFlowable(BackpressureStrategy.LATEST)
                        .sample(1, TimeUnit.SECONDS)
                        .observeOn(AndroidScheduler.mainThread())
                        .toObservable()
                        .retryWhen(new ApiRetryFunc(HttpUtils.DEFAULT_RETRY_COUNT,
                                HttpUtils.DEFAULT_RETRY_DELAY_MILLIS));
            }
        };

    }









}
