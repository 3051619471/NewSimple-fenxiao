package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/11/30.
 */

public class UpdateBean {

    /**
     * msg : success
     * info : success
     * code : 1
     * status : 1
     * data : {"version_number":"11","fileurl":"./Uploads/apkfile/5ac3602685036.apk","downloadfile":"http://www.fxsys.com/Uploads/apkfile/5ac3602685036.apk"}
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
         * version_number : 11
         * fileurl : ./Uploads/apkfile/5ac3602685036.apk
         * downloadfile : http://www.fxsys.com/Uploads/apkfile/5ac3602685036.apk
         */

        private String version_number;
        private String fileurl;
        private String downloadfile;

        public String getVersion_number() {
            return version_number;
        }

        public void setVersion_number(String version_number) {
            this.version_number = version_number;
        }

        public String getFileurl() {
            return fileurl;
        }

        public void setFileurl(String fileurl) {
            this.fileurl = fileurl;
        }

        public String getDownloadfile() {
            return downloadfile;
        }

        public void setDownloadfile(String downloadfile) {
            this.downloadfile = downloadfile;
        }
    }
}
