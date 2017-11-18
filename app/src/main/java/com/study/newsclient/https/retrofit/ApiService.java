package com.study.newsclient.https.retrofit;

import com.study.newsclient.https.response.ResponseResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
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

    public static final String BASE_URL = "https://client.jcnsh.com/jc_app_new/android/androidEntrance?";

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

}
