package com.astgo.naoxuanfeng.tools;

import android.content.ContentValues;

import com.astgo.naoxuanfeng.MyConstant;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/3/23.
 * 对filterList进行排序的工具排序
 */
public class FilterSortComparator implements Comparator{
    @Override
    public int compare(Object o, Object t1) {
        ContentValues cb1 = (ContentValues) o;
        ContentValues cb2 = (ContentValues) t1;
        int i1 = cb1.getAsInteger(MyConstant.CONTACT_STATE_NAME) + cb1.getAsInteger(MyConstant.CONTACT_STATE_NUM);
        int i2 = cb2.getAsInteger(MyConstant.CONTACT_STATE_NAME) + cb2.getAsInteger(MyConstant.CONTACT_STATE_NUM);
        if(i1 == i2){
            return cb2.getAsInteger(MyConstant.CONTACT_STATE_NAME) - cb1.getAsInteger(MyConstant.CONTACT_STATE_NAME);//如果相等则将名字符合的排在前面
        }else{
            return i2-i1;//如果不等则将名字和号码都符合的排在前面
        }
    }
}
