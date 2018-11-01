package com.astgo.fenxiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;

import java.util.HashMap;
import java.util.Map;

public class BalanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBalanceMoneyMain;
    private TextView tvBalanceMoney;
    private TextView tvBalanceTime;
    private TextView tvBalanceExpiry;

    private Map<String, String> urlParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initTitleView();
        tvBalanceMoneyMain = (TextView) findViewById(R.id.tv_balance_money_main);
        tvBalanceMoney = (TextView) findViewById(R.id.tv_balance_money);
        tvBalanceTime = (TextView) findViewById(R.id.tv_balance_time);
        tvBalanceExpiry = (TextView) findViewById(R.id.tv_balance_expiry);
        setUrlParam();
//        WebServiceUtil.postStringData(NetUrl.QUERYACCOUNT, urlParams, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                int ret = XmlUtil.getRetXML(s);
//                switch (ret) {
//                    case 0:
//                        tvBalanceMoneyMain.setText("￥" + XmlUtil.parserFilterXML(s, MyConstant.BALANCE_VAL));
//                        tvBalanceMoney.setText(XmlUtil.parserFilterXML(s, MyConstant.BALANCE_VAL) + getString(R.string.balance_unit));
//                        tvBalanceTime.setText(XmlUtil.parserFilterXML(s, MyConstant.BALANCE_TIME_LONG) + getString(R.string.balance_unit_1));
//                        tvBalanceExpiry.setText(XmlUtil.parserFilterXML(s, MyConstant.BALANCE_DATE));
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

    /**
     * 初始化title
     */
    private void initTitleView() {
        ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.balance_title));
        ((ImageView)findViewById(R.id.title_left)).setImageResource(R.drawable.back_selector);
        findViewById(R.id.title_left).setOnClickListener(this);
    }
    private void setUrlParam() {
        //保存参数为HashMap<>
        urlParams = new HashMap<String, String>();
        urlParams.put(MyConstant.REGNUM, MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
}
