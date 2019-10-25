package com.ebrightmoon.doraemonkit.kit.fileexplorer;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ebrightmoon.doraemonkit.R;
import com.ebrightmoon.doraemonkit.constant.BundleKey;
import com.ebrightmoon.doraemonkit.ui.base.BaseFragment;
import com.ebrightmoon.doraemonkit.util.ImageUtil;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by wanglikun on 2018/10/30.
 */

public class ImageDetailFragment extends BaseFragment {
    private static final String TAG = "ImageDetailFragment";
    private ImageView mImageView;
    private File mFile;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = findViewById(R.id.image);
        Bundle data = getArguments();
        if (data != null) {
            mFile = (File) data.getSerializable(BundleKey.FILE_KEY);
        }
        readImage(mFile);
    }

    private void readImage(File file) {
        if (file == null) {
            return;
        }
        ImageReadTask task = new ImageReadTask(this);
        task.execute(file);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.dk_fragment_image_detail;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mImageView.setImageBitmap(null);
    }

    private static class ImageReadTask extends AsyncTask<File, Void, Bitmap> {
        private WeakReference<ImageDetailFragment> mReference;

        public ImageReadTask(ImageDetailFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        protected Bitmap doInBackground(File... files) {
            return ImageUtil.decodeSampledBitmapFromFilePath(files[0].getPath(), 1080, 1920);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mReference.get() != null) {
                mReference.get().mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
