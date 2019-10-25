package com.ebrightmoon.doraemonkit;

import android.app.Application;

import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.kit.webdoor.WebDoorManager;

import java.util.List;

/**
 * Created by zhangweida on 2018/6/22.
 */

public class DoraemonKit {

    public static void setWebDoorCallback(WebDoorManager.WebDoorCallback callback) {

    }

    public static void install(final Application app) {
    }

    public static void install(final Application app, List<IKit> selfKits) {

    }

    public static void show() {

    }

    public static void hide() {
    }

    public static boolean isShow() {
        return false;
    }

    public static void disableUpload() {
    }

    public static void enableRequestPermissionSelf() {
    }

    public static void setDebug(boolean debug) {

    }
}