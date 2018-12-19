package com.astgo.naoxuanfeng.studyvideo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/7.
 */

public class Video_tab_Bean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : {"category":[{"id":"1","name":"直播","pic":"./Uploads/video/5be1453aa62a6.png","pid":"0","o":"1","createtime":"1541489978"},{"id":"3","name":"录播","pic":"./Uploads/video/5be1499c792b3.png","pid":"0","o":"2","createtime":"1541491100"}],"video":[{"id":"2","cid":"1","title":"直播视频一","url":"","type":"0","o":"1","createtime":"1541491452"}]}
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
        private List<CategoryBean> category;
        private List<VideoBean> video;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class CategoryBean {
            /**
             * id : 1
             * name : 直播
             * pic : ./Uploads/video/5be1453aa62a6.png
             * pid : 0
             * o : 1
             * createtime : 1541489978
             */

            private String id;
            private String name;
            private String pic;
            private String pid;
            private String o;
            private String createtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getO() {
                return o;
            }

            public void setO(String o) {
                this.o = o;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }

        public static class VideoBean {
            /**
             * id : 2
             * cid : 1
             * title : 直播视频一
             * url :
             * type : 0
             * o : 1
             * createtime : 1541491452
             */

            private String id;
            private String cid;
            private String title;
            private String url;
            private String type;
            private String o;
            private String createtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getO() {
                return o;
            }

            public void setO(String o) {
                this.o = o;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }
    }
}
