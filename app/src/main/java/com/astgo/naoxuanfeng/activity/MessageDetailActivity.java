package com.astgo.naoxuanfeng.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.AppConstant;
import com.astgo.naoxuanfeng.bean.JpushMessageBean;
import com.astgo.naoxuanfeng.http.service.HttpClient;
import com.astgo.naoxuanfeng.http.service.HttpResultSubscriber;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.astgo.naoxuanfeng.tools.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ast009 on 2017/11/30.
 */

public class MessageDetailActivity extends BaseActivity {

    private String messageId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        getIntentData();
        initView();
        initData();
    }



    private JpushMessageBean jpushMessageBean;
    private void getIntentData() {
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(AppConstant.INTENT_MESSAGE_ID)){
            messageId = intent.getStringExtra(AppConstant.INTENT_MESSAGE_ID);
        }
    }


    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvDetail;
    private void initView() {
        initTopTitle();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDetail = (TextView) findViewById(R.id.tv_detail);


    }



    private void initData() {
        final Dialog dialog = DialogUtil.getInstance().showDialog(this,"获取数据中");
        Subscription subscription = HttpClient.Builder.getAppService().getMessageDetail(getUrlParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<JpushMessageBean>() {
                    @Override
                    public void onSuccess(JpushMessageBean jpushMessageBean) {
                        dialog.dismiss();
                        //TODO 如果需要更新
                        if(jpushMessageBean != null){
                            setUIState(jpushMessageBean);
                        }
                    }

                    @Override
                    public void _onError(String msg, int code) {
                        dialog.dismiss();
                        DebugUtil.error("msg:"+msg+"-------code"+code);
                    }
                });

        addSubscription(subscription);

    }

    private void setUIState(JpushMessageBean jpushMessageBean) {
        if(jpushMessageBean != null){
            tvTitle.setText(jpushMessageBean.getTitle());
            tvTime.setText(jpushMessageBean.getAdd_time());
            tvDetail.setText(jpushMessageBean.getContent(), TextView.BufferType.NORMAL);
            tvDetail.setMovementMethod(LinkMovementMethod.getInstance());
        }

    }

    private Map<String, String> getUrlParam() {
        Map<String,String> urlMap = new HashMap<>();
        urlMap.put("id",messageId);
        return urlMap;
    }

    private void initTopTitle() {
        RelativeLayout topTile = (RelativeLayout) findViewById(R.id.message_detail_top_title);
        topTile.setBackgroundColor(getResources().getColor(R.color.theme_white));
        ImageView titleLeft = (ImageView) topTile.findViewById(R.id.title_left);
        TextView titleTv = (TextView) topTile.findViewById(R.id.title_tv);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        titleTv.setText("消息详情");
        titleTv.setTextColor(getResources().getColor(R.color.theme_black));
        titleLeft.setImageResource(R.mipmap.icon_back_black);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetailActivity.this.finish();
            }
        });
    }
}
