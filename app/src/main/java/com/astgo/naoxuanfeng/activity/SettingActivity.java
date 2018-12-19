package com.astgo.naoxuanfeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyApplication;
import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 * 通话设置界面
 */
public class SettingActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "通话设置界面";
    private LinearLayout accountQRCode;
    private TextView myselfAccount;
    private TextView tvBalanceMoney;
    private TextView tvBalanceTime;
    private TextView tvBalanceExpiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView(){
        initTitleView();
        accountQRCode = (LinearLayout) findViewById(R.id.account_QR_code);
        myselfAccount = (TextView) findViewById(R.id.myself_account);
        myselfAccount.setText(MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
        tvBalanceMoney = (TextView) findViewById(R.id.tv_balance_money);
        tvBalanceTime = (TextView) findViewById(R.id.tv_balance_time);
        tvBalanceExpiry = (TextView) findViewById(R.id.tv_balance_expiry);

        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.setting_01).setOnClickListener(this);
        findViewById(R.id.setting_02).setOnClickListener(this);
        findViewById(R.id.setting_03).setOnClickListener(this);

        setBalance();
    }

    /**
     * 初始化title
     */
    private void initTitleView() {
        ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.setting_title));
        ((ImageView)findViewById(R.id.title_left)).setImageResource(R.drawable.back_selector);
        findViewById(R.id.title_left).setOnClickListener(this);
    }

    /**
     * 请求并设置余额信息
     */
    private void setBalance() {
        Map<String, String> urlParams;
        //保存参数为HashMap<>
        urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
//        WebServiceUtil.postStringData(NetUrl.QUERYACCOUNT, urlParams, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                int ret = XmlUtil.getRetXML(s);
//                switch (ret) {
//                    case 0:
//                        //将余额信息写入xml文件中
//                        PreferenceUtil.setValue(MyConstant.BALANCE_VAL, XmlUtil.parserFilterXML(s, MyConstant.BALANCE_VAL));
//                        PreferenceUtil.setValue(MyConstant.BALANCE_TIME_LONG, XmlUtil.parserFilterXML(s, MyConstant.BALANCE_TIME_LONG));
//                        PreferenceUtil.setValue(MyConstant.BALANCE_DATE, XmlUtil.parserFilterXML(s, MyConstant.BALANCE_DATE));
//                        //设置余额信息
//                        tvBalanceMoney.setText(MyApplication.mSpInformation.getString(MyConstant.BALANCE_VAL, "") + getString(R.string.balance_unit));
//                        tvBalanceTime.setText(MyApplication.mSpInformation.getString(MyConstant.BALANCE_TIME_LONG, "") + getString(R.string.balance_unit_1));
//                        tvBalanceExpiry.setText(MyApplication.mSpInformation.getString(MyConstant.BALANCE_DATE, ""));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_left://返回按钮
                finish();
                break;
            case R.id.setting_01://拨打方式
                newMyIntent(MyConstant.SP_SETTINGS_BDFS);
                break;
            case R.id.setting_02://声音设置
                newMyIntent(MyConstant.SP_SETTINGS_AJSY);
                break;
            case R.id.setting_03://拨号提示
                newMyIntent(MyConstant.SP_SETTINGS_BHTS);
                break;
            case R.id.logout:
                logout();
                break;
        }
    }
    // 跳转到设置详情页面
    private void newMyIntent(String tag) {
        Intent intent = new Intent(this, SettingDetailsActivity.class);
        intent.putExtra(MyConstant.SETTINGS_DETAIL_TAG, tag);
        startActivity(intent);
    }

    private void logout() {
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }
}
