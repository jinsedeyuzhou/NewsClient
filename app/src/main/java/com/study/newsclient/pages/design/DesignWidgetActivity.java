package com.study.newsclient.pages.design;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.study.newsclient.R;
import com.study.newsclient.banner.LocalImageHolderView;
import com.study.newsclient.base.BaseActivity;
import com.yuxuan.common.viewpagertransforms.CubeOutTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 * //部分3D特效需要调整滑动速度
 * if(transforemerName.equals("StackTransformer")){
 * convenientBanner.setScrollDuration(1200);
 * }
 */

public class DesignWidgetActivity extends BaseActivity {
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    private AppBarLayout mAppBarLayout;
    private View mLine;
    private ConvenientBanner mConvenientBanner;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_design);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));

        mAppBarLayout = (AppBarLayout) findViewById(R.id.jianshu_appbar_layout);
        mLine = findViewById(R.id.line_divider);
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.banner);

        mRecyclerView = (RecyclerView) findViewById(R.id.vertical_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        MyAdapter myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setData(mockData());
        myAdapter.notifyDataSetChanged();

//        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//            @Override
//            public NetworkImageHolderView createHolder() {
//                return new NetworkImageHolderView();
//            }
//        }, Arrays.asList(images));

        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages) //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
        ;
        mConvenientBanner.getViewPager().setPageTransformer(true, new CubeOutTransformer());
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= mAppBarLayout.getTotalScrollRange()) {
                    mLine.setVisibility(View.VISIBLE);
                } else {
                    mLine.setVisibility(View.GONE);
                }
            }
        });
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        mConvenientBanner.stopTurning();
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void processClick(View paramView) {

    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 模拟首页数据
     *
     * @return
     */
    private List<JsEntry> mockData() {
        List<JsEntry> data = new ArrayList<>();
        JsEntry jsEntry = new JsEntry();
        jsEntry.comment = 50;
        jsEntry.award = 3;
        jsEntry.like = 460;
        jsEntry.seek = 12504;
        jsEntry.time = "15小时前";
        jsEntry.title = "这些情商的技巧，你是不是都掌握了？";
        jsEntry.authorName = "JayChou";
        jsEntry.label = "心理";
        jsEntry.cover = "http://upload-images.jianshu.io/upload_images/2785318-5306a632b46a8c27.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1020/q/80";
        JsEntry jsEntry2 = new JsEntry();
        jsEntry2.comment = 150;
        jsEntry2.award = 33;
        jsEntry2.like = 1460;
        jsEntry2.seek = 170444;
        jsEntry2.time = "10小时前";
        jsEntry2.title = "除了阴谋，《锦绣未央》里还有哪些温情？";
        jsEntry2.authorName = "菇凉似梦";
        jsEntry2.label = "文化.艺术";
        jsEntry2.cover = "http://upload-images.jianshu.io/upload_images/2881988-b217e714eb05f88e.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1020/q/80";
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                data.add(jsEntry);
            } else {
                data.add(jsEntry2);
            }
        }
        return data;
    }


    public static class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
//            ImageLoader.getInstance().displayImage(data,imageView);


        }
    }

    public static class MyAdapter extends RecyclerView.Adapter {
        private List<JsEntry> mData;

        public void setData(List<JsEntry> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jianshu_label_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            JsEntry jsEntry = mData.get(position);
            viewHolder.title.setText(jsEntry.title);
            viewHolder.name.setText(jsEntry.authorName);
            viewHolder.label.setText(jsEntry.label);
            viewHolder.time.setText(jsEntry.time);
//            ImageLoader.getInstance().displayImage(jsEntry.cover,viewHolder.cover);
            viewHolder.comment.setText(String.format(viewHolder.comment.getContext().getResources().getString(R.string.js_comment), jsEntry.seek, jsEntry.comment, jsEntry.like, jsEntry.award));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView time;
        private TextView comment;
        private TextView label;
        private TextView name;
        private ImageView cover;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_content);
            time = (TextView) itemView.findViewById(R.id.publish_time);
            comment = (TextView) itemView.findViewById(R.id.js_comment);
            label = (TextView) itemView.findViewById(R.id.js_label);
            name = (TextView) itemView.findViewById(R.id.author_name);
            cover = (ImageView) itemView.findViewById(R.id.cover);
        }
    }

    public class JsEntry {
        public String title;
        public String authorName;
        public String time;
        public String label;
        public int seek;
        public int comment;
        public int like;
        public int award;
        public String cover;
    }

}
