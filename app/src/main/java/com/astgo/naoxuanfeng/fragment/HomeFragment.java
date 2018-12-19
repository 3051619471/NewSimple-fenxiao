package com.astgo.naoxuanfeng.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.NetUrl;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.activity.LoginActivity;
import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.tools.NetworkUtil;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.astgo.naoxuanfeng.video.CommonActivity;
import com.astgo.naoxuanfeng.webview.Js2Java;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的页面
 */
public class HomeFragment extends Fragment {


    private WebView webView;
    private ImageView iv_empty;
    private TextView tv_empty;
    public SmartRefreshLayout refreshLayout;


    public void reload() {
        if (webView != null) {
            webView.reload();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_new_home, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @SuppressLint("JavascriptInterface")
    private void initView(View rootView) {
        refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableAutoLoadmore(true);
        //refreshLayout.setFooterHeightPx(2);
        //refreshLayout.setRefreshHeader(new RefreshHeader(this.getActivity().getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (NetworkUtil.isConnection(getActivity())) {
                    switchDevice(NetUrl.MINE);
                } else {
                    tv_empty.setVisibility(View.VISIBLE);
                    iv_empty.setVisibility(View.GONE);
                    //Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                    webView.setVisibility(View.GONE);
                    if (refreshLayout != null) refreshLayout.finishRefresh();
                }
            }
        });

        webView = (WebView) rootView.findViewById(R.id.webView);
        iv_empty = (ImageView) rootView.findViewById(R.id.iv_empty);
        Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
        tv_empty = (TextView) rootView.findViewById(R.id.tv_empty);

        tv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_empty.getText().equals("重新登录")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    getActivity().finish();
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);  //支持js\


        webView.getSettings().setAllowContentAccess(true);

        webView.getSettings().setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webView.getSettings().setSupportZoom(true);  //支持缩放

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webView.getSettings().supportMultipleWindows();  //多窗口

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存

        webView.getSettings().setAllowFileAccess(true);  //设置可以访问文件

        webView.getSettings().setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView.getSettings().setBuiltInZoomControls(false); //设置支持缩放
//
        webView.getSettings().setLoadsImagesAutomatically(true);  //支持自动加载图片
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(new Js2Java(getActivity(), new Handler(), new ArrayList<String>()), "js2java");
        if (NetworkUtil.isConnection(getActivity())) {
//            Map<String,String> map = new HashMap<>();
//            map.put("token", SPUtils.getString(MyConstant.TOKEN,""));
//            webView.loadUrl(getIntent().getStringExtra("url"),map);
//            iv_empty.setVisibility(View.GONE);
//            webView.setVisibility(View.VISIBLE);
            switchDevice(NetUrl.MINE);
        } else {
            tv_empty.setVisibility(View.VISIBLE);
            iv_empty.setVisibility(View.GONE);
            webView.setVisibility(View.GONE);
            // Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("SLH", url);
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url", url);
                startActivityForResult(intent, 1);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                iv_empty.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                iv_empty.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(this, "j2java");

        /**
         * webView与ViewPager所带来的滑动冲突问题解决方法
         */
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                webView.getParent().requestDisallowInterceptTouchEvent(true);
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                int lastX = 0;
                int lastY = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaY = y - lastY;
                        int deltaX = x - lastX;
                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
                            webView.getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            webView.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    default:
                        break;
                }
                return false;
            }
        });
        List<String> lists = new ArrayList<>();
        webView.addJavascriptInterface(new Js2Java(getActivity(), new Handler(), lists), "js2java");

    }

    public void switchDevice(final String url) {

        OkHttpUtils.get(NetUrl.SWITCH_DEVICE)     // 请求方式和请求url
                .tag("switchDevice")// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("switchDevice")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("switchDevice" + s);
                        //{"data":[{"title":"\u6807\u98981","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48da3b4bf.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6863\u4f4d\u5546\u54c1","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5ac3439b29f58.png","jumpurl":"\/home\/index\/lvgoods"},{"title":"\u6807\u98983","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48a028e85.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6807\u98982","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48fd48b9c.png","jumpurl":"www.baidu.com"},{"title":"\u6807\u98984","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d491c077b1.png","jumpurl":"www.baidu.com"}],"info":"success","status":1}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            Map<String, String> map = new HashMap<>();
                            map.put("token", SPUtils.getString(MyConstant.TOKEN, ""));
                            webView.loadUrl(url, map);
                            webView.setVisibility(View.VISIBLE);
                            tv_empty.setVisibility(View.GONE);
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            webView.setVisibility(View.GONE);
                            tv_empty.setVisibility(View.VISIBLE);
                            iv_empty.setVisibility(View.GONE);
                            if (baseDataObject.getCode() == 100) {
                                tv_empty.setText("重新登录");
                            } else {
                                tv_empty.setText("资源加载失败！");
                            }
                            // Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                        }
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                        webView.setVisibility(View.GONE);
                        tv_empty.setVisibility(View.VISIBLE);
                        iv_empty.setVisibility(View.GONE);
                        //Glide.with(getActivity()).load(R.drawable.gif_error).asGif().into(iv_empty);
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                    }
                });
    }


    //    private void getUserInfor() {
////        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(),"正在获取数据");
//        if(NetworkUtil.isConnection(this.getContext())){
//            HttpClient.Builder.getAppService().getUserInfor()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new HttpResultSubscriber<UserInforBean>() {
//                        @Override
//                        public void onSuccess(UserInforBean userInforBean) {
//                            //TODO
////                        dialog.dismiss();
//                            if(userInforBean != null){
//                                String shopUrl = userInforBean.getShopurl();
//                                setUIState(userInforBean);
//                                SPUtils.putString(AppConstant.KEY_USER_INFOR,new Gson().toJson(userInforBean));
//                                SPUtils.putString(AppConstant.KEY_SHOP_URL,userInforBean.getShopurl());
//                                if(!TextUtils.isEmpty(shopUrl)){
//                                    int indexUserName = shopUrl.indexOf("&username");
//                                    if(indexUserName>=0){
//                                        String shopUrlSuffix =shopUrl.substring(indexUserName,shopUrl.length());
//                                        SPUtils.putString(AppConstant.KEY_SHOP_URL_SUFFIX,shopUrlSuffix);
//                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void _onError(String msg, int code) {
////                        dialog.dismiss();
//                            DebugUtil.error("msg:"+msg+"-------code"+code);
//                            setErrorUserInforUI();
//                        }
//                    });
//        }else{
//            setErrorUserInforUI();
//        }
//
//    }

//    private void setErrorUserInforUI() {
//        String userInforJson = SPUtils.getString(AppConstant.KEY_USER_INFOR, "");
//        if(!TextUtils.isEmpty(userInforJson)){
//            UserInforBean userInforBean = new Gson().fromJson(userInforJson,new TypeToken<UserInforBean>(){}.getType());
//            setUIState(userInforBean);
//        }
//    }

    //    private ImageView ivHeadImg;
//    private ImageView imMsg;
//    private TextView tvPhoneNum;
//    private TextView tvYue;
//    private LinearLayout llCommission;
//    private LinearLayout llDiscont;
//    private LinearLayout llMyIncom;
//    private LinearLayout llMyTeam;
//    private LinearLayout llMyGeneralize;
//    private LinearLayout llWithdraw;
//    private LinearLayout llMobilePrepaid;
//    private LinearLayout llPhoneBill;
//    private LinearLayout llFeedBack;
//    private LinearLayout llShare;
//    private LinearLayout llSysSetting;
//    private LinearLayout llHelpText;
//    private LinearLayout llContactUpdate;
//    private LinearLayout llAboutUs;
//    private ImageView ivRedCircle;

    //private void initView(View view) {
//        ivHeadImg = (ImageView) view.findViewById(R.id.iv_head_img);
//        tvPhoneNum = (TextView) view.findViewById(R.id.tv_phone_num);
//        imMsg = (ImageView) view.findViewById(R.id.im_msg);
//
//        ivRedCircle = (ImageView) view.findViewById(R.id.im_red_circle);
//        tvYue= (TextView) view.findViewById(R.id.tv_yu_e);
//        setItemLayoutMethod(view);
//        ivHeadImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageSelectorActivity.start(HomeFragment.this.getActivity(), 1,ImageSelectorActivity.MODE_SINGLE , true,true,true);
//            }
//        });
//
//        imMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //跳转到消息页面
//                Intent intent = new Intent(HomeFragment.this.getContext(),MessageActivity.class);
//                startActivity(intent);
//            }
//        });
////        setScroll(view);
}

//    private void setScroll(View view) {
//                final LinearLayout llTopBar = (LinearLayout) view.findViewById(R.id.layout_top_bar);
//        final LinearLayout rlUserInfor = (LinearLayout) view.findViewById(R.id.rl_user_infor);
//        int heightUserInfor = rlUserInfor.getLayoutParams().height;
//        int heightllTopbar = llTopBar.getLayoutParams().height;
//        final int height = heightUserInfor-heightllTopbar;
//        NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY < height){
//                    float scale = (float) scrollY/height;
//                    int alpha = (int) (255 * scale);
//                    llTopBar.setBackgroundColor(Color.argb(alpha, 255, 122, 17));
//                }
//
//                if(scrollY >= height){
//                    llTopBar.setBackgroundColor(Color.argb(255, 255, 122, 17));
//                }
//            }
//        });
//   }

//    private TextView tvCommission;
//    private TextView tvCoupon;
//    private void setItemLayoutMethod(View view) {
//        llCommission = (LinearLayout) view.findViewById(R.id.ll_commission);
//
//        tvCommission = (TextView) view.findViewById(R.id.tv_commission);
//        llDiscont = (LinearLayout) view.findViewById(R.id.ll_discont);
//
//        tvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
//        llMyIncom = (LinearLayout) view.findViewById(R.id.ll_my_incom);
//        llMyTeam = (LinearLayout) view.findViewById(R.id.ll_my_team);
//        llMyGeneralize = (LinearLayout) view.findViewById(R.id.ll_my_generalize);
//        llWithdraw = (LinearLayout) view.findViewById(R.id.ll_withdraw);
//        llMobilePrepaid = (LinearLayout) view.findViewById(R.id.ll_mobile_prepaid);
//        llPhoneBill = (LinearLayout) view.findViewById(R.id.ll_phone_bill);
//        llFeedBack = (LinearLayout) view.findViewById(R.id.ll_feed_back);
//        llShare = (LinearLayout) view.findViewById(R.id.ll_share);
//        llSysSetting = (LinearLayout) view.findViewById(R.id.ll_sys_setting);
//        llHelpText = (LinearLayout) view.findViewById(R.id.ll_help_text);
//        llContactUpdate = (LinearLayout) view.findViewById(R.id.ll_contact_update);
//        llAboutUs = (LinearLayout) view.findViewById(R.id.ll_about_us);
//        setLayoutListener();
//    }

//    private void setLayoutListener() {
//        llCommission.setOnClickListener(this);
//        llDiscont.setOnClickListener(this);
//        llMyIncom.setOnClickListener(this);
//        llMyTeam.setOnClickListener(this);
//        llMyGeneralize.setOnClickListener(this);
//        llWithdraw.setOnClickListener(this);
//        llMobilePrepaid.setOnClickListener(this);
//        llPhoneBill.setOnClickListener(this);
//        llFeedBack.setOnClickListener(this);
//        llShare.setOnClickListener(this);
//        llSysSetting.setOnClickListener(this);
//        llHelpText.setOnClickListener(this);
//        llContactUpdate.setOnClickListener(this);
//        llAboutUs.setOnClickListener(this);
//    }

//    public void uploadHeadImag(String localImgUrl) {
//        File file = new File(localImgUrl);
//        if(file.exists()){
//            final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(),"上传头像中");
//            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//            MultipartBody.Part part = MultipartBody.Part.createFormData("head", file.getName(), requestBody);
//            Subscription subscribe = HttpClient.Builder.getAppService().uploadHeadImag(part)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new HttpResultSubscriber<String>() {
//                        @Override
//                        public void onSuccess(String headImag) {
//                            dialog.dismiss();
//                            if (!TextUtils.isEmpty(headImag)) {
//                                Glide.with(HomeFragment.this).load(headImag).placeholder(R.mipmap.icon_launcher)
//                                        .bitmapTransform(new GlideCircleTransform(HomeFragment.this.getContext(),2,getResources().getColor(R.color.white))).into(ivHeadImg);
//                            }
//                        }
//
//                        @Override
//                        public void _onError(String msg, int code) {
//                            dialog.dismiss();
//                        }
//                    });
//            addSubscription(subscribe);
//        }
//    }

//    private void setUIState(UserInforBean userInforBean) {
//        ivRedCircle.setVisibility(View.GONE);
//        tvPhoneNum.setText(userInforBean.getPhone());
//        tvYue.setText(String.valueOf(userInforBean.getBalance()));
//        tvCommission.setText("¥ "+String.valueOf(userInforBean.getCommission()));
//        tvCoupon.setText(String.valueOf(userInforBean.getCoupon())+"张");
//        String headUrl = userInforBean.getHead();
//        if(NetworkUtil.isConnection(this.getActivity().getApplicationContext())){
//            GlideCircleTransform glideCircleTransform = new GlideCircleTransform(this.getActivity().getApplicationContext(),2,getResources().getColor(R.color.theme_white));
//            Glide.with(this).load(headUrl).centerCrop().placeholder(R.mipmap.icon_launcher).bitmapTransform(glideCircleTransform).into(ivHeadImg);
//        }else{
//            getImagFromCache(headUrl);
//        }
//    }

//    private void showPic(final File cacheFile) {
//        Log.d("czc",cacheFile.getAbsolutePath());
//        GlideCircleTransform glideCircleTransform = new GlideCircleTransform(this.getActivity().getApplicationContext(),2,getResources().getColor(R.color.theme_white));
//        Glide.with(this).load(cacheFile).centerCrop().placeholder(R.mipmap.icon_launcher).bitmapTransform(glideCircleTransform).into(ivHeadImg);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(v.getId() == R.id.ll_sys_setting){
//            Intent intent2 = new Intent(this.getActivity(), AccountSettingActivity.class);
//            startActivity(intent2);
//            return;
//        }
//
//        if(v.getId() == R.id.ll_contact_update){
//            List<ServerUpContactsBean> contats = getContats();
//            String jsonData = new Gson().toJson(contats);
//            upContacts(jsonData);
//            return;
//
//        }
//        setUrlItemClickMethod(v);
//    }
//
//
//    private void upContacts(String jsonData) {
//
//        final Dialog dialog = DialogUtil.getInstance().showDialog(getActivity(),"上传中");
//        Subscription subscription = HttpClient.Builder.getAppService().serverUpContacts(jsonData)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        dialog.dismiss();
//                        ToastUtils.showLongToast("上传成功");
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
//        addSubscription(subscription);
//    }
//
//    private List<ServerUpContactsBean> getContats() {
//        List<ServerUpContactsBean> serverUpContactsBeanList = new ArrayList<>();
//        List<ContactsBean> mDataSet1 = MyApplication.mDataSet1;
//        for (ContactsBean contactsBean : mDataSet1) {
//            String contactsName = contactsBean.getContactsName();
//            String phoneNum = "";
//            if(contactsBean.getContactsNum()!= null && contactsBean.getContactsNum().size()>0){
//                phoneNum = contactsBean.getContactsNum().get(0);
//            }
//
//            if(!TextUtils.isEmpty(contactsName) && !TextUtils.isEmpty(phoneNum)){
//                ServerUpContactsBean serverUpContactsBean = new ServerUpContactsBean(contactsName,phoneNum);
//                serverUpContactsBeanList.add(serverUpContactsBean);
//            }
//        }
//        return serverUpContactsBeanList;
//    }
//
//
//
//    private void setUrlItemClickMethod(View v) {
//        String subUrl = "";
//        Intent intent = new Intent(this.getActivity(), TaoWebviewActivity.class);
//        switch (v.getId()){
//            case R.id.ll_commission:
//                subUrl = "user/commission";
//                break;
//            case R.id.ll_discont:
//                subUrl = "user/coupon";
//                break;
//            case R.id.ll_my_incom:
//                subUrl = "user/commission";
//                break;
//            case R.id.ll_my_team:
//                subUrl = "team/getsublist"; //我的团队
//                break;
//            case R.id.ll_my_generalize:
//                subUrl = "user/spread";
//                break;
//            case R.id.ll_withdraw:
//                subUrl = "team/mywithdraw";
//                break;
//            case R.id.ll_mobile_prepaid: //话费充值
//                subUrl = "user/rechange";
//                break;
//            case R.id.ll_phone_bill: //话费查询
//                subUrl = "/user/balance";
//                break;
//            case R.id.ll_feed_back:
//                subUrl = "user/feedback";
//                break;
//            case R.id.ll_share:
//                subUrl = "user/spread";
//                break;
//            case R.id.ll_help_text:
//                subUrl = "user/help";
//                break;
//            case R.id.ll_about_us:
//                subUrl = "user/about";
//                break;
//        }
//        String url = HttpUtils.API_APP+"split/"+subUrl;
//        intent.putExtra(AppConstant.KEY_WEB_URL,url);
//        startActivity(intent);
//    }
//
//    private void getImagFromCache(final String url) {
//        Subscription subscribe = Observable.create(new Observable.OnSubscribe<File>() {
//            @Override
//            public void call(Subscriber<? super File> subscriber) {
//
//                DebugUtil.debug( "call()执行线程是"
//                        + Thread.currentThread().getName() + "线程优先级="
//                        + Thread.currentThread().getPriority()
//                        + "  线程id=" + Thread.currentThread().getId());
//                //在子线程中执行耗时操作
//                try {
//                    File file = Glide.with(HomeFragment.this.getContext())
//                            .load(url)
//                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                            .get();
//                    subscriber.onNext(file);
//                    subscriber.onCompleted();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onNext(null);
//                    subscriber.onCompleted();
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<File>() {
//
//                    @Override
//                    public void onNext(File file) {
//                        if (file != null) showPic(file);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//
//    }

//}
