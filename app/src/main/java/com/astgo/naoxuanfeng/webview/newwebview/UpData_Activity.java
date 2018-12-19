package com.astgo.naoxuanfeng.webview.newwebview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import static com.astgo.naoxuanfeng.webview.newwebview.Utils.context;

public class UpData_Activity extends AppCompatActivity implements View.OnClickListener {

    private String fileurl;
    private String downloadfile;
    private Integer number;
    private String version_number;
    private int versionCode;
    private String versionName;
    /**
     * 当前
     */
    private TextView mUpdate;
    /**
     * < 返回
     */
    private TextView mFanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data_);
        initView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //最新版本
        fileurl = bundle.getString("fileurl");
        version_number = bundle.getString("Version_number");
        try {
            versionCode = context.getPackageManager().getPackageInfo(version_number, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("path111111111", version_number + "");
        // number = Integer.valueOf(version_number);
        //老的版本
        downloadfile = bundle.getString("downloadfile");

        checkPermission();
        checkVersion();

    }

    private void checkPermission() {

        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callback(listener)
                .start();

    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {

            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // TODO ...
            }
        }
    };

    private void checkVersion() {
        // int currentVerCode = Utils.getCurrentVerCode();
        try {
            versionName = Utils.getVersionName();


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("sss1", "" + version_number);
        Log.d("sss2", "" + versionName);
        if (version_number.equals(versionName)) {
            mUpdate.setText("当前已是最新版本：" + version_number);
            //ToastUtils.showShortToast("当前已是最新版本：" + version_number);
        } else {
            Intent intent = new Intent(this, UpdateService.class);
            mUpdate.setText("现在的新版本是：" + version_number + "正在更新请耐心等待");
          //  ToastUtils.showShortToast("当前最新版本是：" + version_number + "正在更新请耐心等待");
            // intent.putExtra("apkUrl", "http://app.mi.com/download/63785");
            intent.putExtra("apkUrl", fileurl);
            intent.putExtra("apkUrl", downloadfile);
            startService(intent);
        }
       /* if (versionCode> currentVerCode) {


        }else {


        }*/
    }

    private void initView() {
        mUpdate = (TextView) findViewById(R.id.update);
        mFanhui = (TextView) findViewById(R.id.fanhui);
        mFanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
