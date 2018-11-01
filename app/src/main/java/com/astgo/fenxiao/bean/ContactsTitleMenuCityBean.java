package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 * 联系人界面侧滑多级菜单的第二级的抽象类
 */
public class ContactsTitleMenuCityBean implements  Comparable<ContactsTitleMenuCityBean>{
    private String cityName;//城市名称
    private List<ContactsBean> cbs; //所属这个城市的联系人ID

    public ContactsTitleMenuCityBean(String cityName, List<ContactsBean> cbs) {
        this.cityName = cityName;
        this.cbs = cbs;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<ContactsBean> getCbs() {
        return cbs;
    }

    public void setCbs(List<ContactsBean> cbs) {
        this.cbs = cbs;
    }

    //根据列表长度排序 长的在前
    @Override
    public int compareTo(ContactsTitleMenuCityBean contactsTitleMenuCityBean) {
        return contactsTitleMenuCityBean.getCbs().size()- cbs.size();
    }
}
