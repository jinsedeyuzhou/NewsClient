package com.study.newsclient.view;

import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.newsclient.R;


/**
 * 自定义弹出对话框
 * @author wyy
 *
 */
public class CustomToast extends Toast {

	private TextView textView;
	private String text;
	private View view;
	private int time;
	private Context context;

	public CustomToast(Context context, String text, int time) {
		super(context);
		this.context = context;
		this.text = text;
		this.time = time;
		init();
	}

	private void init() {
		view = View.inflate(context, R.layout.custom_toast, null);
		setView(view);
		textView = (TextView) view.findViewById(R.id.textView);
		textView.setText(text);
		setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		setDuration(time);
	}


	public static CustomToast makeText(Context context, CharSequence text, @BaseTransientBottomBar.Duration int duration) {
		CustomToast result = new CustomToast(context,text.toString(),duration);
		return result;
	}

}
