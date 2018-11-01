package com.astgo.fenxiao.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.utils.ToastUtils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.astgo.fenxiao.MainActivity;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.activity.register.RegisterActivity;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.LoginBean;
import com.astgo.fenxiao.bean.UserInforBean;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpResultSubscriber;
import com.astgo.fenxiao.tools.NetworkUtil;
import com.astgo.fenxiao.tools.PreferenceUtil;
import com.astgo.fenxiao.tools.Tt;
import com.astgo.fenxiao.tools.XmlUtil;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.DialogUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.widget.URLConstant;

import java.io.UnsupportedEncodingException;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "登录界面";
    private String jPushRid;
    private EditText et_account, et_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        if (SPUtils.getBoolean(MyConstant.SP_LOGIN_STATE,false)){
            autoLogin();
        }
        initView();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            return true;
        }
        return false;
    }

    // 初始化 findViewById 和 ClickListener
    private void initView() {
        findViewById(R.id.btn_register).setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        findViewById(R.id.tv_forgot).setOnClickListener(this);
        // 默认加载上一次成功登录的账号密码

        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        // 默认加载上一次成功登录的账号密码

//        SPUtils.putString(MyConstant.SP_ACCOUNT,username);
//        SPUtils.putString(MyConstant.SP_PASSWORD,password);
        et_account.setText(SPUtils.getString(MyConstant.SP_ACCOUNT, ""));
        et_password.setText(SPUtils.getString(MyConstant.SP_PASSWORD, ""));

    }


//    // 设置登录参数
//    private HashMap<String, String> getUrlParam() {
//        HashMap<String, String> urlParams = new HashMap<String, String>();
//        urlParams.put(ParamConstant.REGNUM, getEtAccount().getText().toString());
//        urlParams.put(ParamConstant.REGPWD, getEtPassword().getText().toString());
//        urlParams.put(ParamConstant.KEY, MD5EncodeUtil.getMD5EncodeStr(getEtAccount().getText().toString() + MyConstant.KEY_FLAG));
//        if (!TextUtils.isEmpty(jPushRid)) {
//            Log.d("jpushid", "jpushid= " + jPushRid);
//            urlParams.put(ParamConstant.JPUSH_RID, jPushRid);
//            SPUtils.putString(AppConstant.KEY_JPUSH_ID, jPushRid);
//        }
//        return urlParams;
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == 1){
//            autoLogin();
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register://注册操作
                startActivity(new Intent(this, RegisterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case R.id.btn_login://登录操作
                if (!TextUtils.isEmpty(et_account.getText().toString().trim())
                        && !TextUtils.isEmpty(et_password.getText().toString().trim())) {
                    if (NetworkUtil.isConnection(this)) {
//                        serverVolleyLogin();
                        jPushRid = JPushInterface.getRegistrationID(getApplicationContext());
                        serverLogin();
                    } else {
                        Tt.showShort(getApplicationContext(), "无网络链接，请检查网络！");
                    }
                } else {
                    Tt.showShort(getApplicationContext(), getString(R.string.login_empty_params));
                }
                break;
            case R.id.tv_forgot://忘记密码操作
                startActivity(new Intent(this, ForgotPwdActivity.class));
                break;
            default:
                break;
        }
    }

    private Dialog dialog;
    private void serverLogin() {
//        username  => 手机号码
//        password  => 登陆密码
//        registrationid => 设备id
        final String username = et_account.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String registrationid = JPushInterface.getRegistrationID(getApplicationContext());
        dialog = DialogUtil.getInstance().showDialog(this, "");
        OkHttpUtils.post(NetUrl.LOGIN)     // 请求方式和请求url
                .tag("serverLogin")// 请求的 tag, 主要用于取消对应的请求
                //.headers("token", SPUtils.getString(AppConstant.KEY_TOKEN, ""))
                .params("username", username)
                .params("password", password)
                .params("registrationid", registrationid)
                .cacheKey("serverLogin")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("serverLogin" + s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":{"token":"6c034f5a5d77b4f253a850dc045f4ac8"}}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                LoginBean loginBean = (LoginBean) BaseDataObject.getBaseBean(s,LoginBean.class);
                                if (loginBean!=null){
                                    String str = loginBean.getData().getToken() + ":" + System.currentTimeMillis() + ":" + registrationid;
                                    Log.e("SLH", "token:" + str);
                                    try {
                                        String base64Str = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
                                        Log.e("SLH", "64token:" + base64Str);
                                        SPUtils.putString(MyConstant.TOKEN, base64Str);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    SPUtils.putString(MyConstant.SP_ACCOUNT,username);
                                    SPUtils.putString(MyConstant.SP_PASSWORD,password);
                                    SPUtils.putBoolean("isFirst",false);
                                    SPUtils.putBoolean(MyConstant.SP_LOGIN_STATE,true);

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                }
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }
                        dialog.dismiss();

                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });

    }

    private void autoLogin() {
//        username  => 手机号码 et_account.setText(SPUtils.getString(MyConstant.SP_ACCOUNT, ""));
//        et_password.setText(SPUtils.getString(MyConstant.SP_PASSWORD, ""));
//        password  => 登陆密码
//        registrationid => 设备id
        final String username = SPUtils.getString(MyConstant.SP_ACCOUNT, "");
        final String password = SPUtils.getString(MyConstant.SP_PASSWORD, "");
        final String registrationid = JPushInterface.getRegistrationID(getApplicationContext());
        dialog = DialogUtil.getInstance().showDialog(this, "");
        OkHttpUtils.post(NetUrl.LOGIN)     // 请求方式和请求url
                .tag("serverLogin")// 请求的 tag, 主要用于取消对应的请求
                //.headers("token", SPUtils.getString(AppConstant.KEY_TOKEN, ""))
                .params("username", username)
                .params("password", password)
                .params("registrationid", registrationid)
                .cacheKey("serverLogin")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("serverLogin" + s);
                        //{"msg":"success","info":"success","code":1,"status":1,"data":{"token":"6c034f5a5d77b4f253a850dc045f4ac8"}}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
                                LoginBean loginBean = (LoginBean) BaseDataObject.getBaseBean(s,LoginBean.class);
                                if (loginBean!=null){
                                    String str = loginBean.getData().getToken()+":"+System.currentTimeMillis()+":"+registrationid;
                                    Log.e("SLH","token:"+str);
                                    try {
                                        String base64Str =new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8");
                                        Log.e("SLH","64token:"+base64Str);
                                        SPUtils.putString(MyConstant.TOKEN,base64Str);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                    SPUtils.putString(MyConstant.SP_ACCOUNT,username);
                                    SPUtils.putString(MyConstant.SP_PASSWORD,password);
                                    SPUtils.putBoolean("isFirst",false);
                                    SPUtils.putBoolean(MyConstant.SP_LOGIN_STATE,true);

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                }
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                        }
                        dialog.dismiss();

                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });

    }

    private void getUserInfor() {
//        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(),"正在获取数据");
        Subscription subscription = HttpClient.Builder.getAppService().getUserInfor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<UserInforBean>() {
                    @Override
                    public void onSuccess(UserInforBean userInforBean) {
                        //TODO

                        if (userInforBean != null) {
                            final String shopUrl = userInforBean.getShopurl();
                            if (!TextUtils.isEmpty(shopUrl)) {
                                int indexUserName = shopUrl.indexOf("&username");
                                if (indexUserName >= 0) {
                                    String shopUrlSuffix = shopUrl.substring(indexUserName, shopUrl.length());
                                    SPUtils.putString(AppConstant.KEY_SHOP_URL_SUFFIX, shopUrlSuffix);
                                }
//                                int indexPwd = shopUrl.indexOf("&password");
//                                int indexKey = shopUrl.indexOf("&key");
//                                String mainKeyValue = shopUrl.substring(0,indexUserName);
//                                String userNameKeyValue = shopUrl.substring(indexUserName,indexPwd);
//                                String pwdkeyValue = shopUrl.substring(indexPwd,indexKey);
//                                String keyValue = shopUrl.substring(indexKey,shopUrl.length());


//                                SPUtils.putString(AppConstant.KEY_SHOP_URL_MAIN,mainKeyValue);
//                                SPUtils.putString(AppConstant.KEY_SHOP_URL_USERNAME,userNameKeyValue);
//                                SPUtils.putString(AppConstant.KEY_SHOP_URL_PWD,pwdkeyValue);
//                                SPUtils.putString(AppConstant.KEY_SHOP_URL_KEY,keyValue);
                            }
                            SPUtils.putString(AppConstant.KEY_SHOP_URL, userInforBean.getShopurl());
                            SPUtils.putString(URLConstant.KEY_EXPIRED_DATE, userInforBean.getExpireddate());
                            //跳转到主页面
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    toMainActivity(shopUrl);
                                }
                            }, 2000);
                        }
                    }

                    @Override
                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
                        DebugUtil.error("msg:" + msg + "-------code" + code);
                    }
                });

        addSubscription(subscription);

    }

    // 登录成功执行的操作
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void loginSuccess(String result) {
        //SIP账号
        PreferenceUtil.setValue(MyConstant.ACCTNAME,
                XmlUtil.parserFilterXML(result, MyConstant.ACCTNAME));
//        //代理商名称
//        PreferenceUtil.setValue(MyConstant.AGENT_NAME, XmlUtil.parserFilterXML(result, MyConstant.AGENT_NAME));
//        Log.d("loginSuccess", MyApplication.mSpInformation.getString(MyConstant.AGENT_NAME,""));

        // 保存绑定电话和短信分享内容
        PreferenceUtil.setValue(MyConstant.BINDTEL,
                XmlUtil.parserFilterXML(result, MyConstant.BINDTEL));
//        PreferenceUtil.setValue(MyConstant.SP_SHARE_SMS,
//                XmlUtil.parserFilterXML(result, MyConstant.HOMEPAGE));
        // 保存账号和密码
//        PreferenceUtil.setValue(MyConstant.SP_ACCOUNT, MyApplication.mSpInformation.getString(MyConstant.BINDTEL, ""));
        PreferenceUtil.setValue(MyConstant.SP_ACCOUNT, et_account.getText().toString().trim());
        PreferenceUtil.setValue(MyConstant.SP_PASSWORD, et_password.getText().toString());
//        //绑定电话归属地信息
//        PreferenceUtil.setValue(MyConstant.SP_ADDR_PROVINCE, XmlUtil.parserFilterXML(result, MyConstant.ADDR_PROVINCE));
//        //无网络拨号相关信息
//        PreferenceUtil.setValue(MyConstant.SP_NO_NET_AREA, XmlUtil.parserFilterXML(result, MyConstant.NO_NET_CALL_AREA));
//        PreferenceUtil.setValue(MyConstant.SP_NO_NET_CODE, XmlUtil.parserFilterXML(result, MyConstant.NO_NET_CALL_CODE));
//        PreferenceUtil.setValue(MyConstant.SP_NO_NET_TEL, XmlUtil.parserFilterXML(result, MyConstant.NO_NET_CALL_TEL));
//        MyApplication.noNetData[0] = MyApplication.mSpInformation.getString(MyConstant.SP_NO_NET_AREA, "");
        // 设置登录状态为已登录并跳转
        toMainActivity("");
    }

    private void toMainActivity(String shopUrl) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//        intent.putExtra(AppConstant.KEY_SHOP_URL,shopUrl);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "已销毁");
    }
}
