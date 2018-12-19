package com.astgo.naoxuanfeng.activity.register;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.ParamConstant;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.activity.LoginActivity;
import com.astgo.naoxuanfeng.fragment.BaseFragment;
import com.astgo.naoxuanfeng.http.service.HttpClient;
import com.astgo.naoxuanfeng.http.service.HttpResultSubscriber;
import com.astgo.naoxuanfeng.tools.NetworkUtil;
import com.astgo.naoxuanfeng.tools.PreferenceUtil;
import com.astgo.naoxuanfeng.tools.Tt;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.DialogUtil;
import com.blankj.utilcode.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 这是充值卡注册界面，注册完成自动跳转到登录界面，
 * 账号为注册使用的手机号，密码为注册使用的充值卡密码
 */
public class CardRegisterFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvError;
    private EditText etRegisterPhone;
    private EditText etRegisterCardN;
    private EditText etRegisterCardP;
    private EditText etCode;
    private Button btnGetCode;
    private CheckBox checkBox;
    private TextView tvProtocal;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            redirect();
        }
    };
//    private MyTextWatcher mWatcher = new MyTextWatcher();
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_register, container, false);
    }

    private RelativeLayout rlAuthCode;
    private boolean isAuthCode = false;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        tvError = (TextView) view.findViewById(R.id.tv_error);
        etRegisterPhone = (EditText) view.findViewById(R.id.et_register_phone);
        etRegisterCardN = (EditText) view.findViewById(R.id.et_register_card_n);
        etRegisterCardP = (EditText) view.findViewById(R.id.et_register_card_p);
        etCode = (EditText) view.findViewById(R.id.et_code);
        btnGetCode = (Button) view.findViewById(R.id.btn_get_code);
//        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
//        tvProtocal = (TextView) view.findViewById(R.id.tv_software_protocal);
//        setSpan(tvProtocal);
//        etRegisterPhone.addTextChangedListener(mWatcher);
//        etRegisterCardN.addTextChangedListener(mWatcher);
//        etRegisterCardP.addTextChangedListener(mWatcher);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        rlAuthCode = (RelativeLayout) view.findViewById(R.id.rl_auth_code);
        if(isAuthCode){
            rlAuthCode.setVisibility(View.VISIBLE);
        }else {
            rlAuthCode.setVisibility(View.GONE);
        }
        btnGetCode.setOnClickListener(this);
    }

    private void setSpan(TextView textView) {
        String text = "阅读并同意《软件使用服务协议》";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.color_new_them)),5,text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    // 设置充值卡注册参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, etRegisterPhone.getText().toString());
        urlParams.put(MyConstant.CARDNO, etRegisterCardN.getText().toString());
        urlParams.put(MyConstant.ADDPWD, etRegisterCardP.getText().toString());
        urlParams.put(MyConstant.CARDTYPE, "3");
        urlParams.put(MyConstant.MONEY, "10");
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm://完成充值卡注册
//                if(!checkBox.isChecked()){
//                    Tt.showShort(getActivity(), getString(R.string.agree_protocal));
//                    return;
//                }

                  if(isAuthCode && rlAuthCode.getVisibility() == View.VISIBLE) {
                      if(TextUtils.isEmpty(etCode.getText().toString().trim())){
                         return;
                      }
                  }
                if (!TextUtils.isEmpty(etRegisterPhone.getText())
                        && !TextUtils.isEmpty(etRegisterCardN.getText())
                        && !TextUtils.isEmpty(etRegisterCardP.getText())
                      ) {
//                    serverVollyCardRegister();
                    serverCardRegister();
                } else {
                    Tt.showShort(getActivity(), getString(R.string.register_card_empty_params));
                }
                break;
            case R.id.btn_get_code:
                if(!TextUtils.isEmpty(etRegisterPhone.getText().toString().trim())){
                    getAuthCode();
                }
                break;
        }
    }

    private void getAuthCode() {
        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(),"正在获取验证码");
        Subscription subscription = HttpClient.Builder.getAppService().serverGetAuthCode(getCodeUrlParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        ToastUtils.showShortToast("短信验证码发送成功");
                        showCountTimer(btnGetCode);
                    }

                    @Override
                    public void _onError(String msg, int code) {
                        dialog.dismiss();
                        DebugUtil.error("msg:"+msg+"-------code"+code);
                    }
                });
        addSubscription(subscription);
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
                    tvSendAuthCode.setText((millisUntilFinished / 1000)+"秒后再次发送");
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

    private Map<String, String> getCodeUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(ParamConstant.PHONE, etRegisterPhone.getText().toString().trim());
//        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
        urlParams.put(ParamConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }
    private void serverCardRegister() {
        //TODO getNewUrlParam
        final Dialog dialog = DialogUtil.getInstance().showDialog(this.getActivity(),"正在注册");
        Subscription subscription = HttpClient.Builder.getAppService().serverCardReg(getNewUrlParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        ToastUtils.showShortToast("注册成功");
                        redirect();
                    }

                    @Override
                    public void _onError(String msg, int code) {
                        dialog.dismiss();
                        DebugUtil.error("msg:"+msg+"-------code"+code);
                    }
                });

        addSubscription(subscription);
    }

    // 设置充值卡注册参数
    private Map<String, String> getNewUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(ParamConstant.PHONE, etRegisterPhone.getText().toString().trim());
        if(isAuthCode){
            urlParams.put(ParamConstant.CODE, etCode.getText().toString().trim());
        }
        urlParams.put(ParamConstant.CARDNAME, etRegisterCardN.getText().toString().trim());
        urlParams.put(ParamConstant.CARDPWD, etRegisterCardP.getText().toString().trim());
//        urlParams.put(MyConstant.CARDNO, etRegisterCardN.getText().toString().trim());
//        urlParams.put(MyConstant.ADDPWD, etRegisterCardP.getText().toString().trim());
//        urlParams.put(MyConstant.CARDTYPE, "3");
//        urlParams.put(MyConstant.MONEY, "10");
        return urlParams;
    }


    private void serverVollyCardRegister() {
        if (NetworkUtil.isConnection(getActivity())) {
            dialog = ProgressDialog.show(getContext(), null, getString(R.string.net_loading));
            dialog.setCanceledOnTouchOutside(true);
//            WebServiceUtil.postStringData(NetUrl.ADDACCOUNT, getUrlParam(), new Response.Listener<String>() {
//                @Override
//                public void onResponse(String s) {
//                    dialog.dismiss();
//                    switch (XmlUtil.getRetXML(s)) {
//                        case 0://充值卡注册成功
//                            tvError.setText(getString(R.string.register_card_api_ret_0));
//                            handler.postDelayed(runnable, 1000);
//                            Tt.showShort(getActivity(), getString(R.string.redirect_success));
//                            break;
//                        case 1://卡不存在
//                            tvError.setText(getString(R.string.register_card_api_ret_1));
//                            break;
//                        case 2://卡密码错误
//                            tvError.setText(getString(R.string.register_card_api_ret_2));
//                            break;
//                        case 3://卡被禁用
//                            tvError.setText(getString(R.string.register_card_api_ret_3));
//                            break;
//                        case 4://该卡已经被充值
//                            tvError.setText(getString(R.string.register_card_api_ret_4));
//                            break;
//                        case 5://改卡类型不是开户卡
//                            tvError.setText(getString(R.string.register_card_api_ret_5));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError volleyError) {
//                    dialog.dismiss();
//                }
//            });
        }
    }

    // 充值卡注册成功操作
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
        // 保存账号和密码
        PreferenceUtil.setValue(MyConstant.SP_ACCOUNT, etRegisterPhone.getText().toString());
        PreferenceUtil.setValue(MyConstant.SP_PASSWORD, etRegisterCardP.getText().toString());
        // 跳转登录界面
        startActivity(new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        getActivity().finish();
    }

    //清除错误提示
//    private class MyTextWatcher implements TextWatcher {
//        public MyTextWatcher() {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            tvError.setText("");
//        }
//    }
}
