//package com.astgo.fenxiao.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//
///**
// * Created by Administrator on 2016/8/26.
// * 监听拨号在桌面弹出的activity
// */
//public class SelectDialogActivity extends Activity implements View.OnClickListener {
//
//    private String outNumber;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        setContentView(R.layout.activity_selector_dialog);
//        initView();
//        outNumber = getIntent().getStringExtra(MyConstant.OUT_NUMBER);
//        MyApplication.selectDialog = true;
//    }
//
//    private void initView() {
//        findViewById(R.id.select01).setOnClickListener(this);
//        findViewById(R.id.select02).setOnClickListener(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        MyApplication.selectDialog = false;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.select01://本机电话拨号
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + outNumber));
//                startActivity(intent);//调用系统拨号
//                finish();
//                break;
//            case R.id.select02://4G电话拨号
//                ContactsOrCallsUtil.getInstance().mCall(this,  ContactsOrCallsUtil.getInstance().fromPhoneNumToContactsName(outNumber), outNumber);
//                finish();
//                break;
//        }
//    }
//}
