package com.ebrightmoon.retrofitrx.retrofit;


import com.ebrightmoon.retrofitrx.response.ResponseResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by wyy on 2017/9/6.
 */

public interface ApiService {

    public static final String BASE_URL = "http://www.weather.com.cn/";



    /**
     * 普通写法
     */
    @GET("service/getIpInfo.php/")
    Observable<ResponseResult> getData(@Query("ip") String ip);


    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);


    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path("url") String url,
            @FieldMap Map<String, String> maps);

    @GET("adat/sk/{cityId}.html")
    Call<ResponseBody> getWeather(@Path("cityId") String cityId);

    @GET("adat/sk/{cityId}.html")
    Observable<ResponseBody> getWeathers(@Path("cityId") String cityId);




}
