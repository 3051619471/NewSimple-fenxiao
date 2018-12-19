package com.astgo.naoxuanfeng.bean;

import java.util.List;

/**
 * Created by ast009 on 2018/4/16.
 */

public class PlatformUrl {

    /**
     * msg : success
     * code : 1
     * data : {"title":"腾讯视频-手机版链接","system_type":"android","apilist":["http://jx.icewl.net/2.23.php?url=","https://api.vparse.org/?url=","http://mlxztz.com/vip/index.php?url=","http://jiexi.071811.cc/jx2.php?url=","http://www.82190555.com/video.php?url=","http://player.jidiaose.com/supapi/iframe.php?v="]}
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
        /**
         * title : 腾讯视频-手机版链接
         * system_type : android
         * apilist : ["http://jx.icewl.net/2.23.php?url=","https://api.vparse.org/?url=","http://mlxztz.com/vip/index.php?url=","http://jiexi.071811.cc/jx2.php?url=","http://www.82190555.com/video.php?url=","http://player.jidiaose.com/supapi/iframe.php?v="]
         */

        private String title;
        private String system_type;
        private List<String> apilist;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSystem_type() {
            return system_type;
        }

        public void setSystem_type(String system_type) {
            this.system_type = system_type;
        }

        public List<String> getApilist() {
            return apilist;
        }

        public void setApilist(List<String> apilist) {
            this.apilist = apilist;
        }
    }
}
