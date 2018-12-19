package com.astgo.naoxuanfeng.studyvideo.video_fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.classdomel.class_utils.RecyclerViewItemDecoration;
import com.astgo.naoxuanfeng.studyvideo.adapter.Video_Whale_Adapter;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Study_Bean;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/12/5.
 */
//小视频
public class Small_Fragment extends Fragment {
    private View view;
    private XRecyclerView mVideoSmall;
    private String path;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Video_Study_Bean.DataBean> data = ( List<Video_Study_Bean.DataBean>) msg.obj;
            Video_Whale_Adapter video_whale_adapter = new Video_Whale_Adapter(data, getActivity());
            mVideoSmall.setAdapter(video_whale_adapter);
            // mXrecyclShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mVideoSmall.setLayoutManager(new GridLayoutManager(getActivity(),2));
            // StaggeredGridLayoutManager管理RecyclerView的布局。
            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mVideoSmall.setLayoutManager(mLayoutManager);

            //为RecyclerView增加分割线，水平和垂直方向都有。增加分割线值比如为32。
            RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(32);
            mVideoSmall.addItemDecoration(decoration);
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_video_ment, container, false);
        initView(view);
        ConstantURL constantURL = new ConstantURL();
        String ip_url = constantURL.IP_url;
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String  uid = data1.getString("uid", "");
        //视频
        path = ip_url +"index.php?s=/Home/study/studyResource&id=13&uid="+uid;
        initResource();
        return view;
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

            }
        });
    }
    private void initView(View view) {
        mVideoSmall = (XRecyclerView) view.findViewById(R.id.video_small);
    }
}
