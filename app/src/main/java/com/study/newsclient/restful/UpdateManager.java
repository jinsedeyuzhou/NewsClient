package com.study.newsclient.restful;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.study.newsclient.restful.service.UpdataService;
import com.study.newsclient.view.CustomDialog;

/**
 * Created by wyy on 2017/4/25.
 */

public class UpdateManager {

    /**
     * 自定义升级弹窗
     */
    protected void showUpdateDialogT(final Context mContext) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setTitle("发现新版本");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(mContext, UpdataService.class);
                mContext.startService(intent);
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.create().show();

    }
}
