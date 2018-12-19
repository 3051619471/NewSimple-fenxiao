package com.astgo.naoxuanfeng.http.service;


import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.AppConstant;
import com.astgo.naoxuanfeng.bean.BaseShopDataObject;
import com.astgo.naoxuanfeng.http.retrofit.ApiException;
import com.blankj.utilcode.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * Created by ast009 on 2017/11/16.
 */

public abstract class HttpShopResultSubscriber<T> extends Subscriber<BaseShopDataObject<T>> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(BaseShopDataObject<T> t) {
        if (t.getStatus() == AppConstant.CODE_SUCCESS) {
            onSuccess(t.getData());
        } else {
            _onError(t.getInfo(),t.getStatus());
            if(t.getStatus() == AppConstant.CODE_DEVICE_CHANGE){
                EventBus.getDefault().post(AppConstant.KEY_RE_LOGIN);
            }
            ToastUtils.showLongToast(t.getInfo());
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof ConnectException ||
                e instanceof SocketTimeoutException ||
                e instanceof TimeoutException
                ) {
            //网络错误
            _onError(e.getMessage(),-9999);
            ToastUtils.showLongToast(R.string.net_erro);
        }
        if (e instanceof ApiException) {
            _onError(e.getMessage(),((ApiException)e).getmErrorCode());
            ToastUtils.showLongToast(((ApiException) e).getmErrorMessage());
        }
    }


    public abstract void onSuccess(T t);

    public abstract void _onError(String msg, int code);
}
