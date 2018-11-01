//package com.astgo.fenxiao.activity.call;
//
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.NetUrl;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.bean.AppConstant;
//import com.astgo.fenxiao.http.service.HttpUtils;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//import com.astgo.fenxiao.tools.DensityUtil;
//import com.astgo.fenxiao.tools.FileUtil;
//import com.astgo.fenxiao.tools.NetworkUtil;
//import com.astgo.fenxiao.tools.PreferenceUtil;
//import com.astgo.fenxiao.tools.TimeUtil;
//import com.astgo.fenxiao.tools.Tt;
//import com.astgo.fenxiao.tools.WebServiceUtil;
//import com.astgo.fenxiao.tools.XmlUtil;
//import com.astgo.fenxiao.tools.utils.SPUtils;
//import com.astgo.fenxiao.widget.RingWaterView;
//
//import java.util.List;
//
//import cn.qfishphone.PhoneService;
//
//import cn.qfishphone.sipengine.SipEngineEventListener;
//
///**
// * Created by Administrator on 2016/3/18.
// *直拨界面
// */
//public class CallDirectActivity extends AppCompatActivity implements View.OnClickListener,SipEngineEventListener, SensorEventListener {
//
//    private static final String TAG = "直拨界面CallBackActivity";
//
//    private ImageView ivCallBackBg;
//    private LinearLayout keyboard_in_call;//通话时的键盘
//    private EditText keyboard_input;//通话键盘的输入框
//    private StringBuffer keyboardNum = new StringBuffer();//存放号码
//    private LinearLayout call_direct_top;//通话界面顶部姓名布局
//    private RingWaterView ringWaterView;//水纹扩撒动画控件
//    private TextView call_direct_name;
//    private TextView call_direct_num;
//    private TextView call_direct_state;
//    private LinearLayout call_direct_mute, call_direct_handsfree, call_direct_recording, call_direct_keyboard, call_direct_callBack, call_direct_hold;
//    private ImageView ivCall_direct_mute,ivCall_direct_handsfree;
//    private ImageView call_direct_keyboard_iv;
//    private String formatNumber;//被叫号码(区号处理后的)
//
//    private AudioManager audioManager;
//    public Handler mHandler = new Handler();//用来改写界面通话状态
//    private SensorManager mSensorManager;
//    private long timelong = 0;
//    private int TIME = 500;
//    private Long STARTTIME;//通话开始时间（单位秒）
//    private int is_talking = 0;//通话状态
//    private int close_timer_count = 0;
//    //通话开始与结束的时间戳
//    private long start;
//    private long end;
//    private int addCallTag = 0;//是否添加了通话记录标识
//    public boolean LoudspeakerStatus = false;//免提标识
//    public boolean MuteMic = false;//静音标识
//    public boolean RecordingStatus = false;//录音标识
//    public boolean KeyBoardStatus = false;//键盘显示标识
//    public boolean HoldStates = false;//通话hold标识
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_call_direct);
//        //初始化界面
//        initView();
//        initData();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (is_talking == 1 || is_talking == 2) {
//            is_talking = 0;
//            handler.removeCallbacks(runnable);
//        }
//        if (MyApplication.getsipengine() != null) {
//            MyApplication.getsipengine().Hangup();
//        }
//        if (PhoneService.isready()) {
//            PhoneService.instance().DeRegisterUIEventListener();
//        }
//        if (mSensorManager != null) {
//            mSensorManager.unregisterListener(this);
//        }
//        if(addCallTag == 0){//如果未添加过记录则添加记录
//            Log.e(TAG, "add----calls");
//            ContactsOrCallsUtil.addCalls(this, MyConstant.cName, MyConstant.cNum, System.currentTimeMillis(), 0);
//            ringWaterView.stop();
//        }
//    }
//
//    /**
//     * 初始化界面
//     */
//    private void initView(){
//        ivCallBackBg = (ImageView) findViewById(R.id.iv_call_direct_bg);
//        call_direct_top = (LinearLayout) findViewById(R.id.call_direct_top_ll);
//        ringWaterView = (RingWaterView) findViewById(R.id.ringWaterView);
//        ringWaterView.setRadius(DensityUtil.dip2px(this, 125));
//        ringWaterView.start();
//        call_direct_name = (TextView) findViewById(R.id.call_direct_name);
//        call_direct_num = (TextView) findViewById(R.id.call_direct_num);
//        call_direct_state = (TextView) findViewById(R.id.call_direct_state);
//        findViewById(R.id.call_direct_hangup).setOnClickListener(this);
//        call_direct_mute = (LinearLayout) findViewById(R.id.call_direct_mute);
//        ivCall_direct_mute = (ImageView) findViewById(R.id.iv_call_back_mute);
//        call_direct_handsfree = (LinearLayout) findViewById(R.id.call_direct_handsfree);
//        ivCall_direct_handsfree = (ImageView) findViewById(R.id.iv_call_back_handsfree);
//        call_direct_recording = (LinearLayout) findViewById(R.id.call_direct_recording);
//        call_direct_keyboard = (LinearLayout) findViewById(R.id.call_direct_keyboard);
//        call_direct_callBack = (LinearLayout)findViewById(R.id.call_direct_callback);
//        call_direct_hold = (LinearLayout)findViewById(R.id.call_direct_hold);
//        call_direct_keyboard_iv = (ImageView) findViewById(R.id.call_direct_keyboard_iv);
//        call_direct_mute.setOnClickListener(this);
//        call_direct_handsfree.setOnClickListener(this);
//        call_direct_recording.setOnClickListener(this);
//        call_direct_keyboard.setOnClickListener(this);
//        call_direct_callBack.setOnClickListener(this);
//        call_direct_hold.setOnClickListener(this);
//        initKeyBoard();
//    }
//
//    /**
//     * 初始化通话时的键盘
//     */
//    private void initKeyBoard(){
//        keyboard_in_call = (LinearLayout) findViewById(R.id.keyboard_in_call);
//        keyboard_input = (EditText) findViewById(R.id.keyboard_input);
//        findViewById(R.id.DigitOneButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitTwoButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitThreeButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitFourButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitFiveButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitSixButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitSevenButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitEightButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitNineButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitZeroButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitXhButton_in_call).setOnClickListener(this);
//        findViewById(R.id.DigitJhButton_in_call).setOnClickListener(this);
//    }
//    /**
//     * 加载数据
//     */
//    private void initData(){
//        if (getXmlNode(MyConstant.IMAGEURL) != null && getXmlNode(MyConstant.IMAGEURL).length() >= 10)
//        {
//            Log.d("MyConstant.IMAGEURL", getXmlNode(MyConstant.IMAGEURL));
//            initCallBg(getXmlNode(MyConstant.IMAGEURL), getXmlNode(MyConstant.LINKURL));
//        }
//        if(MyConstant.cName == null || "".equals(MyConstant.cName)){
//            call_direct_name.setText("陌生人");
//        }else{
//            call_direct_name.setText(MyConstant.cName);
//        }
//        call_direct_num.setText(MyConstant.cNum);
//        call();
//    }
//
//    // 获取xml指定节点的值
//    private String getXmlNode(String filter) {
//        return XmlUtil.parserFilterXML(FileUtil.read(this, MyConstant.FILE_LOGIN), filter);
//    }
//
//    /**
//     * 进行网络拨号
//     */
//    private void call(){
//        // 获取系统声音服务
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        Log.e("*ASTGO*", "number:" + MyConstant.cNum);
////        // 检查本地区号设置，并执行相应的操作
////        if (RegexUtil.isFixedPhone(MyConstant.cNum)) {
////            formatNumber = MyApplication.mSpInformation.getString(MyConstant.SP_SETTINGS_BDQH, "") + MyConstant.cNum;
////        } else {
////            formatNumber = MyConstant.cNum;
////        }
////
////        // 检查来电显示设置，并执行相应的操作
////        if (!MyApplication.mSpInformation.getBoolean(MyConstant.SP_SETTINGS_LDXS, true)) {
////            formatNumber = "66" + formatNumber;
////        }
//        formatNumber = MyConstant.cNum;
//        Log.e("*ASTGO*", "formatNumber:" + formatNumber);
//
//
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        if (mSensorManager != null) {
//            List<Sensor> lSensorList = mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
//            if (lSensorList.size() > 0) {
//                mSensorManager.registerListener(this, lSensorList.get(0), SensorManager.SENSOR_DELAY_UI);
//                Log.i("*SipEngine*", "Proximity sensor detected, registering");
//            }
//        }
//
//
//
//        // 判断网络环境，请求回拨接口
//        if (NetworkUtil.isConnection(this)) {
//
//            if (PhoneService.isready() == false) {
//                Log.d("*ASTGO*", "start PhoneService!");
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setClass(this, PhoneService.class);
//                startService(intent);
//            } else {
//
//                if (MyApplication.getsipengine() == null) {
//                    MyApplication.setsipengine(PhoneService.instance().getSipEngine());
//                }
//                PhoneService.instance().RegisterUIEventListener(this);
//                Log.d("*ASTGO*", "PhoneService already runing !");
//            }
//
//            PhoneService.instance().getSipEngine().SetNS(true);
//            PhoneService.instance().getSipEngine().SetAGC(true);
//            PhoneService.instance().getSipEngine().SetAEC(true);
//
//
//            //SIP直拨调用方法
//            if (MyApplication.getInstance() != null)
//            {
//
//                if (! MyApplication.getsipengine().AccountIsRegistered())
//                {
//                    String sipusername =  MyApplication.mSpInformation.getString(MyConstant.ACCTNAME, "");
//                    String Loginpassword = MyApplication.mSpInformation.getString(MyConstant.SP_PASSWORD, "");
//                    Log.e("*ASTGO*","sipusername:----"+sipusername);
//                    Log.e("*ASTGO*","Loginpassword:----"+Loginpassword);
//
//                    String sipAddress = PreferenceUtil.getString(MyConstant.SIPADDRESS, "");
//                    int sipPort = PreferenceUtil.getInt(MyConstant.SIPPORT, -1);
//                    if(!TextUtils.isEmpty(sipAddress) && sipPort != -1){
//                        MyApplication.getsipengine().RegisterSipAccount(sipusername,Loginpassword, sipAddress,sipPort);
//                    }else{
//                        MyApplication.getsipengine().RegisterSipAccount(sipusername,Loginpassword, HttpUtils.SIP_APP,NetUrl.SIPIPORT );
//                    }
//                    Log.e("*ASTGO*","MyApplication.getsipengine().RegisterSipAccount: start" );
//
//                }
//
//                Log.e("*ASTGO*","calledNumber："+formatNumber);
//
//
//                if (formatNumber.startsWith("sip:")) {
//                    MyApplication.getsipengine().MakeUrlCall(formatNumber);
//                } else {
//                    MyApplication.getsipengine().MakeCall(formatNumber);
//                }
//            }
//
//        } else {
//            Tt.showShort(this, getString(R.string.string_network_error));
//        }
//    }
//
//    //设置拨号键盘已输入的号码keyboardNum内容
//    private void setKeyboardText(int i) {
//        if(i == 10){
//            keyboardNum.append("*");
//            MyApplication.getsipengine().SendDtmf(1,"*");
//        }else if(i == 11){
//            keyboardNum.append("#");
//            MyApplication.getsipengine().SendDtmf(1, "#");
//        }else{
//            keyboardNum.append(i);
//            MyApplication.getsipengine().SendDtmf(1, i + "");
//        }
//        keyboard_input.setText(keyboardNum);
//        //设置光标位置
//        keyboard_input.setSelection(keyboardNum.length());
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
//        Intent intent;
//        switch (view.getId()){
//            case R.id.call_direct_hangup://挂断按钮
//                finish();
//                if(MyApplication.getsipengine() != null){
//                    MyApplication.getsipengine().Hangup();
//                }
//                break;
//            case R.id.call_direct_callback://回拨按钮
//                MyApplication.getsipengine().Hangup();
//                intent = new Intent(this, CallBackActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.call_direct_handsfree://免提按钮
//                setHandsFreeStatus();
//                break;
//            case R.id.call_direct_mute://静音按钮
//                checkMuteMicClick();
//                break;
//            case R.id.call_direct_recording://录音按钮
//                break;
//            case R.id.call_direct_hold://通话保持
//                break;
//            //键盘按钮
//            case R.id.call_direct_keyboard:
//                setKeyBoard();
//                break;
//            case R.id.DigitOneButton_in_call:
//                setKeyboardText(1);
//                break;
//            case R.id.DigitTwoButton_in_call:
//                setKeyboardText(2);
//                break;
//            case R.id.DigitThreeButton_in_call:
//                setKeyboardText(3);
//                break;
//            case R.id.DigitFourButton_in_call:
//                setKeyboardText(4);
//                break;
//            case R.id.DigitFiveButton_in_call:
//                setKeyboardText(5);
//                break;
//            case R.id.DigitSixButton_in_call:
//                setKeyboardText(6);
//                break;
//            case R.id.DigitSevenButton_in_call:
//                setKeyboardText(7);
//                break;
//            case R.id.DigitEightButton_in_call:
//                setKeyboardText(8);
//                break;
//            case R.id.DigitNineButton_in_call:
//                setKeyboardText(9);
//                break;
//            case R.id.DigitZeroButton_in_call:
//                setKeyboardText(0);
//                break;
//            case R.id.DigitXhButton_in_call:
//                setKeyboardText(10);
//                break;
//            case R.id.DigitJhButton_in_call:
//                setKeyboardText(11);
//                break;
//        }
//    }
//    // 设置是否免提
//    private void setHandsFreeStatus() {
//        LoudspeakerStatus = !LoudspeakerStatus;
//
//        if (LoudspeakerStatus) {
//            // 关闭麦克风
//            audioManager.setMicrophoneMute(false);
//            // 打开扬声器
//            audioManager.setSpeakerphoneOn(true);
//            // 设置声音模式(注意以上顺序问题)
//            audioManager.setMode(AudioManager.MODE_NORMAL);
//            // HangFree 按钮 on
//            ivCall_direct_handsfree.setImageResource(R.mipmap.new_call_direct_btn_handsfree_press);
//        } else {
//            // 关闭麦克风
//            audioManager.setMicrophoneMute(false);
//            // 关闭扬声器
//            audioManager.setSpeakerphoneOn(false);
//            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
//            audioManager.setMode(AudioManager.MODE_IN_CALL);
//            // HangFree 按钮 off
//            ivCall_direct_handsfree.setImageResource(R.mipmap.new_call_direct_btn_handsfree);
//        }
//
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().SetLoudspeakerStatus(LoudspeakerStatus);
//
//    }
//
//    //是否通话保持hold
//    private void setHoldClick(){
//        HoldStates = !HoldStates;
//        if(HoldStates){
//            call_direct_hold.setBackgroundResource(R.drawable.shape_cyclo_translucency_call_btn_bg_green);
//            Log.e("*ASTGO*","Hold" );
//        }else{
//            ivCall_direct_mute.setBackgroundResource(R.mipmap.new_call_direct_btn_mute);
//            Log.e("*ASTGO*","not Hold" );
//        }
//    }
//
//    //是否设置静音
//    private void checkMuteMicClick()
//    {
//        MuteMic =  ! MuteMic;
//        if (MuteMic)
//        {
//            ivCall_direct_mute.setImageResource(R.mipmap.new_call_direct_btn_mute_press);
//            if(MyApplication.getInstance() != null){
//                MyApplication.getsipengine().SetHold();
//            }
//            Log.e("*ASTGO*","MuteMiced" );
//
//        }
//        else
//        {
//            Log.e("*ASTGO*","not MuteMiced" );
//            ivCall_direct_mute.setImageResource(R.mipmap.new_call_direct_btn_mute);
//            if(MyApplication.getInstance() != null){
//                MyApplication.getsipengine().SetUnHold();
//            }
//        }
//
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().MuteSpk(MuteMic);
//        if (MyApplication.getsipengine() != null ) MyApplication.getsipengine().MuteMic(MuteMic);
//    }
//
//    /**
//     * 是否显示键盘
//     */
//    private void setKeyBoard(){
//        KeyBoardStatus = !KeyBoardStatus;
//        if (KeyBoardStatus) {//打开键盘
//            call_direct_keyboard_iv.setImageResource(R.drawable.call_direct_btn_keyboard_close);
//            call_direct_top.setVisibility(View.GONE);
//            keyboard_in_call.setVisibility(View.VISIBLE);
//            Log.e("*ASTGO*", "open KeyBoard");
//        } else {//关闭键盘
//            call_direct_keyboard_iv.setImageResource(R.drawable.call_direct_btn_keyboard_open);
//            keyboard_in_call.setVisibility(View.GONE);
//            call_direct_top.setVisibility(View.VISIBLE);
//            Log.e("*ASTGO*", "close KeyBoard");
//        }
//
//    }
//    //通话计时器
//    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//
//        @Override
//        public void run() {
//            // handler自带方法实现定时器
//            try {
//
//                if (is_talking == 1)
//                {
//                    close_timer_count = 0;
//                    handler.postDelayed(this, TIME);
//                    long tsLong = System.currentTimeMillis() / 1000;
//                    timelong = tsLong - STARTTIME;
//                    call_direct_state.setText(TimeUtil.formatLongToTimeStr(timelong * 1000));
//                }
//                else if (is_talking == 2)
//                {
//                    close_timer_count ++;
//                    if (close_timer_count < 3)
//                    {
//                        handler.postDelayed(this, TIME);
//                    }
//                    else
//                    {
//                        CallDirectActivity.this.finish();
//
//                    }
//
//                }
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                System.out.println("exception...");
//            }
//        }
//    };
//
//    /**
//     * 用来改写界面通话状态
//     */
//    public void displayStatus(final String msg) {
//        mHandler.post(new Runnable() {
//
//            public void run() {
//                call_direct_state.setText(msg);
//                if("正在振铃".equals(msg)){
//                    String callAdsBg = SPUtils.getString(AppConstant.KEY_CALL_ADS_BG, "");
//                    Glide.with(CallDirectActivity.this).load(callAdsBg).placeholder(R.mipmap.new_call_bg_default).into(ivCallBackBg);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
//
//    @Override
//    public void OnSipEngineState(int code) {
//
//    }
//
//    @Override
//    public void OnRegistrationState(int code, int error_code) {
//
//    }
//
//    @Override
//    public void OnNewCall(int CallDir, String peer_caller, boolean is_video_call) {
//        Log.e(TAG, "OnNewCall---" + CallDir);
//    }
//
//    @Override
//    public void OnCallProcessing() {
//        displayStatus("正在振铃");
//    }
//
//    @Override
//    public void OnCallRinging() {
//        displayStatus("正在振铃");
//    }
//
//    @Override
//    public void OnCallConnected() {
//        displayStatus("正在通话");
//        ringWaterView.stop();
//        start = System.currentTimeMillis();
//        STARTTIME = start / 1000;
//        is_talking = 1;
//        handler.postDelayed(runnable, TIME); // 每隔1s执行
//    }
//
//    @Override
//    public void OnCallStreamsRunning(boolean is_video_call) {
//
//    }
//
//    @Override
//    public void OnCallMediaStreamConnected(int mode) {
//
//    }
//
//    @Override
//    public void OnCallPaused() {
//
//    }
//
//    @Override
//    public void OnCallResuming() {
//
//    }
//
//    @Override
//    public void OnCallPausedByRemote() {
//
//    }
//
//    @Override
//    public void OnCallResumingByRemote() {
//
//    }
//
//    @Override
//    public void OnCallEnded() {
//        if (is_talking == 1) {
//            is_talking = 2;
////            handler.removeCallbacks(runnable);
//            end = System.currentTimeMillis();
//            Log.e("OnCallEnded", "接通后挂断");
//            ContactsOrCallsUtil.addCalls(this, MyConstant.cName, MyConstant.cNum, start, end - start);
//            addCallTag = 1;//表明已添加过通话记录
//        }
//        else if  (is_talking == 0) {
//            ringWaterView.stop();
//            displayStatus("通话结束");
//            is_talking = 2;
//            handler.postDelayed(runnable, TIME); // 每隔1s执行
//            ContactsOrCallsUtil.addCalls(this, MyConstant.cName, MyConstant.cNum, System.currentTimeMillis(), 0);
//            addCallTag = 1;//表明已添加过通话记录
//            Log.e("OnCallEnded", "未接通挂断");
//        }
//    }
//
//    @Override
//    public void OnCallFailed(int status) {
//
//    }
//
//    @Override
//    public void OnNetworkQuality(int ms, String vos_balance) {
//
//    }
//
//    @Override
//    public void OnRemoteDtmfClicked(int dtmf) {
//
//    }
//
//    @Override
//    public void OnCallReport(long nativePtr) {
//
//    }
//}
