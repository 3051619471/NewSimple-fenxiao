package com.astgo.naoxuanfeng.studyvideo.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.classdomel.class_utils.RecyclerViewItemDecoration;
import com.astgo.naoxuanfeng.glide.GlideImageLoader;
import com.astgo.naoxuanfeng.studyvideo.adapter.Video_Adapter;
import com.astgo.naoxuanfeng.studyvideo.bean.Enroll_Bean;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Banner_Bean;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Study_Bean;
import com.astgo.naoxuanfeng.studyvideo.uitls.MarqueeTextView;
import com.astgo.naoxuanfeng.studyvideo.view.Enroll_Activity;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/11/23.
 */

public class Study_Video_Fragment extends Fragment {
    private Banner mBanner;
    private List<String> listimg;
    private String id;
    private XRecyclerView mXrecyclShop;
    private String path, path_my;
    private RelativeLayout activityListVideo;
    private View view;
    private MarqueeTextView text_video;
    //private VerticalTextview text_video;
    private ArrayList<String> text_data = new ArrayList<>();
    private ArrayList<String> banner_data = new ArrayList<>();
    private List<String> enroll_data = new ArrayList<>();
    private LinearLayout linear_video;
    private String ip_url;
    private List<Video_Banner_Bean.DataBean> data_banner;
    private String path_banner;
    private List<Enroll_Bean.DataBean> data_my;
    private String name;
    private String phone;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Video_Study_Bean.DataBean> data = ( List<Video_Study_Bean.DataBean>) msg.obj;
            initImg();

            initpaoming();
           Video_Adapter video_adapter = new Video_Adapter(data, getActivity());
            mXrecyclShop.setAdapter(video_adapter);
            // mXrecyclShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mXrecyclShop.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            // StaggeredGridLayoutManager管理RecyclerView的布局。
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mXrecyclShop.setLayoutManager(mLayoutManager);

            //为RecyclerView增加分割线，水平和垂直方向都有。增加分割线值比如为32。
            RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(32);
            mXrecyclShop.addItemDecoration(decoration);

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

    private String imgurl;
    private MarqueeView mMarqueeView;


    //回调用来接收参数
    public static Study_Video_Fragment getiniturl(String id) {
        Study_Video_Fragment study_video_fragment = new Study_Video_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("idvideo", id);
        study_video_fragment.setArguments(bundle);
        return study_video_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.study_video_ment, container, false);
        setRetainInstance(true);
        initView(view);
        //请求保存的数据
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String uid = data1.getString("uid", "");
        Bundle arguments = getArguments();
        id = arguments.getString("idvideo");

        //保存id
        SharedPreferences videoid = getActivity().getSharedPreferences("videoid", MODE_PRIVATE);
        SharedPreferences.Editor editor = videoid.edit();
        editor.putString("id", id);
        editor.commit();
        ConstantURL constantURL = new ConstantURL();
        ip_url = constantURL.IP_url;
        if (id != null) {
            //视频
            path = ip_url + "index.php?s=/Home/video/videoResource&id=" + id + "&uid=" + uid;
            //轮播图
            path_banner = ip_url + "index.php?s=/Home/video/getVideoImg";
            //报名人
            path_my = ip_url + "/index.php?s=/Home/study/getApply";
        } else {
            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
        }
        initBanner();
        initPly();
        initResource();
        initMyEnroll();

        return view;
    }

    private void initImg() {
        mBanner.setImageLoader(new GlideImageLoader()); //设置图片加载器
        mBanner.setImages(banner_data);//设置图片源
        mBanner.setDelayTime(1500);//设置轮播事件，单位毫秒
        //  mBanner.setBannerAnimation(Transformer.Accordion);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
        mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        mBanner.isAutoPlay(true);   //设置是否为自动轮播，默认是“是”。
        mBanner.start();//开始轮播，一定要调用此方法。
    }


    private void initPly() {
        mXrecyclShop.setPullRefreshEnabled(true);
        mXrecyclShop.setLoadingMoreEnabled(true);
        mXrecyclShop.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initImg();
                initBanner();
                initMyEnroll();
                mXrecyclShop.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mXrecyclShop.loadMoreComplete();
            }
        });
    }


    //视频解析
    private void initResource() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Video_Study_Bean video_study_bean = gson.fromJson(string, Video_Study_Bean.class);
                List<Video_Study_Bean.DataBean> data = video_study_bean.getData();
               Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);
                //Resource_Adapter resource_shop_adapter = new Resource_Adapter(data, getActivity());

            }
        });
    }
    //报名
    private void initMyEnroll() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_my, new OkhttpUtils.fun1() {

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Enroll_Bean enroll_bean = gson.fromJson(string, Enroll_Bean.class);
                data_my = enroll_bean.getData();

                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < data_my.size(); i++) {
                            name = data_my.get(i).getName();
                            phone = data_my.get(i).getPhone();
                            enroll_data.add(name);
                            enroll_data.add(phone);
                            Log.d("TAGS",enroll_data.get(0)+"");
                        }
                        initpaoming();
                    }
                }).start();


                     }
        });
    }

    private void initpaoming() {
        mMarqueeView.startWithList(enroll_data);
        mMarqueeView.startWithList(enroll_data, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

    /**
     * 检查是否是电话号码
     *
     * @return
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //轮播图
    private void initBanner() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_banner, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Video_Banner_Bean video_banner_bean = gson.fromJson(string, Video_Banner_Bean.class);
                data_banner = video_banner_bean.getData();

                Log.d("TAGSS", ip_url + data_banner.get(0).getImgurl() + "" + ip_url + data_banner.get(1).getImgurl() + "" + ip_url + data_banner.get(2).getImgurl() + "" + ip_url + data_banner.get(3).getImgurl());
                banner_data = new ArrayList<>();
                for (int i = 0; i < data_banner.size(); i++) {
                    imgurl = ip_url + data_banner.get(i).getImgurl();
                    banner_data.add(imgurl);
                }

            }

        });
    }

    private void initView(View view) {
        mBanner = (Banner) view.findViewById(R.id.banner_video);
        mXrecyclShop = (XRecyclerView) view.findViewById(R.id.xrecycl_video);
        activityListVideo = view.findViewById(R.id.activity_list_video);
        linear_video = view.findViewById(R.id.linear_video);
        mMarqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        //报名集合
        //text_video.setText(text_data.size());//加入显示内容,集合类型
        linear_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Enroll_Activity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        //text_video.stopAutoScroll();
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
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
