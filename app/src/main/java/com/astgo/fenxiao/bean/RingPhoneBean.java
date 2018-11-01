package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/27.
 * 回铃电话抽象类
 *
 */
public class RingPhoneBean {

    private String phoneName;
    private List<String> phoneListNum;

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public List<String> getPhoneListNum() {
        return phoneListNum;
    }

    public void setPhoneListNum(List<String> phoneListNum) {
        this.phoneListNum = phoneListNum;
    }
}
