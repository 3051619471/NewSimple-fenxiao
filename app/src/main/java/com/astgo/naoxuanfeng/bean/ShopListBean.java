package com.astgo.naoxuanfeng.bean;

import java.util.List;

/**
 * Created by ast009 on 2018/4/17.
 */

public class ShopListBean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : {"list":[{"goods_id":"6444","type":"5","title":"【梵尼诗】F1877-66大喇叭仿古留声机 LP黑胶唱片机 电唱机CD机","price":"14399.00","img":"http://img.alicdn.com/tfscom/i2/1858845692/TB1SIIemaSWBuNjSsrbXXa0mVXa_!!0-item_pic.jpg","item_url":"http://detail.tmall.com/item.htm?id=36650609589","sale":"0","coupon_price":"1000.00","num_iid":"36650609589","coupon_end_time":"1523980800","integral":null,"coupon":"可抵扣:1000.00","is_taobao":2,"url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=view&goods_id=6444"},{"goods_id":"124920","type":"5","title":"OPP男鞋2018春季新品休闲皮鞋男商务鞋真皮套脚鞋婚礼鞋英伦流苏","price":"666.00","img":"http://img.alicdn.com/tfscom/i1/776338498/TB1nOzGXoz.BuNjt_j7XXX0nFXa_!!0-item_pic.jpg","item_url":"http://detail.tmall.com/item.htm?id=565603118093","sale":"1","coupon_price":"230.00","num_iid":"565603118093","coupon_end_time":"1523980800","integral":null,"coupon":"可抵扣:230.00","is_taobao":2,"url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=view&goods_id=124920"}],"countPage":1920,"curPage":1,"count":"11517","status":1}
     */

    private String msg;
    private String info;
    private int code;
    private int status;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"goods_id":"6444","type":"5","title":"【梵尼诗】F1877-66大喇叭仿古留声机 LP黑胶唱片机 电唱机CD机","price":"14399.00","img":"http://img.alicdn.com/tfscom/i2/1858845692/TB1SIIemaSWBuNjSsrbXXa0mVXa_!!0-item_pic.jpg","item_url":"http://detail.tmall.com/item.htm?id=36650609589","sale":"0","coupon_price":"1000.00","num_iid":"36650609589","coupon_end_time":"1523980800","integral":null,"coupon":"可抵扣:1000.00","is_taobao":2,"url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=view&goods_id=6444"},{"goods_id":"124920","type":"5","title":"OPP男鞋2018春季新品休闲皮鞋男商务鞋真皮套脚鞋婚礼鞋英伦流苏","price":"666.00","img":"http://img.alicdn.com/tfscom/i1/776338498/TB1nOzGXoz.BuNjt_j7XXX0nFXa_!!0-item_pic.jpg","item_url":"http://detail.tmall.com/item.htm?id=565603118093","sale":"1","coupon_price":"230.00","num_iid":"565603118093","coupon_end_time":"1523980800","integral":null,"coupon":"可抵扣:230.00","is_taobao":2,"url":"http://hssq.dykj168.com/index.php?m=Home&c=index&a=view&goods_id=124920"}]
         * countPage : 1920
         * curPage : 1
         * count : 11517
         * status : 1
         */

        private int countPage;
        private int curPage;
        private String count;
        private int status;
        private List<ListBean> list;

        public int getCountPage() {
            return countPage;
        }

        public void setCountPage(int countPage) {
            this.countPage = countPage;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * goods_id : 6444
             * type : 5
             * title : 【梵尼诗】F1877-66大喇叭仿古留声机 LP黑胶唱片机 电唱机CD机
             * price : 14399.00
             * img : http://img.alicdn.com/tfscom/i2/1858845692/TB1SIIemaSWBuNjSsrbXXa0mVXa_!!0-item_pic.jpg
             * item_url : http://detail.tmall.com/item.htm?id=36650609589
             * sale : 0
             * coupon_price : 1000.00
             * num_iid : 36650609589
             * coupon_end_time : 1523980800
             * integral : null
             * coupon : 可抵扣:1000.00
             * is_taobao : 2
             * url : http://hssq.dykj168.com/index.php?m=Home&c=index&a=view&goods_id=6444
             */

            private String goods_id;
            private String type;
            private String title;
            private String price;
            private String img;
            private String item_url;
            private String sale;
            private String coupon_price;
            private String num_iid;
            private String coupon_end_time;
            private Object integral;
            private String coupon;
            private int is_taobao;
            private String url;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getItem_url() {
                return item_url;
            }

            public void setItem_url(String item_url) {
                this.item_url = item_url;
            }

            public String getSale() {
                return sale;
            }

            public void setSale(String sale) {
                this.sale = sale;
            }

            public String getCoupon_price() {
                return coupon_price;
            }

            public void setCoupon_price(String coupon_price) {
                this.coupon_price = coupon_price;
            }

            public String getNum_iid() {
                return num_iid;
            }

            public void setNum_iid(String num_iid) {
                this.num_iid = num_iid;
            }

            public String getCoupon_end_time() {
                return coupon_end_time;
            }

            public void setCoupon_end_time(String coupon_end_time) {
                this.coupon_end_time = coupon_end_time;
            }

            public Object getIntegral() {
                return integral;
            }

            public void setIntegral(Object integral) {
                this.integral = integral;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public int getIs_taobao() {
                return is_taobao;
            }

            public void setIs_taobao(int is_taobao) {
                this.is_taobao = is_taobao;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
