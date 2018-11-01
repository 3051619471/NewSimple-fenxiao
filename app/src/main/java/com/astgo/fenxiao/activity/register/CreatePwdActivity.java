package com.astgo.fenxiao.activity.register;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.PreferenceUtil;
import com.astgo.fenxiao.tools.Tt;

import java.util.HashMap;
import java.util.Map;

public class CreatePwdActivity extends Activity implements View.OnClickListener {

    private TextView tvError;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            redirect();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pwd);
        initTitleView();
        tvError = (TextView) findViewById(R.id.tv_error);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        getEtPassword().addTextChangedListener(new TextWatcher() {
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
        });
    }

    /**
     * 初始化title
     */
    private void initTitleView() {
        ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.create_pwd_title));
        ((ImageView)findViewById(R.id.title_left)).setImageResource(R.drawable.back_selector);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private EditText getEtPassword() {
        return (EditText) findViewById(R.id.et_password);
    }

    // 设置免费注册参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
        urlParams.put(MyConstant.REGPWD, getEtPassword().getText().toString());
        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
        urlParams.put(MyConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(getEtPassword().getText())) {
                    Tt.showShort(this, getString(R.string.string_create_pwd_empty_params));
                } else {
                    if (NetworkUtil.isConnection(this)) {
//                        WebServiceUtil.postStringData(NetUrl.USERREGISTER, getUrlParam(), new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String s) {
//                                switch (XmlUtil.getRetXML(s)) {
//                                    case 0:// 注册成功
//                                        handler.postDelayed(runnable, 1000);
//                                        tvError.setText(getString(R.string.create_pwd_api_ret_0));
//                                        break;
//                                    case 1:// 注册失败
//                                        tvError.setText(getString(R.string.create_pwd_api_ret_1));
//                                        break;
//                                    case 2:// 已被注册
//                                        tvError.setText(getString(R.string.create_pwd_api_ret_5));
//                                        break;
//                                    case 5:// 已被注册
//                                        tvError.setText(getString(R.string.create_pwd_api_ret_5));
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//
//                            }
//                        });
                    }
                }
                break;
        }
    }

    // 创建密码并登录成功跳转登录界面
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
        // 保存设置的密码
        PreferenceUtil.setValue(MyConstant.SP_PASSWORD, getEtPassword().getText().toString());
        // 跳转登录界面
        startActivity(new Intent(getBaseContext(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}
