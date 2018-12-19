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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.glide.GlideImageLoader;
import com.astgo.naoxuanfeng.studyvideo.bean.Enroll_Bean;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Banner_Bean;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Study_Bean;
import com.astgo.naoxuanfeng.studyvideo.uitls.MarqueeTextView;
import com.astgo.naoxuanfeng.studyvideo.view.Enroll_Activity;
import com.astgo.naoxuanfeng.studyvideo.view.Free_Video_Activity;
import com.astgo.naoxuanfeng.studyvideo.view.My_Video_Activity;
import com.astgo.naoxuanfeng.studyvideo.view.Small_Video_Activity;
import com.astgo.naoxuanfeng.studyvideo.view.VIP_Video_Activity;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/12/5.
 */
//小视频模块
public class Tab_XVideo_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private Banner mBannerXvideo;
    private MarqueeTextView mTextXvideo;
    private LinearLayout mLinearXvideo;
    private String path_banner, path_my, path;
    private String ip_url;
    private List<Enroll_Bean.DataBean> data_my;
    private ArrayList<String> banner_video = new ArrayList<>();
    private List<Video_Banner_Bean.DataBean> video_banner;
    private String imgurl;
    /**
     * 我的视频
     */
    private TextView mMyText;
    /**
     * 免费视频
     */
    private TextView mFreeText;
    /**
     * VIP视频
     */
    private TextView mVipText;
    /**
     * 小视频
     */
    private TextView mSmallText;
    /**
     * 我的视频
     */
    private RecyclerView mMyVideoRecycle;
    /**
     * 免费视频
     */
    private RecyclerView mFreeVideoRecycle;
    /**
     * VIP视频
     */
    private RecyclerView mVipVideoRecycle;
    /**
     * 小视频
     */
    private RecyclerView mSmallVideoRecycle;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Video_Study_Bean.DataBean> data = (List<Video_Study_Bean.DataBean>) msg.obj;
           /* //我的视频
            Study_MyVideo_Adapter study_myVideo_adapter = new Study_MyVideo_Adapter(data, getActivity());
            mMyVideoRecycle.setAdapter(study_myVideo_adapter);
            mMyVideoRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

            //免费视频
            Study_FreeVideo_Adapter study_freeVideo_adapter =
                    new Study_FreeVideo_Adapter(data, getActivity());
            mFreeVideoRecycle.setAdapter(study_freeVideo_adapter);
            mFreeVideoRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

            //VIP视频

            Study_VIPVideo_Adapter study_vipVideo_adapter = new Study_VIPVideo_Adapter(data, getActivity());
            mVipVideoRecycle.setAdapter(study_vipVideo_adapter);
            mVipVideoRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

            //小视频

            Study_SmallVideo_Adapter study_smallVideo_adapter = new Study_SmallVideo_Adapter(data, getActivity());
            mSmallVideoRecycle.setAdapter(study_smallVideo_adapter);
            mSmallVideoRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
*/
        }
    };
    private LinearLayout mMyLinear;
    private LinearLayout mFreeLinear;
    private LinearLayout mVipLinear;
    private LinearLayout mSmallLinear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_xvideo_ment, container, false);
        //获取资源id
        initView(view);
        //请求保存的数据
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String uid = data1.getString("uid", "");
        ConstantURL constantURL = new ConstantURL();
        ip_url = constantURL.IP_url;
        //轮播图
        path_banner = ip_url + "index.php?s=/Home/video/getVideoImg";
        //报名人
        path_my = ip_url + "/index.php?s=/Home/study/getApply";
        //视频
        path = ip_url + "index.php?s=/Home/video/videoResource&id=1&uid=" + uid;
        //轮播图
        initBanner();
        //报名请求数据
        initMyEnroll();
        //我的视频
        //  initMyVideo();
        //免费视频
        // initFreeVideo();
        //VIP视频
        // initVipVideo();
        //小视频
        //  initSmallVideo();
        return view;
    }

    private void initSmallVideo() {
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

            }
        });
    }

    private void initVipVideo() {
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

            }
        });
    }

    private void initFreeVideo() {
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

            }
        });
    }

    private void initMyVideo() {
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

            }
        });

    }

    private void initImg() {
        mBannerXvideo.setImageLoader(new GlideImageLoader()); //设置图片加载器
        mBannerXvideo.setImages(banner_video);//设置图片源
        mBannerXvideo.setDelayTime(1500);//设置轮播事件，单位毫秒
        //  mBanner.setBannerAnimation(Transformer.Accordion);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
        mBannerXvideo.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        mBannerXvideo.isAutoPlay(true);   //设置是否为自动轮播，默认是“是”。
        mBannerXvideo.start();//开始轮播，一定要调用此方法。
    }

    //报名
    private void initMyEnroll() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_my, new OkhttpUtils.fun1() {
            private String phone;
            private String name;

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Enroll_Bean enroll_bean = gson.fromJson(string, Enroll_Bean.class);
                data_my = enroll_bean.getData();
                for (int i = 0; i < data_my.size(); i++) {

                  //  mTextXvideo.setText("用户:" + data_my.get(0).getName() + "|" + "手机号:" et(10).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(13).getName() + "|" + "手机号:" + data_my.get(13).getPhone());
                    mTextXvideo.setText("用户:" + data_my.get(0).getName() + "|" + "手机号:" + data_my.get(0).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(1).getName() + "|" + "手机号:" + data_my.get(1).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(2).getName() + "|" + "手机号:" + data_my.get(2).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(3).getName() + "|" + "手机号:" + data_my.get(3).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(4).getName() + "|" + "手机号:" + data_my.get(4).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(5).getName() + "|" + "手机号:" + data_my.get(5).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(6).getName() + "|" + "手机号:" + data_my.get(6).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(7).getName() + "|" + "手机号:" + data_my.get(7).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(8).getName() + "|" + "手机号:" + data_my.get(8).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(9).getName() + "|" + "手机号:" + data_my.get(9).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(10).getName() + "|" + "手机号:" + data_my.get(10).getPhone() + "\t\t\t\t\t\t" + "用户:" + data_my.get(13).getName() + "|" + "手机号:" + data_my.get(13).getPhone());

                }
            }
        });
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
                video_banner = video_banner_bean.getData();

             //   Log.d("TAGtag", ip_url + video_banner.get(0).getImgurl() + "" + ip_url + video_banner.get(1).getImgurl());

                for (int i = 0; i < video_banner.size(); i++) {
                    imgurl = ip_url + video_banner.get(i).getImgurl();
                    banner_video.add(imgurl);

                }
                //轮播图加载
                initImg();
            }


        });
    }

    private void initView(View view) {
        mBannerXvideo = (Banner) view.findViewById(R.id.banner_xvideo);
        mTextXvideo = (MarqueeTextView) view.findViewById(R.id.text_xvideo);
        mLinearXvideo = (LinearLayout) view.findViewById(R.id.linear_xvideo);
        //报名集合
        mLinearXvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Enroll_Activity.class);
                startActivity(intent);

            }
        });

        /*mMyText = (TextView) view.findViewById(R.id.my_text);
        mMyText.setOnClickListener(this);
        mFreeText = (TextView) view.findViewById(R.id.free_text);
        mFreeText.setOnClickListener(this);
        mVipText = (TextView) view.findViewById(R.id.vip_text);
        mVipText.setOnClickListener(this);
        mSmallText = (TextView) view.findViewById(R.id.small_text);
        mSmallText.setOnClickListener(this);*/
       /* mMyVideoRecycle = (RecyclerView) view.findViewById(R.id.my_video_recycle);
        mFreeVideoRecycle = (RecyclerView) view.findViewById(R.id.free_video_recycle);
        mVipVideoRecycle = (RecyclerView) view.findViewById(R.id.vip_video_recycle);
        mSmallVideoRecycle = (RecyclerView) view.findViewById(R.id.small_video_recycle);*/
        mMyLinear = (LinearLayout) view.findViewById(R.id.my_linear);
        mMyLinear.setOnClickListener(this);
        mFreeLinear = (LinearLayout) view.findViewById(R.id.free_linear);
        mFreeLinear.setOnClickListener(this);
        mVipLinear = (LinearLayout) view.findViewById(R.id.vip_linear);
        mVipLinear.setOnClickListener(this);
        mSmallLinear = (LinearLayout) view.findViewById(R.id.small_linear);
        mSmallLinear.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的视频
            case R.id.my_linear:
                Intent intent_my = new Intent(getActivity(), My_Video_Activity.class);
                startActivity(intent_my);
                break;
            //免费视频
            case R.id.free_linear:
                Intent intent_free = new Intent(getActivity(), Free_Video_Activity.class);
                startActivity(intent_free);
                break;
            //VIP视频
            case R.id.vip_linear:
                Intent intent_vip = new Intent(getActivity(), VIP_Video_Activity.class);
                startActivity(intent_vip);
                break;
            //小视频
            case R.id.small_linear:
                Intent intent_small = new Intent(getActivity(), Small_Video_Activity.class);
                startActivity(intent_small);
                break;
        }
    }


}
