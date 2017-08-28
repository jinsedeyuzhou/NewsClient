package com.android.volley.toolbox;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.apache.OkApacheClient;

import org.apache.http.client.HttpClient;


/**
 * Created by wyy on 2017/4/19.
 */

public class HttpClientOkHttpStack extends HttpClientStack {
    public HttpClientOkHttpStack(OkHttpClient client) {
        super(new OkApacheClient(client));
    }
}
