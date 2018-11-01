//package com.astgo.fenxiao;
//
//import android.content.Context;
//import android.database.ContentObserver;
//import android.os.Handler;
//import android.util.Log;
//
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//
//import org.greenrobot.eventbus.EventBus;
//
///**
// * Created by Administrator on 2016/3/16.
// * 联系人表内容观察者类
// */
//public class ContactsLogObserver extends ContentObserver{
//    private static final String TAG = "ContactsLogObserver类";
//    private Context context;
//
//    public ContactsLogObserver(Handler handler, Context context) {
//        super(handler);
//        this.context = context;
//    }
//
//    //当所联系人数据表发生改变时，就会回调此方法
//    @Override
//    public void onChange(boolean selfChange) {
//        super.onChange(selfChange);
//        Log.e("SLH", "onChange");
//        ContactsOrCallsUtil.getInstance().updateContactsData(context);//更新全局储存联系人的全局变量
//        EventBus.getDefault().post("change");
//    }
//}
