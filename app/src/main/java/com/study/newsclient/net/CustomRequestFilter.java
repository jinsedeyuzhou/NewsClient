package com.study.newsclient.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by wyy on 2017/5/14.
 */

public class CustomRequestFilter implements RequestQueue.RequestFilter {
    @Override
    public boolean apply(Request<?> request) {
        return false;
    }
}
