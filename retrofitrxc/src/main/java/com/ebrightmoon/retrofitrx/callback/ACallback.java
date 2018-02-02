package com.ebrightmoon.retrofitrx.callback;

/**
 * @Description: 请求接口回调
 */
public abstract class ACallback<T> {
    public abstract void onSuccess(T data);
    public abstract void onFail(int errCode, String errMsg);
}
