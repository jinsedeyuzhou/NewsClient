package com.study.newsclient.restful.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

import java.io.File;

/**
 * Created by wyy on 2017/4/24.
 */

public class UpdataService extends Service {
    public static final String DOWNLOAD_FOLDER_NAME = "qidian";
    public static final String DOWNLOAD_FILE_NAME = "qidian.apk";
    private String downloadLink;
    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;
    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        //在下载之前清空源文件
        File file = getExternalFilesDir(DOWNLOAD_FOLDER_NAME);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles(); //列出当前文件夹下的所有文件
            for (File f : files) {
                f.delete();
            }
        } else {
            file.mkdirs();

        }
    }

    /**
     * 初始化下载器
     **/
    private void initDownManager(String downloadLink) {

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse(downloadLink));

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);

        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);

        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this,
                DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME);

        // 将下载请求放入队列
        manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null)
            downloadLink = intent.getStringExtra("downloadLink");
        // 调用下载
        initDownManager(downloadLink);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //获取下载的文件id
                long downId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                File file = getExternalFilesDir(DOWNLOAD_FOLDER_NAME+File.separator+DOWNLOAD_FILE_NAME);
                //自动安装apk
                //乐视和努比亚等 获取uri 出现路径错误
                if (file.exists()&&file.isFile())
                     installAPK(Uri.fromFile(file));
                //部分手机不可以适用
//                installAPK(manager.getUriForDownloadedFile(downId));
                //停止服务并关闭广播
                UpdataService.this.stopSelf();

            }
        }

        /**
         * 安装apk文件
         */
        private void installAPK(Uri apk) {

            // 通过Intent安装APK文件
            Intent intents = new Intent();
            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
//            intents.setType("application/vnd.android.package-archive");
//            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            android.os.Process.killProcess(android.os.Process.myPid());
            // 如果不加上这句的话在apk安装完成之后点击单开会崩溃

            startActivity(intents);

        }

    }
}
