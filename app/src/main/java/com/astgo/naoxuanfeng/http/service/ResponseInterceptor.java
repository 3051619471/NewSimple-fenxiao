package com.astgo.naoxuanfeng.http.service;

import android.text.TextUtils;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.ParamConstant;
import com.astgo.naoxuanfeng.bean.AppConstant;
import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.bean.LoginBean;
import com.astgo.naoxuanfeng.tools.MD5EncodeUtil;
import com.astgo.naoxuanfeng.tools.PreferenceUtil;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ast009 on 2017/11/17.
 * refresh token
 */

public class ResponseInterceptor implements Interceptor {

    private String bodystring;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            DebugUtil.debug("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newToken = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("token", newToken)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }

        //此处要重新建一个builder,因为isTokenExpired中的response的ResponseBody调用过string()方法后，不能被第二次调用
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, bodystring))
                .build();
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) throws IOException {
        ResponseBody body = response.body();
        bodystring = body.string();
//        BaseShopDataObject baseDataObject = new Gson().fromJson(bodystring, new TypeToken<BaseShopDataObject>() {}.getType());
//        if (baseDataObject.getStatus() == AppConstant.CODE_TOKEN_INVAILI
//                || baseDataObject.getStatus() == AppConstant.CODE_TOKEN_LOSS) {
//            return true;
//        }
//
//        if (baseDataObject.getStatus() == AppConstant.CODE_FAILED) {
//            throw new ApiException(baseDataObject.getStatus(), baseDataObject.getInfo());
//        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUtils.API_APP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit2.Response<BaseDataObject<LoginBean>> response = retrofit.create(LoginService.class).verifiLogin(getUrlParam()).execute();
        BaseDataObject baseDataObject = response.body();
        if (baseDataObject != null) {
            if (baseDataObject.getCode() == AppConstant.CODE_SUCCESS) {
                SPUtils.putBoolean(AppConstant.KEY_IS_LOGIN, true);
                LoginBean loginBean= (LoginBean) baseDataObject.getData();
                String token = loginBean.getData().getToken();
                if (!TextUtils.isEmpty(token)) {
                    SPUtils.putString(AppConstant.KEY_TOKEN, token);
                    return token;
                }
            }
        }
        return "";
    }


    // 设置登录参数
    private HashMap<String, String> getUrlParam() {
        HashMap<String, String> urlParams = new HashMap<String, String>();
        String stringAccount = PreferenceUtil.getString(MyConstant.SP_ACCOUNT, "");
        urlParams.put(ParamConstant.REGNUM, stringAccount);
        urlParams.put(ParamConstant.REGPWD, PreferenceUtil.getString(MyConstant.SP_PASSWORD, ""));
        urlParams.put(ParamConstant.KEY, MD5EncodeUtil.getMD5EncodeStr(stringAccount + MyConstant.KEY_FLAG));
        urlParams.put(ParamConstant.JPUSH_RID, SPUtils.getString(AppConstant.KEY_JPUSH_ID, ""));
        return urlParams;
    }
}