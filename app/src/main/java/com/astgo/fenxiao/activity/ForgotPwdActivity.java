package com.astgo.fenxiao.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.ParamConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.tools.MD5EncodeUtil;
import com.astgo.fenxiao.tools.Tt;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 这是应用找回密码的交互界面，如果存在之前登录过的账号信息，将自动填充
 * 编辑栏，否则需要手动输入账号信息，来找回密码（找回密码方式：电话语音）
 */
public class ForgotPwdActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "找回密码";
    public static final int TYPE_FORGET_PWD = 1;
    public static final int TYPE_MODIFY_PWD = 2;
    private TextView tvError;
    private int type= -1;
    private EditText etPhone,etCode,etNewPwd,etNewPwdAgain;
    private Button btnGetCode;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            redirect();
        }
    };
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        getIntentData();
        initFBI();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent!= null && intent.hasExtra(AppConstant.TYPE_MODIFY_PWD)){
            type  = intent.getIntExtra(AppConstant.TYPE_MODIFY_PWD,-1);
        }
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        redirect();
    }

    private void initFBI() {
        initTitleView();
//        tvError = (TextView) findViewById(R.id.tv_error);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        etNewPwd = (EditText) findViewById(R.id.et_new_pwd);
        etNewPwdAgain = (EditText) findViewById(R.id.et_new_pwd_again);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        // 默认填充以前登录过的账号
        getEtPhone().setText(MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
        // 当编辑框内容为空时，重置错误提示
        getEtPhone().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    getEtPhone().setSelection(s.length());
                }
//                tvError.setText("");
            }
        });
    }

    /**
     * 初始化title
     */
    private void initTitleView() {
        if(type == TYPE_MODIFY_PWD){
            ((TextView) findViewById(R.id.title_tv)).setText("修改密码");
        } else {
            ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.forget_title));
        }
        ((ImageView) findViewById(R.id.title_left)).setImageResource(R.mipmap.icon_back_black);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    private EditText getEtPhone() {
        return (EditText) findViewById(R.id.et_phone);
    }

    // 设置找回密码参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, getEtPhone().getText().toString());
        urlParams.put(MyConstant.CODEKEY, MD5EncodeUtil.getMD5EncodeStr(getEtPhone().getText().toString() + MyConstant.CODEKEY_FLAG));
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(getEtPhone().getText().toString().trim())) {
                    Tt.showShort(this, getString(R.string.forget_empty_params));
                    return;
                }else if(TextUtils.isEmpty(etCode.getText().toString().trim())){
                    Tt.showShort(this, getString(R.string.input_code));
                    return;
                }else if(TextUtils.isEmpty(etNewPwd.getText().toString().trim())){
                    Tt.showShort(this, getString(R.string.input_pwd));
                    return;
                }else if(TextUtils.isEmpty(etNewPwdAgain.getText().toString().trim())){
                    Tt.showShort(this, getString(R.string.input_pwd));
                    return;
                } else if(!etNewPwd.getText().toString().trim().equals(etNewPwdAgain.getText().toString().trim())){
                    Tt.showShort(this, "两次密码输入不一致，请重新输入");
                    return;
                } else {
//      username	=> 手机号码
//        code		=> 验证码
//        new_pwd	=> 新密码
                    serveForgetPwd(getEtPhone().getText().toString().trim(),etCode.getText().toString().trim(),etNewPwd.getText().toString().trim());
                }
                break;
            case R.id.title_left:
                this.finish();
                break;
            case R.id.btn_get_code:
                if(!TextUtils.isEmpty(etPhone.getText().toString().trim())){
                    getAuthCode(etPhone.getText().toString().trim());
                }
                break;
        }
    }

    private void serveForgetPwd(String username,String code,String new_pwd) {
//        username	=> 手机号码
//        code		=> 验证码
//        new_pwd	=> 新密码

        final Dialog dialog = DialogUtil.getInstance().showDialog(this,"正在保存新密码");

        OkHttpUtils.post(NetUrl.FORGET_PASSWORD)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                //.headers("token",SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .params("username", username)
                .params("code", code)
                .params("new_pwd", new_pwd)
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, okhttp3.Response response) {
                        DebugUtil.error("serveForgetPwd" + s);
                        //{"msg":"\u77ed\u4fe1\u53d1\u9001\u6210\u529f","info":"\u77ed\u4fe1\u53d1\u9001\u6210\u529f","code":1,"status":1,"data":[]}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
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





//
//        Subscription subscription = HttpClient.Builder.getAppService().serverForgetPwd(getForgetPwdUrlParam())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        dialog.dismiss();
////                        tvError.setText(getString(R.string.forget_api_ret_0));
//                        redirect();
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
//        addSubscription(subscription);
    }

//    private void serverVolleyforgetPwd() {
//        dialog = ProgressDialog.show(ForgotPwdActivity.this, null, getString(R.string.net_loading));
//        dialog.setCanceledOnTouchOutside(true);
////        WebServiceUtil.postStringData(NetUrl.GETPWDSMS, getUrlParam(), new Response.Listener<String>() {
////            @Override
////            public void onResponse(String s) {
////                dialog.dismiss();
////                switch (XmlUtil.getRetXML(s)) {
////                    case 0://找回密码成功
////                        tvError.setText(getString(R.string.forget_api_ret_0));
////                        handler.postDelayed(runnable, 1000);
////                        Tt.showShort(getApplicationContext(), getString(R.string.redirect_success));
////                        break;
////                    case 401://要找回密码的账户不存在
////                        tvError.setText(getString(R.string.forget_api_ret_401));
////                        break;
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError volleyError) {
////                dialog.dismiss();
////            }
////        });
//    }

    private void getAuthCode(String phone) {
        final Dialog dialog = DialogUtil.getInstance().showDialog(ForgotPwdActivity.this, "正在获取验证码");
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




//        final Dialog dialog = DialogUtil.getInstance().showDialog(this,"正在获取验证码");
//        HttpClient.Builder.getAppService().serverGetAuthCode(getCodeUrlParam())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        dialog.dismiss();
//                        ToastUtils.showShortToast("短信验证码发送成功");
//                        showCountTimer(btnGetCode);
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
    }


    CountDownTimer countDownTimer;
    private void showCountTimer(final Button tvSendAuthCode) {
        if(countDownTimer == null){
            //显示倒计时
            countDownTimer = new CountDownTimer(120000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    DebugUtil.debug("--------"+millisUntilFinished);
                    tvSendAuthCode.setClickable(false);
                    tvSendAuthCode.setText((millisUntilFinished / 1000)+"秒");
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

    private Map<String, String> getForgetPwdUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(ParamConstant.PHONE, etPhone.getText().toString().trim());
        urlParams.put(ParamConstant.CODE,etCode.getText().toString().trim());
        urlParams.put(ParamConstant.PASSWD, etNewPwd.getText().toString().trim());
        return urlParams;
    }


    private Map<String, String> getCodeUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(ParamConstant.PHONE, etPhone.getText().toString().trim());
        urlParams.put(ParamConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }



    // 跳转到登录界面
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
//        startActivity(new Intent(getBaseContext(), LoginActivity.class)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}
