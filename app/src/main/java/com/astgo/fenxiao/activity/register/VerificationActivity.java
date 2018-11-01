package com.astgo.fenxiao.activity.register;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.tools.MD5EncodeUtil;
import com.astgo.fenxiao.tools.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends Activity implements View.OnClickListener {

    public static final String VERIFICATION = "验证码是";

    private TextView tvError;
    private TextView tvVerifyClock;

    private String mVerify;
    private TimeCount mTimeCount;
    private static final int CLOCK = 10;    //重新获取验证码的默认间隔时间90秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        initFBI();
    }

    private void initFBI() {
        mVerify = getIntent().getStringExtra(VERIFICATION);
        Log.d("VERIFICATION", " == >>" + mVerify);

        initTitleView();
        tvError = (TextView) findViewById(R.id.tv_error);
        tvVerifyClock = (TextView) findViewById(R.id.tv_verify_clock);
        tvVerifyClock.setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);

        mTimeCount = new TimeCount(CLOCK * 1000, 1000);
        mTimeCount.start();//开始计时

        getEtVerify().addTextChangedListener(new TextWatcher() {
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
        ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.verification_title));
        ((ImageView)findViewById(R.id.title_left)).setImageResource(R.drawable.back_selector);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    private EditText getEtVerify() {
        return (EditText) findViewById(R.id.et_verify);
    }

    // 重新获取验证码参数
    private Map<String, String> getUrlParam() {
        Map<String, String> urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
        urlParams.put(MyConstant.KEY, MD5EncodeUtil.getMD5EncodeStr(MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null) + MyConstant.KEY_FLAG));
        urlParams.put(MyConstant.CODETYPE, MyConstant.VOC);
        urlParams.put(MyConstant.ACCT, MyConstant.ACCT_VALUE);
        urlParams.put(MyConstant.AIRPWD, MyConstant.AIRPWD_VALUE);
        return urlParams;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_verify_clock:
                if (NetworkUtil.isConnection(this)) {
//                    WebServiceUtil.postStringData(NetUrl.GETREGCODE, getUrlParam(), new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String s) {
//                            switch (XmlUtil.getRetXML(s)) {
//                                case 0://获取验证码成功
//                                    mVerify = XmlUtil.parserFilterXML(s, MyConstant.VERIFY_VAL);
//                                    Log.d("VERIFICATION", " == >>" + mVerify);
//                                    mTimeCount.start();//开始计时
//                                    break;
//                                case 1://号码已被限制使用
//                                    tvError.setText(getString(R.string.register_free_api_ret_1));
//                                    break;
//                                case 5://号码已被注册
//                                    tvError.setText(getString(R.string.register_free_api_ret_2));
//                                    break;
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//
//                        }
//                    });
                }
                break;
            case R.id.btn_confirm:
                //TODO implement
                if (TextUtils.isEmpty(getEtVerify().getText())) {
                    tvError.setText(getString(R.string.verification_empty));
                } else {
                    if (mVerify.equals(getEtVerify().getText().toString())) {
                        redirect();
                    } else {
                        tvError.setText(getString(R.string.verification_error));
                    }
                }
                break;
        }
    }

    // 跳转到设置密码
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void redirect() {
        startActivity(new Intent(getBaseContext(), CreatePwdActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            // 参数依次为总时长,和计时的时间间隔
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tvVerifyClock.setText(getString(R.string.verification_verify_again));
            tvVerifyClock.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tvVerifyClock.setClickable(false);
            tvVerifyClock.setText(millisUntilFinished / 1000 + getString(R.string.verification_sec));
        }
    }
}
