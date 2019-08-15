package com.study.newsclient.entity;

import com.study.newsclient.widget.sidebar.BaseSidePinyinBean;

/**
 * Time: 2019-08-13
 * Author:wyy
 * Description:
 */
public class ContactInfo extends BaseSidePinyinBean {
    private int fid;//人员id
    private String fusercode;//人员编码（工号）
    private String frealname; //人员名称
    private String fname; //岗位名称
    private String firstalphabet; //人员首字母
    private String fgroupname; //所属部门名称
    private boolean isChecked;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFusercode() {
        return fusercode;
    }

    public void setFusercode(String fusercode) {
        this.fusercode = fusercode;
    }

    public String getFrealname() {
        return frealname;
    }

    public void setFrealname(String frealname) {
        this.frealname = frealname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFirstalphabet() {
        return firstalphabet;
    }

    public void setFirstalphabet(String firstalphabet) {
        this.firstalphabet = firstalphabet;
    }

    public String getFgroupname() {
        return fgroupname;
    }

    public void setFgroupname(String fgroupname) {
        this.fgroupname = fgroupname;
    }

    @Override
    public String getTarget() {
        return frealname;
    }

    @Override
    public boolean isNeedToPinyin() {
        return true;
    }


    @Override
    public boolean isShowSuspension() {
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContactInfo) {
            ContactInfo contactInfo = (ContactInfo) obj;
            return fid == contactInfo.getFid();
        }
        return false;
    }
}
