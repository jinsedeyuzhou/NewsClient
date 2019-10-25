package com.ebrightmoon.doraemonkit.kit.network.stream;

import com.ebrightmoon.doraemonkit.kit.network.NetworkManager;
import com.ebrightmoon.doraemonkit.kit.network.bean.NetworkRecord;
import com.ebrightmoon.doraemonkit.kit.network.core.NetworkInterpreter;
import com.ebrightmoon.doraemonkit.kit.network.core.RequestBodyHelper;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: linjizong
 * @date: 2019/3/14
 * @desc:
 */
public class HttpOutputStreamProxy extends OutputStreamProxy {
    private final int mRequestId;
    private final NetworkInterpreter mInterpreter;

    public HttpOutputStreamProxy(OutputStream out,int requestId, NetworkInterpreter interpreter) {
        super(out);
        mRequestId = requestId;
        mInterpreter = interpreter;
    }

    @Override
    protected  void onStreamComplete() throws IOException {
        NetworkRecord record = NetworkManager.get().getRecord(mRequestId);
        if (record != null && record.mRequest != null) {
            RequestBodyHelper requestBodyHelper = new RequestBodyHelper();
            try {
                OutputStream out = requestBodyHelper.createBodySink(record.mRequest.encode);
                mOutputStream.writeTo(out);
            } finally {
                out.close();
            }
            byte[] body = requestBodyHelper.getDisplayBody();
            mInterpreter.fetRequestBody(record, body);
        }
    }

}
