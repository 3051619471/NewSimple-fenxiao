package com.astgo.fenxiao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.fenxiao.activity.BaseActivity;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.RingBackBean;
import com.astgo.fenxiao.fragment.GearShopFragment;
import com.astgo.fenxiao.fragment.HomeFragment;
import com.astgo.fenxiao.fragment.shop.NestShoppingFragment;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpResultSubscriber;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tts.control.SpeakVoiceUtil;
import com.astgo.fenxiao.video.CommonActivity;
import com.astgo.fenxiao.video.VipVideoFragment;
import com.astgo.fenxiao.zxing.activity.CaptureActivity;
import com.astgo.fenxiao.zxing.activity.CodeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.qfishphone.sipengine.SipEngineEventListener;
import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener, SipEngineEventListener {
    private static final String TAG = "主界面--MainActivity";
    private FragmentManager manager = getSupportFragmentManager();
    public static MainActivity context;
    private LinearLayout phone, shop, video, tab02, personal;
    private ImageView phone_iv, tab02_iv, personal_iv, keyboard_state_iv, shop_iv, video_iv;
    private TextView phone_tv, shop_tv, tab02_tv, personal_tv, video_tv;
    private LinearLayout keyboardBottom;//隐藏的软键盘底部布局
    private LinearLayout llBottomBar;
    // private CallReceiver myReceiver;
    // 键盘的默认初始状态( true 打开;false 关闭 )
    public static boolean state = true;
    //联系人数据库表的内容监听者
    // private ContactsLogObserver clb = new ContactsLogObserver(new Handler(), this);

    private Fragment currentFragment, shopFragment, homeFragment, vipVideoFragment, gearFragment, phoneFragment, callsFragment, contactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getIntentData();
        context = this;
        initView();
        // 注册EventBus
//        EventBus.getDefault().register(this);
        //注册内容监听，当联系人数据库表发生改变时更新全局变量的联系人数据
        //this.getContentResolver().registerContentObserver(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, true, clb);
        // sip_init();//sip初始化

//            setOnTab01Click();
//            setOntabshopClick();
        setDefaultFragment();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpUtils.get(NetUrl.GET_VERSION_INFO)     // 请求方式和请求url
//                        .tag("update")// 请求的 tag, 主要用于取消对应的请求
//                        //.headers("token", SPUtils.getString(MyConstant.TOKEN, ""))
//                        .cacheKey("update")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                        .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                DebugUtil.error("update" + s);
//                                BaseDataObject baseDataObject = (BaseDataObject) BaseDataObject.getBaseBean(s, BaseDataObject.class);
//                                if (baseDataObject.getCode() == 1) {
//                                    if (!TextUtils.isEmpty(baseDataObject.getData().toString())){
//                                        UpdateBean updateBean = (UpdateBean) BaseDataObject.getBaseBean(s,UpdateBean.class);
//                                        if (updateBean!=null&&MainActivity.this!=null){
//                                            UpdateAppUtils.from(MainActivity.this)
//                                                    .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
//                                                    //.serverVersionCode(2)
//                                                    .serverVersionName(updateBean.getData().getVersion_number())
//                                                    .apkPath(updateBean.getData().getDownloadfile())
//                                                    .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
//                                                    .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
//                                                    .needFitAndroidN(false)
//                                                    .update();
//                                        }
//
//                                    }
//                                } else {
//                                   // ToastUtils.showShortToast(baseDataObject.getMsg());
//                                }
//                            }
//                            @Override
//                            public void onError(Call call, Response response, Exception e) {
//                                super.onError(call, response, e);
//                               // ToastUtils.showShortToast(e.getMessage());
//                            }
//                        });
//            }
//        },3000);

        // initData();
        //  EventBus.getDefault().register(this);

    }

//    private void initData() {
//        getRingBackPhoneList();
//    }
//
//    //获取回铃电话
//    private void getRingBackPhoneList() {
//
//        HttpClient.Builder.getAppService().getRingBackList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<List<RingBackBean>>() {
//                    @Override
//                    public void onSuccess(List<RingBackBean> ringBackBeanList) {
//                        Log.e("czx", "getRingBackPhoneList success");
//                        //将电话写入通讯录
//                        if (ringBackBeanList != null && ringBackBeanList.size() > 0) {
//                            for (RingBackBean ringBackBean : ringBackBeanList) {
//                                Log.e("SLH", ringBackBean.toString());
//                                String name = ringBackBean.getName();
//                                List<String> phoneList = ringBackBean.getPhoneList();
//                                if (!TextUtils.isEmpty(name) && phoneList != null && phoneList.size() > 0) {
//                                    ContactsOrCallsUtil.addContacts(MainActivity.this.getApplicationContext(), name, phoneList);
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        DebugUtil.error("msg:" + msg + "-------code" + code);
//                    }
//                });
//
//    }

    public static String shopUrl = "";

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AppConstant.KEY_SHOP_URL)) {
            shopUrl = intent.getStringExtra(AppConstant.KEY_SHOP_URL);
        }
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        shopFragment = new NestShoppingFragment();
        currentFragment = shopFragment;
        transaction.replace(R.id.fragment_content, shopFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        keyboardBottom.setVisibility(View.GONE);
        selectTab(1);
        // selectTabShopOn(state);
        // tabshop_tv.setSelected(true);
    }

    private void selectTabShopOn(boolean state) {
        // tabShopSelect(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void toRequset(String url) {
        String URL = NetUrl.IP + "index.php?m=Home&c=user&a=handle_qr&url=" + url;
        Intent intent = new Intent(this, CommonActivity.class);
        intent.putExtra("url", URL);
        startActivity(intent);
    }


    /**
     * 选择后，回传值
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            ((HomeFragment) homeFragment).reload();
            return;
        }
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == CaptureActivity.REQUEST_CODE_SCAN) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String content = bundle.getString(CodeUtils.RESULT_STRING);
                    String url = null;
                    try {
                        url = URLEncoder.encode(content, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    toRequset(url);
                    Log.e("slh", "扫描结果为：" + content + "," + url);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析失败", Toast.LENGTH_LONG).show();
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //SpeakVoiceUtil.getInstance(MyApplication.mApplicationContext).cancelResource();
        // 撤销注册EventBus
        // EventBus.getDefault().unregister(this);
        // 撤销注册内容监听
        // this.getContentResolver().unregisterContentObserver(clb);
//        //撤销通话状态广播接收器
//        unregisterThis();
    }

    private void initView() {

        //电话
        phone = (LinearLayout) findViewById(R.id.tab_call);
        phone_iv = (ImageView) findViewById(R.id.tab_call_iv);
        phone_tv = (TextView) findViewById(R.id.tab_call_tv);
        phone.setOnClickListener(this);

        //商城
        shop = (LinearLayout) findViewById(R.id.tab_shopping_mall);
        shop_iv = (ImageView) findViewById(R.id.tab_shop_iv);
        shop_tv = (TextView) findViewById(R.id.tab_shop_tv);
        shop.setOnClickListener(this);

        //视频
        video = (LinearLayout) findViewById(R.id.tab_vipvideo);
        video_iv = (ImageView) findViewById(R.id.tab_vipvideo_iv);
        video_tv = (TextView) findViewById(R.id.tab_vipvideo_tv);
        video.setOnClickListener(this);


        //个人中心
        personal = (LinearLayout) findViewById(R.id.tab_setting);
        personal_iv = (ImageView) findViewById(R.id.tab_setting_iv);
        personal_tv = (TextView) findViewById(R.id.tab_setting_tv);
        personal.setOnClickListener(this);


//        tab02 = (LinearLayout) findViewById(R.id.tab_contacts);
//
//        tab02.setOnClickListener(this);
//
//        tab02_iv = (ImageView) findViewById(R.id.tab_contacts_iv);
//
//        tab02_tv = (TextView) findViewById(R.id.tab_contacts_tv);


        keyboardBottom = (LinearLayout) findViewById(R.id.ll_bottom_key_board);
        llBottomBar = (LinearLayout) findViewById(R.id.ll_bottom_bar);
        keyboard_state_iv = (ImageView) findViewById(R.id.iv_state);
        keyboard_state_iv.setOnClickListener(this);
        keyboard_state_iv.setImageResource(R.drawable.icon_key_board);
        findViewById(R.id.DigitCallButton).setOnClickListener(this);
        findViewById(R.id.iv_delete).setOnClickListener(this);
        findViewById(R.id.iv_delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //发送event触发相应动作
                EventBus.getDefault().post(MyConstant.EVENT_DELETE_LONG_TAG);
                return true;
            }
        });
    }

    private void selectTab(int index) {
        switch (index) {
            case 0:
                phone_iv.setImageResource(R.drawable.gear_shop_press);
                phone_tv.setTextColor(getResources().getColor(R.color.bottom_new_tab_select));

                shop_iv.setImageResource(R.drawable.bottombar_shop_normal);
                shop_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                video_iv.setImageResource(R.drawable.icon_bottombar_video_normal);
                video_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                personal_iv.setImageResource(R.drawable.bottombar_new_tab2_normal);
                personal_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));
                break;
            case 1:


                shop_iv.setImageResource(R.drawable.bottombar_shop_press);
                shop_tv.setTextColor(getResources().getColor(R.color.bottom_new_tab_select));


                phone_iv.setImageResource(R.drawable.gear_shop_normal);
                phone_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                video_iv.setImageResource(R.drawable.icon_bottombar_video_normal);
                video_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                personal_iv.setImageResource(R.drawable.bottombar_new_tab2_normal);
                personal_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                break;
            case 2:

                video_iv.setImageResource(R.drawable.icon_bottombar_video_select);
                video_tv.setTextColor(getResources().getColor(R.color.bottom_new_tab_select));

                phone_iv.setImageResource(R.drawable.gear_shop_normal);
                phone_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                shop_iv.setImageResource(R.drawable.bottombar_shop_normal);
                shop_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));


                personal_iv.setImageResource(R.drawable.bottombar_new_tab2_normal);
                personal_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                break;
            case 3:


                personal_iv.setImageResource(R.drawable.bottombar_new_tab2_press);
                personal_tv.setTextColor(getResources().getColor(R.color.bottom_new_tab_select));

                phone_iv.setImageResource(R.drawable.gear_shop_normal);
                phone_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                shop_iv.setImageResource(R.drawable.bottombar_shop_normal);
                shop_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));

                video_iv.setImageResource(R.drawable.icon_bottombar_video_normal);
                video_tv.setTextColor(getResources().getColor(R.color.bottom_tab_new_normal));


                break;
        }


    }


//    /**切换 fragment 的方法*/
//    private void fragmentReplace(Fragment fragment) {
//        resetSelected();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment_content, fragment).commitAllowingStateLoss();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_call://电话
                selectTab(0);
//                FragmentTransaction transaction = manager.beginTransaction();
//                if (phoneFragment == null) {
//                    phoneFragment = new PhoneFragment();
//                    if (!phoneFragment.isAdded()) {
//                        if (currentFragment != null) transaction.hide(currentFragment);
//                        transaction.add(R.id.fragment_content, phoneFragment);
//                        currentFragment = phoneFragment;
//                        transaction.commit();
//                    }
//                } else {
//                    if (currentFragment != phoneFragment) {
//                        if (currentFragment != null) transaction.hide(currentFragment);
//                        transaction.show(phoneFragment);
//                        currentFragment = phoneFragment;
//                        transaction.commit();
//                    }
//                }
                FragmentTransaction transaction = manager.beginTransaction();
                if (gearFragment == null) {
                    gearFragment = new GearShopFragment();
                    if (!gearFragment.isAdded()) {
                        if (currentFragment != null) transaction.hide(currentFragment);
                        transaction.add(R.id.fragment_content, gearFragment);
                        currentFragment = gearFragment;
                        transaction.commit();
                    }
                } else {
                    if (currentFragment != gearFragment) {
                        if (currentFragment != null) transaction.hide(currentFragment);
                        transaction.show(gearFragment);
                        currentFragment = gearFragment;
                        transaction.commit();
                    }
                }


                break;
            case R.id.tab_vipvideo:
                selectTab(2);
                FragmentTransaction transaction2 = manager.beginTransaction();
                if (vipVideoFragment == null) {
                    vipVideoFragment = new VipVideoFragment();
                    if (!vipVideoFragment.isAdded()) {
                        if (currentFragment != null) transaction2.hide(currentFragment);
                        transaction2.add(R.id.fragment_content, vipVideoFragment);
                        currentFragment = vipVideoFragment;
                        transaction2.commit();
                    }
                } else {
                    if (currentFragment != null) transaction2.hide(currentFragment);
                    if (currentFragment != vipVideoFragment) {
                        transaction2.show(vipVideoFragment);
                        currentFragment = vipVideoFragment;
                        transaction2.commit();
                    }
                }


                break;
            case R.id.tab_shopping_mall:
                selectTab(1);

                FragmentTransaction transaction1 = manager.beginTransaction();
                if (shopFragment == null) {
                    shopFragment = new NestShoppingFragment();
                    if (!shopFragment.isAdded()) {
                        if (currentFragment != null) transaction1.hide(currentFragment);
                        transaction1.add(R.id.fragment_content, shopFragment);
                        currentFragment = shopFragment;
                        transaction1.commit();
                    }
                } else {
                    if (currentFragment != shopFragment) {
                        if (currentFragment != null) transaction1.hide(currentFragment);
                        transaction1.show(shopFragment);
                        currentFragment = shopFragment;
                        transaction1.commit();
                    }
                }

                break;
            case R.id.tab_setting:
                selectTab(3);

                FragmentTransaction transaction3 = manager.beginTransaction();
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    if (!homeFragment.isAdded()) {
                        if (currentFragment != null) transaction3.hide(currentFragment);
                        transaction3.add(R.id.fragment_content, homeFragment);
                        currentFragment = homeFragment;
                        transaction3.commit();
                    }
                } else {
                    if (currentFragment != homeFragment) {
                        if (currentFragment != null) transaction3.hide(currentFragment);
                        transaction3.show(homeFragment);
                        currentFragment = homeFragment;
                        transaction3.commit();
                    }
                }


                break;
            case R.id.iv_delete:
                EventBus.getDefault().post(MyConstant.EVENT_DELETE_TAG);
                break;
            case R.id.DigitCallButton:
                EventBus.getDefault().post("dial");
                break;
            case R.id.iv_state:
                state = !state;
                EventBus.getDefault().post(state);//触发软键盘显示或者隐藏
                EventBus.getDefault().post(MyConstant.EVENT_STATE_TAG);//触发软键盘底部显示或隐藏
                break;
        }

    }

//

    public void onEventMainThread(Boolean event) {
        Log.d("onEventMainThread", "CallsFragment---" + event);
        if (event) {
            //显示软键盘底部布局
            keyboardBottom.setVisibility(View.VISIBLE);
            llBottomBar.setVisibility(View.GONE);
        } else {
            //隐藏软键盘底部布局
            keyboardBottom.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 用来控制软键盘底部布局显示状态
     */
    public void onEventMainThread(StringBuffer stringBuffer) {
        Log.d("onEventMainThread", "MainActivity---" + stringBuffer);
//        if (stringBuffer.length() > 0) {
//            //显示软键盘底部布局
//            keyboardBottom.setVisibility(View.VISIBLE);
//        } else {
//            //隐藏软键盘底部布局
//            keyboardBottom.setVisibility(View.GONE);
//        }
    }

    /**
     * 用来管理拨号键盘底部栏state图片变换
     */
    public void onEventMainThread(String s) {
        Log.d("onEventMainThread", "MainActivity---" + s);
        if (MyConstant.EVENT_STATE_TAG.equals(s)) {
//            if(state){
//                keyboard_state_iv.setImageResource(R.drawable.ic_kb_close);
//            }else{
//                keyboard_state_iv.setImageResource(R.drawable.ic_kb_open);
//            }
            //使导航栏中的键盘图标也软键盘底部的键盘图标状态一致
            //  selectCallTabOn(state);
        }
    }

    //网络电话注册状态
    private static int REG_STATE = 0;
    //sip定时注册
    private int TIME = 5000;//每隔5秒执行一次
    public Handler handler = new Handler();
//    public Runnable regRunnable = new Runnable() {
//        @Override
//        public void run() {
//            // handler自带方法实现定时器
//            try {
//                sip_Reg();
//                handler.postDelayed(this, TIME);
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                // e.printStackTrace();
//                System.out.println("exception...RegisterSipAccount");
//            }
//
//
//        }
//    };

//    //初始化sip拨号配置
//    protected void sip_init() {
//        if (PhoneService.isready() == false) {
//            Log.e("*ASTGO*", "start PhoneService MainTabActivity");
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setClass(this, PhoneService.class);
//            startService(intent);
//        } else {
//            if (MyApplication.getsipengine() == null)
//                MyApplication.setsipengine(PhoneService.instance().getSipEngine());
//
//            PhoneService.instance().RegisterUIEventListener(this);
//            Log.e("*ASTGO*", "PhoneService RegisterUIEventListener !");
//        }
//
//        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
//        pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "*SipEngine*");
//
//
//        handler.postDelayed(regRunnable, TIME); // 每隔TIMEs执行
//
//
//    }

    /**
     * 注册sip账号用于网络拨号
     */
//    protected void sip_Reg() {
//        Log.e("*ASTGO*", "sip_Reg start");
//        boolean reg_flag = true;
//
//        if (MyApplication.getsipengine() == null) {
//            Log.e("*ASTGO*", "sip_Reg return");
//
//            return;
//        }
//
//        if (reg_flag) {
//
//            String sipusername = MyApplication.mSpInformation.getString(MyConstant.SP_ACCOUNT, "");
//            Log.d("astgo","sipusername:"+sipusername);
//
//            String Loginpassword = MyApplication.mSpInformation.getString(MyConstant.SP_PASSWORD, "");
//            Log.d("astgo","Loginpassword:"+Loginpassword);
//
//
//            if (sipusername.trim().length() > 0 && Loginpassword.trim().length() > 0) {
//                if (REG_STATE == 0 || REG_STATE == 3) {
//                    MyApplication.getsipengine().EnableDebug(true);
//                    if (MyApplication.getsipengine() != null) {
//                        Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + sipusername + " Loginpassword:" + Loginpassword);
////                        boolean b = MyApplication.getsipengine().RegisterSipAccount(sipusername, Loginpassword, NetUrl.SIPIPADRESS, NetUrl.SIPIPORT);
//                        boolean b = MyApplication.getsipengine().RegisterSipAccount(sipusername, Loginpassword, HttpUtils.SIP_APP, NetUrl.SIPIPORT);
//                        if (b) {
//                            Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + "success");
//                        } else {
//                            Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + "failed");
//                        }
//                    }
//
//                }
//
//            }
//
//
//        } else {
//            if (REG_STATE == 2) {
//                REG_STATE = 3;
//                if (MyApplication.getsipengine() != null)
//                    MyApplication.getsipengine().DeRegisterSipAccount();
//                System.out.println("...DeRegisterSipAccount");
//
//            }
//        }
//    }
//    protected void sip_Reg() {
//        /*
//           0://智能判断
//           1://直拨
//           2://回拨
//           3://手动设置
//		 */
//        boolean reg_flag = false;
//
//        if (MyApplication.getsipengine() == null) {
//            Log.e("*ASTGO*", "sip_Reg return");
//
//            return;
//        }
//
//        int PRE_CALL_TYPE = MyApplication.mSpInformation.getInt(MyConstant.SP_SETTINGS_BDFS, MyConstant.CALL_BACK_TAG);
//
//        // 2://回拨
//        if (PRE_CALL_TYPE == 1) {
//            reg_flag = false;
//        }
////        //1://直拨
////        else if (PRE_CALL_TYPE == 1) {
////            reg_flag = true;
////        }
//        //0://智能判断
//        else if (PRE_CALL_TYPE == 0) {
//            int getAPNType = MyApplication.getAPNType(MyApplication.getInstance());
//            if (getAPNType == 1) {
//                reg_flag = true;
//            }
//
//        }
//        //3://手动设置
//        else if (PRE_CALL_TYPE == 3) {
//            reg_flag = true;
//        }
//
//
//        if (reg_flag) {
//
//            String sipusername = MyApplication.mSpInformation.getString(MyConstant.ACCTNAME, "");
//
//            String Loginpassword = MyApplication.mSpInformation.getString(MyConstant.SP_PASSWORD, "");
//
//
//            if (sipusername.trim().length() > 0 && Loginpassword.trim().length() > 0) {
//                if (REG_STATE == 0 || REG_STATE == 3) {
//
//                    //MyApplication.getsipengine().EnableDebug(true);
//                    if (MyApplication.getsipengine() != null) {
//                        Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + sipusername + " Loginpassword:" + Loginpassword);
//                        boolean b = false;
//                        String sipAddress = PreferenceUtil.getString(MyConstant.SIPADDRESS, "");
//                        int sipPort = PreferenceUtil.getInt(MyConstant.SIPPORT, -1);
//                        if (!TextUtils.isEmpty(sipAddress) && sipPort != -1) {
//                            b = MyApplication.getsipengine().RegisterSipAccount(sipusername, Loginpassword, sipAddress, sipPort);
//                        } else {
//                            b = MyApplication.getsipengine().RegisterSipAccount(sipusername, Loginpassword, HttpUtils.SIP_APP, NetUrl.SIPIPORT);
//                        }
//
//                        if (b) {
//                            Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + "success");
//                        } else {
//                            Log.e("*ASTGO*", "MyApplication.getsipengine().RegisterSipAccount " + "failed");
//                        }
//                    }
//
//                }
//
//            }
//
//
//        } else {
//            if (REG_STATE == 2) {
//                REG_STATE = 3;
//                if (MyApplication.getsipengine() != null)
//                    MyApplication.getsipengine().DeRegisterSipAccount();
//                System.out.println("...DeRegisterSipAccount");
//
//            }
//        }
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Intent.ACTION_MAIN);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.addCategory(Intent.CATEGORY_HOME);

            startActivity(i);
            return true;
        }
        return false;
    }

    @Override
    public void OnSipEngineState(int code) {

    }

    @Override
    public void OnRegistrationState(int code, int error_code) {
        Log.e("SIP注册状态：", "code:" + String.valueOf(code) + " error_code:" + String.valueOf(error_code));

        REG_STATE = code;
    }

    @Override
    public void OnNewCall(int CallDir, String peer_caller, boolean is_video_call) {
        Log.e(TAG, "OnNewCall---" + CallDir);

    }

    @Override
    public void OnCallProcessing() {

    }

    @Override
    public void OnCallRinging() {

    }

    @Override
    public void OnCallConnected() {

    }

    @Override
    public void OnCallStreamsRunning(boolean is_video_call) {

    }

    @Override
    public void OnCallMediaStreamConnected(int mode) {

    }

    @Override
    public void OnCallPaused() {

    }

    @Override
    public void OnCallResuming() {

    }

    @Override
    public void OnCallPausedByRemote() {

    }

    @Override
    public void OnCallResumingByRemote() {

    }

    @Override
    public void OnCallEnded() {

    }

    @Override
    public void OnCallFailed(int status) {

    }

    @Override
    public void OnNetworkQuality(int ms, String vos_balance) {

    }

    @Override
    public void OnRemoteDtmfClicked(int dtmf) {

    }

    @Override
    public void OnCallReport(long nativePtr) {

    }


    //   @Override
    //   public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
//            if(data != null){
//                ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
//                // do something
//                if(images != null && images.size() >0){
//                    String localImgUrl = images.get(0);
//                    if(homeFragment != null){
//                        ((HomeFragment)homeFragment).uploadHeadImag(localImgUrl);
//                    }
//                }
//            }
//        }


    //  }
}
