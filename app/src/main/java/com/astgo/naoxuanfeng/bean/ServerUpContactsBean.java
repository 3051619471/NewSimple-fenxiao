package com.astgo.naoxuanfeng.bean;

/**
 * Created by ast009 on 2017/12/1.
 */

public class ServerUpContactsBean {

    private String name;
    private String phone;


    public ServerUpContactsBean(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ServerUpContactsBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
