package com.yuxuan.common.permission;

import android.app.Activity;
import android.os.Build;

import io.reactivex.functions.Consumer;

public class PermissionManager {

    private static PermissionManager permissionManager;
    private Activity activity;

    private PermissionManager() {

    }

    public static PermissionManager instance() {
        if (permissionManager == null) {
            synchronized (PermissionManager.class) {
                if (permissionManager == null) {
                    permissionManager = new PermissionManager();
                }
            }
        }
        return permissionManager;
    }

    public PermissionManager with(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void request(final OnPermissionCallback permissionCallback, final String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.activity != null && permissionCallback != null) {
            RxPermissions rxPermissions = new RxPermissions(this.activity);
            rxPermissions.requestEach(permissions).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        permissionCallback.onRequestAllow(permission.name);
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionCallback.onRequestRefuse(permission.name);
                    } else {
                        permissionCallback.onRequestNoAsk(permission.name);
                    }
                }
            });
        }
    }
}
