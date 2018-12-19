package com.astgo.naoxuanfeng.bean;

import java.util.List;

/**
 * Created by ast009 on 2017/12/13.
 */

public class BaseCommodityBean {
//    countPage   总页码
//    count		商品总数量
//    list		商品列表{coupon:优惠券可抵扣,img:商品图片,price:商品原价,sale:销量,title:商品标题,url:跳转链接}

    private int countPage;
    private int count;
    private int curPage;
    private List<CommodityBean> list;

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommodityBean> getList() {
        return list;
    }

    public void setList(List<CommodityBean> list) {
        this.list = list;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    @Override
    public String toString() {
        return "BaseCommodityBean{" +
                "countPage=" + countPage +
                ", count=" + count +
                ", curPage=" + curPage +
                ", list=" + list +
                '}';
    }
}
