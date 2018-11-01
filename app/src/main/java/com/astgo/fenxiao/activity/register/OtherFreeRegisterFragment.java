package com.astgo.fenxiao.activity.register;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFreeRegisterFragment extends Fragment implements View.OnClickListener {

    private TextView tvError;
    private EditText etPhone;
    private EditText etPassword;
    private ProgressDialog dialog;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            redirect();
        }
    };
    private MyTextWatcher mWatcher = new MyTextWatcher();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_free_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvError = (TextView) view.findViewById(R.id.tv_error);
        etPhone = (EditText) view.findViewById(R.id.et_phone);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etPhone.addTextChangedListener(mWatcher);
        if (MyApplication.NEED_CREATE_PWD) {
            etPassword.setVisibility(View.VISIBLE);
            etPassword.addTextChangedListener(mWatcher);
        } else {
            etPassword.setVisibility(View.GONE);
        }
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    // 获取密码
    private String getPassword() {
        String pwd;
        if (MyApplication.NEED_CREATE_PWD) {
            pwd = etPassword.getText().toString();
        } else {
            pwd = "168168";
        }
        return pwd;
    }

    // 设置免费注册参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, etPhone.getText().toString());
        urlParams.put(MyConstant.REGPWD, getPassword());
        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
        urlParams.put(MyConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (MyApplication.NEED_CREATE_PWD) {
                    if (!TextUtils.isEmpty(etPhone.getText())
                            && !TextUtils.isEmpty(etPassword.getText())) {
                        if (NetworkUtil.isConnection(getActivity())) {
//                            WebServiceUtil.postStringData(NetUrl.USERREGISTER, getUrlParam(), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String s) {
//                                    switch (XmlUtil.getRetXML(s)) {
//                                        case 0:// 注册成功
//                                            handler.postDelayed(runnable, 1000);
//                                            tvError.setText(getString(R.string.create_pwd_api_ret_0));
//                                            break;
//                                        case 1:// 限制注册
//                                            tvError.setText(getString(R.string.create_pwd_api_ret_1));
//                                            break;
//                                        case 2:// 已被注册
//                                            tvError.setText(getString(R.string.register_free_api_ret_2));
////                            case 5:// 已被注册
////                                tvError.setText(getString(R.string.string_create_pwd_ret_5));
////                                break;
//                                        default:
//                                            break;
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//
//                                }
//                            });
                        }
                    } else {
                        tvError.setText(getString(R.string.register_free_other_empty_params));
                    }
                } else {
                    if (TextUtils.isEmpty(etPhone.getText())) {
                        tvError.setText(getString(R.string.register_free_other_empty_params));
                    } else {
                        if (NetworkUtil.isConnection(getActivity())) {
                            dialog = ProgressDialog.show(getContext(), null, getString(R.string.net_loading));
//                            WebServiceUtil.postStringData(NetUrl.USERREGISTER, getUrlParam(), new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String s) {
//                                    dialog.dismiss();
//                                    switch (XmlUtil.getRetXML(s)) {
//                                        case 0:// 注册成功
//                                            handler.postDelayed(runnable, 1000);
//                                            tvError.setText(getString(R.string.create_pwd_api_ret_0));
//                                            break;
//                                        case 1:// 限制注册
//                                            tvError.setText(getString(R.string.create_pwd_api_ret_1));
//                                            break;
//                                        case 2:// 已被注册
//                                            tvError.setText(getString(R.string.register_free_api_ret_2));
////                            case 5:// 已被注册
////                                tvError.setText(getString(R.string.string_create_pwd_ret_5));
////                                break;
//                                        default:
//                                            break;
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    dialog.dismiss();
//                                }
//                            });
                        }
                    }
                }
                break;
        }
    }

    // 创建密码并登录成功跳转登录界面
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
        // 保存设置的密码
        PreferenceUtil.setValue(MyConstant.SP_ACCOUNT, etPhone.getText().toString());
        PreferenceUtil.setValue(MyConstant.SP_PASSWORD, getPassword());
        // 跳转登录界面
        startActivity(new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        getActivity().finish();
    }

    //清除错误提示
    private class MyTextWatcher implements TextWatcher {
        public MyTextWatcher() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            tvError.setText("");
        }
    }
}
