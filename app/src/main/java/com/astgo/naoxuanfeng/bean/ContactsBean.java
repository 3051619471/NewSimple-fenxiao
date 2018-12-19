package com.astgo.naoxuanfeng.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 * 联系人实体类
 */
public class ContactsBean implements Serializable {
    private int contactsId;//联系人id
    private String contactsAlpha;//联系人字母缩写
    private String contactsName;//联系人名字
    private List<String> contactsNum;//联系人电话
    private Long contactsPhotoId;//联系人头像id
    private String contactsProvince;//联系人省份
    private String contactsCity;//联系人城市
    private String contactsCarrier;//联系人运营商
    private String initialNum;//名字首字母转化成的九宫格数字按键，用于检索
    private int discolorNameState;//联系人姓名变色状态，用于检索
    private int discolorNumState;//联系人号码变色状态，用于检索

    public int getContactsId() {
        return contactsId;
    }

    public void setContactsId(int contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsAlpha() {
        return contactsAlpha;
    }

    public void setContactsAlpha(String contactsAlpha) {
        this.contactsAlpha = contactsAlpha;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getInitialNum() {
        return initialNum;
    }

    public void setInitialNum(String initialNum) {
        this.initialNum = initialNum;
    }

    public List<String> getContactsNum() {
        return contactsNum;
    }

    public void addContactsNum(String phoneNum) {
        if (contactsNum == null) {
            contactsNum = new ArrayList<>();
        }
        contactsNum.add(phoneNum);
    }

    public Long getContactsPhotoId() {
        return contactsPhotoId;
    }

    public void setContactsPhotoId(Long contactsPhotoId) {
        this.contactsPhotoId = contactsPhotoId;
    }

    public String getContactsProvince() {
        return contactsProvince;
    }

    public void setContactsProvince(String contactsProvince) {
        this.contactsProvince = contactsProvince;
    }

    public String getContactsCity() {
        return contactsCity;
    }

    public void setContactsCity(String contactsCity) {
        this.contactsCity = contactsCity;
    }

    public String getContactsCarrier() {
        return contactsCarrier;
    }

    public void setContactsCarrier(String contactsCarrier) {
        this.contactsCarrier = contactsCarrier;
    }

    public int getDiscolorNameState() {
        return discolorNameState;
    }

    public void setDiscolorNameState(int discolorNameState) {
        this.discolorNameState = discolorNameState;
    }

    public int getDiscolorNumState() {
        return discolorNumState;
    }

    public void setDiscolorNumState(int discolorNumState) {
        this.discolorNumState = discolorNumState;
    }
}
