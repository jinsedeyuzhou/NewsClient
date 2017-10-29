package com.study.newsclient.pages.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.study.newsclient.R;
import com.study.newsclient.base.BaseActivity;
import com.study.newsclient.utils.FileUtils;
import com.study.newsclient.view.CustomToast;
import com.study.newsclient.widget.photoview.PhotoView;
import com.yuxuan.common.util.NetUtils;

import java.util.ArrayList;

/**
 * Created by Berkeley on 7/13/17.
 */

public class PhotoBrowserAty extends BaseActivity {

    private ViewPager mPhotoViewPager;
    private ImageView mPhotoLoading;
    private TextView mCurPage;
    private TextView mTotalPage;
    private ImageView mPhotoDownload;


    private String curImageUrl = "";
    private ArrayList<String> imageUrls = new ArrayList<>();
    private String[] imgUrls = new String[]{};

    private int curPosition = -1;
    private int[] initialedPositions = null;
    private ObjectAnimator objectAnimator;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private CustomToast customToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_browser);
    }

    @Override
    protected void bindEvent() {

    }


    private void showLoadingAnimation() {
        mPhotoLoading.setVisibility(View.VISIBLE);
        mPhotoLoading.setImageResource(R.drawable.ic_loading);
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(mPhotoLoading, "rotation", 0f, 360f);
            objectAnimator.setDuration(2000);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                objectAnimator.setAutoCancel(true);
            }
        }
        objectAnimator.start();
    }

    private void hideLoadingAnimation() {
        releaseResource();
        mPhotoLoading.setVisibility(View.GONE);
    }

    private void showErrorLoading() {
        mPhotoLoading.setVisibility(View.VISIBLE);
        releaseResource();
        mPhotoLoading.setImageResource(R.drawable.ic_load_error);
    }

    private void showError() {
        mPhotoLoading.setVisibility(View.VISIBLE);
        mPhotoLoading.setImageResource(R.drawable.ic_load_error);
    }

    private int returnClickedPosition() {
        if (imageUrls == null || curImageUrl == null) {
            return -1;
        }
        for (int i = 0; i < imageUrls.size(); i++) {
            if (curImageUrl.equals(imageUrls.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private void releaseResource() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (mPhotoLoading.getAnimation() != null) {
            mPhotoLoading.getAnimation().cancel();
        }
    }

    private void occupyOnePosition(int position) {
        initialedPositions[position] = position;
    }

    private void releaseOnePosition(int position) {
        initialedPositions[position] = -1;
    }

    private void initInitialedPositions() {
        for (int i = 0; i < initialedPositions.length; i++) {
            initialedPositions[i] = -1;
        }
    }

    private void savePhotoToLocal() {
        ViewGroup containerTemp = (ViewGroup) mPhotoViewPager.findViewWithTag(mPhotoViewPager.getCurrentItem());
        if (containerTemp == null) {
            return;
        }
        PhotoView photoViewTemp = (PhotoView) containerTemp.getChildAt(0);
        if (photoViewTemp != null) {
            GlideBitmapDrawable glideBitmapDrawable = (GlideBitmapDrawable) photoViewTemp.getDrawable();
            if (glideBitmapDrawable == null) {
                return;
            }
            Bitmap bitmap = glideBitmapDrawable.getBitmap();
            if (bitmap == null) {
                return;
            }
            FileUtils.savePhoto(this, bitmap, new FileUtils.SaveResultCallback() {
                @Override
                public void onSavedSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PhotoBrowserAty.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onSavedFailed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PhotoBrowserAty.this, "保存失败", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        imgUrls = getIntent().getStringArrayExtra("imgUrls");
        curImageUrl = getIntent().getStringExtra("curImageUrl");
        if (imageUrls == null || imageUrls.isEmpty()) {
            imageUrls = new ArrayList<>();
            for (int i = 0; i < imgUrls.length; i++) {
                imageUrls.add(imgUrls[i]);
            }
        }
        initialedPositions = new int[imageUrls.size()];
        initInitialedPositions();

        mPhotoViewPager = (ViewPager) findViewById(R.id.photo_viewpager);
        mPhotoLoading = (ImageView) findViewById(R.id.photo_loading);
        mCurPage = (TextView) findViewById(R.id.tv_cur_page);
        mTotalPage = (TextView) findViewById(R.id.tv_total_page);
        mPhotoDownload = (ImageView) findViewById(R.id.iv_photo_download);
        mPhotoDownload.setOnClickListener(this);
        mPhotoViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageUrls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                if (imageUrls.get(position) != null && !"".equals(imageUrls.get(position))) {
                    final PhotoView view = new PhotoView(PhotoBrowserAty.this);
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    if (NetUtils.isConnected(PhotoBrowserAty.this)) {
                        Glide.with(PhotoBrowserAty.this).load(imageUrls.get(position)).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                if (position == curPosition) {
                                    hideLoadingAnimation();
                                }
                                showErrorLoading();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                occupyOnePosition(position);
                                if (position == curPosition) {
                                    hideLoadingAnimation();
                                }
                                return false;
                            }
                        }).into(view);
                    } else {
                        showError();
                    }


                    container.addView(view);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PhotoBrowserAty.this.finish();
                        }
                    });
                    return view;
                }
                return null;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                releaseOnePosition(position);
                container.removeView((View) object);
            }
        });


        curPosition = returnClickedPosition() == -1 ? 0 : returnClickedPosition();
        mPhotoViewPager.setCurrentItem(curPosition);
        mPhotoViewPager.setTag(curPosition);
        if (initialedPositions[curPosition] != curPosition && NetUtils.isConnected(this)) {//如果当前页面未加载完毕，则显示加载动画，反之相反；
            showLoadingAnimation();
        }
        mCurPage.setText(curPosition + 1 + "");//设置页面的编号
        mTotalPage.setText(" / " + imageUrls.size());
        mPhotoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!NetUtils.isConnected(PhotoBrowserAty.this)) {
                    showError();
                } else if (initialedPositions[position] != position) {//如果当前页面未加载完毕，则显示加载动画，反之相反；
                    showLoadingAnimation();
                } else {
                    hideLoadingAnimation();
                }
                curPosition = position;
                mCurPage.setText(position + 1 + "");//设置页面的编号
                mPhotoViewPager.setTag(position);//为当前view设置tag

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        releaseResource();
        if (mPhotoViewPager != null) {
            mPhotoViewPager.removeAllViews();
            mPhotoViewPager = null;
        }
        super.onDestroy();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(PhotoBrowserAty.this).clearDiskCache();//清理磁盘缓存需要在子线程中执行
            }
        }).start();
        Glide.get(this).clearMemory();//清理内存缓存可以在UI主线程中进行
    }

    @Override
    public void processClick(View paramView) {
        int id = paramView.getId();
        if (id == R.id.iv_photo_download) {
            try {
                //检测是否有写的权限
                int permission = ActivityCompat.checkSelfPermission(this,
                        "android.permission.WRITE_EXTERNAL_STORAGE");
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                } else {
                    savePhotoToLocal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

}
