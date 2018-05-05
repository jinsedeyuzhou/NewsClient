package com.study.newsclient.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.newsclient.R;


/**
 * 自定义弹出对话框
 *
 * @author wyy
 */
public class CustomToast extends Toast {

    private TextView textView;
    private static String mText = "";
    private View view;
    private int time;
    private Context context;
    private static CustomToast result;

    public CustomToast(Context context, String text, int time) {
        super(context);
        this.context = context;
        this.mText = text;
        this.time = time;
        init();
    }

    private void init() {
        view = View.inflate(context, R.layout.custom_toast, null);
        setView(view);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(mText);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(time);
    }


    public static CustomToast makeText(Context context, CharSequence text, int duration) {
        if (result == null || !mText.equals(text)) {
            result = new CustomToast(context, text.toString(), duration);
        }
        return result;
    }
}
