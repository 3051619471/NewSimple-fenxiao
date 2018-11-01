package com.astgo.fenxiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.tools.utils.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ast009 on 2017/11/24.
 */

public class BaseActivity extends AppCompatActivity{
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String  event) {
        Log.d("onEventMainThread", "BaseActivity---" + event);
        if(AppConstant.KEY_RE_LOGIN.equals(event)){
//            跳转到登录页面
            toLoginActivity();
        }
    }

    public void toLoginActivity() {
        SPUtils.clear();
//        PreferenceUtil.clear();
        JPushInterface.stopPush(this.getApplicationContext());
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
