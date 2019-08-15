package com.study.newsclient.adapter.sidebar;

import android.content.Context;
import android.widget.CheckBox;


import com.study.newsclient.R;
import com.study.newsclient.entity.ContactInfo;
import com.yuxuan.common.adapter.recycler.absrecyclerview.CommonAdapter;
import com.yuxuan.common.adapter.recycler.absrecyclerview.ViewHolder;

import java.util.List;


/**
 * Created by wyy
 * 选择分配人员适配器
 */

public class AllocationAdapter extends CommonAdapter<ContactInfo> {

    public AllocationAdapter(Context context, int layoutId, List<ContactInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ContactInfo contactInfo, int position) {
        CheckBox cbAssign = holder.getView(R.id.cbAssign);
        cbAssign.setText(contactInfo.getFrealname());
        cbAssign.setChecked(contactInfo.isChecked());
    }


}
