package com.astgo.fenxiao.bean;

import java.util.List;

/**
 * Created by ast009 on 2018/4/17.
 */

public class NewDataVideo {

    /**
     * msg : success
     * code : 1
     * data : {"carouselimg":[{"id":1,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/guide01.jpg","url":"http://www.baidu.com"},{"id":2,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/guide02.jpg","url":"http://www.baidu.com"},{"id":3,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/guide03.jpg","url":"http://www.baidu.com"}],"notice":[{"id":1,"content":"公告一","url":"http://www.baidu.com"},{"id":2,"content":"公告二","url":"http://www.baidu.com"}],"encyclopedia":[{"id":1,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/ad01.png","url":"http://www.baidu.com"},{"id":2,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/ad02.png","url":"http://www.baidu.com"}],"advert":[{"id":1,"img_url":"http://lzx.7oks.com/split/Public/imginterfase/tengxun.jpg"}]}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CarouselimgBean> carouselimg;
        private List<NoticeBean> notice;
        private List<EncyclopediaBean> encyclopedia;
        private List<AdvertBean> advert;

        public List<CarouselimgBean> getCarouselimg() {
            return carouselimg;
        }

        public void setCarouselimg(List<CarouselimgBean> carouselimg) {
            this.carouselimg = carouselimg;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<EncyclopediaBean> getEncyclopedia() {
            return encyclopedia;
        }

        public void setEncyclopedia(List<EncyclopediaBean> encyclopedia) {
            this.encyclopedia = encyclopedia;
        }

        public List<AdvertBean> getAdvert() {
            return advert;
        }

        public void setAdvert(List<AdvertBean> advert) {
            this.advert = advert;
        }

        public static class CarouselimgBean {
            /**
             * id : 1
             * img_url : http://lzx.7oks.com/split/Public/imginterfase/guide01.jpg
             * url : http://www.baidu.com
             */

            private int id;
            private String img_url;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class NoticeBean {
            /**
             * id : 1
             * content : 公告一
             * url : http://www.baidu.com
             */

            private int id;
            private String content;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class EncyclopediaBean {
            /**
             * id : 1
             * img_url : http://lzx.7oks.com/split/Public/imginterfase/ad01.png
             * url : http://www.baidu.com
             */

            private int id;
            private String img_url;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AdvertBean {
            /**
             * id : 1
             * img_url : http://lzx.7oks.com/split/Public/imginterfase/tengxun.jpg
             */

            private int id;
            private String img_url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
