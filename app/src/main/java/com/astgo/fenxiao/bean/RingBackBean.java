package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by ast009 on 2017/12/1.
 */

public class RingBackBean {
    private String name;
    private List<String> phoneList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public String toString() {
        return "RingBackBean{" +
                "name='" + name + '\'' +
                ", phoneList=" + phoneList +
                '}';
    }
}
