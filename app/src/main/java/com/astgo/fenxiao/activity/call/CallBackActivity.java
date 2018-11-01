//package com.astgo.fenxiao.activity.call;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.android.internal.telephony.ITelephony;
//import com.bumptech.glide.Glide;
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.activity.BaseActivity;
//import com.astgo.fenxiao.bean.AppConstant;
//import com.astgo.fenxiao.http.service.HttpClient;
//import com.astgo.fenxiao.http.service.HttpResultSubscriber;
//import com.astgo.fenxiao.tools.FileUtil;
//import com.astgo.fenxiao.tools.NetworkUtil;
//import com.astgo.fenxiao.tools.PreferenceUtil;
//import com.astgo.fenxiao.tools.Tt;
//import com.astgo.fenxiao.tools.WebServiceUtil;
//import com.astgo.fenxiao.tools.XmlUtil;
//import com.astgo.fenxiao.tools.utils.DebugUtil;
//import com.astgo.fenxiao.tools.utils.SPUtils;
//
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
//import rx.Subscription;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by Administrator on 2016/3/26.
// * 回拨界面
// */
//public class CallBackActivity extends BaseActivity implements View.OnClickListener {
//
//    public static CallBackActivity instance = null;
//
//    private ImageView ivCallBackBg;
//    private TextView call_back_name;
//    private TextView call_back_num;
//    public static TextView call_back_state;
//    private LinearLayout call_back_mute, call_back_handsfree;
//    private ImageView ivCall_back_mute,ivCall_back_handsfree;
//    // 保存 post 请求参数的 HashMap
//    private Map<String, String> urlParams;
//    private String formatNumber;//被叫号码(区号处理)
//    //语音提醒
//    private MediaPlayer mMediaPlayer;
//    private AudioManager audioManager;
//    private static boolean soundStart = false;
//    //自动挂断
//    private Handler mHandler = new Handler();
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
////            exHangUp();
//        }
//    };
//    //用于改写通话状态
//    public static Handler stateHandler = new Handler();
//    public static Runnable runnable = new Runnable() {
//
//        @Override
//        public void run() {
//            // handler自带方法实现定时器
//            try {
//
//                long timeLong = System.currentTimeMillis() - startTime;
////                call_back_state.setText(TimeUtil.formatLongToTimeStr(timeLong));
//                stateHandler.postDelayed(this, 500);
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                System.out.println("exception...");
//            }
//        }
//    };
//    public static long startTime;
//    public boolean LoudspeakerStatus = false;//免提标识
//    public boolean MuteMic = false;//静音标识
//    private int current;//当前通话音量
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_call_back);
//        instance = this;
//        initView();
//        initData();
//        //通话音量
//        current = audioManager.getStreamVolume( AudioManager.STREAM_VOICE_CALL );
//    }
//
//    @Override
//    protected void onResume() {
//        // 默认听筒播放语音提醒
//        audioManager.setMicrophoneMute(false);
//        audioManager.setSpeakerphoneOn(false);
//        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//        audioManager.setMode(AudioManager.MODE_IN_CALL);
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        // 停止播放语音提醒
//        stop_play_wav();
//        mHandler.removeCallbacks(mRunnable);
//        super.onPause();
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        stateHandler.removeCallbacks(runnable);
//        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, current, 0);
//    }
//
//    /**
//     * 初始化界面
//     */
//    private void initView(){
//        ivCallBackBg = (ImageView) findViewById(R.id.iv_call_direct_bg);
//        call_back_name = (TextView) findViewById(R.id.call_back_name);
//        call_back_num = (TextView) findViewById(R.id.call_back_num);
//        call_back_state = (TextView) findViewById(R.id.call_back_state);
//        call_back_mute = (LinearLayout) findViewById(R.id.call_back_mute);
//        ivCall_back_mute = (ImageView) findViewById(R.id.iv_call_back_mute);
//        call_back_handsfree = (LinearLayout) findViewById(R.id.call_back_handsfree);
//        ivCall_back_handsfree = (ImageView) findViewById(R.id.iv_call_back_handsfree);
//        findViewById(R.id.call_back_hangup).setOnClickListener(this);
//        call_back_mute.setOnClickListener(this);
//        call_back_handsfree.setOnClickListener(this);
//    }
//
//    /**
//     * 加载数据
//     */
//    private void initData(){
//        if (getXmlNode(MyConstant.IMAGEURL)!= null && getXmlNode(MyConstant.IMAGEURL).length() >= 10)
//        {
//            Log.d("MyConstant.IMAGEURL", getXmlNode(MyConstant.IMAGEURL));
//            initCallBg(getXmlNode(MyConstant.IMAGEURL), getXmlNode(MyConstant.LINKURL));
//        }
//        if(MyConstant.cName == null || "".equals(MyConstant.cName)){
//            call_back_name.setText("陌生人");
//        }else{
//            call_back_name.setText(MyConstant.cName);
//        }
//        call_back_num.setText(MyConstant.cNum);
//        call();
//    }
//
//    // 获取xml指定节点的值
//    private String getXmlNode(String filter) {
//        return XmlUtil.parserFilterXML(FileUtil.read(this, MyConstant.FILE_LOGIN), filter);
//    }
//
//    /**
//     * 网络拨号
//     */
//    private void call(){
//        // 获取系统声音服务
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
////        // 检查本地区号设置，并执行相应的操作
////        if (RegexUtil.isFixedPhone(MyConstant.cNum)) {
////            formatNumber = MyApplication.mSpInformation.getString(MyConstant.SP_SETTINGS_BDQH, "") + MyConstant.cNum;
////        } else  {
////            formatNumber = MyConstant.cNum;
////        }
////
////        // 检查来电显示设置，并执行相应的操作
////        if (!MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_LDXS, true)) {
////            formatNumber = "66" + formatNumber;
////        }
//        formatNumber = MyConstant.cNum;
//        // 判断网络环境，请求回拨接口
//        if (NetworkUtil.isConnection(this)) {
//            setHashMap(true);
////            serverCallback();
//            serverNewCall();
//        } else {
//            Tt.showShort(this, getString(R.string.string_network_error));
//        }
//    }
//
//
//    private void serverNewCall() {
//        Subscription subscribe = HttpClient.Builder.getAppService().serverCall(urlParams)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<Object>() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        MyApplication.getInstance().setCBTAG(true);
//                        call_back_state.setText(getString(R.string.call_back_api_ret_0));
//                        showCountTmer();
//                        boolean aBoolean = MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_YYTX, true);
//                        if(aBoolean){
//                            play_wav(); // 播放语音提醒
//                        }
//                        setCallBackAdsBg();
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                        call_back_state.setText(getString(R.string.call_back_api_ret_1));
//                    }
//                });
//        addSubscription(subscribe);
//    }
//
//    private void setCallBackAdsBg() {
//        String callAdsBg = SPUtils.getString(AppConstant.KEY_CALL_ADS_BG, "");
//        Glide.with(CallBackActivity.this).load(callAdsBg).placeholder(R.mipmap.new_call_bg_default).into(ivCallBackBg);
//    }
//
//
////    private void serverCallback() {
////        WebServiceUtil.postStringData(NetUrl.SUBCALLTASK, urlParams, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String s) {
////                int ret = XmlUtil.getRetXML(s);
////                switch (ret) {
////                    case 0://回拨成功
////                        MyApplication.getInstance().setCBTAG(true);
////                        call_back_state.setText(getString(R.string.call_back_api_ret_0));
////                        play_wav(); // 播放语音提醒
////                        break;
////                    case 1://回拨失败
////                        call_back_state.setText(getString(R.string.call_back_api_ret_1));
////                        mHandler.postDelayed(mRunnable, 5000);
////                        break;
////                    case 5://
////                        call_back_state.setText(getString(R.string.call_back_api_ret_5));
////                        mHandler.postDelayed(mRunnable, 5000);
////                        break;
////                    default:
////                        break;
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError volleyError) {
////                Tt.showShort(CallBackActivity.this, getString(R.string.string_network_error));
////            }
////        });
////    }
//
//    // 将POST请求的参数封装成HashMap<>对象
//    private void setHashMap(boolean b) {
//        if (b) {//回拨参数
//            urlParams = new HashMap<String, String>();
//            urlParams.put(MyConstant.CALLER, PreferenceUtil.getString(MyConstant.SP_ACCOUNT, ""));
//            urlParams.put(MyConstant.CALLED, formatNumber);
//        } else {//挂断回拨参数
//            urlParams = new HashMap<String, String>();
//            urlParams.put(MyConstant.REGNUM, MyApplication.mSpInformation.getString(MyConstant.BINDTEL, ""));
//            urlParams.put(MyConstant.REGPWD, MyApplication.mSpInformation.getString(MyConstant.PASSWORD, ""));
//            urlParams.put(MyConstant.CALLED, formatNumber);
//        }
//    }
//
//    // 网络获取通话界面的背景图片
//    private void initCallBg(String image_url, final String image_link) {
//        // 显示图片的配置
//        WebServiceUtil.setWebImage(getBaseContext(), ivCallBackBg, image_url);
////        ivCallBackBg.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                // 通话背景图片点击事件
////                Tt.showShort(getBaseContext(), image_link);
////            }
////        });
//    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.call_back_hangup:     //挂断
////                exHangUp();
//                endCallBack();
//                break;
//            case R.id.call_back_handsfree: //免提
//                if(MuteMic){//不静音的时候才能开免提
//                    checkMuteMicClick();
//                }
//                setHandsFreeStatus();
//                break;
//            case R.id.call_back_mute://静音
//                if(LoudspeakerStatus){
//                    setHandsFreeStatus();
//                }
//                checkMuteMicClick();
//                break;
//        }
//    }
//
//    private void endCallBack() {
////        endCall();
//        stateHandler.removeCallbacks(runnable);
//        this.finish();
//    }
//
//    // 执行挂断号码操作
//    public void exHangUp() {
//        if (NetworkUtil.isConnection(this)) {
//            setHashMap(false);
////            WebServiceUtil.postStringData(NetUrl.HANGUP, urlParams, new Response.Listener<String>() {
////                @Override
////                public void onResponse(String s) {
////                    int ret = XmlUtil.getRetXML(s);
////                    switch (ret) {
////                        case 0://挂断号码成功
////                            Log.d("MyApp", "Hang Up Success");
////                            break;
////                        case -1://接口问题
////                            Log.d("MyApp", getString(R.string.xml_error));
////                            break;
////                        default://挂断失败
////                            Log.d("MyApp", "Hang Up Fail");
////                            break;
////                    }
////                }
////            }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError volleyError) {
////                    Tt.showShort(CallBackActivity.this, getString(R.string.string_network_error));
////                }
////            });
////        } else {
////            Tt.showShort(this, getString(R.string.string_network_error));
//        }
//    }
//
//    // 播放语音提醒
//    private void play_wav() {
//
//
//        try {
//            mMediaPlayer = MediaPlayer.create(this, R.raw.calling_short);
//            mMediaPlayer.start();
//            soundStart = true;
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 关闭语音提醒
//    private void stop_play_wav() {
//        if (soundStart) {
//            soundStart = false;
//            audioManager.setMicrophoneMute(false);
//            audioManager.setSpeakerphoneOn(true);
//            audioManager.setMode(AudioManager.MODE_NORMAL);
//            if (mMediaPlayer != null) {
//                try {
//                    if (mMediaPlayer.isPlaying())
//                        mMediaPlayer.stop();
//                    mMediaPlayer.release();
//                    mMediaPlayer = null;
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public static void displayStatus(final String msg) {
//        stateHandler.post(new Runnable() {
//
//            public void run() {
//                call_back_state.setText(msg);
//            }
//        });
//    }
//    //启动计时器
//    public static void displayStatus(final long time){
//        startTime = time;
//        stateHandler.postDelayed(runnable, 500);
//    }
//    //关闭计时器
//    public static void stopStatus(){
//        if(stateHandler != null){
//            stateHandler.removeCallbacks(runnable);
//        }
//    }
//
//    /**
//     * 自动挂断来电
//     */
//    public static void endCall() {
//        TelephonyManager telMag = (TelephonyManager) MyApplication.mApplicationContext.getSystemService(Context.TELEPHONY_SERVICE);
//        Class<TelephonyManager> c = TelephonyManager.class;
//        Method mthEndCall;
//        try {
//            mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
//            mthEndCall.setAccessible(true);
//            ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,
//                    (Object[]) null);
//            iTel.endCall();
//            //LogOut.out(this, iTel.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //LogOut.out(this, "endCall test");
//    }
//
//    // 设置是否免提
//    private void setHandsFreeStatus() {
//        LoudspeakerStatus = !LoudspeakerStatus;
//
//        if (LoudspeakerStatus) {
//            // 不设置静音
//            audioManager.setMicrophoneMute(false);
//            // 打开扬声器
//            audioManager.setSpeakerphoneOn(true);
//            // 设置声音模式(注意以上顺序问题)
//            audioManager.setMode(AudioManager.MODE_NORMAL);
//            // HangFree 按钮 on
//            ivCall_back_handsfree.setImageResource(R.mipmap.new_call_direct_btn_handsfree_press);
//        } else {
//            // 设置静音
//            audioManager.setMicrophoneMute(false);
//            // 关闭扬声器
//            audioManager.setSpeakerphoneOn(false);
//            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//            audioManager.setMode(AudioManager.MODE_IN_CALL);
//            // HangFree 按钮 off
//            ivCall_back_handsfree.setImageResource(R.mipmap.new_call_direct_btn_handsfree);
//        }
//
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().SetLoudspeakerStatus(LoudspeakerStatus);
//
//    }
//
//    //是否设置静音
//    private void checkMuteMicClick()
//    {
//        MuteMic =  ! MuteMic;
//        if (MuteMic)
//        {
//            audioManager.setMicrophoneMute(true);
//            ivCall_back_mute.setImageResource(R.mipmap.new_call_direct_btn_mute_press);
//            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, 0);
//
//            Log.e("*ASTGO*","MuteMiced" );
//
//        }
//        else
//        {
//            audioManager.setMicrophoneMute(false);
//            Log.e("*ASTGO*","not MuteMiced" );
//            ivCall_back_mute.setImageResource(R.mipmap.new_call_direct_btn_mute);
//            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, current, 0);
//        }
////        audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL,MuteMic);
//        Log.e("*ASTGO*","MuteMiced" + audioManager.isMicrophoneMute());
//
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().MuteSpk(MuteMic);
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().MuteMic(MuteMic);
//    }
//
//    CountDownTimer countDownTimer;
//    private void showCountTmer() {
//        if(countDownTimer == null){
//            //显示倒计时
//            countDownTimer = new CountDownTimer(15000,1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                }
//
//                @Override
//                public void onFinish() {
//                    endCallBack();
//                    countDownTimer = null;
//                }
//            };
//            countDownTimer.start();
//        }
//    }
//}
