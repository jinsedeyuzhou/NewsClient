package com.ebrightmoon.retrofitrx.retrofit;


import com.ebrightmoon.retrofitrx.response.ResponseCode;
import com.ebrightmoon.retrofitrx.response.ResponseResult;
import com.ebrightmoon.retrofitrx.retrofit.ApiException;

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
