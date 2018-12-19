package com.astgo.naoxuanfeng.http.service;


import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.bean.LoginBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ast009 on 2017/11/17.
 */

public interface LoginService {

    @FormUrlEncoded //表示调提交单
    @POST("/split/user/login") //表示post请求
    Call<BaseDataObject<LoginBean>>  verifiLogin(@FieldMap Map<String,String> map);
}
