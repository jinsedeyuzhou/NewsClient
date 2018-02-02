package com.ebrightmoon.retrofitrx.retrofit;



import com.ebrightmoon.retrofitrx.response.ResponseResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by wyy on 2017/9/6.
 */

public interface ApiService {

    /**
     * 通用get请求
     * @param url
     * @param maps
     * @return
     */
    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * post 请求
     * @param url
     * @param requestBody
     * @return
     */
    @POST()
    Observable<ResponseBody> post(@Url() String url, @Body RequestBody requestBody);

    /**
     * post表单提交
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> post(@Url() String url, @FieldMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(@Url() String url, @FieldMap Map<String, Object> maps);

    /**
     * @param url
     * @param requestBody
     * @return
     */
    @POST()
    Observable<ResponseBody> postBody(@Url() String url, @Body RequestBody requestBody);

    /**
     * @param url
     * @param maps
     * @return
     */
    @HEAD()
    Observable<ResponseBody> head(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @OPTIONS()
    Observable<ResponseBody> options(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @PUT()
    Observable<ResponseBody> put(@Url() String url, @FieldMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @PATCH()
    Observable<ResponseBody> patch(@Url() String url, @FieldMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @DELETE()
    Observable<ResponseBody> delete(@Url() String url, @FieldMap Map<String, String> maps);

    /**
     * @param url
     * @param maps
     * @return
     */
    @Streaming
    @GET()
    Observable<ResponseBody> downFile(@Url() String url, @QueryMap Map<String, String> maps);

    /**
     * @param url
     * @param parts
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url() String url, @Part() List<MultipartBody.Part> parts);

    //====================================以下为自定义请求=============================================

    /**
     * 通用get请求
     * @param url
     * @param maps
     * @return
     */
    @GET()
    Observable<ResponseBody> executeGet(@Url String url,@HeaderMap Map<String,String> headers, @QueryMap Map<String, String> maps);


    /**
     * post 请求
     * @param url
     * @param requestBody
     * @return
     */
    @POST()
    Observable<ResponseBody> executePost(@Url() String url,@HeaderMap Map<String,String> headers,  @Body RequestBody requestBody);
    /**
     * @param headers
     * @param params
     * @return
     */


    /**
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @PUT()
    Observable<ResponseBody> executePut(@Url() String url,@HeaderMap Map<String,String> header, @FieldMap Map<String, String> maps);

    //局部设置header
    @POST("api/v1.0/Mobile/SendMobileCode")
    Observable<ResponseResult<String>> getMobileCode(@HeaderMap Map<String, String> headers, @Body RequestBody params);


    @GET()
    Observable<String> getWeather(@Url String url);






}
