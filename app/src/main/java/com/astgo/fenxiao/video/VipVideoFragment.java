package com.astgo.fenxiao.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.fenxiao.activity.LoginActivity;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.BannerInfo;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.NewDataVideo;
import com.astgo.fenxiao.fragment.BaseFragment;
import com.astgo.fenxiao.glide.GlideImageLoader;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.widget.URLConstant;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ast009 on 2018/2/1.
 */

public class VipVideoFragment extends BaseFragment implements View.OnClickListener {

    private Context context;
    private ImageView iv_tencent;
    private ImageView iv_iqiyi;
    private ImageView iv_youku;
    private ImageView iv_pptv;
    private ImageView iv_haokan;
    private ImageView iv_letv;
    //private ImageView iv_1905;
    private ImageView iv_meipai;
    private ImageView iv_mgtv;
    private ImageView tv_ad_left,tv_ad_right;
    private Banner banner;
    private LinearLayout appbar;
    private MarqueeTextView tv_marquee;

    private NewDataVideo newDataVideo;
    private NoticeBean noticeBean;
    private ImageBottomAD imageBottomAD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();//获取上下文
        return inflater.inflate(R.layout.fragment_vipvideo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
       // getData();
        getBannerInfor();
        GetNotificationInfo();
        GetBottomPic();
        GetThumbnailPic();
    }

//    private void getData() {
//        String url = HttpUtils.API_APP+"split/tshapi/getdata";
//        OkHttpUtils.get(url)     // 请求方式和请求url
//                .tag(this)// 请求的 tag, 主要用于取消对应的请求
//                .headers("token", SPUtils.getString(AppConstant.KEY_TOKEN,""))
//                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        DebugUtil.error("getData结果"+s);
//                        try {
//                            JSONObject job = new JSONObject(s);
//                            if (job.getInt("code") == 1){
//                                newDataVideo = new Gson().fromJson(s,NewDataVideo.class);
//                                if (newDataVideo == null){
//                                    Log.e("SLH","newDataVideo=null");
//                                    return;
//                                }
//                                setBannerData(newDataVideo.getData().getCarouselimg());
//                                URLConstant.PLAY_IMG = newDataVideo.getData().getAdvert().get(0).getImg_url();
//                                tv_marquee.setEnabled(true);
//                                tv_marquee.setText(newDataVideo.getData().getNotice().get(0).getContent());
//                                Glide.with(getActivity()).load(newDataVideo.getData().getEncyclopedia().get(0).getImg_url()).placeholder(R.drawable.ad01).into(tv_ad_left);
//                                Glide.with(getActivity()).load(newDataVideo.getData().getEncyclopedia().get(1).getImg_url()).placeholder(R.drawable.ad02).into(tv_ad_right);
//                                tv_ad_left.setEnabled(true);
//                                tv_ad_right.setEnabled(true);
//                            }else {
//                                ToastUtils.showShortToast(job.getString("msg"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        ToastUtils.showShortToast(e.getMessage());
//                    }
//                });
//    }

    private void GetThumbnailPic() {
        OkHttpUtils.get(NetUrl.VIDEO_PLAY_IMG)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetThumbnailPic结果"+s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                ThumbnailBean thumbnailBean = (ThumbnailBean) BaseDataObject.getBaseBean(s,ThumbnailBean.class);
                                URLConstant.PLAY_IMG = thumbnailBean.getData().getImg_url();
                            }
                        }else {
                           // ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void GetBottomPic() {
        OkHttpUtils.get(NetUrl.VIDEO_BOTTOM_AD)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetBottomPic结果"+s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                imageBottomAD = (ImageBottomAD) BaseDataObject.getBaseBean(s,ImageBottomAD.class);
                                Glide.with(getActivity()).load(imageBottomAD.getData().get(0).getImg_url()).placeholder(R.drawable.ad01).into(tv_ad_left);
                                Glide.with(getActivity()).load(imageBottomAD.getData().get(1).getImg_url()).placeholder(R.drawable.ad02).into(tv_ad_right);
                                tv_ad_left.setEnabled(true);
                                tv_ad_right.setEnabled(true);
                            }
                        }else {
                            //ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void GetNotificationInfo() {

        OkHttpUtils.get(NetUrl.VIDEO_NOTICE)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN,""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("GetNotificationInfo结果"+s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":[{"id":"1","content":"\u89c6\u9891\u516c\u544a1","url":"http:\/\/www.baidu.com"}]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                noticeBean = (NoticeBean) BaseDataObject.getBaseBean(s,NoticeBean.class);
                                tv_marquee.setText(noticeBean.getData().get(0).getContent());
                                tv_marquee.setEnabled(true);
                            }
                        }else {
                            //ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });

    }

    private void getBannerInfor() {

        OkHttpUtils.get(NetUrl.BANNER_VIDEO)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token",SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("getBannerInfor结果"+s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":[{"id":"11","img_url":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5acc2026c8b8a.png","url":"www.baidu.com"},{"id":"12","img_url":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5acc234035d76.png","url":"www.baidu.com"}]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s,BaseDataObject.class);
                        if (baseDataObject.getCode() == 1){
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                BannerVidoListBean bannerVidoListBean = (BannerVidoListBean) BaseDataObject.getBaseBean(s,BannerVidoListBean.class);
                                setBannerData(bannerVidoListBean.getData());
                            }
                        }else {
                            if (baseDataObject.getCode() == 100){
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin",false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void initView(View view) {
        appbar = (LinearLayout) view.findViewById(R.id.vipvideo_app_bar);

        FrameLayout flAppbarBack = (FrameLayout) view.findViewById(R.id.fl_title_back);
        flAppbarBack.setVisibility(View.GONE);
        TextView tvFragmnetTitle = (TextView) view.findViewById(R.id.tv_app_bar_title);
        tvFragmnetTitle.setText("VIP视频");

        banner = (Banner) view.findViewById(R.id.vipvideo_banner);

        iv_tencent = (ImageView) view.findViewById(R.id.iv_tencent);
        iv_iqiyi = (ImageView) view.findViewById(R.id.iv_iqiyi);
        iv_youku = (ImageView) view.findViewById(R.id.iv_youku);
        iv_pptv = (ImageView) view.findViewById(R.id.iv_pptv);
        iv_mgtv = (ImageView) view.findViewById(R.id.iv_mgtv);
        iv_haokan = (ImageView) view.findViewById(R.id.iv_haokan);
        iv_letv = (ImageView) view.findViewById(R.id.iv_letv);
        //iv_1905 = (ImageView) view.findViewById(R.id.iv_1905);
        iv_meipai = (ImageView) view.findViewById(R.id.iv_meipai);

        tv_ad_left = (ImageView) view.findViewById(R.id.tv_ad_left);
        tv_ad_left.setEnabled(false);
        tv_ad_right = (ImageView) view.findViewById(R.id.tv_ad_right);

        tv_ad_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBottomAD==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",imageBottomAD.getData().get(0).getUrl());
                startActivity(intent);
            }
        });
        tv_ad_right.setEnabled(false);
        tv_ad_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBottomAD==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",imageBottomAD.getData().get(1).getUrl());
                startActivity(intent);
            }
        });

        iv_tencent.setOnClickListener(this);
        iv_iqiyi.setOnClickListener(this);
        iv_youku.setOnClickListener(this);
        iv_pptv.setOnClickListener(this);

        iv_haokan.setOnClickListener(this);
        iv_letv.setOnClickListener(this);
        //iv_1905.setOnClickListener(this);
        iv_meipai.setOnClickListener(this);
        iv_mgtv.setOnClickListener(this);
        tv_marquee = (MarqueeTextView) view.findViewById(R.id.tv_auto_scroll);
        tv_marquee.setEnabled(false);
        tv_marquee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeBean==null)return;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url",noticeBean.getData().get(0).getUrl());
                startActivity(intent);
            }
        });
        //tv_marquee.setText("好消息！");

    }

    private List<BannerInfo> bannerInfors = new ArrayList<>();

    private void setBannerData(final List<BannerVidoListBean.DataBean> bannerInfors) {

        List<String> bannerImaglist = new ArrayList<>();
            for (BannerVidoListBean.DataBean bannerInfo : bannerInfors) {
                bannerImaglist.add(bannerInfo.getImg_url());
            }
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide01);
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide02);
//        bannerImaglist.add("android.resource://com.pomelorange.taoshihui/drawable/"+R.drawable.guide03);
        banner.setImages(bannerImaglist).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("SLH",""+position);
                BannerVidoListBean.DataBean bannerInfo = bannerInfors.get(position);
                if (bannerInfo != null) {
                    Intent intent = new Intent(getActivity(), CommonActivity.class);
                    intent.putExtra("url",bannerInfo.getUrl());
                    startActivity(intent);
                }else {
                    Log.e("SLH","bannerInfo == null"+position);
                }
            }
        });
//        banner.setOnBannerClickListener(new OnBannerClickListener() {
//            @Override
//            public void OnBannerClick(int position) {
//
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        if (jumpVipVideo(v)) return;
    }

    private boolean jumpVipVideo(View v) {
        // Intent intent = new Intent(this.getActivity(), VideoListActivity.class);
        Intent intent = new Intent(this.getActivity(), WebVideoListActivity.class);
        //1：腾讯视频-手机版链接、2：爱奇艺、3：优酷、4：芒果TV、5：乐视、6：PPTV、7：搜狐视频、8：看看视频、9：暴风影音、10：1905
        if (v.getId() == R.id.iv_tencent) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_TENCENT);
            intent.putExtra("platform",1);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_iqiyi) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_IQIYI);
            intent.putExtra("platform",2);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_youku) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_YOUKU);
            intent.putExtra("platform",3);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_pptv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_PPTV);
            intent.putExtra("platform",6);
            startActivity(intent);
            return true;

        }
        if (v.getId() == R.id.iv_haokan) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_HAO_KAN);
            intent.putExtra("platform",999);
            startActivity(intent);
            return true;
        }
        if (v.getId() == R.id.iv_letv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_LE_TV);
            intent.putExtra("platform",5);
            startActivity(intent);
            return true;
        }
//        if (v.getId() == R.id.iv_1905) {
//            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_1905_TV);
//            intent.putExtra("platform",10);
//            startActivity(intent);
//            return true;
//        }
        if (v.getId() == R.id.iv_meipai) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_MEIPAI_TV);
            intent.putExtra("platform",999);
            startActivity(intent);
            return true;

        }
        if (v.getId() == R.id.iv_mgtv) {
            intent.putExtra(Constant.INTENT_KEY_WEB_URL, Constant.URL_VIDEO_LIST_MG_TV);
            intent.putExtra("platform",4);
            startActivity(intent);
            return true;

        }
        return false;
    }

}
