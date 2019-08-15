package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.ebrightmoon.retrofitrx.callback.ACallback;
import com.ebrightmoon.retrofitrx.callback.UCallback;
import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.mode.DownProgress;
import com.ebrightmoon.retrofitrx.retrofit.ApiService;
import com.ebrightmoon.retrofitrx.retrofit.AppClient;
import com.study.newsclient.R;
import com.study.newsclient.base.AppConfig;
import com.study.newsclient.base.BaseApplication;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.Version;
import com.study.newsclient.net.VersionRequest;
import com.study.newsclient.restful.manager.RequestQueueManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by wyy on 2017/9/4.
 */

public class VideoFragment extends BaseFragment {

    private Button tv_show_network;
    private HashMap<String, String> params;
    private ProgressBar mPbar;
    private TextView mTvShowPercent;

    public  static VideoFragment newInstance()
    {
        VideoFragment videoFragment=new VideoFragment();
         Bundle bundle=new Bundle();
        videoFragment.setArguments(bundle);

        return videoFragment;
    }


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {
//        mPbar = view.findViewById(R.id.pb_bar);
//        mTvShowPercent = view.findViewById(R.id.tv_show_percent);
//        tv_show_network = (Button) view.findViewById(R.id.tv_show_network);
//        params = new HashMap<>();
//        tv_show_network.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AppClient.getInstance(mContext,"http://p73r3bamp.bkt.clouddn.com/").downloadFile("app-GooglePlay_signed_zipalign04271.apk", params, mContext, new ACallback<DownProgress>() {
//                            @Override
//                            public void onSuccess(DownProgress downProgress) {
//                                mPbar.setProgress((int) (downProgress.getDownloadSize()*1.0/downProgress.getTotalSize()*100));
//                                mPbar.setMax((int) downProgress.getDownloadSize());
//                                mTvShowPercent.setText(downProgress.getPercent());
//                            }
//
//                            @Override
//                            public void onFail(int errCode, String errMsg) {
//
//                            }
//                        }
//                );
//            }
//        });
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

//        Retrofit retrofit = new Retrofit.Builder()
//                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
//                .baseUrl("http://www.weather.com.cn/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        ApiService apiStores = retrofit.create(ApiService.class);
//        try {
//            Response<ResponseBody> execute = call.execute();
//            execute.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Map<String, String> maps = new HashMap<>();
//        maps.put("ip", "21.22.11.33");
//        AppClient.getInstance().getWeather("http://www.weather.com.cn/",new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String value) {
//                tv_show_network.setText(value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        VersionRequest<Version> versionRequest=new VersionRequest<Version>(Request.Method.GET,
                AppConfig.BASE_URL, Version.class, new com.android.volley.Response.Listener<Version>() {
            @Override
            public void onResponse(Version response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueManager.getInstance(mContext).getRequestQueue().add(versionRequest);



    }

    @Override
    protected void processClick(View v) {

    }
}
