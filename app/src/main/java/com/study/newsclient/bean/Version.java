package com.study.newsclient.bean;


import java.io.Serializable;

/**
 * Created by wyy on 2017/4/24.
 */

public class Version implements Serializable {
    private static final long serialVersionUID = 1L;

    //应用程序名称
    private String appName;
    //apk名称
    private String apkName;
    //版本号
    private int versionCode;
    //版本名称
    private String versionName;
    //下载地址
    private String downloadURL;
    //显示信息
    private String displayMessage;
    //类型
    private int type;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
