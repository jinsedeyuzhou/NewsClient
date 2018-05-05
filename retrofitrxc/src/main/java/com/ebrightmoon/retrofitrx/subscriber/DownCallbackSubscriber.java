package com.ebrightmoon.retrofitrx.subscriber;


import com.ebrightmoon.retrofitrx.callback.ACallback;

public class DownCallbackSubscriber<T> extends ApiCallbackSubscriber<T> {
    public DownCallbackSubscriber(ACallback<T> callBack) {
        super(callBack);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        callBack.onSuccess(super.data);
    }
}
