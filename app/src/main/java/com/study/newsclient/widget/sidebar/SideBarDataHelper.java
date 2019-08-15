package com.study.newsclient.widget.sidebar;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 介绍：IndexBar 的 数据相关帮助类 实现
 * * 1 将汉语转成拼音(利用tinyPinyin)
 * 2 填充indexTag (取拼音首字母)
 * 3 排序源数据源
 * 4 根据排序后的源数据源-
 * 5 生成模糊搜索关键字
 */

public class SideBarDataHelper {

    private SideBarDataHelper() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("can not be instantiated");
    }

    /**
     * 如果需要，
     * 字符->拼音，
     *
     * @param datas
     */
    public static void convertKeyword(List<? extends BaseSidePinyinBean> datas) {
        if (null == datas || datas.isEmpty()) {
            return ;
        }
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            BaseSidePinyinBean indexPinyinBean = datas.get(i);
            StringBuilder pySb = new StringBuilder();
            //add by zhangxutong 2016 11 10 如果不是top 才转拼音，否则不用转了
            if (indexPinyinBean.isNeedToKeyword()) {
                String target = indexPinyinBean.getTarget();//取出需要被拼音化的字段
                //遍历target的每个char得到它的全拼音
                for (int i1 = 0; i1 < target.length(); i1++) {
                    //利用TinyPinyin将char转成拼音
                    //查看源码，方法内 如果char为汉字，则返回大写拼音
                    //如果c不是汉字，则返回String.valueOf(c)
                    pySb.append(Pinyin.toPinyin(target.charAt(i1)).substring(0, 1).toUpperCase());
                }
                indexPinyinBean.setKeyword(pySb.toString());//设置关键字
            } else {
                //pySb.append(indexPinyinBean.getBaseIndexPinyin());
            }
        }
    }


    /**
     * 如果需要，
     * 字符->拼音，
     *
     * @param datas
     */
    public static void  convert(List<? extends BaseSidePinyinBean> datas) {
        if (null == datas || datas.isEmpty()) {
            return ;
        }
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            BaseSidePinyinBean indexPinyinBean = datas.get(i);
            StringBuilder pySb = new StringBuilder();
            //add by zhangxutong 2016 11 10 如果不是top 才转拼音，否则不用转了
            if (indexPinyinBean.isNeedToPinyin()) {
                String target = indexPinyinBean.getTarget();//取出需要被拼音化的字段
                //遍历target的每个char得到它的全拼音
                for (int i1 = 0; i1 < target.length(); i1++) {
                    //利用TinyPinyin将char转成拼音
                    //查看源码，方法内 如果char为汉字，则返回大写拼音
                    //如果c不是汉字，则返回String.valueOf(c)
                    pySb.append(Pinyin.toPinyin(target.charAt(i1)).toUpperCase());
                }
                indexPinyinBean.setBaseIndexPinyin(pySb.toString());//设置城市名全拼音
            } else {
                //pySb.append(indexPinyinBean.getBaseIndexPinyin());
            }
            StringBuilder py = new StringBuilder();
            if (indexPinyinBean.isNeedToKeyword()) {
                String target = indexPinyinBean.getTarget();//取出需要被拼音化的字段
                //遍历target的每个char得到它的全拼音
                for (int i1 = 0; i1 < target.length(); i1++) {
                    //利用TinyPinyin将char转成拼音
                    //查看源码，方法内 如果char为汉字，则返回大写拼音
                    //如果c不是汉字，则返回String.valueOf(c)
                    py.append(Pinyin.toPinyin(target.charAt(i1)).toUpperCase().substring(0, 1));
                }
                indexPinyinBean.setKeyword(py.toString());//关键字
            }
        }
    }

    /**
     * 如果需要取出，则
     * 取出首字母->tag,或者特殊字母 "#".
     * 否则，用户已经实现设置好
     *
     * @param datas
     */
    public static void  fillInexTag(List<? extends BaseSidePinyinBean> datas) {
        if (null == datas || datas.isEmpty()) {
            return ;
        }
        int size = datas.size();
        for (int i = 0; i < size; i++) {
            BaseSidePinyinBean indexPinyinBean = datas.get(i);
            if (indexPinyinBean.isNeedToPinyin()) {
                //以下代码设置城市拼音首字母
                String tagString = indexPinyinBean.getBaseIndexPinyin().toString().substring(0, 1);
                if (tagString.matches("[A-Z]")) {//如果是A-Z字母开头
                    indexPinyinBean.setBaseIndexTag(tagString);
                } else {//特殊字母这里统一用#处理
                    indexPinyinBean.setBaseIndexTag("#");
                }
            }
        }
    }

    public static void sortSourceDatas(List<? extends BaseSidePinyinBean> datas) {
        if (null == datas || datas.isEmpty()) {
            return ;
        }
        convert(datas);
        fillInexTag(datas);
        //对数据源进行排序
        Collections.sort(datas, new Comparator<BaseSidePinyinBean>() {
            @Override
            public int compare(BaseSidePinyinBean lhs, BaseSidePinyinBean rhs) {
                if (!lhs.isNeedToPinyin()) {
                    return 0;
                } else if (!rhs.isNeedToPinyin()) {
                    return 0;
                } else if (lhs.getBaseIndexTag().equals("#")) {
                    return 1;
                } else if (rhs.getBaseIndexTag().equals("#")) {
                    return -1;
                } else {
                    return lhs.getBaseIndexPinyin().compareTo(rhs.getBaseIndexPinyin());
                }
            }
        });
    }


    public static void getSortedIndexDatas(List<? extends BaseSidePinyinBean> sourceDatas, List<String> indexDatas) {
        if (null == sourceDatas || sourceDatas.isEmpty()) {
            return ;
        }
        //按数据源来 此时sourceDatas 已经有序
        int size = sourceDatas.size();
        String baseIndexTag;
        for (int i = 0; i < size; i++) {
            baseIndexTag = sourceDatas.get(i).getBaseIndexTag();
            if (!indexDatas.contains(baseIndexTag)) {//则判断是否已经将这个索引添加进去，若没有则添加
                indexDatas.add(baseIndexTag);
            }
        }
    }
}
