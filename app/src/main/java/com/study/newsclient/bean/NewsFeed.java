package com.study.newsclient.bean;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by wyy on 2017/4/26.
 */

public class NewsFeed implements Serializable {

    /**
     * 栏目对应ID
     */
    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private String title;
}
