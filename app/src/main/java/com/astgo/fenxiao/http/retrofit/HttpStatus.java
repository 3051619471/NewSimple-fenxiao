package com.astgo.fenxiao.http.retrofit;

import com.google.gson.annotations.SerializedName;
import com.astgo.fenxiao.bean.AppConstant;

/**
 * Created by ast009 on 2017/12/7.
 */

public class HttpStatus {
    @SerializedName("code")
    private int mCode;
    @SerializedName("msg")
    private String mMsg;

    public int getCode() {
        return mCode;
    }

    public String getMsg() {
        return mMsg;
    }

    /**
     * API是否请求失败
     *
     * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return mCode != AppConstant.CODE_SUCCESS;
    }
}
