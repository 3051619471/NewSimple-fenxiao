package com.astgo.fenxiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.astgo.fenxiao.activity.BaseActivity;
import com.astgo.fenxiao.bean.AppConstant;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.greenrobot.event.EventBus;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ast009 on 2017/12/6.
 */

public class BaseFragment extends Fragment {
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String  event) {
        Log.d("onEventMainThread", "BaseFragment---" + event);
        if(AppConstant.KEY_RE_LOGIN.equals(event)){
//            跳转到登录页面
            ((BaseActivity)getActivity()).toLoginActivity();
        }
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
