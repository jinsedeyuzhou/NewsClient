package com.study.newsclient.pages.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.newsclient.R;
import com.study.newsclient.adapter.NewsChannelAdapter;
import com.study.newsclient.adapter.label.OnItemDragListener;
import com.study.newsclient.adapter.rv.OnEditCompleteListener;
import com.study.newsclient.base.BaseFragment;
import com.study.newsclient.bean.Channel;
import com.study.newsclient.database.BaseDao;
import com.study.newsclient.listener.OnChannelListener;
import com.study.newsclient.view.CustomViewPager;
import com.yuxuan.common.util.DensityUtils;
import com.yuxuan.common.widget.colortrackview.ColorTrackTabLayout;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by wyy on 2016/9/12.
 */
public class DiscoveryFragment extends BaseFragment  {

    private ColorTrackTabLayout mColorTabLayout;
    private ImageView mCategory;
    private CustomViewPager mViewPager;
    private ArrayList<BaseFragment> mFragments;
    public ArrayList<Channel> mSelectedDatas ;
    public ArrayList<Channel> mUnSelectedDatas;
    private NewsChannelAdapter newsChannelAdapter;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initView(View view) {
        mColorTabLayout = (ColorTrackTabLayout) view.findViewById(R.id.color_tablayout);
        mCategory = (ImageView) view.findViewById(R.id.iv_category);
        mViewPager = (CustomViewPager) view.findViewById(R.id.custom_viewpager);
    }

    @Override
    protected void bindEvent() {
        mCategory.setOnClickListener(this);

    }




    @Override
    protected void initData(Bundle savedInstanceState) {
        //不能在类初始化的时候新建数组对象

        mSelectedDatas = new ArrayList<>();
        mUnSelectedDatas=new ArrayList<>();
        try {
            BaseDao<Channel,Integer> mChannel=new BaseDao<>(mContext.getApplicationContext(),Channel.class);
            mSelectedDatas= (ArrayList<Channel>) mChannel.query("selected","1");
            mUnSelectedDatas= (ArrayList<Channel>) mChannel.query("selected","0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        mSelectedDatas.add(new Channel(1,"新闻",1,20));
//        mSelectedDatas.add(new Channel(2,"视频",2,20));
//        mSelectedDatas.add(new Channel(3,"热点",3,20));
//        mSelectedDatas.add(new Channel(4,"体育",4,20));
//        mSelectedDatas.add(new Channel(5,"文化",5,20));
//        mSelectedDatas.add(new Channel(6,"多媒体",6,20));
        mFragments = new ArrayList<>();
        for (int i=0;i<mSelectedDatas.size();i++)
        {
            NewsFragment fragment = NewsFragment.newInstance(mSelectedDatas.get(i).getId());
            mFragments.add(fragment);
        }

        newsChannelAdapter = new NewsChannelAdapter(getContext(),getChildFragmentManager(),mFragments,mSelectedDatas);
        mViewPager.setAdapter(newsChannelAdapter);
        mViewPager.setScrollable(true);
        mViewPager.setOffscreenPageLimit(mSelectedDatas.size());
        mColorTabLayout.setTabPaddingLeftAndRight(DensityUtils.dp2px(getContext(),10), DensityUtils.dp2px(getContext(),10));
        mColorTabLayout.setupWithViewPager(mViewPager);
        mColorTabLayout.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) mColorTabLayout.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + mCategory.getMeasuredWidth());
            }
        });
        //隐藏指示器
        mColorTabLayout.setSelectedTabIndicatorHeight(0);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void processClick(View v) {
        switch(v.getId())
        {
            case R.id.iv_category:
                ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedDatas, mUnSelectedDatas);
                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
                dialogFragment.setOnEditCompleteListener(new OnEditCompleteListener() {
                    @Override
                    public void onEditFinish(ArrayList<Channel> selectedLabels, ArrayList<Channel> unselectedLabel, ArrayList<Channel> alwaySelectedLabels) {
                        Log.v("","");
                    }
                });
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                break;
            default:
                break;
        }

    }


}
