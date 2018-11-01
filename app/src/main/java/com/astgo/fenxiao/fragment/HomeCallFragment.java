//package com.astgo.fenxiao.fragment;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.media.AudioManager;
//import android.media.ToneGenerator;
//import android.os.Bundle;
//import android.provider.CallLog;
//import android.provider.Settings;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.ClipboardManager;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//
//import com.astgo.fenxiao.MainActivity;
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.activity.ContactsDetailsActivity;
//import com.astgo.fenxiao.activity.SettingActivity;
//import com.astgo.fenxiao.adapter.CallsRecyclerAdapter;
//import com.astgo.fenxiao.adapter.FilterRecyclerAdapter;
//import com.astgo.fenxiao.bean.BannerInfo;
//import com.astgo.fenxiao.bean.CallsBean;
//import com.astgo.fenxiao.http.service.HttpClient;
//import com.astgo.fenxiao.http.service.HttpResultSubscriber;
//import com.astgo.fenxiao.tools.AddressDbUtil;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//import com.astgo.fenxiao.tools.FileUtil;
//import com.astgo.fenxiao.tools.FilterSortComparator;
//import com.astgo.fenxiao.tools.XmlUtil;
//import com.astgo.fenxiao.tools.utils.DebugUtil;
//import com.astgo.fenxiao.widget.ViewPagerScrollImage;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import de.greenrobot.event.EventBus;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by Administrator on 2016/1/28.
// * 拨号界面
// */
//public class HomeCallFragment extends Fragment implements CallsRecyclerAdapter.OnRecyclerViewListener, View.OnClickListener, FilterRecyclerAdapter.OnRecyclerViewListener {
//    private static final String TAG = "拨号界面";
//
//
//    public static StringBuffer keyboardNum = new StringBuffer();//存放号码
//    private static final int UNKNOWN_NUM = 123;//设置未知号码类型
//    private Context context;
//    private RelativeLayout keyBoard_view;// 键盘的容器View
//    private EditText et_call;//键盘的号码显示框
//    private EditText et_top_call;//键盘的号码显示框
//    private int currentIndex = -1;//当前光标位置
//    private PopupWindow popupWindowTitleMenu;//title弹出菜单
//    private PopupWindow popupWindowItemMenu;//item弹出菜单
//    private PopupWindow popupWindowFilterItemMenu;//FilterList列表弹窗
//    private RecyclerView recyclerView;//通话记录列表
//    private RecyclerView filterRecyclerView;//检索查询列表
//    private LinearLayoutManager layoutManager;
//    private CallsRecyclerAdapter callsRecyclerAdapter;//通话记录适配器
//    private FilterRecyclerAdapter filterRecyclerAdapter;//检索查询列表适配器
//    private List<CallsBean> mDataSet = new ArrayList<>();//存放通话记录信息的列表
//    private CallsBean callsTemp;//存放当前弹出框的callsBean
//    private ContentValues contactsTemp;//存放当前弹出框的ContactsBean
//    private int currentPosition;//当前点击的itemPosition
//    private int currentFilterPosition;//当前点击的filterItemPosition
//
//    // 按键音的播放时长
//    private static final int DTMF_DURATION_MS = 120;
//    // 监视器对象锁
//    private final Object mToneGeneratorLock = new Object();
//    // 声音产生器
//    private ToneGenerator mToneGenerator;
//    // 是否使用按键音的状态
//    private static boolean mDTMFToneEnabled;
//
//    // 自动轮播广告
//    private ViewPagerScrollImage mViewPager;
//    List<BannerInfo> bannerInfos = new ArrayList<>();
//
//    private FrameLayout flTitleBase;
//
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if(!hidden){
//            initData();
//        }
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        EventBus.getDefault().register(this);
//        context = getActivity();//获取上下文
//        return inflater.inflate(R.layout.fragment_call_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initView(view);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initData();
//        initViewPagerData();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mViewPager != null) {
//            mViewPager.stopAutoScroll();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
//
//    /**
//     * 初始化界面
//     */
//    private void initView(View view) {
//        initTitleView(view);
//        initRecyclerView(view);
//        initFilterRecyclerView(view);
//        initKeyboardView(view);
////        initPopupWindowTitleMenu();
//        initPopupWindowItemMenu();
//        initPopupWindowFilterItemMenu();
//    }
//
//    /**
//     * 初始化fragmentTitle
//     */
//    private void initTitleView(View view) {
//
//        //隐藏baseTitle
//        RelativeLayout rlBaseTitle = (RelativeLayout) view.findViewById(R.id.base_title);
//        rlBaseTitle.setVisibility(View.GONE);
//        flTitleBase = (FrameLayout) view.findViewById(R.id.fl_title_base);
//        if(MainActivity.state){
//            flTitleBase.setVisibility(View.GONE);
//        }else {
//            flTitleBase.setVisibility(View.VISIBLE);
//        }
////        ((TextView) view.findViewById(R.id.title_tv)).setText(getString(R.string.title_calls));
////        view.findViewById(R.id.title_left).setOnClickListener(this);
////        view.findViewById(R.id.title_right).setOnClickListener(this);
////        view.findViewById(R.id.title_right).setVisibility(View.GONE);
//    }
//
//    private LinearLayout llNoData;
//    /**
//     * 初始化RecyclerView定义的list列表
//     */
//    private void initRecyclerView(View view) {
//        llNoData = (LinearLayout) view.findViewById(R.id.ll_no_data);
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
//        recyclerView.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能。
//        layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        callsRecyclerAdapter = new CallsRecyclerAdapter(context, mDataSet);
//        callsRecyclerAdapter.setOnRecyclerViewListener(this);
//        recyclerView.setAdapter(callsRecyclerAdapter);
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                // 滑动时收起键盘
//                if (MainActivity.state) {
//                    MainActivity.state = !MainActivity.state;
//                    //收起键盘
//                    keyBoard_view.setVisibility(View.GONE);
//                    //改变软键盘底部拨号键左侧按钮状态
//                    EventBus.getDefault().post(MyConstant.EVENT_STATE_TAG);
//                }
//            }
//        });
//
//        if(mDataSet != null && mDataSet.size() == 0){
//            llNoData.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }else {
//            llNoData.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 初始化FilterRecyclerView定义的列表
//     */
//    private void initFilterRecyclerView(View view) {
//        filterRecyclerView = (RecyclerView) view.findViewById(R.id.filter_list);
//        filterRecyclerView.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能。
//        layoutManager = new LinearLayoutManager(context);
//        filterRecyclerView.setLayoutManager(layoutManager);
//        filterRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        filterRecyclerAdapter = new FilterRecyclerAdapter(context, MyApplication.mDataSet2);
//        filterRecyclerView.setAdapter(filterRecyclerAdapter);
//        filterRecyclerAdapter.setOnRecyclerViewListener(this);
//        filterRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                // 滑动时收起键盘
//                if (MainActivity.state) {
//                    MainActivity.state = !MainActivity.state;
//                    //收起键盘
//                    keyBoard_view.setVisibility(View.GONE);
//                    //改变软键盘底部拨号键左侧按钮状态
//                    EventBus.getDefault().post(MyConstant.EVENT_STATE_TAG);
//                }
//            }
//        });
//    }
//
//
//    private RelativeLayout rlViewPager;
//    /**
//     * 初始化软键盘界面
//     */
//    private void initKeyboardView(View view) {
//        keyBoard_view = (RelativeLayout) view.findViewById(R.id.keyboard_view);
//        // 初始化键盘状态
//        if (MainActivity.state) {
//            keyBoard_view.setVisibility(View.VISIBLE);
//        } else {
//            keyBoard_view.setVisibility(View.GONE);
//        }
//        view.findViewById(R.id.DigitOneButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitTwoButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitThreeButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitFourButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitFiveButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitSixButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitSevenButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitEightButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitNineButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitZeroButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitXhButton).setOnClickListener(this);
//        view.findViewById(R.id.DigitJhButton).setOnClickListener(this);
//
//
//        rlViewPager = (RelativeLayout) view.findViewById(R.id.rl_view_pager);
////        et_call = (EditText) getActivity().findViewById(R.id.et_call);
//        et_top_call = (EditText) getActivity().findViewById(R.id.et_top_call);
//        et_top_call.setVisibility(View.VISIBLE);
//
//        et_call = (EditText) getActivity().findViewById(R.id.et_phone_num);
//        et_call.setOnTouchListener(new View.OnTouchListener() {//触摸editText禁止弹出自带输入法
//            public boolean onTouch(View v, MotionEvent event) {
//                disableShowSoftInput();
////                mEditText.setCursorVisible(false);
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    Log.d("ACTION_UP", "-----" + et_call.getSelectionStart());
//                }
//                return false;
//            }
//        });
//
//        //et_call中光标位置设置
//        et_call.setOnClickListener(new View.OnClickListener() {//点击editText时获取光标位置
//            @Override
//            public void onClick(View v) {
//                //获取光标位置
//                et_call.setCursorVisible(true);
//                currentIndex = et_call.getSelectionStart();
//            }
//
//        });
//
//
//        et_call.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Log.e(TAG, editable.toString());
//                if (!TextUtils.isEmpty(editable)) {//如果内容不为空
//                    filterRecyclerView.setVisibility(View.VISIBLE);
//                    mViewPager.stopAutoScroll();
//                    rlViewPager.setVisibility(View.GONE);
//                    filterContactsData(editable.toString());
//                } else {
//                    filterRecyclerView.setVisibility(View.GONE);
//                    rlViewPager.setVisibility(View.VISIBLE);
//                    mViewPager.stopAutoScroll();
//                }
//            }
//        });
//
//        et_top_call.setOnClickListener(new View.OnClickListener() {//点击editText时获取光标位置
//            @Override
//            public void onClick(View v) {
//                //获取光标位置
//                et_top_call.setCursorVisible(true);
//                currentIndex = et_top_call.getSelectionStart();
//            }
//
//        });
//
//
//        //et_call内容监听
//        et_top_call.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Log.e(TAG, editable.toString());
//                if (!TextUtils.isEmpty(editable)) {//如果内容不为空
//                    filterRecyclerView.setVisibility(View.VISIBLE);
//                    filterContactsData(editable.toString());
//                } else {
//                    filterRecyclerView.setVisibility(View.GONE);
//                }
//            }
//        });
//        // 按键声音播放设置及初始化
//        try {
//            // 获取系统参数“按键操作音”是否开启
//            mDTMFToneEnabled = Settings.System.getInt(getActivity().getContentResolver(),
//                    Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
//            synchronized (mToneGeneratorLock) {
//                if (mDTMFToneEnabled && mToneGenerator == null) {
//                    mToneGenerator = new ToneGenerator(
//                            AudioManager.STREAM_DTMF, 80); // 设置声音的大小
//                    getActivity().setVolumeControlStream(AudioManager.STREAM_DTMF);
//                }
//            }
//        } catch (Exception e) {
//            Log.i(TAG, e.getMessage());
//            mDTMFToneEnabled = false;
//            mToneGenerator = null;
//        }
//        initViewPager(view);
//    }
//
//    /**
//     * 初始化店铺上方轮播图控件
//     */
//    private void initViewPager(View view) {
//        // 加载轮播图控件
//        rlViewPager.setVisibility(View.VISIBLE);
//        mViewPager = (ViewPagerScrollImage) view.findViewById(R.id.store_image_scroll);
//        mViewPager.setVisibility(View.VISIBLE);
//    }
//    // 初始化 ViewPager 广告的链接数据
//    private void initViewPagerData() {
//        HttpClient.Builder.getAppService().getBannerData()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<List<BannerInfo>>() {
//                    @Override
//                    public void onSuccess(List<BannerInfo> bannerInfoList) {
//                        //TODO 如果需要更新
//                        if(bannerInfoList!= null && bannerInfoList.size()>0){
//                            bannerInfos = bannerInfoList;
//                        }
//
//                        mViewPager.setViewData(bannerInfos);
//                        mViewPager.startAutoScroll();
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
//
//        // 广告图片地址和对应的链接
////        bannerInfos.add(new BannerInfo("http://ojyz0c8un.bkt.clouddn.com/b_2.jpg",""));
////        bannerInfos.add(new BannerInfo("http://ojyz0c8un.bkt.clouddn.com/b_3.jpg",""));
////        bannerInfos.add(new BannerInfo("http://ojyz0c8un.bkt.clouddn.com/b_4.jpg",""));
////        bannerInfos.add(new BannerInfo("http://ojyz0c8un.bkt.clouddn.com/b_9.jpg",""));
////        mViewPager.setViewData(bannerInfos);
////        mViewPager.startAutoScroll();
//    }
//
//    //获取xml指定节点的值
//    private String getXmlNode(String filter) {
//        return XmlUtil.parserFilterXML(FileUtil.read(getActivity(), MyConstant.FILE_LOGIN), filter);
//    }
//
//    /**
//     * 初始化Item弹出菜单
//     */
//    private void initPopupWindowItemMenu() {
//        View myView = LayoutInflater.from(context).inflate(R.layout.popuwindow_calls_item_menu, null);
//        myView.findViewById(R.id.calls_item_add).setOnClickListener(this);
//        myView.findViewById(R.id.calls_item_call).setOnClickListener(this);
//        myView.findViewById(R.id.calls_item_message).setOnClickListener(this);
//        myView.findViewById(R.id.calls_item_delete).setOnClickListener(this);
//        myView.findViewById(R.id.calls_item_copy).setOnClickListener(this);
//        //初始化popupwindow，绑定显示view，设置该view的宽度/高度
//        popupWindowItemMenu = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //设置控件可获取焦点
//        popupWindowItemMenu.setFocusable(true);
//        //PopupWindow以外的区域是可点击,点击即可使PopupWindow消失
//        popupWindowItemMenu.setOutsideTouchable(true);
//        if (isAdded()) {
//            popupWindowItemMenu.setBackgroundDrawable(getResources().getDrawable(R.color.theme_color));
//        }
//        popupWindowItemMenu.setAnimationStyle(R.style.popwin_vertical_anim_style);
//    }
//
//    /**
//     * 初始化title弹出菜单
//     */
//    private void initPopupWindowTitleMenu() {
//        View myView = LayoutInflater.from(context).inflate(R.layout.popuwindow_calls_title_menu, null);
//        myView.findViewById(R.id.calls_title_all).setOnClickListener(this);
//        myView.findViewById(R.id.calls_title_missed).setOnClickListener(this);
//        myView.findViewById(R.id.calls_title_outgoing).setOnClickListener(this);
//        myView.findViewById(R.id.calls_title_incoming).setOnClickListener(this);
//        myView.findViewById(R.id.calls_title_unknown).setOnClickListener(this);
//        myView.findViewById(R.id.calls_title_delete).setOnClickListener(this);
//        //初始化popupwindow，绑定显示view，设置该view的宽度/高度
//        popupWindowTitleMenu = new PopupWindow(myView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //设置控件可获取焦点
//        popupWindowTitleMenu.setFocusable(true);
//        //PopupWindow以外的区域是可点击,点击即可使PopupWindow消失
//        popupWindowTitleMenu.setOutsideTouchable(true);
//        if (isAdded()) {
//            popupWindowTitleMenu.setBackgroundDrawable(getResources().getDrawable(R.color.theme_color));
//        }
//        popupWindowTitleMenu.setAnimationStyle(R.style.popwin_vertical_anim_style);
//    }
//
//    /**
//     * 初始化FilterListItem弹出菜单
//     */
//    private void initPopupWindowFilterItemMenu() {
//        View myView = LayoutInflater.from(context).inflate(R.layout.popuwindow_contacts_item_menu, null);
//        LinearLayout item_menu_call = (LinearLayout) myView.findViewById(R.id.contacts_item_call);
//        LinearLayout item_menu_message = (LinearLayout) myView.findViewById(R.id.contacts_item_message);
//        LinearLayout item_menu_delete = (LinearLayout) myView.findViewById(R.id.contacts_item_delete);
//        LinearLayout item_menu_edit = (LinearLayout) myView.findViewById(R.id.contacts_item_edit);
//        LinearLayout item_menu_collect = (LinearLayout) myView.findViewById(R.id.contacts_item_collect);
//        item_menu_call.setOnClickListener(this);
//        item_menu_message.setOnClickListener(this);
//        item_menu_delete.setOnClickListener(this);
//        item_menu_edit.setOnClickListener(this);
//        item_menu_collect.setOnClickListener(this);
//        //初始化popupwindow，绑定显示view，设置该view的宽度/高度
//        popupWindowFilterItemMenu = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //设置控件可获取焦点
//        popupWindowFilterItemMenu.setFocusable(true);
//        //PopupWindow以外的区域是可点击,点击即可使PopupWindow消失
//        popupWindowFilterItemMenu.setOutsideTouchable(true);
//        popupWindowFilterItemMenu.setAnimationStyle(R.style.popwin_vertical_anim_style);
//    }
//
//
//    /**
//     * 加载数据
//     */
//    private void initData() {
//        initCallsData();
//    }
//
//    /**
//     * 加载联系人数据
//     */
//    private void initCallsData() {
//        mDataSet = ContactsOrCallsUtil.getInstance().getCallsData(context);
//        callsRecyclerAdapter.updateChanged(mDataSet);
//        if(mDataSet != null && mDataSet.size()==0){
//            llNoData.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }else{
//            llNoData.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 显示popupwindow_item_menu
//     *
//     * @param parents   父view
//     * @param callsBean 传入对应item的数据
//     */
//    private void showPopwinItemMenu(final View parents, CallsBean callsBean, int position) {
//        callsTemp = callsBean;
//        currentPosition = position;
//        if (callsBean.isCallsNewNum()) {
//            popupWindowItemMenu.getContentView().findViewById(R.id.calls_item_add).setVisibility(View.VISIBLE);
//        } else {
//            popupWindowItemMenu.getContentView().findViewById(R.id.calls_item_add).setVisibility(View.GONE);
//        }
//        //PopupWindow消失时控制弹出框的arrow消失
//        popupWindowItemMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                parents.findViewById(R.id.recycler_list_arrow).setVisibility(View.GONE);
//            }
//        });
//        if (!popupWindowItemMenu.isShowing()) {
//            popupWindowItemMenu.showAsDropDown(parents, 0, -10);
////            callsTemp = null;//清除callsTemp
//        }
//    }
//
//    /**
//     * 显示popupwindow_title_menu
//     */
//    private void showPopwinTitleMenu(View parents) {
//        if (!popupWindowTitleMenu.isShowing()) {
//            popupWindowTitleMenu.showAsDropDown(parents, 10, 0);
//        }
//    }
//
//    /**
//     * 显示FilterList的popupwindow
//     */
//    private void showFilterPopupWindow(final View parents, ContentValues cv, int position) {
//        contactsTemp = cv;
//        currentFilterPosition = position;
//        //设置背景颜色与arrow箭头匹配
//        if (isAdded()) {
//            popupWindowFilterItemMenu.setBackgroundDrawable(getResources().getDrawable(R.color.theme_color));
//        }
//        //PopupWindow消失时控制弹出框的arrow消失
//        popupWindowFilterItemMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                parents.findViewById(R.id.filter_list_arrow).setVisibility(View.GONE);
//            }
//        });
//        if (!popupWindowFilterItemMenu.isShowing()) {
//            popupWindowFilterItemMenu.showAsDropDown(parents, 0, -10);
//        }
//    }
//
//    /**
//     * 过滤列表分组显示
//     *
//     * @param callsType 通话类型Id
//     */
//    private void filterCallsData(int callsType) {
//        List<CallsBean> filterLists = new ArrayList<>();
//        if (mDataSet != null) {
//            filterLists.clear();
//            if (callsType == UNKNOWN_NUM) {//如果传入类型为未知号码
//                for (CallsBean cb : mDataSet) {
//                    if (cb.isCallsNewNum()) {
//                        filterLists.add(cb);
//                    }
//                }
//            } else {
//                for (CallsBean cb : mDataSet) {
//                    if (cb.getCallsType() == callsType) {
//                        filterLists.add(cb);
//                    }
//                }
//            }
//        }
//        //更新列表
//        callsRecyclerAdapter.updateChanged(filterLists);
//    }
//
//    /**
//     * 根据输入框中的值来过滤联系人数据并更新FilterList
//     *
//     * @param filterStr 输入的关键字
//     */
//    public void filterContactsData(String filterStr) {
//        List<ContentValues> filterLists = new ArrayList<>();
////        try {
//            for(ContentValues cv : MyApplication.mDataSet2){
//                ContentValues filterCV = new ContentValues();
//                filterCV.put(MyConstant.CONTACT_ID, cv.getAsInteger(MyConstant.CONTACT_ID));
//                filterCV.put(MyConstant.CONTACT_NAME, cv.getAsString(MyConstant.CONTACT_NAME));
//                filterCV.put(MyConstant.CONTACT_NUMBER, cv.getAsString(MyConstant.CONTACT_NUMBER));
//                filterCV.put(MyConstant.CONTACT_INITIAL, cv.getAsString(MyConstant.CONTACT_INITIAL));
//                filterCV.put(MyConstant.CONTACT_STATE_NUM, cv.getAsInteger(MyConstant.CONTACT_STATE_NUM));
//                filterCV.put(MyConstant.CONTACT_STATE_NAME, cv.getAsInteger(MyConstant.CONTACT_STATE_NAME));
//                if (filterCV.getAsString(MyConstant.CONTACT_NUMBER).contains(filterStr)) {//设置号码是否变色
//                    filterCV.put(MyConstant.CONTACT_STATE_NUM, 1);
//                }
//                if (filterCV.getAsString(MyConstant.CONTACT_INITIAL).contains(filterStr)) {//设置名字是否变色
//                    filterCV.put(MyConstant.CONTACT_STATE_NAME, 1);
//                }
//                if (filterCV.getAsInteger(MyConstant.CONTACT_STATE_NUM) == 1 || filterCV.getAsInteger(MyConstant.CONTACT_STATE_NAME) == 1) {//包含关键字的联系人则插入数组中
//                    filterLists.add(filterCV);
//                }
//            }
//            Collections.sort(filterLists, new FilterSortComparator());//队列表
//            filterRecyclerAdapter.updateChanged(filterLists, filterStr);//更新检索列表
//
//    }
//
//
//
//    @Override
//    public void onItemClick(CallsBean cb, View view, int position) {//通话记录列表点击事件
////        Log.d(TAG, "onItemClick");
//        switch (view.getId()) {
//            case R.id.calls_item_long_press://通话记录中间的三个点图片点击事件
//                showPopwinItemMenu(view, cb, position);
//                view.findViewById(R.id.recycler_list_arrow).setVisibility(View.VISIBLE);
//                break;
//            case R.id.calls_item_details://通话记录尾部箭头点击事件
////                Toast.makeText(context, "detailsClick", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, ContactsDetailsActivity.class);
//                intent.putExtra(MyConstant.CONTACTS_ID, ContactsOrCallsUtil.getInstance().fromPhoneNumToContactsId(AddressDbUtil.formatNum(cb.getCallsNum())));//传递ContactsId
//                String temp;//传递详情界面标题
//                if (cb.isCallsNewNum()) {//如果是陌生号码标题为号码
//                    temp = cb.getCallsNum();
//                } else {//否则为联系人
//                    temp = cb.getCallsName();
//                }
//                intent.putExtra(MyConstant.CONTACTS_TAG, temp);
//                startActivity(intent);
//                break;
//            case R.id.calls_item://整个item点击事件
//                ContactsOrCallsUtil.getInstance().mCall(context, cb.getCallsName(), cb.getCallsNum());//拨打电话
//                break;
//        }
//    }
//
//    @Override
//    public boolean onItemLongClick(CallsBean cb, View view, int position) {//通话记录列表整个item长按事件
//        Log.d(TAG, "onItemLongClick");
////        Toast.makeText(context, "onItemLongClick", Toast.LENGTH_SHORT).show();
//        showPopwinItemMenu(view, cb, position);
//        view.findViewById(R.id.recycler_list_arrow).setVisibility(View.VISIBLE);
//        return true;
//    }
//
//    @Override
//    public void onItemClick(ContentValues cv, View view, int position) {//filterList列表点击事件
//        switch (view.getId()) {
//            case R.id.filter_item_long_press://filterListItem中间的三个点图片点击事件
//                showFilterPopupWindow(view, cv, position);
//                view.findViewById(R.id.filter_list_arrow).setVisibility(View.VISIBLE);
//                break;
//            case R.id.filter_item_details://filterListItem尾部箭头点击事件
//                Intent intent = new Intent();
//                intent.setClass(context, ContactsDetailsActivity.class);
//                intent.putExtra(MyConstant.CONTACTS_ID, cv.getAsInteger(MyConstant.CONTACT_ID));
//                intent.putExtra(MyConstant.CONTACTS_TAG, cv.getAsString(MyConstant.CONTACT_NAME));
//                startActivity(intent);
//                break;
//            case R.id.filter_item://整个item点击事件
//                ContactsOrCallsUtil.getInstance().mCall(context, cv.getAsString(MyConstant.CONTACT_NAME), cv.getAsString(MyConstant.CONTACT_NUMBER));//拨打电话
//                break;
//        }
//    }
//
//    @Override
//    public boolean onItemLongClick(ContentValues cv, View view, int position) {//filterList列表长按事件
//        showFilterPopupWindow(view, cv, position);
//        view.findViewById(R.id.filter_list_arrow).setVisibility(View.VISIBLE);
//        return true;
//    }
//
//    @Override
//    public void onClick(View view) {
//        Intent mIntent;
//        switch (view.getId()) {
//            case R.id.title_left://标题栏菜单
//                showPopwinTitleMenu(view);
//                break;
//            case R.id.title_right:
//                mIntent = new Intent(getContext(), SettingActivity.class);
//                startActivity(mIntent);
//                break;
//            /**
//             * 通话记录 item弹出监听
//             */
//            case R.id.calls_item_add://item弹出菜单添加联系人
//                if (callsTemp != null) {
//                    ContactsOrCallsUtil.getInstance().addContacts(context, callsTemp.getCallsName(), callsTemp.getCallsNum());
//                }
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.calls_item_call://item弹出菜单 拨打电话
//                if (callsTemp != null) {
//                    ContactsOrCallsUtil.getInstance().mCall(context, callsTemp.getCallsName(), callsTemp.getCallsNum());//拨打电话
//                }
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.calls_item_message://item弹出菜单 发短信
//                //向指定号码发送短信
//                if (callsTemp != null) {
//                    ContactsOrCallsUtil.getInstance().sendMessage(context, callsTemp.getCallsNum());
//                }
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.calls_item_delete://item弹出菜单 删除
//                callsTemp.addCallsSameId(callsTemp.getCallsId());
//                //先new出一个监听器，设置好监听
//                DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case Dialog.BUTTON_POSITIVE:
//                                ContactsOrCallsUtil.getInstance().deleteCalls(callsTemp.getCallsSameId(), context);
//                                callsRecyclerAdapter.removeData(currentPosition);
//                                break;
//                            case Dialog.BUTTON_NEGATIVE:
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                };
//                //dialog参数设置
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
//                builder.setTitle(context.getString(R.string.TIPS)); //设置标题
//                builder.setMessage(context.getString(R.string.tips_whether_delete)); //设置内容
//                builder.setIcon(R.mipmap.ic_launcher_new);//设置图标，图片id即可
//                builder.setPositiveButton(context.getString(R.string.tips_confirm), dialogOnclicListener);
//                builder.setNegativeButton(context.getString(R.string.tips_cancle), dialogOnclicListener);
//                builder.create().show();
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.calls_item_copy://item弹出菜单  复制号码
//                ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
////                clip.getText(); // 粘贴
//                clip.setText(callsTemp.getCallsNum()); // 复制
//                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
//                popupWindowItemMenu.dismiss();
//                break;
//                /**
//                * title item弹出监听
//                */
//            case R.id.calls_title_all://全部记录
//                callsRecyclerAdapter.updateChanged(mDataSet);
//                popupWindowTitleMenu.dismiss();
//                break;
//            case R.id.calls_title_missed://未接来电
//                filterCallsData(CallLog.Calls.MISSED_TYPE);
//                popupWindowTitleMenu.dismiss();
//                break;
//            case R.id.calls_title_outgoing://呼出号码
//                filterCallsData(CallLog.Calls.OUTGOING_TYPE);
//                popupWindowTitleMenu.dismiss();
//                break;
//            case R.id.calls_title_incoming://呼入号码
//                filterCallsData(CallLog.Calls.INCOMING_TYPE);
//                popupWindowTitleMenu.dismiss();
//                break;
//            case R.id.calls_title_unknown://未知号码
//                filterCallsData(UNKNOWN_NUM);
//                popupWindowTitleMenu.dismiss();
//                break;
//            case R.id.calls_title_delete://清除记录
//                //先new出一个监听器，设置好监听
//                DialogInterface.OnClickListener dialogOnclicListener1 = new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case Dialog.BUTTON_POSITIVE:
//                                ContactsOrCallsUtil.getInstance().deleteCallsAll(context);
//                                mDataSet.clear();
//                                callsRecyclerAdapter.clearData();
//                                break;
//                            case Dialog.BUTTON_NEGATIVE:
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                };
//                //dialog参数设置
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);  //先得到构造器
//                builder1.setTitle(context.getString(R.string.TIPS)); //设置标题
//                builder1.setMessage(context.getString(R.string.tips_whether_delete_all)); //设置内容
//                builder1.setIcon(R.mipmap.ic_launcher_new);//设置图标，图片id即可
//                builder1.setPositiveButton(context.getString(R.string.tips_confirm), dialogOnclicListener1);
//                builder1.setNegativeButton(context.getString(R.string.tips_cancle), dialogOnclicListener1);
//                builder1.create().show();
//                popupWindowTitleMenu.dismiss();
//                break;
//            /**
//             * filterList item弹出监听
//             */
//            case R.id.contacts_item_call://item弹出菜单 拨打电话
//                if (contactsTemp != null) {
//                    ContactsOrCallsUtil.getInstance().mCall(context, contactsTemp.getAsString(MyConstant.CONTACT_NAME), contactsTemp.getAsString(MyConstant.CONTACT_NUMBER));//拨打电话
//                }
//                popupWindowFilterItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_message://item弹出菜单 发短信
//                //向指定号码发送短信
//                if (contactsTemp != null) {
//                    ContactsOrCallsUtil.getInstance().sendMessage(context, contactsTemp.getAsString(MyConstant.CONTACT_NUMBER));
//                }
//                popupWindowFilterItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_delete://item弹出菜单 删除
//                //先new出一个监听器，设置好监听
//                DialogInterface.OnClickListener dialogOnclicListener2 = new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case Dialog.BUTTON_POSITIVE:
//                                ContactsOrCallsUtil.getInstance().deleteContacts(contactsTemp.getAsInteger(MyConstant.CONTACT_ID), context);
//                                filterRecyclerAdapter.removeData(currentFilterPosition);
//                                break;
//                            case Dialog.BUTTON_NEGATIVE:
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                };
//                //dialog参数设置
//                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);  //先得到构造器
//                builder2.setTitle(context.getString(R.string.TIPS)); //设置标题
//                builder2.setMessage(context.getString(R.string.tips_whether_delete)); //设置内容
//                builder2.setIcon(R.mipmap.ic_launcher_new);//设置图标，图片id即可
//                builder2.setPositiveButton(context.getString(R.string.tips_confirm), dialogOnclicListener2);
//                builder2.setNegativeButton(context.getString(R.string.tips_cancle), dialogOnclicListener2);
//                builder2.create().show();
//                popupWindowFilterItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_edit://item弹出菜单 编辑
////                Toast.makeText(context, "onClick----edit", Toast.LENGTH_SHORT).show();
//                ContactsOrCallsUtil.getInstance().editContacts(context, contactsTemp.getAsInteger(MyConstant.CONTACT_ID));
//                popupWindowFilterItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_collect://item弹出菜单 收藏
//                Toast.makeText(context, "onClick----collect", Toast.LENGTH_SHORT).show();
//                popupWindowFilterItemMenu.dismiss();
//                break;
//            case R.id.DigitOneButton:
//                setKeyboardText(1);
//                break;
//            case R.id.DigitTwoButton:
//                setKeyboardText(2);
//                break;
//            case R.id.DigitThreeButton:
//                setKeyboardText(3);
//                break;
//            case R.id.DigitFourButton:
//                setKeyboardText(4);
//                break;
//            case R.id.DigitFiveButton:
//                setKeyboardText(5);
//                break;
//            case R.id.DigitSixButton:
//                setKeyboardText(6);
//                break;
//            case R.id.DigitSevenButton:
//                setKeyboardText(7);
//                break;
//            case R.id.DigitEightButton:
//                setKeyboardText(8);
//                break;
//            case R.id.DigitNineButton:
//                setKeyboardText(9);
//                break;
//            case R.id.DigitZeroButton:
//                setKeyboardText(0);
//                break;
//            case R.id.DigitXhButton:
//                playTone(10);
//                //从中间插入还是后方插入
//                if (currentIndex >= 0 && currentIndex < keyboardNum.length()) {
//                    keyboardNum.insert(currentIndex++, "*");
//                } else {
//                    keyboardNum.append("*");
//                    currentIndex = keyboardNum.length();
//                }
//                lastKeyBoardString = keyboardNum.toString();
//                EventBus.getDefault().post(keyboardNum);
//                break;
//            case R.id.DigitJhButton:
//                playTone(11);
//                //从中间插入还是后方插入
//                if (currentIndex >= 0 && currentIndex < keyboardNum.length()) {
//                    keyboardNum.insert(currentIndex++, "#");
//                } else {
//                    keyboardNum.append("#");
//                    currentIndex = keyboardNum.length();
//                }
//                lastKeyBoardString = keyboardNum.toString();
//                EventBus.getDefault().post(keyboardNum);
//                break;
//
//        }
//    }
//
//    //设置拨号键盘已输入的号码keyboardNum内容
//    private void setKeyboardText(int i) {
//        playTone(i);
//        //从中间插入还是后方插入
//        if (currentIndex >= 0 && currentIndex < keyboardNum.length()) {
//            keyboardNum.insert(currentIndex++, String.valueOf(i));
//        } else {
//            keyboardNum.append(String.valueOf(i));
//            currentIndex = keyboardNum.length();
//        }
//        lastKeyBoardString = keyboardNum.toString();
//        EventBus.getDefault().post(keyboardNum);
//    }
//
//    // 注册了 EventBus 必须实现如下方法，响应mainTabActivity中底部tab栏的动作
//    public void onEventMainThread(Boolean event) {
//        Log.d("onEventMainThread", "CallsFragment---" + event);
//        if (event) {
//            keyBoard_view.setVisibility(View.VISIBLE);
//            flTitleBase.setVisibility(View.GONE);
//        } else {
//            keyBoard_view.setVisibility(View.GONE);
//            flTitleBase.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 用来相应拨号键盘上的拨号键和加号键和删除键
//     */
//    public void onEventMainThread(String s) {
//        lastKeyBoardString = keyboardNum.toString();
//        Log.d("onEventMainThread", "CallsFragment---" + s);
//        //拨号键动作
//        if ("dial".equals(s)) {
//            if (et_call.getText().length() > 3) {
//               ContactsOrCallsUtil.getInstance().mCall(context, ContactsOrCallsUtil.getInstance().fromPhoneNumToContactsName(et_call.getText().toString()), et_call.getText().toString());
//            }
//        }
//        //加号键动作
//        if ("add".equals(s)) {
//            if (keyboardNum.length() > 0) {
//
//            }
//        }
//        //响应删除按钮动作
//        if (MyConstant.EVENT_DELETE_TAG.equals(s)) {
//            lastKeyBoardString = keyboardNum.toString();
//            if (keyboardNum.length() > 0) {
//                playTone(1);
//                if (currentIndex < keyboardNum.length() && currentIndex >= 0) {
//                    keyboardNum.delete(currentIndex - 1, currentIndex);
//                    currentIndex--;
//                } else {
//                    keyboardNum.deleteCharAt(keyboardNum.length() - 1);
//                    currentIndex = keyboardNum.length();
//                }
//            }
//            EventBus.getDefault().post(keyboardNum);
//        }
//        //响应长按删除按钮动作
//        if (MyConstant.EVENT_DELETE_LONG_TAG.equals(s)) {
//            keyboardNum.setLength(0);
//            lastKeyBoardString = keyboardNum.toString();
//            EventBus.getDefault().post(keyboardNum);
//        }
//    }
//
//    private String lastKeyBoardString = new String();
//    /**
//     * 用来更新et_call内容和显示状态
//     */
//    public void onEventMainThread(StringBuffer stringBuffer) {
//
//        Log.d("onEventMainThread", "CallsFragment---" + stringBuffer);
//        et_call.setText(stringBuffer);
//        //设置光标位置
//        et_call.setSelection(currentIndex);
//        if(lastKeyBoardString.length() == 0){
//            EventBus.getDefault().post(false);
//        }
//
////        if (stringBuffer.length() > 0) {
////            et_call.setVisibility(View.VISIBLE);
//////            et_call.requestFocus();//自动获取焦点显示光标
////            et_call.setText(stringBuffer);
////            //设置光标位置
////            et_call.setSelection(currentIndex);
////            mViewPager.setVisibility(View.INVISIBLE);//轮播广告消失
//////            mViewPager.stopAutoScroll();
////        } else {
////            //号码框隐藏
////            et_call.setVisibility(View.GONE);
////            filterRecyclerView.setVisibility(View.GONE);
////            mViewPager.setVisibility(View.VISIBLE);//轮播广告出现
//////            mViewPager.startAutoScroll();
////        }
//    }
//
//    /**
//     * 禁止Edittext弹出软件盘，光标依然正常显示。
//     */
//    public void disableShowSoftInput() {
//        if (android.os.Build.VERSION.SDK_INT <= 10) {
//            et_call.setInputType(InputType.TYPE_NULL);
//        } else {
//            Class<EditText> cls = EditText.class;
//            Method method;
//            try {
//                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(et_call, false);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//            try {
//                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
//                method.setAccessible(true);
//                method.invoke(et_call, false);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
//    }
//
//    /**
//     * 播放按键声音
//     */
//    private void playTone(int tone) {
//        if (!mDTMFToneEnabled) {
//            return;
//        }
//        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//        int ringerMode = audioManager.getRingerMode();
//        if (ringerMode == AudioManager.RINGER_MODE_SILENT
//                || ringerMode == AudioManager.RINGER_MODE_VIBRATE
//                || !MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_AJSY, true)) {
//            // 静音或者震动时不发出声音
//            return;
//        }
//        synchronized (mToneGeneratorLock) {
//            if (mToneGenerator == null) {
//                Log.w(TAG, "playTone: mToneGenerator == null, tone: " + tone);
//                return;
//            }
//            mToneGenerator.startTone(tone, DTMF_DURATION_MS);
//            // 发出声音
//        }
//    }
//}
