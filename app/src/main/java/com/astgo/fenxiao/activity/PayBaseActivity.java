package com.astgo.fenxiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.astgo.fenxiao.tools.utils.EventString;
import com.astgo.fenxiao.tools.utils.Preconditions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



    /**
     * Created by Administrator on 2016/5/24.
     */
public abstract class PayBaseActivity extends AppCompatActivity {

    protected Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        setContentView(getLayoutResource());
        //CloseActivityClass.activityList.add(this);
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closectivity(EventString eventString) {
        if (eventString.getInfo().equals("finish")) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void getIntentData() {
        intent = getIntent();
        intent = Preconditions.checkNotNull(intent, "intent can not be null");
    }

    protected abstract
    @LayoutRes
    int getLayoutResource();


}

