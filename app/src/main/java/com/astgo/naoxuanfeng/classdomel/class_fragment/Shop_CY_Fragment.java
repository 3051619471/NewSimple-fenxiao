package com.astgo.naoxuanfeng.classdomel.class_fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.NetUrl;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.activity.LoginActivity;
import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.classdomel.class_adapter.Resource_Adapter;
import com.astgo.naoxuanfeng.classdomel.class_adapter.Resource_Shop_Adapter;
import com.astgo.naoxuanfeng.classdomel.class_bean.Class_Banner_Bean;
import com.astgo.naoxuanfeng.classdomel.class_bean.Resource_Bean;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.classdomel.class_utils.RecyclerViewItemDecoration;
import com.astgo.naoxuanfeng.glide.GlideImageLoader;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/11/21.
 */

public class Shop_CY_Fragment extends Fragment  {
    private View view;
    private Banner mBanner;
    private List<String> listimg;
    private String id;
    private XRecyclerView mXrecyclShop;
    private String path, path_banner;
    private LinearLayout activityListVideo;

    private List<String> banner_datas = new ArrayList<>();
    private List<Class_Banner_Bean.DataBean> data_banne;
    private static final String TAG = Shop_CY_Fragment.class.getSimpleName();
    private static final boolean DEBUG = true; // Set this to false to disable logs.

    private static final String TAG_TASK_FRAGMENT = "task_fragment";

    private Resource_Shop_Adapter resource_shop_adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Resource_Bean.DataBean> data = (List<Resource_Bean.DataBean>) msg.obj;
            initDataImg();
            //Resource_Adapter resource_shop_adapter = new Resource_Adapter(data, getActivity());
            resource_shop_adapter = new Resource_Shop_Adapter(data, getActivity());
            mXrecyclShop.setAdapter(resource_shop_adapter);
            // mXrecyclShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mXrecyclShop.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            // StaggeredGridLayoutManager管理RecyclerView的布局。
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mXrecyclShop.setLayoutManager(mLayoutManager);
            //为RecyclerView增加分割线，水平和垂直方向都有。增加分割线值比如为32。
            RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(32);
            mXrecyclShop.addItemDecoration(decoration);
            resource_shop_adapter.notifyDataSetChanged();
            mXrecyclShop.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {


                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {
                    Jzvd jzvd = view.findViewById(R.id.videoplayer);
                    if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                        Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                        if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                            Jzvd.releaseAllVideos();
                        }
                    }
                }
            });
        }
    };
    private Resource_Adapter resource_adapter;
    private String path_img;
    private String ip_url,pathsou;
    private EditText mEditSousuo;
    /**
     * 搜索
     */
    private TextView mButSousuo;
    private String uid;
private Boolean but=false;
    //回调用来接收参数
    public static Shop_CY_Fragment getiniturl(String id) {
        Shop_CY_Fragment shop_cy_fragment = new Shop_CY_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("ids", id);
        shop_cy_fragment.setArguments(bundle);
        return shop_cy_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_cy_ment, container, false);
        setRetainInstance(true);
        initView(view);
        //请求保存的数据
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        uid = data1.getString("uid", "");
        SharedPreferences data2 = getActivity().getSharedPreferences("id_banner", MODE_PRIVATE);
        String id_banner = data2.getString("id", "");
        ConstantURL constantURL = new ConstantURL();
        ip_url = constantURL.IP_url;
        path_banner = ip_url + "index.php?s=/Home/study/studyPic&id=" + id_banner;
        Bundle arguments = getArguments();
        id = arguments.getString("ids");

        if (id != null) {
            path = ip_url + "index.php?s=/Home/study/studyResource&id=" + id + "&uid=" + uid;
        } else {
            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
        }
        initPly();
        initBanner();
        initResource();

            //搜索框的点击事件
            mButSousuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mEditSousuo.getText().toString().trim();
                    //搜索
                    if (title!=null){
                        pathsou=ip_url +"index.php?s=/Home/study/studyResource&id=" + id + "&uid=" + uid+"&title="+title;
                        initSearch();
                    }else if (title==null){
                        initResource();
                        //Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return view;

    }
    private void initPly() {
        mXrecyclShop.setPullRefreshEnabled(true);
        mXrecyclShop.setLoadingMoreEnabled(true);
        mXrecyclShop.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initDataImg();
                initBanner();
                mXrecyclShop.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                mXrecyclShop.loadMoreComplete();
            }
        });
    }

    private void initDataImg() {
        mBanner.setImageLoader(new GlideImageLoader()); //设置图片加载器
        mBanner.setImages(banner_datas);//设置图片源
        mBanner.setDelayTime(1500);//设置轮播事件，单位毫秒
        //  mBanner.setBannerAnimation(Transformer.Accordion);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
        mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        mBanner.isAutoPlay(true);   //设置是否为自动轮播，默认是“是”。
        mBanner.start();//开始轮播，一定要调用此方法。
    }
//搜索
private void initSearch() {
    OkhttpUtils getshu = OkhttpUtils.getshu();
    getshu.say(pathsou, new OkhttpUtils.fun1() {
        @Override
        public void onres(String string) {
            Gson gson = new Gson();
            Resource_Bean resource_bean = gson.fromJson(string, Resource_Bean.class);
            List<Resource_Bean.DataBean> data = resource_bean.getData();
            resource_shop_adapter = new Resource_Shop_Adapter(data, getActivity());
            mXrecyclShop.setAdapter(resource_shop_adapter);
            // mXrecyclShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mXrecyclShop.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            // StaggeredGridLayoutManager管理RecyclerView的布局。
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mXrecyclShop.setLayoutManager(mLayoutManager);
            //为RecyclerView增加分割线，水平和垂直方向都有。增加分割线值比如为32。
            RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(32);
            mXrecyclShop.addItemDecoration(decoration);
            resource_shop_adapter.notifyDataSetChanged();
        }
    });
}
    private void initResource() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Resource_Bean resource_bean = gson.fromJson(string, Resource_Bean.class);
                List<Resource_Bean.DataBean> data = resource_bean.getData();
                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);

            }
        });
    }

    private void initBanner() {

        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_banner, new OkhttpUtils.fun1() {
            private String pic;

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Class_Banner_Bean class_banner_bean = gson.fromJson(string, Class_Banner_Bean.class);
                data_banne = class_banner_bean.getData();
                banner_datas = new ArrayList<>();
                for (int i = 0; i < data_banne.size(); i++) {
                    pic = ip_url + data_banne.get(i).getPic();
                    banner_datas.add(pic);
                }

            }
        });

    }

    private void initView(View view) {
        mBanner = (Banner) view.findViewById(R.id.banner);
        mXrecyclShop = (XRecyclerView) view.findViewById(R.id.xrecycl_shop);
        activityListVideo = view.findViewById(R.id.activity_list_video);
        mEditSousuo = (EditText) view.findViewById(R.id.edit_sousuo);
        mButSousuo = (TextView) view.findViewById(R.id.but_sousuo);

    }

    private void initBannerClass() {
        OkHttpUtils.get(NetUrl.BANNER_CLASS)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("getBannerData")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {

                    private String pic;

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("getBannerData" + s);
                        //{"data":[{"title":"\u6807\u98981","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48da3b4bf.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6863\u4f4d\u5546\u54c1","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5ac3439b29f58.png","jumpurl":"\/home\/index\/lvgoods"},{"title":"\u6807\u98983","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48a028e85.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6807\u98982","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48fd48b9c.png","jumpurl":"www.baidu.com"},{"title":"\u6807\u98984","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d491c077b1.png","jumpurl":"www.baidu.com"}],"info":"success","status":1}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
                                Class_Banner_Bean baseBean = (Class_Banner_Bean) BaseDataObject.getBaseBean(s, Class_Banner_Bean.class);
                                data_banne = baseBean.getData();
                                banner_datas = new ArrayList<>();
                                for (int i = 0; i < data_banne.size(); i++) {
                                    pic = ip_url + data_banne.get(i).getPic();
                                    banner_datas.add(pic);
                                }
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100) {
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin", false);
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
    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getActivity(), "退出", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
