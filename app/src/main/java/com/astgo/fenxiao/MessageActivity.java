package com.astgo.fenxiao;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.fenxiao.activity.BaseActivity;
import com.astgo.fenxiao.activity.MessageDetailActivity;
import com.astgo.fenxiao.adapter.MessageAdapter;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.JpushMessageBean;
import com.astgo.fenxiao.bean.MessageListBean;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpResultSubscriber;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ast009 on 2017/11/30.
 */

public class MessageActivity extends BaseActivity{

    private List<JpushMessageBean> jpushMessageBeanList = new ArrayList<>();
    private String flag="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getIntentData();
        initView();
        initData();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(AppConstant.KEY_JPUSH_FLAG)){
            flag = intent.getStringExtra(AppConstant.KEY_JPUSH_FLAG);
        }
    }

    private void initData() {

        final Dialog dialog = DialogUtil.getInstance().showDialog(this,"");
        Subscription subscription = HttpClient.Builder.getAppService().getMessageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultSubscriber<MessageListBean>() {
                    @Override
                    public void onSuccess(MessageListBean messageListBean) {
                        dialog.dismiss();
                        if(messageListBean != null && messageListBean.getList()!= null && messageListBean.getList().size()>0){
                                MessageActivity.this.jpushMessageBeanList = messageListBean.getList();
                                messageAdapter.setJpushMessageBeanList(MessageActivity.this.jpushMessageBeanList);
                        }else{
                            setNoDataUIState();
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

    private void setNoDataUIState() {
        llNoData.setVisibility(View.VISIBLE);
        rcvMessage.setVisibility(View.GONE);
    }

    RelativeLayout topTile;
    private void initView() {
        initTopTitle();
        initRec();
    }

    private void initTopTitle() {
        topTile = (RelativeLayout) findViewById(R.id.message_top_title);
        topTile.setBackgroundColor(getResources().getColor(R.color.theme_white));
        ImageView titleLeft = (ImageView) topTile.findViewById(R.id.title_left);
        TextView titleTv = (TextView) topTile.findViewById(R.id.title_tv);
        titleTv.setText("消息通知");
        titleTv.setTextColor(getResources().getColor(R.color.theme_black));
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        titleLeft.setImageResource(R.mipmap.icon_back_black);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if("JPSUH_MESSAGE".equals(flag)){
//                    Intent intent = new Intent(MessageActivity.this, LaunchActivity.class);
//                    startActivity(intent);
//                }
                MessageActivity.this.finish();
            }
        });
    }

    private RecyclerView rcvMessage;
    private MessageAdapter messageAdapter;
    private LinearLayout llNoData;
    private void initRec() {
        rcvMessage = (RecyclerView) findViewById(R.id.rcv_message);
        llNoData = (LinearLayout) findViewById(R.id.ll_no_data);
        rcvMessage.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(jpushMessageBeanList);
        rcvMessage.setAdapter(messageAdapter);
        messageAdapter.setOnRecyclerViewListener(new MessageAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(JpushMessageBean jpushMessageBean, View view, int position) {
                if(view.getId() == R.id.ll_content){
                    //跳转到详情页面
                    Intent intent = new Intent(MessageActivity.this,MessageDetailActivity.class);
                    if(jpushMessageBeanList.size()>0 && jpushMessageBeanList.get(position) != null){
//                        intent.putExtra(AppConstant.INTENT_MESSAGE_DETAIL,jpushMessageBeanList.get(position));
                        intent.putExtra(AppConstant.INTENT_MESSAGE_ID,jpushMessageBeanList.get(position).getId());

                    }
                    startActivity(intent);
                }
            }
        });
    }
}
