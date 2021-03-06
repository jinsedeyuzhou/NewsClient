package com.study.newsclient.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.GsonRequest;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyy on 2017/4/24.
 *  VersionRequest<Version> versionRequest=new VersionRequest<Version>(Request.Method.GET,
     AppConfig.BASE_URL, Version.class, new com.android.volley.Response.Listener<Version>() {
    @Override
    public void onResponse(Version response) {

    }
    }, new com.android.volley.Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {

    }
    });

     BaseApplication.getApp().getRequestQueue().add(versionRequest);
 */

public class VersionRequest<T> extends GsonRequest<T> {
    private final static String TAG = VersionRequest.class.getSimpleName();
    private HashMap mParams, mHeader;

    public VersionRequest(int method,String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method,url, clazz, listener, errorListener);
    }

    public VersionRequest(int method,String url, Type typeToken, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method,url, typeToken, listener, errorListener);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public String checkJsonData(NetworkResponse response) {
        try {
            String data = new String(response.data, "utf-8");
            JSONObject jsonObject = new JSONObject(data);
            String code = jsonObject.optString("code", "");
            if ("2000".equals(code)) {
                return jsonObject.optString("data", "");
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setRequestParams(HashMap params) {
        this.mParams = params;
    }
}
