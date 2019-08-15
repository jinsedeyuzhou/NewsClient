package com.study.newsclient.widget.sidebar;


import com.study.newsclient.widget.suspension.ISuspensionInterface;

import java.io.Serializable;

/**
 * 介绍：索引类的标志位的实体基类
 */

public abstract class BaseSideBean implements ISuspensionInterface, Serializable {
    private String baseIndexTag;//所属的分类（城市的汉语拼音首字母）

    public String getBaseIndexTag() {
        return baseIndexTag;
    }

    public BaseSideBean setBaseIndexTag(String baseIndexTag) {
        this.baseIndexTag = baseIndexTag;
        return this;
    }

    @Override
    public String getSuspensionTag() {
        return baseIndexTag;
    }

    @Override
    public boolean isShowSuspension() {
        return true;
    }
}
