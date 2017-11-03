package com.study.newsclient.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.study.newsclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 * ArrayList  cancelReasons = new ArrayList<>();
 * cancelReasons.add("报价信息异常，不能出单");
 * cancelReasons.add("客户无意愿出单");
 * cancelReasons.add("想更换投保公司");
 * cancelReasons.add("其它");
 * cancelReasons.add("取消");
 * CustomPopWindow customPop = new CustomPopWindow(this);
 * <p>
 * customPop.setData(cancelReasons)
 * .setPopupWindow()
 * .setOnItemClickListener(new AdapterView.OnItemClickListener() {
 *
 * @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 * if (!cancelReasons.get(position).equals("取消")) {
 * cancelOrderRequest(cancelReasons.get(position));
 * finish();
 * }
 * customPop.dismiss();
 * <p>
 * }
 * }).openPopWindow();
 */

public class CustomPopWindow extends PopupWindow {
    private Context mContext;
    private View mPopView;
    private GridView mGridViewPop;
    private List<String> strs = new ArrayList<>();
    private PopAdapter popAdapter;
    private AdapterView.OnItemClickListener onItemClickListeners;

    public CustomPopWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * 初始化布局
     */
    private void init() {
        mPopView = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_pop, null);
        mGridViewPop = (GridView) mPopView.findViewById(R.id.gv_pop);
        popAdapter = new PopAdapter();
        mGridViewPop.setAdapter(popAdapter);
        mGridViewPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(strs.get(position), position);
                    dismiss();
                }
            }
        });


    }

    public CustomPopWindow setGridView(int numColumns) {
        mGridViewPop.setNumColumns(numColumns);
        return this;
    }

    /**
     * 初始化popwindow
     */
    public CustomPopWindow setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        //点击外部消失
        this.setOutsideTouchable(true);
        //设置覆盖全屏，包括状态栏
        this.setClippingEnabled(false);
        this.setAnimationStyle(R.style.AnimBottom);// 设置动画
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x88000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);// 设置背景透明
        return this;
    }


    public CustomPopWindow setData(List<String> strs) {
        this.strs = strs;
        popAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 按钮的监听
     *
     * @param
     */
    public CustomPopWindow openPopWindow() {
        //从底部显示
        showAtLocation(mPopView, Gravity.BOTTOM, 0, 0);
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListeners
     * @return
     */
    public CustomPopWindow setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListeners) {
        if (onItemClickListeners != null) {
            mGridViewPop.setOnItemClickListener(onItemClickListeners);
            dismiss();
        }
        return this;
    }

    class PopAdapter extends BaseAdapter {

        private TextView mTvButton;

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int position) {
            return strs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.item_gridview, null);
                mTvButton = (TextView) convertView.findViewById(R.id.tv_custom_popwindow);
                convertView.setTag(mTvButton);
            } else {
                mTvButton = (TextView) convertView.getTag();
            }
            mTvButton.setText(strs.get(position));
            return convertView;
        }
    }

    private OnItemClickListener onItemClickListener;

    public CustomPopWindow setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(String title, int position);
    }
}
