package com.astgo.naoxuanfeng.studyvideo.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.studyvideo.adapter.My_Enroll_Adapter;
import com.astgo.naoxuanfeng.studyvideo.bean.My_Enroll_Bean;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/11/26.
 */

public class My_Enroll_Fragment extends Fragment {
    private View view;
    private XRecyclerView mRecycleMy;

private String path_my;
@SuppressLint("HandlerLeak")
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        List<My_Enroll_Bean.DataBean> data= (List<My_Enroll_Bean.DataBean>) msg.obj;
        My_Enroll_Adapter my_enroll_adapter = new My_Enroll_Adapter(data, getActivity());
        mRecycleMy.setAdapter(my_enroll_adapter);
        mRecycleMy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //添加Android自带的分割线
        mRecycleMy.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        my_enroll_adapter.notifyDataSetChanged();

    }
};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_enroll_ment, container, false);
        ConstantURL constantURL = new ConstantURL();
        String ip_url = constantURL.IP_url;
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String uid1 = data1.getString("uid", "");
        initView(view);
        path_my=ip_url +"index.php?s=/Home/study/userApply&uid="+uid1;
       // Toast.makeText(getActivity(), uid1+"", Toast.LENGTH_SHORT).show();
        initMyEnroll();
        initply();
        return view;
    }

    private void initply() {

        mRecycleMy.setPullRefreshEnabled( true );
        mRecycleMy.setLoadingMoreEnabled( true );
        mRecycleMy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initMyEnroll();
                mRecycleMy.refreshComplete();
            }

            @Override
            public void onLoadMore() {
               // initMyEnroll();
                mRecycleMy.loadMoreComplete();
            }
        });
    }

    private void initView(View view) {
        mRecycleMy =  view.findViewById(R.id.recycle_my);
    }
    private void initMyEnroll() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_my, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                My_Enroll_Bean my_enroll_bean = gson.fromJson(string, My_Enroll_Bean.class);
                List<My_Enroll_Bean.DataBean> data = my_enroll_bean.getData();
                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);

            }
        });
    }
}
