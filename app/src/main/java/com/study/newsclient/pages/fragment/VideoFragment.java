package com.study.newsclient.pages.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebrightmoon.retrofitrx.convert.GsonConverterFactory;
import com.ebrightmoon.retrofitrx.retrofit.ApiService;
import com.study.newsclient.R;
import com.study.newsclient.base.BaseFragment;

import java.io.IOException;

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

    private TextView tv_show_network;

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
        tv_show_network = (TextView) view.findViewById(R.id.tv_show_network);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder()
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl("http://www.weather.com.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiStores = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiStores.getWeather("101010100");
//        try {
//            Response<ResponseBody> execute = call.execute();
//            execute.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    tv_show_network.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    protected void processClick(View v) {

    }
}
