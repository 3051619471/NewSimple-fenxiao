package com.astgo.naoxuanfeng.studyvideo.video_fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.AppConstant;
import com.astgo.naoxuanfeng.bean.WXPay;
import com.astgo.naoxuanfeng.classdomel.class_utils.OkhttpUtils;
import com.astgo.naoxuanfeng.classdomel.class_utils.RecyclerViewItemDecoration;
import com.astgo.naoxuanfeng.studyvideo.adapter.My_Upload_Adapter;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Study_Bean;
import com.astgo.naoxuanfeng.tools.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.astgo.naoxuanfeng.MyApplication.mContext;

/**
 * Created by Administrator on 2018/12/5.
 */
//我的上传
public class My_Upload_Fragment extends Fragment {
    private View view;
    private XRecyclerView mMyUploadRecycle;
private String path;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_upload_ment, container, false);
        initView(view);
        ConstantURL constantURL = new ConstantURL();
        String ip_url = constantURL.IP_url;
        SharedPreferences data1 = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String uid = data1.getString("uid", "");
        //视频
        path = ip_url + "index.php?s=/Home/video/videoResource&id=1&uid=" + uid;
        initResource();
        initply();
        return view;
    }
    private void initResource() {
        OkhttpUtils getshu = OkhttpUtils.getshu();
        getshu.say(path, new OkhttpUtils.fun1() {
            @Override
            public void onres(String string) {
                Gson gson = new Gson();
                Video_Study_Bean video_study_bean = gson.fromJson(string, Video_Study_Bean.class);
                List<Video_Study_Bean.DataBean> data = video_study_bean.getData();
                My_Upload_Adapter my_upload_adapter = new My_Upload_Adapter(data, getActivity());
                mMyUploadRecycle.setAdapter(my_upload_adapter);
                mMyUploadRecycle.setLayoutManager( new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false) );
                //为RecyclerView增加分割线，水平和垂直方向都有。增加分割线值比如为32。
                RecyclerViewItemDecoration decoration = new RecyclerViewItemDecoration(32);
                mMyUploadRecycle.addItemDecoration(decoration);
                my_upload_adapter.OnItemClickListenerUpload(new My_Upload_Adapter.OnItemClickListenerUpload() {
                    @Override
                    public void onClick(int position) {

                    }
                });

            }
        });
    }
    /**
     * /微信支付
     */
    private void wxPay(WXPay wxPay) {
        //{"appid":"wx4eeaa909776fb55a","mch_id":"1503083041","nonce_str":"YUCKp2uPkuRXc4i1","prepay_id":"wx08142816301372661f1995ae3598675191","sign":"968634B6A4101534935E82620D6E6738","timestamp":"1525760896","key":"heshunshangquanweixinpay20180508","package":"Sign=WXPay","type":"wechat","viewurl":"http://hssq.dykj168.com/index.php?m=Home&c=order&a=view&order_id=348"}
        if (getActivity() != null) {
            if (!checkPackage("com.tencent.mm")) {
                ToastUtils.showShortToast("请先安装微信");
                return;
            }
            SPUtils.putString(AppConstant.KEY_VIEW_URL, wxPay.getViewurl());
            AppConstant.WX_APP_ID = wxPay.getAppid();//将appid写到常量中保存
            IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), AppConstant.WX_APP_ID);
            // api.registerApp(AppConstant.WX_APP_ID);
            PayReq req = new PayReq();
            req.appId = wxPay.getAppid();//appid号
            req.partnerId = wxPay.getMch_id();//商户号
            req.prepayId = wxPay.getPrepay_id();//
            req.nonceStr = wxPay.getNonce_str();
            req.timeStamp = wxPay.getTimestamp();
            req.packageValue = wxPay.getPackageX();
            req.sign = wxPay.getSign();
            api.sendReq(req);
        }
    }

    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            mContext.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void initply() {

        mMyUploadRecycle.setPullRefreshEnabled( true );
        mMyUploadRecycle.setLoadingMoreEnabled( true );
        mMyUploadRecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mMyUploadRecycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mMyUploadRecycle.loadMoreComplete();
            }
        });
    }
    private void initView(View view) {
        mMyUploadRecycle = (XRecyclerView) view.findViewById(R.id.my_upload_recycle);
    }
}
