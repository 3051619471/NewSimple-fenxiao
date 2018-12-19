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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.studyvideo.adapter.Enroll_Adapter;
import com.astgo.naoxuanfeng.studyvideo.bean.Enroll_Title_Bean;
import com.astgo.naoxuanfeng.studyvideo.bean.Enroll_name_Bean;
import com.astgo.naoxuanfeng.studyvideo.view.WebView_Enroll_Activity;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/11/26.
 */

public class Name_Enroll_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * 会议选择
     */
    private EditText mMentting;
    /**
     * 请输入姓名
     */
    private EditText mEditName;
    /**
     * 请输入手机号
     */
    private EditText mEditPhone;
    /**
     * 提交
     */
    private Button mSubmit;
    private RecyclerView mRecycleEnroll;
    ConstantURL constantURL = new ConstantURL();
    String ip_url = constantURL.IP_url;
    private String path = ip_url + "index.php?s=/Home/study/getMeeting";
    private String path_name;
    private String uid;
    private List<Enroll_Title_Bean.DataBean> data;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data = (List<Enroll_Title_Bean.DataBean>) msg.obj;
            Enroll_Adapter enroll_adapter = new Enroll_Adapter(data, getActivity());
            mRecycleEnroll.setAdapter(enroll_adapter);
            mRecycleEnroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            enroll_adapter.OnItemClickListenerEnroll(new Enroll_Adapter.OnItemClickListenerEnroll() {
                @Override
                public void onClick(int position) {
                    String content = data.get(position).getContent().trim().replaceAll(" ","");
                    Intent intent = new Intent(getActivity(), WebView_Enroll_Activity.class);
                    intent.putExtra( "content",content );
                    startActivity(intent);
                }
            });
        }
    };
    private String enroll;
    private String name;
    private String phone;
    private String id;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_enroll_ment, container, false);
        initView(view);
        initNameEnroll();
        return view;
    }

    private void initNameEnroll() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Enroll_Title_Bean enroll_title_bean = gson.fromJson(string, Enroll_Title_Bean.class);
                List<Enroll_Title_Bean.DataBean> data = enroll_title_bean.getData();
                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);

            }
        });
    }

    private void initView(View view) {
        mRecycleEnroll = (RecyclerView) view.findViewById(R.id.recycle_enroll);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mSubmit = (Button) view.findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                //请求保存的数据
                SharedPreferences checkbox = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                id = checkbox.getString("id", "");
                SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                uid = data1.getString("uid", "");
               // enroll = mMentting.getText().toString().trim();
                name = mEditName.getText().toString().trim();
                phone = mEditPhone.getText().toString().trim();
                path_name=ip_url+"index.php?s=/Home/study/putApply&id="+ id +"&uid="+uid+"&name="+name+"&phone="+phone;
                nameEnroll();
                break;
        }
    }
    private void nameEnroll() {
        final OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path_name, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Enroll_name_Bean enroll_name_bean = gson.fromJson(string, Enroll_name_Bean.class);
                int code = enroll_name_bean.getCode();
                   if (code==1){
                       if (id.length()!=0&&name.length()!=0&&phone.length()!=0) {
                           Toast.makeText(getActivity(), "报名成功", Toast.LENGTH_SHORT).show();
                         /*  getFragmentManager().beginTransaction()
                                   .replace(R.id.fragment, new My_Enroll_Fragment())
                                   .commit();*/
                       }else {
                           Toast.makeText(getActivity(), "信息不能为空", Toast.LENGTH_SHORT).show();
                       }

                   }else {
                       Toast.makeText(getActivity(), "报名失败", Toast.LENGTH_SHORT).show();

                   }



            }
        });
    }
}
