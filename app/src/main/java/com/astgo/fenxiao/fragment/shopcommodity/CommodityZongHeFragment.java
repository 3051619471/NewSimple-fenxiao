package com.astgo.fenxiao.fragment.shopcommodity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.adapter.CommodityAdapter;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.BaseCommodityBean;
import com.astgo.fenxiao.bean.ShopListBean;
import com.astgo.fenxiao.fragment.BaseFragment;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpUtils;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.newwebview.TaoWebviewActivity;
import com.astgo.fenxiao.widget.MyDecoration;
import com.astgo.fenxiao.widget.WhiteRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ast009 on 2017/12/13.
 */

public class CommodityZongHeFragment extends BaseFragment {

    private static final String TAG = "CommodityZongHeFragment";

    private boolean isVisible = false;
    private List<ShopListBean.DataBean.ListBean> commodityBeenList = new ArrayList<>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG,"isVisibleToUser:"+isVisibleToUser);
        isVisible = isVisibleToUser;
        if(isVisible && commodityBeenList.size() == 0){
            //请求服务器获取数据
//            getCommodity(page,orders);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return inflater.inflate(R.layout.fragment_recv, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Log.d(TAG,"onViewCreated");
    }

    private RecyclerView recvCommodity;
    private CommodityAdapter commodityAdapter ;
    private SmartRefreshLayout refreshLayout;
    private TextView tvRequesting;
    private void initView(View view) {
        tvRequesting = (TextView) view.findViewById(R.id.tv_requesting);
        tvRequesting.setVisibility(View.GONE);

        recvCommodity = (RecyclerView) view.findViewById(R.id.recv_commodity);
        recvCommodity.setVisibility(View.VISIBLE);

        commodityAdapter = new CommodityAdapter(R.layout.item_commodity,commodityBeenList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recvCommodity.setLayoutManager(layoutManager);
        recvCommodity.getItemAnimator().setChangeDuration(0);
        recvCommodity.setHasFixedSize(true);
        recvCommodity.setNestedScrollingEnabled(false);
        recvCommodity.addItemDecoration(new MyDecoration(this.getContext(), MyDecoration.VERTICAL_LIST));
        recvCommodity.setAdapter(commodityAdapter);
        commodityAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getCommodity(page,orders);
            }
        });

        commodityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(commodityBeenList != null && commodityBeenList.size()>0){
                    ShopListBean.DataBean.ListBean commodityBean = commodityBeenList.get(position);
                    if(commodityBean != null){
                        String url = commodityBean.getUrl();
                        if(!TextUtils.isEmpty(url)){
                            TaoWebviewActivity.loadUrl(getParentFragment().getActivity(),url+SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,""));
                        }
                    }
                }
            }
        });

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.recv_refresh);

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setRefreshHeader(new WhiteRefreshHeader(this.getActivity().getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                getCommodity(page,orders);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"onActivityCreated");
        initData();
    }

    private int page = 1; //分页请求数据
    private String orders = "comprehensive";
    private void initData() {
        if(isVisible && commodityBeenList.size() == 0){
            //请求服务器获取数据
            getCommodity(page,orders);
        }
    }

    private void getCommodity(final int page, String orders) {
        String shopSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,"");
        if(!TextUtils.isEmpty(shopSuffix)){
            setRefreshingUI();
            Subscription subscription = HttpClient.Builder.getShopServer().getShopVolumeData(HttpUtils.API_SHOP_APP+"index.php?m=Home&c=index&a=index"+shopSuffix,page,orders)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseCommodityBean>() {
                        @Override
                        public void onCompleted() {

                            refreshLayout.finishRefresh();
                        }

                        @Override
                        public void onError(Throwable e) {
                            refreshLayout.finishRefresh();
                        }

                        @Override
                        public void onNext(BaseCommodityBean baseCommodityBean) {
                            refreshLayout.finishRefresh();
                            setRefreshCompleteUI();
                            if(baseCommodityBean != null && baseCommodityBean.getList() != null
                                    && baseCommodityBean.getList().size()>0){
                                if(page ==1){
                                    //setData(true,baseCommodityBean);
                                }
                                if(page >1){
                                    //setData(false,baseCommodityBean);
                                }
                            }
                        }
                    });
            addSubscription(subscription);
        }
    }

    private void setRefreshCompleteUI() {
        if(page == 1){
            tvRequesting.setVisibility(View.GONE);
            recvCommodity.setVisibility(View.VISIBLE);
        }
    }

    private void setRefreshingUI() {
        if(page == 1){
            tvRequesting.setVisibility(View.VISIBLE);
            recvCommodity.setVisibility(View.GONE);
        }
    }

    private static final int PAGE_SIZE = 6;
//    private void setData(boolean isRefresh, BaseCommodityBean baseCommodityBean) {
//        page++;
//        List<CommodityBean> data = baseCommodityBean.getList();
//        final int size = data == null ? 0 : data.size();
//
//        if (size > 0) {
//            this.commodityBeenList.addAll(data);
//            commodityAdapter.addData(data);
//        }
//
//        if (isRefresh) {
//            this.commodityBeenList = baseCommodityBean.getList();
//            commodityAdapter.setNewData(data);
//        } else {
//            if (size > 0) {
//                this.commodityBeenList.addAll(data);
//                commodityAdapter.addData(data);
//            }
//        }
//        if (size < PAGE_SIZE) {
//            //第一页如果不够一页就不显示没有更多数据布局
//            commodityAdapter.loadMoreEnd(true);
//        } else {
//            commodityAdapter.loadMoreComplete();
//        }
//
//        if(page > baseCommodityBean.getCountPage()){
//            commodityAdapter.loadMoreEnd(true);
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }


}
