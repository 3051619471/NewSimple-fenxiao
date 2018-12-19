package com.astgo.naoxuanfeng.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.astgo.naoxuanfeng.MessageActivity;
import com.astgo.naoxuanfeng.bean.AppConstant;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ast009 on 2017/11/30.
// */
//private static final int MSG_SET_ALIAS = 1001;
//
//private void setAlias() {
//        String alias = userName;
//        if (TextUtils.isEmpty(alias)) {
//        Toast.makeText(this, "异常", Toast.LENGTH_SHORT).show();
//        return;
//        }
//        // 调用 Handler 来异步设置别名
//        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//        }
//
//private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//@Override
//public void gotResult(int code, String alias, Set<String> tags) {
//        String logs;
//        switch (code) {
//        case 0:
//        logs = "Set tag and alias success";
//        // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//        break;
//        case 6002:
//        logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//        // 延迟 60 秒来调用 Handler 设置别名
//        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//        break;
//default:
//        logs = "Failed with errorCode = " + code;
//        }
//        }
//        };
//
//private final Handler mHandler = new Handler() {
//@Override
//public void handleMessage(android.os.Message msg) {
//        super.handleMessage(msg);
//        switch (msg.what) {
//        case MSG_SET_ALIAS:
//        // 调用 JPush 接口来设置别名。
//        JPushInterface.setAliasAndTags(getApplicationContext(),
//        (String) msg.obj,
//        null,
//        mAliasCallback);
//        break;
//default:
//        }
//        }
//        };
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG="JPushReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            //接收到了自定义的消息
            if(intent.getAction().equals("cn.jpush.android.intent.MESSAGE_RECEIVED")){
                Bundle bundle = intent.getExtras();
                //消息的标题。对应 API 消息内容的 title 字段。Portal 推送消息界上不作展示
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
                //消息内容对应 API 消息内容的 message 字段。对应 Portal 推送消息界面上的"自定义消息内容”字段。
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                //附加字段。这是个 JSON 字符串,对应 API 消息内容的 extras 字段。对应 Portal 推送消息界面上的“可选设置”里的附加字段。
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                唯一标识消息的 ID
                String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);

            }

            if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
                Log.d(TAG, "[MyReceiver] 收到了通知");
            }

            if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                //打开自定义的Activity
                Bundle bundle = intent.getExtras();
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra(AppConstant.KEY_JPUSH_FLAG,"JPSUH_MESSAGE");
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }

    }
}
