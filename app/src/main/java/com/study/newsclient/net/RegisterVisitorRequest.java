package com.study.newsclient.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.study.newsclient.restful.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 注册游客用户
 */
public class RegisterVisitorRequest extends JsonObjectRequest {
    public RegisterVisitorRequest(String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, AppConfig.URL_REGISTER_VISITOR,requestBody, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject data = new JSONObject();
            if ("2000".equals(jsonObject.optString("code"))){
                data = jsonObject.getJSONObject("data");
                data.put("Authorization",response.headers.get("Authorization"));
            }
            return Response.success(data,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}
