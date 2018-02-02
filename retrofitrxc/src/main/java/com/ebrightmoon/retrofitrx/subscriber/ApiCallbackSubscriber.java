package com.ebrightmoon.retrofitrx.subscriber;


import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.exception.ApiException;

public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    ACallback<T> callBack;
    T data;

    public ApiCallbackSubscriber(ACallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    public void onError(ApiException e) {
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(T t) {
        this.data = t;
        callBack.onSuccess(t);
    }

    @Override
    public void onComplete() {
    }

    public T getData() {
        return data;
    }
}
