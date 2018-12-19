/*
package com.astgo.naoxuanfeng.classdomel.class_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_adapter.Resource_Shop_Adapter;
import com.astgo.naoxuanfeng.classdomel.class_bean.Resource_Bean;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.google.gson.Gson;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


public class Resource_Shop_Activity extends AppCompatActivity {

    private RecyclerView recycle_resource;
    private String path;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Resource_Bean.DataBean> data = (List<Resource_Bean.DataBean>) msg.obj;
            Resource_Shop_Adapter resource_shop_adapter = new Resource_Shop_Adapter(data, Resource_Shop_Activity.this);
            recycle_resource.setAdapter(resource_shop_adapter);
            recycle_resource.setLayoutManager(new LinearLayoutManager(Resource_Shop_Activity.this, LinearLayoutManager.VERTICAL, false));

        }
    };

    private TextView tv_app_title;
    private ImageView iv_app_bar_back;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_shop);
        //请求保存的数据
        SharedPreferences data1 = getSharedPreferences("data", MODE_PRIVATE);
        uid = data1.getString("uid", "");
        ConstantURL constantURL = new ConstantURL();
        String IP_url = constantURL.IP_url;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");
        String name = bundle.getString("name");
        //String uid = bundle.getString("uid");
       // Log.e("RZJ",uid+"");
        if (id != null) {
            path = IP_url+"index.php?s=/Home/study/studyResource&id="+id+"&uid="+uid;
            //Toast.makeText(this, uid+"ui", Toast.LENGTH_SHORT).show();

        }else {

            Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
        }
        recycle_resource = findViewById(R.id.recycle_resource);
        //标题
        tv_app_title = findViewById(R.id.tv_app_title);
        //返回
        iv_app_bar_back = findViewById(R.id.iv_app_bar_back);
        tv_app_title.setText(name);
        iv_app_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initResource();
    }

    private void initResource() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {

            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Resource_Bean resource_bean = gson.fromJson(string, Resource_Bean.class);
                Toast.makeText(Resource_Shop_Activity.this, resource_bean+"", Toast.LENGTH_SHORT).show();
                List<Resource_Bean.DataBean> data = resource_bean.getData();
                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
*/
