package com.astgo.fenxiao.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.adapter.MyFragmentPagerAdapter;
import com.astgo.fenxiao.adapter.ShopCategoryAdapter;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.CategoryBean;
import com.astgo.fenxiao.bean.ShopBannerInfor;
import com.astgo.fenxiao.fragment.shopcommodity.CommodityXiaoLiangFragment;
import com.astgo.fenxiao.fragment.shopcommodity.CommodityXinPinFragment;
import com.astgo.fenxiao.fragment.shopcommodity.CommodityZongHeFragment;
import com.astgo.fenxiao.glide.GlideImageLoader;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpShopResultSubscriber;
import com.astgo.fenxiao.http.service.HttpUtils;
import com.astgo.fenxiao.tools.utils.DialogUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.webview.newwebview.TaoWebviewActivity;
import com.astgo.fenxiao.widget.RefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 这是主页fragment，包括了一个自动滚动广告和滚动字幕
 * 还有5个自定义菜单按钮和3个固定菜单按钮
 */
public class NewShoppingFragment extends BaseFragment  {

    private SmartRefreshLayout refreshLayout;
    private String shopUrlSuffix;
    public NewShoppingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_tao_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        shopUrlSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX, "");
        getBannerData();
       // getShopCategoryData();
    }

//    private void getShopCategoryData() {
//        String shopSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX, "");
//        if(!TextUtils.isEmpty(shopSuffix)){
//            HttpClient.Builder.getShopServer().getShopCategoryData(HttpUtils.API_SHOP_APP+"index.php?m=Home&c=index&a=category"+shopSuffix)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpShopResultSubscriber<List<ShopCategoryItemBean>>() {
//                    @Override
//                    public void onSuccess(List<ShopCategoryItemBean> shopCategoryItemBeans) {
//                        if(shopCategoryItemBeans != null && shopCategoryItemBeans.size()>0){
//                            NewShoppingFragment.this.shopCategoryBeanList = shopCategoryItemBeans;
//                            shopCategoryAdapter.setNewData(NewShoppingFragment.this.shopCategoryBeanList);
//                        }
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//
//                    }
//                });
//        }
//
//    }

    private void getBannerData() {
        String shopSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX, "");
        if(!TextUtils.isEmpty(shopSuffix)){
            final Dialog dialog = DialogUtil.getInstance().showDialog(getActivity(),"获取数据中");
            HttpClient.Builder.getShopServer().getShopBannerData(HttpUtils.API_SHOP_APP+"index.php?m=Home&c=index&a=swiper"+shopSuffix)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpShopResultSubscriber<List<ShopBannerInfor>>() {
                        @Override
                        public void onSuccess(List<ShopBannerInfor> shopBannerInfors) {
                            dialog.dismiss();
                            if(shopBannerInfors != null && shopBannerInfors.size()>0){
                                setBannerData(shopBannerInfors);
                            }
                        }

                        @Override
                        public void _onError(String msg, int code) {
                            dialog.dismiss();
                        }
                    });
        }

    }

    private void setBannerData(final List<ShopBannerInfor> shopBannerInforList) {
        List<String> bannerImaglist =  new ArrayList<>();
        for (ShopBannerInfor shopBannerInfor : shopBannerInforList) {
            bannerImaglist.add(shopBannerInfor.getImgurl());
        }
        banner.setImages(bannerImaglist).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                ShopBannerInfor bannerBean = shopBannerInforList.get(position-1);
                if(bannerBean!= null){
                    TaoWebviewActivity.loadUrl(getActivity(),bannerBean.getJumpurl());
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 初始化 findViewById 和 ClickListener
    private Banner banner;
    private EditText etSearch;
    private void initView(View view) {
        etSearch = (EditText) view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    //搜索
                    dismissSoftInput();
                    String searchString = etSearch.getText().toString().trim();
                    String url = "http://taobaobuy.7oks.net/index.php?m=Home&c=Index&a=search&keywords="+searchString;
                    TaoWebviewActivity.loadUrl(getActivity(),url+SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,""));
                }
                return true;
            }
        });

        banner = (Banner) view.findViewById(R.id.banner);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setRefreshHeader(new RefreshHeader(this.getActivity().getApplicationContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
            }
        });

        initRecv(view);
        initTabLayout(view);
    }


    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private void initTabLayout(View view) {
        initFragmentList();
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tl_shop_list);
        ViewPager myViewPager = (ViewPager) view.findViewById(R.id.vp_all_commodity);
        myViewPager.setAdapter(new MyFragmentPagerAdapter(this.getChildFragmentManager(),mFragments,mTitleList));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(myViewPager);
    }

    private void initFragmentList() {
        mTitleList.add("综合");
        mTitleList.add("销量");
        mTitleList.add("新品");
        mFragments.add(new CommodityZongHeFragment());
        mFragments.add(new CommodityXiaoLiangFragment());
        mFragments.add(new CommodityXinPinFragment());
    }

    private List<CategoryBean.DataBean> shopCategoryBeanList = new ArrayList<>();
    private ShopCategoryAdapter shopCategoryAdapter;
    private void initRecv(View view) {
        RecyclerView recvShopCategory = (RecyclerView) view.findViewById(R.id.shop_category);
        shopCategoryAdapter = new ShopCategoryAdapter(R.layout.shop_category_item,shopCategoryBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager (getActivity(),5);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recvShopCategory.setLayoutManager(gridLayoutManager);
        recvShopCategory.getItemAnimator().setChangeDuration(0);
        recvShopCategory.setHasFixedSize(true);
        recvShopCategory.setNestedScrollingEnabled(false);
        recvShopCategory.setAdapter(shopCategoryAdapter);
//        http://taobaobuy.7oks.net/index.php?m=Home&c=index&a=search&category=1
        shopCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(shopCategoryBeanList != null && shopCategoryBeanList.size()>0){
                    CategoryBean.DataBean shopCategoryItemBean = shopCategoryBeanList.get(position);
                    if(shopCategoryItemBean != null){
                        TaoWebviewActivity.loadUrl(getActivity(),shopCategoryItemBean.getUrl()+SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX,""));
                    }
                }
            }
        });

    }


    public void dismissSoftInput() {
        Window mWindow  = this.getActivity().getWindow();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

