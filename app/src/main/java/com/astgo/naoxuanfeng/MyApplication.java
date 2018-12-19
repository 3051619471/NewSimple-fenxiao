package com.astgo.naoxuanfeng;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.astgo.naoxuanfeng.bean.ContactsBean;
import com.astgo.naoxuanfeng.dao.DaoMaster;
import com.astgo.naoxuanfeng.dao.DaoSession;
import com.astgo.naoxuanfeng.dao.PhoneAddInfoDao;
import com.astgo.naoxuanfeng.http.service.HttpUtils;
import com.astgo.naoxuanfeng.tools.ComTools;
import com.astgo.naoxuanfeng.tools.utils.DebugUtil;
import com.blankj.utilcode.utils.Utils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okhttputils.OkHttpUtils;
import com.mob.MobSDK;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.qfishphone.sipengine.SipEngineCore;



/**
 * Created by Administrator on 2016/2/23.
 * Application 类，可以在这里配置全局变量或者执行初始话操作
 */
public class MyApplication extends Application {
    public static int initnums=0;
    public static   int DURATIONS=20;  //控制游戏的时长
    public static   int SPEEDS=1000;
    public static String lengs_id="1";
    public static int lengs_id_num=5;
    public static int essay=30;
    public  Context context;
    public static boolean sound = true;

    public static boolean needSave = true;

    public static int game1HighScore = 0;
    //英文训练
    public static  int DURATION_EnglishTraining=10;
    //数字
    public static  int DURATION_digital=10;
    //中文
    public static  int Chinesenb=10;
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_new_them, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    // SharedPreferences 文件名
    private static final String SP_FILE_NAME = "sp_file";
    private static MyApplication mApplication;
    public static Context mApplicationContext;
    public static SharedPreferences mSpInformation;
    // 注册流程控制
    public static final boolean NEED_VERIFY = true;//需要验证码
    public static final boolean NEED_CREATE_PWD = true;//需要创建密码
    // 数据库操作全局变量
    private DaoMaster daoMaster;
    private DaoSession daosession;
    private PhoneAddInfoDao phoneAddInfoDao;
    private static SipEngineCore the_sipengine = null;
    public static List<ContactsBean> mDataSet1 = new ArrayList<>();// 联系人存放数据集
    public static List<ContentValues> mDataSet2 = new ArrayList<>();// 联系人存放数据集(每个联系人只有一个号码，并且号码已经去除+86等字符，用于检索)
    public static List<Integer> mIDs = new ArrayList<>();// 存放联系人 Contact ID 索引(用来去重)
    public static List<String> mPhoneNum = new ArrayList<>();//联系人号码的数据集

    //回拨相关
    public static boolean ANWSERTAG = false;//是否自动接听标记
    public static boolean CBTAG = false;//回拨标记
    public static Handler mhandler = new Handler();//监听CBTAG值的改变
    public static Runnable cbRunnable = new Runnable() {//15秒后自动关闭自动接听标签

        @Override
        public void run() {
            ANWSERTAG = false;
            Log.e("cbRunnable", "mhandler--false");
        }
    };

    /**
     * request的标签设置
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * volley的全局 RequestQueue变量
     */
    private RequestQueue mRequestQueue;
    /**
     * volley的全局 ImageLoader
     */
    private com.android.volley.toolbox.ImageLoader imageLoader;
    //用来判断是否app在后台运行
    public static int stateCount = 0;
    //用来判断拨号选择Dialog是否onCreate
    public static boolean selectDialog = false;
    public static Context mContext;

    private String TAGs = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        Fresco.initialize(this);
        mContext = this;
        FileDownloader.setup(mContext);
        context = getApplicationContext();

        //JCVideoPlayer.releaseAllVideos();
        mApplication = this;
        mApplicationContext = getApplicationContext();
        //CrashHandler.getInstance().init(this)

        OkHttpUtils.init(this);
        //OkHttpUtils.getInstance().addInterceptor(new TokenInterceptor());
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        mSpInformation = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
//        ContactsOrCallsUtil.getInstance().updateContactsData(this);//将联系人保存进全局变量便于查看
//        AddressDbUtil.updatePhoneAddInfos(mPhoneNum);//更新联系人归属地数据库
        //JPush初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        String id = JPushInterface.getRegistrationID(getApplicationContext());
        Log.e("slh", id);
        //登录时进行绑定
        //JPushInterface.setAlias(this,1,"");
        //退出登录进行解除绑定（即不再接收根据别名推送的消息）
        // JPushInterface.deleteAlias(ContentActivity.this,int sequence);
        Utils.init(this);
        //share sdk 配置
        MobSDK.init(mApplicationContext, "22b325b042dbd", "910d6c3310129da5e479f2a89ada102b");

        //解决7.0以上打开相机闪退
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//            StrictMode.setVmPolicy(builder.build());
//        }


/* <WechatMoments
                Id="5"
        SortId="5"
        AppId="wx4eeaa909776fb55a"
        AppSecret="dd3017f97d7be9127d3deae0de8e7a6e"
        BypassApproval="false"
        Enable="true" />

	<WechatFavorite
                Id="6"
        SortId="6"
        AppId="wx4eeaa909776fb55a"
        AppSecret="dd3017f97d7be9127d3deae0de8e7a6e"
        Enable="true" />*/
//        HashMap<String, Object> hashMap = new HashMap<String, Object>();
//        hashMap.put("Id", "5");
//        hashMap.put("AppId", "wx4eeaa909776fb55a");
//        hashMap.put("AppSecret", "dd3017f97d7be9127d3deae0de8e7a6e");
//        hashMap.put("BypassApproval", "false");
//        hashMap.put("Enable", "true");
//        hashMap.put("SortId", "5");
//       // hashMap.put("path", "pages/index/index.html?id=1");
//      //  hashMap.put("userName", "gh_afb25ac019c9");
//        ShareSDK.setPlatformDevInfo(Wechat.NAME, hashMap);
//
//        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
//        hashMap.put("Id", "6");
//        hashMap.put("AppId", "wx4eeaa909776fb55a");
//        hashMap.put("AppSecret", "dd3017f97d7be9127d3deae0de8e7a6e");
//        hashMap.put("BypassApproval", "false");
//        hashMap.put("Enable", "true");
//        hashMap.put("SortId", "6");
//        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, hashMap1);

        initTextSize();

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@", "加载内核是否成功:" + b);
            }
        });
    }

    public static boolean  btn =false;
    public static Context getContext() {
        return mContext;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

//    /**
//     * 设置回拨标签
//     */
//    public void setCBTAG(boolean b) {
//        CBTAG = b;
//        if (CBTAG == true) {
//            setANWSERTAG(true);//设置可自动接听
//        }
//    }
//
//    /**
//     * 设置自动接听标签
//     */
//    public void setANWSERTAG(boolean b) {
//        ANWSERTAG = b;
//        if (ANWSERTAG == true) {//设置自动接听
//            mhandler.postDelayed(cbRunnable, 30000);//延迟15秒执行
//        } else {//取消自动接听
//            mhandler.removeCallbacks(cbRunnable);
//        }
//    }
//
//    /**
//     * 获取全局traInfoDao
//     *
//     * @return
//     */
//    public PhoneAddInfoDao getPhoneAddInfoDao() {
//
//        if (daoMaster == null) {
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(),
//                    "PhoneAddress", null);
//            SQLiteDatabase db = helper.getWritableDatabase();
//            daoMaster = new DaoMaster(db);
//        }
//        if (daosession == null) {
//            daosession = daoMaster.newSession();
//        }
//        if (phoneAddInfoDao == null) {
//            phoneAddInfoDao = daosession.getPhoneAddInfoDao();
//        }
//        return phoneAddInfoDao;
//    }


    /**
     * synchronized 加在方法之前作用是限制在同一时刻只能有一个线程执行此方法
     */


    public synchronized static MyApplication getInstance() {
        if (mApplication == null) {
            mApplication = new MyApplication();
        }
        return mApplication;
    }

//
private HttpProxyCacheServer proxy;
    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }

    /**
     * @return Volley的RequestQueue，如果为空则被创建
     */
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        }

        return mRequestQueue;
    }

    /**
     * 获得图片加载器
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            // 这个是ImageLoader 的缓存，每次新启动应用，都会走这里
            final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(
                    20);
            ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
                @Override
                public void putBitmap(String key, Bitmap value) {
                    mImageCache.put(key, value);
                    // 保存到本地
                    ComTools.saveBitmap2(value, key, getApplicationContext());
                }

                @Override
                public Bitmap getBitmap(String key) {
                    return mImageCache.get(key);
                }
            };
            imageLoader = new ImageLoader(getRequestQueue(), imageCache);
        }
        return imageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     * 添加指定的request到全局RequestQueu中，如果request有设置标签则使用此标签，没有就使用默认标签
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     * 添加指定的request到全局RequestQueu中，使用默认标签
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     * 通过标签取消所有未发出的请求，只有设置了标签的requests才能被取消
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

//    public static SipEngineCore getsipengine() {
//        return the_sipengine;
//    }
//
//    public static void setsipengine(SipEngineCore sipengine) {
//        the_sipengine = sipengine;
//    }

    private void initActivityLife() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                stateCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                stateCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static final int CMNET = 3;
    private static final int CMWAP = 2;
    private static final int WIFI = 1;

    /**
     * 获取网络连接类型
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = CMNET;
            } else {
                netType = CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }

    public static boolean isBackgroud() {
        if (stateCount == 0) {
            Log.i("isBackgroud", "后台");
            return true;
        } else {
            Log.i("isBackgroud", "前台");
            return false;
        }
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
