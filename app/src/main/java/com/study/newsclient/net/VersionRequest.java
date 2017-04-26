package com.study.newsclient.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.yuxuan.common.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyy on 2017/4/24.
 */

public class VersionRequest<T> extends GsonRequest<T> {
    private final static String TAG = VersionRequest.class.getSimpleName();
    private HashMap mParams, mHeader;

    public VersionRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(url, clazz, listener, errorListener);
    }

    public VersionRequest(String url, Type typeToken, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(url, typeToken, listener, errorListener);
    }


    @Override
    protected String checkJsonData(String data, NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String code = jsonObject.optString("code", "");
            LogUtils.e(TAG, "code = " + code);
            if ("200".equals(code)) {
                return jsonObject.optString("data", "");
            } else if ("202".equals(code)) {
                return "202";
            } else {
                return "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    public void setRequestParams(HashMap params) {
        this.mParams = params;
    }
}
