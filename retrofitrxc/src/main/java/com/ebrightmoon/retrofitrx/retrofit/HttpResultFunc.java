package com.ebrightmoon.retrofitrx.retrofit;



import com.study.newsclient.https.response.ResponseCode;
import com.study.newsclient.https.response.ResponseResult;

import io.reactivex.functions.Function;

/**
 * Created by wyy on 2017/9/12.
 */

public class HttpResultFunc<T> implements Function<ResponseResult<T>,T> {
    @Override
    public T apply(ResponseResult<T> tResponseResult) throws Exception {
        if (tResponseResult.getCode()!= ResponseCode.HTTP_SUCCESS)
        {
            throw  new ApiException(tResponseResult.getCode());
        }

        return tResponseResult.getData();
    }
}
