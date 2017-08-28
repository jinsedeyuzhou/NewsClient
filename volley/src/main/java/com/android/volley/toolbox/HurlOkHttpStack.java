package com.android.volley.toolbox;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class HurlOkHttpStack extends HurlStack {
    private final OkUrlFactory okUrlFactory;

    public HurlOkHttpStack() {
        this(new OkHttpClient());
    }
    public HurlOkHttpStack(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.okUrlFactory = new OkUrlFactory(okHttpClient);
    }
    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return okUrlFactory.open(url);
    }
}
