package com.astgo.fenxiao.http.service;


import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.BannerInfo;
import com.astgo.fenxiao.bean.BaseCommodityBean;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.BaseShopDataObject;
import com.astgo.fenxiao.bean.JpushMessageBean;
import com.astgo.fenxiao.bean.LoginBean;
import com.astgo.fenxiao.bean.MessageListBean;
import com.astgo.fenxiao.bean.RingBackBean;
import com.astgo.fenxiao.bean.ShopBannerInfor;
import com.astgo.fenxiao.bean.ShopCategoryItemBean;
import com.astgo.fenxiao.bean.UpdateBean;
import com.astgo.fenxiao.bean.UserInforBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {
        public static HttpClient getAppService() {
            return HttpUtils.getInstance().getAppserver(HttpClient.class);
        }
        public static HttpClient getWeatherServer() {
            return HttpUtils.getInstance().getWeatherServer(HttpClient.class);
        }

        public static HttpClient getShopServer() {
            return HttpUtils.getInstance().getShopServer(HttpClient.class);
        }
    }

    /**
     * 首页广告
     */
//    @GET("/Api-service-start")
//    Observable<BaseDataObject<AdsPicBean>> getAdsPic();

    /**
     * 验证登录
     */
    @FormUrlEncoded //表示调提交单
    @POST(AppConstant.API_HOST_END+"/user/login") //表示post请求
    Observable<BaseDataObject<LoginBean>> verifiLogin(@FieldMap Map<String,String> map);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/user/register")
    Observable<BaseDataObject<Object>> serverReg(@FieldMap Map<String,String> map);

    /**
     * 充值卡注册
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/server/cardpay")
    Observable<BaseDataObject<Object>> serverCardReg(@FieldMap Map<String,String> map);

    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/user/getCode")
    Observable<BaseDataObject<Object>> serverGetAuthCode(@FieldMap Map<String, String> map);

    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/user/forgetPwd")
    Observable<BaseDataObject<Object>> serverForgetPwd(@FieldMap Map<String, String> map);

    @GET(AppConstant.API_HOST_END+"/user/getUserInfo")
    Observable<BaseDataObject<UserInforBean>> getUserInfor();

    @GET(AppConstant.API_HOST_END+"/server/getversion")
    Observable<BaseDataObject<UpdateBean>> getServerAppVersion();

    @Multipart
    @POST(AppConstant.API_HOST_END+"/user/uploadhead")
    Observable<BaseDataObject<String>> uploadHeadImag(@Part MultipartBody.Part file);


    @GET(AppConstant.API_HOST_END+"/server/carousel")
    Observable<BaseDataObject<List<BannerInfo>>> getBannerData();

    @GET(AppConstant.API_HOST_END+"/server/news")
    Observable<BaseDataObject<MessageListBean>> getMessageList();

    /**
     * 获取消息详情
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/server/newsview")
    Observable<BaseDataObject<JpushMessageBean>> getMessageDetail(@FieldMap Map<String, String> map);
    /**
     * 获回铃电话
     */
    @GET(AppConstant.API_HOST_END+"/server/ringback")
    Observable<BaseDataObject<List<RingBackBean>>> getRingBackList();

    /**
     *服务器拨打电话
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/server/call")
    Observable<BaseDataObject<Object>> serverCall(@FieldMap Map<String, String> map);

    /**
     *上传通讯录
     */
    @FormUrlEncoded
    @POST(AppConstant.API_HOST_END+"/server/upContacts")
    Observable<BaseDataObject<Object>> serverUpContacts(@Field("contacts")String contactsJson);

    /**
     * 获取商城页面的广告
     * @param url
     * @return
     */
    @GET
    Observable<BaseShopDataObject<List<ShopBannerInfor>>> getShopBannerData(@Url String url);

    /**
     * 获取商品的分类
     * @param url
     * @return
     */
    @GET
    Observable<BaseShopDataObject<List<ShopCategoryItemBean>>> getShopCategoryData(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<BaseCommodityBean> getShopVolumeData(@Url String url, @Field("p") int p, @Field("orders") String orders);
}