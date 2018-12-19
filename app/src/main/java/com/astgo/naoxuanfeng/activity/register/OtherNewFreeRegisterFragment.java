package com.astgo.naoxuanfeng.activity.register;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyApplication;
import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.NetUrl;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.activity.LoginActivity;
import com.astgo.naoxuanfeng.bean.BaseDataObject;
import com.astgo.naoxuanfeng.fragment.BaseFragment;
import com.astgo.naoxuanfeng.tools.NetworkUtil;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.DialogUtil;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherNewFreeRegisterFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvError;
    private EditText etPhone, et_nickname, etCode;
    private EditText etPassword, et_password2;
    private Button btnGetCode;
    private ProgressDialog dialog;
    private boolean isAuthCode = true;

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

    private RelativeLayout rlAutoCode;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        tvError = (TextView) view.findViewById(R.id.tv_error);
        //手机号
        etPhone = (EditText) view.findViewById(R.id.et_phone);

        rlAutoCode = (RelativeLayout) view.findViewById(R.id.rl_auth_code);
        if (!isAuthCode) {
            rlAutoCode.setVisibility(View.GONE);
        } else {
            rlAutoCode.setVisibility(View.VISIBLE);
        }
        et_nickname = (EditText) view.findViewById(R.id.et_father_phone);
//        短信验证码
        etCode = (EditText) view.findViewById(R.id.et_code);
        //获取短信验证码按钮
        btnGetCode = (Button) view.findViewById(R.id.btn_get_code);
//        密码
        etPassword = (EditText) view.findViewById(R.id.et_password);
        et_password2 = (EditText) view.findViewById(R.id.et_password_again);
        etPhone.addTextChangedListener(mWatcher);
        if (MyApplication.NEED_CREATE_PWD) {
            etPassword.setVisibility(View.VISIBLE);
            etPassword.addTextChangedListener(mWatcher);
        } else {
            etPassword.setVisibility(View.GONE);
        }
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (MyApplication.NEED_CREATE_PWD) {
                    if (rlAutoCode.getVisibility() == View.VISIBLE) {
                        if (TextUtils.isEmpty(etCode.getText())) {
                            return;
                        }
                    }
                    if (!TextUtils.isEmpty(etPhone.getText())
                            && !TextUtils.isEmpty(etPassword.getText())) {
                        if (NetworkUtil.isConnection(getActivity())) {
//                            serverVolleyReg();
                            //        username  	=> 手机号码
//        code		=> 验证码
//        password	=> 密码
//        nickname	=> 昵称
//        cfmpwd		=> 确认密码
                            String username = etPhone.getText().toString();
                            String code = etCode.getText().toString();
                            String password = etPassword.getText().toString();
                            String nickname = et_nickname.getText().toString();
                            String cfmpwd = et_password2.getText().toString();
                            serverReg(username,code,password,nickname,cfmpwd);
                        }
                    } else {
//                        tvError.setText(getString(R.string.register_free_other_empty_params));
                    }
                } else {
                    if (TextUtils.isEmpty(etPhone.getText())) {
//                        tvError.setText(getString(R.string.register_free_other_empty_params));
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
////                                            tvError.setText(getString(R.string.create_pwd_api_ret_0));
//                                            break;
//                                        case 1:// 限制注册
////                                            tvError.setText(getString(R.string.create_pwd_api_ret_1));
//                                            break;
//                                        case 2:// 已被注册
////                                            tvError.setText(getString(R.string.register_free_api_ret_2));
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
            case R.id.btn_get_code:
                //
                if (!TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    getAuthCode(etPhone.getText().toString().trim());
                }
                break;
        }
    }

    private void getAuthCode(String phone) {
        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(), "正在获取验证码");
        OkHttpUtils.post(NetUrl.CODE)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                //.headers("token",SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .params("phone", phone)
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        DebugUtil.error("getAuthCode" + s);
                        //{"msg":"\u77ed\u4fe1\u53d1\u9001\u6210\u529f","info":"\u77ed\u4fe1\u53d1\u9001\u6210\u529f","code":1,"status":1,"data":[]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            //开始倒计时
                             showCountTimer(btnGetCode);
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


//        Subscription subscription = HttpClient.Builder.getAppService().serverGetAuthCode(getCodeUrlParam())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        dialog.dismiss();
//                        ToastUtils.showShortToast("短信验证码发送成功");
//                        //开始倒计时
//                        showCountTimer(btnGetCode);
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
//
//        addSubscription(subscription);
    }


    CountDownTimer countDownTimer;

    private void showCountTimer(final Button tvSendAuthCode) {
        if (countDownTimer == null) {
            //显示倒计时
            countDownTimer = new CountDownTimer(120000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    DebugUtil.debug("--------" + millisUntilFinished);
                    tvSendAuthCode.setClickable(false);
                    tvSendAuthCode.setText((millisUntilFinished / 1000) + "秒");
                }

                @Override
                public void onFinish() {
                    tvSendAuthCode.setText(R.string.get_code);
                    tvSendAuthCode.setClickable(true);
                    countDownTimer = null;
                }
            };
            countDownTimer.start();
        }
    }


    private void serverReg(final String username, String code, final String password, String nickname, String cfmpwd) {

        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(), "");
//        username  	=> 手机号码
//        code		=> 验证码
//        password	=> 密码
//        nickname	=> 昵称
//        cfmpwd		=> 确认密码
        OkHttpUtils.post(NetUrl.REGISTER)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                //.headers("token",SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .params("username", username)
                .params("code", code)
                .params("password", password)
                .params("nickname", nickname)
                .params("cfmpwd", cfmpwd)
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        DebugUtil.error("serverReg" + s);
                        //{"msg":"\u624b\u673a\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a","info":"\u624b\u673a\u53f7\u7801\u4e0d\u80fd\u4e3a\u7a7a","code":0,"status":0,"data":[]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            SPUtils.putString(MyConstant.SP_ACCOUNT,username);
                            SPUtils.putString(MyConstant.SP_PASSWORD,password);
                            SPUtils.putBoolean("isLogin",true);
                            redirect();
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, okhttp3.Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


//        HttpClient.Builder.getAppService().serverReg(getUrlParam())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        dialog.dismiss();
//                        redirect();
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                        if(code >= 0){
////                            tvError.setText(msg);
//                        }
//                    }
//                });

    }

//    // 设置免费注册参数
//    private Map<String, String> getUrlParam() {
//        Map<String, String> urlParams = new HashMap<String, String>();
//        urlParams.put(ParamConstant.PHONE, etPhone.getText().toString().trim());
//        urlParams.put(ParamConstant.FARTHER, etFartherNum.getText().toString().trim());
//        if(isAuthCode){
//            urlParams.put(ParamConstant.CODE, etCode.getText().toString().trim());
//        }
//        urlParams.put(ParamConstant.PASSWD, getPassword());
////        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
//        urlParams.put(ParamConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
//        return urlParams;
//    }
//
//    private Map<String, String> getCodeUrlParam() {
//        Map<String, String> urlParams = new HashMap<String, String>();
//        urlParams.put(ParamConstant.PHONE, etPhone.getText().toString().trim());
////        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
//        urlParams.put(ParamConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
//        return urlParams;
//    }

    private void serverVolleyReg() {
//        WebServiceUtil.postStringData(NetUrl.USERREGISTER, getUrlParam(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                switch (XmlUtil.getRetXML(s)) {
//                    case 0:// 注册成功
//                        handler.postDelayed(runnable, 1000);
//                        tvError.setText(getString(R.string.create_pwd_api_ret_0));
//                        break;
//                    case 1:// 限制注册
//                        tvError.setText(getString(R.string.create_pwd_api_ret_1));
//                        break;
//                    case 2:// 已被注册
//                        tvError.setText(getString(R.string.register_free_api_ret_2));
////                            case 5:// 已被注册
////                                tvError.setText(getString(R.string.string_create_pwd_ret_5));
////                                break;
//                    default:
//                        break;
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
    }

    // 创建密码并登录成功跳转登录界面
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
        // 跳转登录界面
        startActivity(new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        getActivity().setResult(1);
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
//            tvError.setText("");
        }
    }
}
