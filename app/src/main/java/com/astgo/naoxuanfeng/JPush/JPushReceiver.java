package com.astgo.naoxuanfeng.JPush;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.astgo.naoxuanfeng.MainActivity;
import com.astgo.naoxuanfeng.MyApplication;
import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.tools.MAppUtils;
import com.astgo.naoxuanfeng.tts.control.SpeakVoiceUtil;
import com.astgo.naoxuanfeng.video.CommonActivity;
import com.blankj.utilcode.utils.ActivityUtils;
import com.blankj.utilcode.utils.AppUtils;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ast009 on 2018/7/6.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPushReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                // processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                String extrs = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if (!TextUtils.isEmpty(extrs)) {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    if (json.has("is_voice")) {
                        if (json.getString("is_voice").equals("1")) {
                            String content = json.getString("voice_msg");

                            speakMethod(content);
                        }
                    }

                }


            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
                String extrs = bundle.getString(JPushInterface.EXTRA_EXTRA);


                if (MAppUtils.getAppSatus(context, context.getPackageName()) == 3 ||
                        !com.astgo.naoxuanfeng.tools.utils.SPUtils.getBoolean(MyConstant.SP_LOGIN_STATE, false)) {//应该不存在这种情况以防万一
                    AppUtils.launchApp(context.getPackageName());
                } else {

                    if (!TextUtils.isEmpty(extrs)) {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));

                        if (json.has("url")) {
                            String url = json.getString("url");
                            Intent intent1 = new Intent(ActivityUtils.getTopActivity(), CommonActivity.class);
                            intent1.putExtra("url", url);
                            ActivityUtils.getTopActivity().startActivity(intent1);
                            return;
                        }
                    }
                    //打开自定义的Activity
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtras(bundle);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }


    public void speakMethod(final String content) {
        Log.e("slh", "speakMethod");
        Observable
                .create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        if (SpeakVoiceUtil.getInstance(MyApplication.mApplicationContext) != null) {
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } else {
                            Log.e("slh", "speakVoiceUtil == null");
                        }


                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        Log.e("slh", "Action1" + b);
                        if (b) {
                            SpeakVoiceUtil
                                    .getInstance(MyApplication.mApplicationContext)
                                    .speak(content);
                        }
                    }
                });

    }

}
