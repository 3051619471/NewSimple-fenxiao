package com.astgo.fenxiao.fragment.shop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.astgo.fenxiao.activity.LoginActivity;
import com.astgo.fenxiao.bean.HomeColumnBean;
import com.astgo.fenxiao.tools.utils.CommonRCAdapter;
import com.astgo.fenxiao.tools.utils.CommonRCViewHolder;
import com.astgo.fenxiao.tools.utils.DialogUtil;
import com.astgo.fenxiao.widget.ImageViewWH1b1;
import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.NetUrl;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.adapter.CommodityAdapter;
import com.astgo.fenxiao.adapter.ShopCategoryAdapter;
import com.astgo.fenxiao.bean.BaseDataObject;
import com.astgo.fenxiao.bean.CategoryBean;
import com.astgo.fenxiao.bean.NewBanner;
import com.astgo.fenxiao.bean.ShopListBean;
import com.astgo.fenxiao.fragment.BaseFragment;
import com.astgo.fenxiao.glide.GlideImageLoader;
import com.astgo.fenxiao.minterface.EditTextChangeInterface;
import com.astgo.fenxiao.tools.CommonAdapter;
import com.astgo.fenxiao.tools.ViewHolder;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.video.CommonActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 这是主页fragment，包括了一个自动滚动广告和滚动字幕
 * 还有5个自定义菜单按钮和3个固定菜单按钮
 */
public class NestShoppingFragment extends BaseFragment implements View.OnClickListener {

    private SmartRefreshLayout refreshLayout;
    private static final String ZONGHE = "comprehensive";
    private static final String XIAOLIANG = "volume";
    private static final String XINPIN = "newgoods";

    public static int p = 1;
    public static String category_id = "";
    public static String keywords = "";
    public static String gtype = "";
    public static String otype = "";
    private PopupWindow mPopupWindow;
    private String pop_otype = "";


    public RecyclerView recyclerView;
    public CommonRCAdapter<HomeColumnBean> adapterColumn;
    public List<HomeColumnBean> homeColumnBeanList;
    public int[] imgTitles = new int[]{R.drawable.p01, R.drawable.p02, R.drawable.p03, R.drawable.p04, R.drawable.p05, R.drawable.p06};
    public String[] desList = new String[]{"爆款直降 低至2.5折", "优品大集合 带你逛不停", "优惠券抵扣 实惠多多", "积分！用心挑好物", "探索新奇特", "邂逅好物 发现理想生活"};

    public NestShoppingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_nest_tao_shop, container, false);
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
        getBannerData();
        getShopCategoryData();
        p = 1;
        category_id = "";
        keywords = "";
        gtype = "3";
        otype = "comprehensive";

        //  getShopList(p, category_id, keywords, gtype, otype, false, true);
        getHomeColumn();

    }

    private void getHomeColumn() {
        // 1档位商品 2和顺商城 3返利商城 4积分商城 5淘宝商城 和顺入驻type=”shop” 特价商品type=9

        OkHttpUtils.get(NetUrl.GET_COLUMN)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject job = new JSONObject(s);
                            int code = job.getInt("code");
                            if (code == 1) {
                                homeColumnBeanList.clear();
                                JSONObject dataObj = job.getJSONObject("data");

                                JSONArray jrr9 = dataObj.getJSONArray("9");
                                HomeColumnBean homeColumnBean9 = new HomeColumnBean();
                                homeColumnBean9.IMGID = imgTitles[0];
                                homeColumnBean9.des = desList[0];
                                homeColumnBean9.IMG_1 = jrr9.get(0).toString();
                                homeColumnBean9.IMG_2 = jrr9.get(1).toString();
                                homeColumnBeanList.add(homeColumnBean9);

                                JSONArray jrrshop = dataObj.getJSONArray("shop");
                                HomeColumnBean homeColumnBeanshop = new HomeColumnBean();
                                homeColumnBeanshop.IMGID = imgTitles[1];
                                homeColumnBeanshop.des = desList[1];
                                homeColumnBeanshop.IMG_1 = jrrshop.get(0).toString();
                                homeColumnBeanshop.IMG_2 = jrrshop.get(1).toString();
                                homeColumnBeanList.add(homeColumnBeanshop);

                                JSONArray jrr5 = dataObj.getJSONArray("5");
                                HomeColumnBean homeColumnBean5 = new HomeColumnBean();
                                homeColumnBean5.IMGID = imgTitles[2];
                                homeColumnBean5.des = desList[2];
                                homeColumnBean5.IMG_1 = jrr5.get(0).toString();
                                homeColumnBean5.IMG_2 = jrr5.get(1).toString();
                                homeColumnBeanList.add(homeColumnBean5);


                                JSONArray jrr4 = dataObj.getJSONArray("4");
                                HomeColumnBean homeColumnBean4 = new HomeColumnBean();
                                homeColumnBean4.IMGID = imgTitles[3];
                                homeColumnBean4.des = desList[3];
                                homeColumnBean4.IMG_1 = jrr4.get(0).toString();
                                homeColumnBean4.IMG_2 = jrr4.get(1).toString();
                                homeColumnBeanList.add(homeColumnBean4);

                                JSONArray jrr3 = dataObj.getJSONArray("3");
                                HomeColumnBean homeColumnBean3 = new HomeColumnBean();
                                homeColumnBean3.IMGID = imgTitles[4];
                                homeColumnBean3.des = desList[4];
                                homeColumnBean3.IMG_1 = jrr3.get(0).toString();
                                homeColumnBean3.IMG_2 = jrr3.get(1).toString();
                                homeColumnBeanList.add(homeColumnBean3);


                                JSONArray jrr2 = dataObj.getJSONArray("2");
                                HomeColumnBean homeColumnBean2 = new HomeColumnBean();
                                homeColumnBean2.IMGID = imgTitles[5];
                                homeColumnBean2.des = desList[5];
                                homeColumnBean2.IMG_1 = jrr2.get(0).toString();
                                homeColumnBean2.IMG_2 = jrr2.get(1).toString();
                                homeColumnBeanList.add(homeColumnBean2);


                                adapterColumn.notifyDataSetChanged();


                            } else {
                                if (code == 100) {
                                    OkHttpUtils.getInstance().cancelTag(this);
                                    SPUtils.putBoolean("isLogin", false);
                                    startActivity(new Intent(getActivity(), LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    getActivity().finish();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("slh", e.getMessage());
                    }
                });
    }

    /**
     * p			=> 页数
     * category_id	=> 分类
     * keywords	=> 搜索关键词
     * gtype		=> 商品类别：5淘宝商城2和顺商城1档位商品3返利商城4积分商城
     * otype		=> 排序：comprehensive 综合排序，volume销量，newgoods 新品，price_desc价格降序，price_asc 价格升序
     */
//    private void getShopList(int p, String category_id, String keywords, final String gtype, final String otype, final boolean isLoadMore, final boolean isRefrash) {
//        final Dialog dialog = DialogUtil.getInstance().showDialog(getActivity(), "");
//        OkHttpUtils.get(NetUrl.GET_SHOP_LIST)     // 请求方式和请求url
//                .tag(this)// 请求的 tag, 主要用于取消对应的请求
//                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
//
//                .params("p", p)
//                .params("category_id", category_id)
//                .params("keywords", keywords)
//                .params("gtype", gtype)
//                .params("otype", otype)
//
//                .cacheKey("ShopList")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        if (refreshLayout != null) refreshLayout.finishRefresh();
//                        DebugUtil.error("ShopList" + s);
//                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
//                        if (baseDataObject.getCode() == 1) {
//                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
//                                ShopListBean shopListBean = (ShopListBean) BaseDataObject.getBaseBean(s, ShopListBean.class);
//                                countPage = shopListBean.getData().getCountPage();
//                                Log.e("SLH", "count" + countPage);
//                                if (!isLoadMore) {
//                                    commodityBeanList.clear();
//                                    //comprehensive 综合排序，price_desc价格降序，price_asc 价格升序
//                                    if (isRefrash) {
//                                        resetSelect();
//                                        resetSelect1();
//                                        tv1.setSelected(true);
//                                        tv1.setText("综合排序");
//                                        tv_shop3.setSelected(true);
//                                        //commodityAdapter.loadMoreComplete();
//
//                                    }
//                                }
//                                int size = shopListBean.getData().getList().size();
//                                if (size == 0) {
//                                    ToastUtils.showShortToast("没有数据了");
//                                }
//
//                                commodityBeanList.addAll(shopListBean.getData().getList());
//
////                                if (size<PAGE_SIZE){
////                                    //第一页如果不够一页就不显示没有更多数据布局
////                                    commodityAdapter.loadMoreEnd(true);
////                                }else {
////                                    commodityAdapter.loadMoreComplete();
////                                }
////                                if (countPage > 0 && NestShoppingFragment.p > countPage) {
////                                    commodityAdapter.loadMoreEnd(true);
////                                }
//                                if (gtype.equals("5")) {
//                                    commodityAdapter.setTicket(true);
//
//                                } else {
//                                    commodityAdapter.setTicket(false);
//                                }
//                                commodityAdapter.notifyDataSetChanged();
//                                //commodityAdapter.loadMoreComplete();
//                                if (refreshLayout != null) {
//                                    refreshLayout.finishRefresh();
//                                    refreshLayout.finishLoadmore();
//                                }
//                            }
//                        } else {
//                            if (refreshLayout != null) {
//                                refreshLayout.finishRefresh();
//                                refreshLayout.finishLoadmore();
//                            }
//                            ToastUtils.showShortToast(baseDataObject.getMsg());
//
//                            if (baseDataObject.getCode() == 100) {
//                                OkHttpUtils.getInstance().cancelTag(this);
//                                SPUtils.putBoolean("isLogin", false);
//                                startActivity(new Intent(getActivity(), LoginActivity.class)
//                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                                getActivity().finish();
//                            }
//
//                        }
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        dialog.dismiss();
//                        if (refreshLayout != null) {
//                            refreshLayout.finishRefresh();
//                            refreshLayout.finishLoadmore();
//                        }
//                        ToastUtils.showShortToast(e.getMessage());
//                    }
//                });
//    }
    private void getShopCategoryData() {
        OkHttpUtils.get(NetUrl.SHOP_CATEGRORY)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("category")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("category" + s);
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
                                CategoryBean categoryBean = (CategoryBean) BaseDataObject.getBaseBean(s, CategoryBean.class);
                                shopCategoryBeanList.clear();
                                shopCategoryBeanList.addAll(categoryBean.getData());
                                shopCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100) {
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin", false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void getBannerData() {
        OkHttpUtils.get(NetUrl.BANNER_SHOP)     // 请求方式和请求url
                .tag(this)// 请求的 tag, 主要用于取消对应的请求
                .headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
                .cacheKey("getBannerData")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        DebugUtil.error("getBannerData" + s);
                        //{"data":[{"title":"\u6807\u98981","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48da3b4bf.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6863\u4f4d\u5546\u54c1","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5ac3439b29f58.png","jumpurl":"\/home\/index\/lvgoods"},{"title":"\u6807\u98983","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48a028e85.png","jumpurl":"http:\/\/www.baidu.com"},{"title":"\u6807\u98982","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d48fd48b9c.png","jumpurl":"www.baidu.com"},{"title":"\u6807\u98984","imgurl":"http:\/\/hssq.dykj168.com\/Uploads\/carouselimg\/5a0d491c077b1.png","jumpurl":"www.baidu.com"}],"info":"success","status":1}
                        BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
                        if (baseDataObject.getCode() == 1) {
                            if (!TextUtils.isEmpty(baseDataObject.getData().toString())) {
                                NewBanner newBanner = (NewBanner) BaseDataObject.getBaseBean(s, NewBanner.class);
                                setBannerData(newBanner.getData());
                            }
                        } else {
                            ToastUtils.showShortToast(baseDataObject.getMsg());
                            if (baseDataObject.getCode() == 100) {
                                OkHttpUtils.getInstance().cancelTag(this);
                                SPUtils.putBoolean("isLogin", false);
                                startActivity(new Intent(getActivity(), LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                getActivity().finish();
                            }

                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast(e.getMessage());
                    }
                });


    }

    private void setBannerData(final List<NewBanner.DataBean> shopBannerInforList) {
        List<String> bannerImaglist = new ArrayList<>();
        for (NewBanner.DataBean shopBannerInfor : shopBannerInforList) {
            bannerImaglist.add(shopBannerInfor.getImgurl());
        }
        banner.setImages(bannerImaglist).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position >= shopBannerInforList.size()) return;
                NewBanner.DataBean bannerBean = shopBannerInforList.get(position);
                if (bannerBean != null) {
                    Intent intent = new Intent(getActivity(), CommonActivity.class);
                    String url = StringEscapeUtils.unescapeHtml4(shopBannerInforList.get(position).getJumpurl());
                    Log.e("sll", url);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 初始化 findViewById 和 ClickListener

    private EditText etSearch;

    private void initView(View view) {
        final LinearLayout llOrder = (LinearLayout) view.findViewById(R.id.ll_order);
        final TextView tvSearch = (TextView) view.findViewById(R.id.tv_search);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startSearch();
                }
                return true;
            }
        });

        etSearch.addTextChangedListener(new EditTextChangeInterface() {
            @Override
            public void afterEditeChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    llOrder.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.VISIBLE);
                } else {
                    llOrder.setVisibility(View.VISIBLE);
                    tvSearch.setVisibility(View.GONE);
                }
            }
        });

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(true);
        ClassicsFooter classicsFooter = new ClassicsFooter(getActivity());
        classicsFooter.setAccentColorId(android.R.color.transparent);
        classicsFooter.setPrimaryColorId(android.R.color.transparent);
        classicsFooter.setProgressBitmap(null);
        classicsFooter.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        refreshLayout.setRefreshFooter(classicsFooter);
        refreshLayout.setEnableLoadmore(false);
        //refreshLayout.setFooterHeightPx(2);
        //refreshLayout.setRefreshHeader(new RefreshHeader(this.getActivity().getApplicationContext()));
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
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                p++;
//                getShopList(p, category_id, keywords, gtype, otype, true, false);
//            }
//        });
        initRecv(view);


        llOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = NetUrl.SHOP_ORDER;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();
            }
        });

    }

    private void startSearch() {
        //搜索
        dismissSoftInput();
        String searchString = etSearch.getText().toString().trim();
        String url = NetUrl.SHOP_SEARCH + "&keywords=" + searchString;
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }


    private List<ShopListBean.DataBean.ListBean> commodityBeanList = new ArrayList<>();
    private CommodityAdapter commodityAdapter;
    private RecyclerView recvShop;
    private String currentOrder = ZONGHE;

    private void initRecv(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        homeColumnBeanList = new ArrayList<>();
        adapterColumn = new CommonRCAdapter<HomeColumnBean>(getActivity(), homeColumnBeanList, R.layout.item_column) {
            @Override
            public void convert(CommonRCViewHolder holder, int position, HomeColumnBean homeColumnBean) {
                holder.setImage(R.id.iv_title, homeColumnBean.IMGID);
                holder.setText(R.id.tv_des, homeColumnBean.des);
                ImageViewWH1b1 imageView1 = holder.getView(R.id.iv_pic1);
                ImageViewWH1b1 imageView2 = holder.getView(R.id.iv_pic2);
                Glide.with(getActivity()).load(homeColumnBean.IMG_1).error(R.mipmap.load_err).into(imageView1);
                Glide.with(getActivity()).load(homeColumnBean.IMG_2).error(R.mipmap.load_err).into(imageView2);


            }
        };
        recyclerView.setAdapter(adapterColumn);
        adapterColumn.setOnRCItemOnClickListener(new CommonRCAdapter.OnRCItemOnClickListener() {
            @Override
            public void setOnRCItemOnClickListener(CommonRCViewHolder holder, int position) {
                // 1档位商品 2和顺商城 3返利商城 4积分商城 5淘宝商城 和顺入驻type=”shop” 特价商品type=9
                //http://域名/index.php?m=Home&c=index&a=shopModule 参数type
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + 9);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + "shop");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + 5);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + 4);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + 3);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("url", NetUrl.IP + "index.php?m=Home&c=index&a=shopModule" + "&type=" + 2);
                        startActivity(intent);
                        break;

                }
            }
        });


        recvShop = (RecyclerView) view.findViewById(R.id.recv_shop);
        LinearLayoutManager layoutManger = new LinearLayoutManager(getActivity());
        recvShop.setLayoutManager(layoutManger);
        recvShop.setHasFixedSize(true);
        recvShop.setNestedScrollingEnabled(true);
        commodityAdapter = new CommodityAdapter(R.layout.item_commodity, commodityBeanList);
        commodityAdapter.addHeaderView(getHeaderView());
        //commodityAdapter.setLoadMoreView(new MSimpleLoadMoreView());
        recvShop.setAdapter(commodityAdapter);
//        commodityAdapter.setEmptyView(R.layout.layout_load_more_empty_view);

//        commodityAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                    p++;
//                getShopList(p,category_id,keywords,gtype,otype,true,true);
//
//
//            }
//        });

        commodityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (commodityBeanList != null && commodityBeanList.size() > 0) {
                    ShopListBean.DataBean.ListBean commodityBean = commodityBeanList.get(position);
                    if (commodityBean != null) {
                        String url = commodityBean.getUrl();
                        if (!TextUtils.isEmpty(url)) {
                            Intent intent = new Intent(getActivity(), CommonActivity.class);
                            intent.putExtra("url", commodityBean.getUrl());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    private View getHeaderView() {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.shop_head_view, (ViewGroup) recvShop.getParent(), false);
        initHeaderView(headerView);
        return headerView;
    }

    private Banner banner;
    private List<CategoryBean.DataBean> shopCategoryBeanList = new ArrayList<>();
    private ShopCategoryAdapter shopCategoryAdapter;

    private void initHeaderView(View headerView) {
        RecyclerView recvShopCategory = (RecyclerView) headerView.findViewById(R.id.shop_category);
        shopCategoryAdapter = new ShopCategoryAdapter(R.layout.shop_category_item, shopCategoryBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        recvShopCategory.setLayoutManager(gridLayoutManager);
        recvShopCategory.setAdapter(shopCategoryAdapter);
//        http://taobaobuy.7oks.net/index.php?m=Home&c=index&a=search&category=1
        shopCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (shopCategoryBeanList != null && shopCategoryBeanList.size() > 0) {
                    CategoryBean.DataBean shopCategoryItemBean = shopCategoryBeanList.get(position);
                    if (shopCategoryItemBean != null) {
                        Intent intent = new Intent(getActivity(), CommonActivity.class);
                        intent.putExtra("url", shopCategoryItemBean.getUrl());
                        startActivity(intent);
                    }
                }
            }
        });
        banner = (Banner) headerView.findViewById(R.id.banner);
        initTablayout(headerView);
    }

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv_shop1;
    private TextView tv_shop2;
    private TextView tv_shop3;
    private TextView tv_shop4;

    private void initTablayout(View headerView) {

        tv1 = (TextView) headerView.findViewById(R.id.tv_1);
        tv2 = (TextView) headerView.findViewById(R.id.tv_2);
        tv3 = (TextView) headerView.findViewById(R.id.tv_3);

        tv1.setSelected(true);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        tv_shop1 = (TextView) headerView.findViewById(R.id.tv_shop1);
        tv_shop2 = (TextView) headerView.findViewById(R.id.tv_shop2);
        tv_shop3 = (TextView) headerView.findViewById(R.id.tv_shop3);
        tv_shop4 = (TextView) headerView.findViewById(R.id.tv_shop4);
        tv_shop3.setSelected(true);
        tv_shop1.setOnClickListener(this);
        tv_shop2.setOnClickListener(this);
        tv_shop3.setOnClickListener(this);
        tv_shop4.setOnClickListener(this);


    }

//    private void getCommodity(final int page, String orders) {
//        String shopSuffix = SPUtils.getString(AppConstant.KEY_SHOP_URL_SUFFIX, "");
//        if (!TextUtils.isEmpty(shopSuffix)) {
//            Subscription subscription = HttpClient.Builder.getShopServer().getShopVolumeData(HttpUtils.API_SHOP_APP + "index.php?m=Home&c=index&a=index" + shopSuffix, page, orders)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<BaseCommodityBean>() {
//                        @Override
//                        public void onCompleted() {
//                            if (refreshLayout != null) refreshLayout.finishRefresh();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            if (refreshLayout != null) refreshLayout.finishRefresh();
//                            if (page == 1) {
//                                commodityBeanList.clear();
//                                setData(true, commodityBeanList);
//                            }
//                            commodityAdapter.loadMoreFail();
//                        }
//
//                        @Override
//                        public void onNext(BaseCommodityBean baseCommodityBean) {
//                            if (refreshLayout != null) refreshLayout.finishRefresh();
//                            if (baseCommodityBean != null && baseCommodityBean.getList() != null
//                                    && baseCommodityBean.getList().size() > 0) {
//                                NestShoppingFragment.this.countPage = baseCommodityBean.getCountPage();
//
//                                if (page == 1) {
//                                    commodityBeanList.clear();
//                                    setData(true, baseCommodityBean.getList());
//                                }
//                                if (page > 1) {
//                                    setData(false, baseCommodityBean.getList());
//                                }
//                            }
//                        }
//                    });
//            addSubscription(subscription);
//        }
//    }

    private static final int PAGE_SIZE = 6;
    private int countPage = -1;

//    private void setData(boolean isRefresh, List<CommodityBean> data) {
//        page++;
//        final int size = data == null ? 0 : data.size();
//
//        if (data != null) {
//            if (isRefresh) {
//                this.commodityBeanList = data;
//                commodityAdapter.setNewData(this.commodityBeanList);
//            } else {
//                if (size > 0) {
//                    this.commodityBeanList.addAll(data);
//                    commodityAdapter.addData(data);
//                }
//            }
//        }
//        if (size < PAGE_SIZE) {
//            //第一页如果不够一页就不显示没有更多数据布局
//            commodityAdapter.loadMoreEnd(true);
//        } else {
//            commodityAdapter.loadMoreComplete();
//        }
//
//        if (countPage > 0 && page > countPage) {
//            commodityAdapter.loadMoreEnd(true);
//        }
//    }


    public void dismissSoftInput() {
        Window mWindow = this.getActivity().getWindow();
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


    @Override
    public void onClick(View v) {
        /**
         * p			=> 页数
         * category_id	=> 分类
         * keywords	=> 搜索关键词
         * gtype		=> 商品类别：5淘宝商城2和顺商城1档位商品3返利商城4积分商城
         * otype		=> 排序：，volume销量，newgoods 新品，price_desc价格降序，price_asc 价格升序 comprehensive 综合排序
         */
        switch (v.getId()) {
            case R.id.tv_1:
                resetSelect();
                tv1.setSelected(true);
                p = 1;
                showSortDialog();
                break;
            case R.id.tv_2:
                otype = "volume";
                resetSelect();
                tv2.setSelected(true);
                p = 1;
                //getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
            case R.id.tv_3:
                otype = "newgoods";
                resetSelect();
                tv3.setSelected(true);
                p = 1;
                // getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
            case R.id.tv_shop1:
                //5淘宝商品2自有商品1档位商品3全返商品4积分商品
                p = 1;
                gtype = "5";
                resetSelect1();
                tv_shop1.setSelected(true);
                // getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
            case R.id.tv_shop2:
                p = 1;
                gtype = "2";
                resetSelect1();
                tv_shop2.setSelected(true);
                // getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
            case R.id.tv_shop3:
                p = 1;
                gtype = "3";
                resetSelect1();
                tv_shop3.setSelected(true);
                // getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
            case R.id.tv_shop4:
                p = 1;
                gtype = "4";
                resetSelect1();
                tv_shop4.setSelected(true);
                // getShopList(p, category_id, keywords, gtype, otype, false, false);
                break;
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * otype pop_otype
     * ，price_desc价格降序，price_asc 价格升序 comprehensive 综合排序
     */
    private void showSortDialog() {
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        final View dialog_view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sort_layout, null);
        mPopupWindow = new PopupWindow(dialog_view, width, ViewGroup.LayoutParams.WRAP_CONTENT);
        //mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        int[] location = new int[2];
//        img_time.getLocationInWindow(location); //获取在当前窗口内的绝对坐标，含toolBar
//        //img_time.getLocationOnScreen(location); //获取在整个屏幕内的绝对坐标，含statusBar
//        int x = location[0];
//        int y = location[1];
//        int height = DisplayUtil.dp2px(DuanLingActivity.this, 6 * 35 + 48 + 60);
//        mPopupWindow.showAsDropDown(img_time, 0, -height);

        mPopupWindow.showAsDropDown(tv1, 0, dialog_view.getHeight());

        // backgroundAlpha(0.5f);

        final ListView lv_dialog = (ListView) dialog_view.findViewById(R.id.lv_list);
        final List<String> mList = new ArrayList<>();
        mList.add("   综合排序");
        mList.add("   价格升序");
        mList.add("   价格降序");
        final CommonAdapter<String> adapter = new CommonAdapter<String>(getActivity(), mList, R.layout.item_single_text_layout2) {

            public void convert(ViewHolder holder, String item, int pos) {
                TextView tv_text = holder.getView(R.id.tv_text);
                TextView tv_text_right = holder.getView(R.id.tv_text_right);
                tv_text.setText(item);
                if (tv1.getText().toString().trim().equals(item.trim())) {
                    tv_text_right.setVisibility(View.VISIBLE);
                    tv_text.setTextColor(getActivity().getResources().getColor(R.color.color_new_them));
                } else {
                    tv_text_right.setVisibility(View.GONE);
                    tv_text.setTextColor(getActivity().getResources().getColor(R.color.black));
                }

            }
        };
        lv_dialog.setAdapter(adapter);

        lv_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        otype = "comprehensive";
                        pop_otype = "comprehensive";
                        break;
                    case 1:
                        otype = "price_asc";
                        pop_otype = "price_asc";
                        break;
                    case 2:
                        otype = "price_desc";
                        pop_otype = "price_desc";
                        break;
                }
                tv1.setText(mList.get(position).trim());
                //getShopList(p, category_id, keywords, gtype, otype, false, false);
                //backgroundAlpha(1f);
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }

    private void resetSelect() {
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
    }

    private void resetSelect1() {
        tv_shop1.setSelected(false);
        tv_shop2.setSelected(false);
        tv_shop3.setSelected(false);
        tv_shop4.setSelected(false);
    }
}

