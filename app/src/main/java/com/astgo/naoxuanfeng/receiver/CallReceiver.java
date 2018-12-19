//package com.astgo.fenxiao.receiver;
//
//import android.content.ContentResolver;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.ContentObserver;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Handler;
//import android.provider.CallLog;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//import android.view.KeyEvent;
//
//import com.android.internal.telephony.ITelephony;
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.activity.call.CallBackActivity;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//
//import java.lang.reflect.Method;
//
///**
// * Created by Administrator on 2016/6/1.
// * 来电去电Receiver的实现
// */
//public class CallReceiver extends PhoneCallReceiver {
//    private static final String TAG = "CallReceiver";
//    private static String phoneNumber;
//    private ITelephony iTelephony;
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//    }
//
//    @Override
//    protected void onIncomingCallReceived(Context ctx, String number, long start) {
//        Log.e(TAG, "收到来电-----" + number);
//        if (MyApplication.CBTAG && MyApplication.ANWSERTAG) {
//            MyApplication.getInstance().setANWSERTAG(false);//关闭自动接听标记
//            //获取呼入号码
//            phoneNumber = number;
//            MyConstant.cBnum = number;//把号码保存再全局变量中
//            Log.e(TAG,"保存来电电话号码：cBnum"+MyConstant.cBnum);
//            try {//自动接听
//                if (Build.VERSION.SDK_INT >= 21) {
////                    Intent intent = new Intent(ctx, AcceptCallActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
////                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
////                    ctx.startActivity(intent);
//                    return;
//                } else {
////                    autoAnswerPhone();
//                    return;
//                }
//            } catch (Exception e) {
//                Log.e(TAG, "[Broadcast]Exception=" + e.getMessage(), e);
//            }
//        }
//    }
//
//    @Override
//    protected void onIncomingCallAnswered(Context ctx, String number, long start) {
//        Log.e(TAG, "接通来电-----" + number);
//        Log.e(TAG, "接通来电-----" + start);
//        MyConstant.cBnum = phoneNumber;//把号码保存再全局变量中
////        if (MyApplication.CBTAG) {
////            MyApplication.mhandler.removeCallbacks(MyApplication.cbRunnable);//取消自动重置监听标签
////        }
//        if (CallBackActivity.call_back_state != null) {
//            CallBackActivity.displayStatus("正在通话");
//            CallBackActivity.displayStatus(start);
//
//        }
//
//    }
//
//    @Override
//    protected void onIncomingCallEnded(Context ctx, String number, long start, long end) {
//
//        Log.e(TAG, "挂断来电-----" + number);
//        if (MyApplication.CBTAG) {//如果是网络回拨
//            MyApplication.CBTAG = false;
//            try {
//                if (MyConstant.cBnum != null && !"".equals(MyConstant.cBnum)) {
//                    if(!MyConstant.cBnum .equals(MyConstant.cNum) ){
//                        deleteFromCallLog(MyConstant.cBnum);
//                    }
//                }
//            } finally {
//                Log.i(TAG, "[Broadcast]电话挂断=count" + MyConstant.cName);
//                Log.i(TAG, "[Broadcast]电话挂断=oNum" + MyConstant.cNum);
//                CallBackActivity.instance.finish();
//                if (MyConstant.cName != null) {
////                    addCalls(start, (end - start) / 1000, MyConstant.cNum, ctx);//添加拨出号码记录
//                    ContactsOrCallsUtil.addCalls(ctx, MyConstant.cName, MyConstant.cNum, System.currentTimeMillis(),(end - start) / 1000);
//                }
//                CallBackActivity.stopStatus();//关闭计时器
//            }
//        }
//        MyConstant.cName = "";
//        MyConstant.cNum = "";
//        MyConstant.cBnum = "";
//
//    }
//
//    @Override
//    protected void onOutgoingCallStarted(Context ctx, String number, long start) {
//        Log.e(TAG, "开始去电-----" + number);
//////        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
////        TelephonyManager telephonyMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
////        try {
////            if(MyApplication.isBackgroud() && !MyApplication.selectDialog && MyApplication.mSpInformation.getBoolean(MyConstant.SP_LOGIN_STATE, false)) {
////                Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
////                getITelephonyMethod.setAccessible(true);
////                iTelephony = (ITelephony) getITelephonyMethod.invoke(telephonyMgr, (Object[]) null);
////                //挂断电话
////                iTelephony.endCall();
////                Intent mIntent = new Intent();
////                mIntent.setClass(ctx, SelectDialogActivity.class);
////                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                mIntent.putExtra(MyConstant.OUT_NUMBER, number);
////                ctx.startActivity(mIntent);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    @Override
//    protected void onOutgoingCallEnded(Context ctx, String number, long start, long end) {
//        Log.e(TAG, "结束去电-----" + number);
//    }
//
//    @Override
//    protected void onMissedCall(Context ctx, String number, long start) {
//        Log.e(TAG, "未接来电-----" + number);
//        MyApplication.CBTAG = false;
//
//        if (MyConstant.cBnum != null && !"".equals(MyConstant.cBnum)) {
//            if(!MyConstant.cBnum .equals(MyConstant.cNum) ){
//                deleteFromCallLog(MyConstant.cBnum);
//            }
//        }
//
//    }
//
//    /**
//     * 自动接听来电
//     */
//    public void autoAnswerPhone() {
//        // 初始化iTelephony
//        Class<TelephonyManager> c = TelephonyManager.class;
//        Method getITelephonyMethod = null;
//        try {
//            // 获取所有public/private/protected/默认
//            // 方法的函数，如果只需要获取public方法，则可以调用getMethod.
//            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
//            // 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
//            // 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
//            // 则指示反射的对象应该实施 Java 语言访问检查。
//            getITelephonyMethod.setAccessible(true);
//        } catch (SecurityException | NoSuchMethodException e) {
//            // Toast.makeText(MyApplication.context,"安全异常："+e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//
//        try {
//            Log.i(TAG, "autoAnswerPhone");
//
//            TelephonyManager mTelephonyManager;
//            mTelephonyManager = (TelephonyManager) MyApplication.mApplicationContext
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//
//            assert getITelephonyMethod != null;
//            ITelephony itelephony = (ITelephony) getITelephonyMethod.invoke(
//                    mTelephonyManager, (Object[]) null);
//            // 停止响铃
//            itelephony.silenceRinger();
//
//            // itelephony.silenceRinger();
//            itelephony.answerRingingCall();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            try {
//                Log.e(TAG, "用于Android2.3及2.3以上的版本上 4.0   以下");
//
//                // 插耳机
//                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
//                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                localIntent1.putExtra("state", 1);
//                localIntent1.putExtra("microphone", 1);
//                localIntent1.putExtra("name", "Headset");
//                MyApplication.mApplicationContext.sendOrderedBroadcast(localIntent1, "android.permission.CALL_PRIVILEGED");
//                // 按下耳机按钮
//                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
//                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
//                localIntent2.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent1);
//                MyApplication.mApplicationContext.sendOrderedBroadcast(localIntent2, "android.permission.CALL_PRIVILEGED");
//                // 放开耳机按钮
//                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
//                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//                localIntent3.putExtra("android.intent.extra.KEY_EVENT", localKeyEvent2);
//                MyApplication.mApplicationContext.sendOrderedBroadcast(localIntent3, "android.permission.CALL_PRIVILEGED");
//                // 拔出耳机
//                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
//                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                localIntent4.putExtra("state", 0);
//                localIntent4.putExtra("microphone", 1);
//                localIntent4.putExtra("name", "Headset");
//                MyApplication.mApplicationContext.sendOrderedBroadcast(localIntent4, "android.permission.CALL_PRIVILEGED");
//
//            } catch (Exception e2) {
//                e2.printStackTrace();
//                answerRingingCall_ex();
//            }
//        }
//
//    }
//
//    public synchronized void answerRingingCall_ex() {// effect 4.1 or later
//        try {
//            Log.i(TAG, "MyPhoneStateListener->answerRingingCall_ex");
//
//            Log.i("MyPhoneStateListener", "for version 4.1 or larger");
//            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
//            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
//            MyApplication.mApplicationContext.sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
//        } catch (Exception e2) {
//            Log.i("MyPhoneStateListener", "", e2);
//            Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//            mediaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
//            MyApplication.mApplicationContext.sendOrderedBroadcast(mediaButtonIntent, null);
//        }
//
//    }
//
//    // 操作通话记录(添加)
//    public void addCalls(long date, long duration, String number, Context context) {
//        Log.i(TAG, "添加呼叫记录:" + number);
//        ContentValues cv = new ContentValues();
//        cv.put(CallLog.Calls.NUMBER, number);
//        cv.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
//        cv.put(CallLog.Calls.DATE, date);
//        cv.put(CallLog.Calls.DURATION, duration);
//        //保存
//        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, cv);
//    }
//
//    public void deleteFromCallLog(String incomingNumber) {
//        Log.i(TAG, "删除呼叫记录:" + incomingNumber);
//        Uri uri = Uri.parse("content://call_log/calls");// 得到呼叫记录内容提供者的路径
//        if (incomingNumber != null && !"".equals(incomingNumber)) {
////            Cursor cursor = MyApplication.mApplicationContext.getContentResolver().query(uri, new String[]{"_id"},
////                    "number=?", new String[]{incomingNumber}, null);
////            while (cursor.moveToNext()) {
////                String id = cursor.getString(0);
////                MyApplication.mApplicationContext.getContentResolver().delete(uri, "_id=?", new String[]{id});
////            }
////            cursor.close();
////            Log.i(TAG, "删除呼叫记录成功:" );
//
//
//            ContentResolver resolver = MyApplication.mApplicationContext.getContentResolver();
//            resolver.delete(CallLog.Calls.CONTENT_URI, "number=?", new String[]{incomingNumber});
//            resolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, new CallLogObserver(incomingNumber, new Handler()));
//            Log.i(TAG, "删除呼叫记录成功:" );
//        }
//    }
//
//    class CallLogObserver extends ContentObserver {
//        private String number;
//
//        public CallLogObserver(String number, Handler handler) {
//            super(handler);
//            this.number = number;
//        }
//
//        /**
//         * 如果通话记录发生改变
//         *
//         * @param selfChange
//         */
//        @Override
//        public void onChange(boolean selfChange) {
//            super.onChange(selfChange);
//            deleteFromCallLog(number);
//        }
//    }
//
//}
