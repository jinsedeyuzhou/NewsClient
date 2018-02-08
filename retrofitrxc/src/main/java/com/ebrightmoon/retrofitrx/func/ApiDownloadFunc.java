package com.ebrightmoon.retrofitrx.func;

import android.content.Context;
import android.os.Environment;

import com.ebrightmoon.retrofitrx.mode.DownProgress;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

import static com.ebrightmoon.retrofitrx.common.HttpUtils.dirName;
import static com.ebrightmoon.retrofitrx.common.HttpUtils.fileName;

/**
 * Created by wyy on 2018/2/8.
 */

public class ApiDownloadFunc implements Function<ResponseBody, Publisher<DownProgress>> {
    private String rootName;
    private Context mContext;

    public ApiDownloadFunc(Context mContext) {
        this.mContext = mContext;
        this.rootName = getDiskCachePath(mContext);
    }

    @Override
    public Publisher<DownProgress> apply(final ResponseBody responseBody) throws Exception {
        return Flowable.create(new FlowableOnSubscribe<DownProgress>() {
            @Override
            public void subscribe(FlowableEmitter<DownProgress> subscriber) throws Exception {
                File dir = getDiskCacheDir(rootName, dirName);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir.getPath() + File.separator + fileName);
                saveFile(subscriber, file, responseBody);
            }
        }, BackpressureStrategy.LATEST);
    }

    /**
     * @param sub
     * @param saveFile
     * @param resp
     */
    private void saveFile(FlowableEmitter<? super DownProgress> sub, File saveFile, ResponseBody resp) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            try {
                int readLen;
                int downloadSize = 0;
                byte[] buffer = new byte[8192];

                DownProgress downProgress = new DownProgress();
                inputStream = resp.byteStream();
                outputStream = new FileOutputStream(saveFile);

                long contentLength = resp.contentLength();
                downProgress.setTotalSize(contentLength);

                while ((readLen = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, readLen);
                    downloadSize += readLen;
                    downProgress.setDownloadSize(downloadSize);
                    sub.onNext(downProgress);
                }
                outputStream.flush();
                sub.onComplete();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (resp != null) {
                    resp.close();
                }
            }
        } catch (IOException e) {
            sub.onError(e);
        }
    }

    /**
     * @param rootName
     * @param dirName
     * @return
     */
    private File getDiskCacheDir(String rootName, String dirName) {
        return new File(rootName + File.separator + dirName);
    }

    /**
     * @param context
     * @return
     */
    private String getDiskCachePath(Context context) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
