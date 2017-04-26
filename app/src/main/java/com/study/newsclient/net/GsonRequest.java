package com.study.newsclient.net;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.ParseError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyy on 2017/4/22.
 */

public class GsonRequest<T> extends Request<T> {
    private Listener<T> mListener;
    private static Gson mGson = new Gson();
    private Class<T> mClass;
    private Map<String, String> mParams;//post Params
    private Type mTypeToken;

    public GsonRequest(int method, String url, ErrorListener listener) {
        super(method, url, listener);
    }

    public GsonRequest(int method, Map<String, String> params, String url, Class<T> clazz, Listener<T> listener,
                       ErrorListener errorListener) {
        this(method, url, errorListener);
        mClass = clazz;
        mListener = listener;
        mParams = params;
        mGson = new Gson();
    }


    public GsonRequest(int method, Map<String, String> params, String url, Type typeToken, Listener<T> listener,
                       ErrorListener errorListener) {
        this(method, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
        mParams = params;
        mGson = new Gson();
    }

    //get
    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, null, url, clazz, listener, errorListener);
    }

    public GsonRequest(String url, Type typeToken, Listener<T> listener, ErrorListener errorListener) {

        this(Method.GET, null, url, typeToken, listener, errorListener);

    }

    public Map<String, String> getmParams() {
        return mParams;
    }

    public void setmParams(HashMap<String, String> mParams) {
        this.mParams = mParams;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return getmParams();
    }

    protected String checkJsonData(String data, NetworkResponse response) {
        return data;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
//            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            T o = mGson.fromJson(jsonString, mTypeToken == null ? mClass : mTypeToken.getType());
//            return Response.success(o, HttpHeaderParser.parseCacheHeaders(response));
            String data = new String(response.data, "utf-8");
            JSONObject jsonRes = new JSONObject(data);
            String code = jsonRes.optString("code");
            if ("2000".equals(code)) {
                data = checkJsonData(data, response);
                T o = mGson.fromJson(data, mTypeToken == null ? mClass : mTypeToken);
                return Response.success(o, HttpHeaderParser.parseCacheHeaders(response));
            } else if ("2002".equals(code)) {
                return Response.error(new VolleyError("服务端未找到数据 2002"));
            } else {
                return Response.error(new VolleyError("获取数据异常!--" + code));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
