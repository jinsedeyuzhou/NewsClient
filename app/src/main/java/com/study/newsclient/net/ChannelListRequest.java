package com.study.newsclient.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.GsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class ChannelListRequest<T> extends GsonRequest<T> {
    private HashMap mParams, mHeader;

    public ChannelListRequest(int method, Map<String, String> params, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, params, url, clazz, listener, errorListener);
    }

    public ChannelListRequest(int method, Map<String, String> params, String url, Type typeToken, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, params, url, typeToken, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public String checkJsonData(NetworkResponse response) {
        String data = null;
        try {
            data = new String(response.data, "utf-8");
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
