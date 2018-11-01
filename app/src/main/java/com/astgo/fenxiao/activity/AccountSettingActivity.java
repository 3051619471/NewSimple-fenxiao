package com.astgo.fenxiao.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.AppConstant;
import com.astgo.fenxiao.bean.UpdateBean;
import com.astgo.fenxiao.http.service.HttpClient;
import com.astgo.fenxiao.http.service.HttpResultSubscriber;
import com.astgo.fenxiao.tools.utils.DebugUtil;
import com.astgo.fenxiao.tools.utils.DialogUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;
import com.astgo.fenxiao.update.UpdateAppUtils;
import com.blankj.utilcode.utils.ToastUtils;

import cn.jpush.android.api.JPushInterface;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by ast009 on 2017/11/28.
 */

public class AccountSettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        initView();
    }

    private void initView() {
        initTopTitle();
        Button btnQuiteLogin = (Button) findViewById(R.id.btn_quite_login);
        btnQuiteLogin.setOnClickListener(this);

        RelativeLayout rlPhoneType = (RelativeLayout) findViewById(R.id.rl_phone_type);
        RelativeLayout rlSoundTip = (RelativeLayout) findViewById(R.id.rl_sound_tip);
        RelativeLayout rlModifyPwd = (RelativeLayout) findViewById(R.id.rl_modify_pwd);
        RelativeLayout rlUpdateSoftWare = (RelativeLayout) findViewById(R.id.rl_update_software);

        rlPhoneType.setOnClickListener(this);
        rlSoundTip.setOnClickListener(this);
        rlModifyPwd.setOnClickListener(this);
        rlUpdateSoftWare.setOnClickListener(this);

    }

    private RelativeLayout topTitle;

    private void initTopTitle() {
        topTitle = (RelativeLayout) findViewById(R.id.account_setting_top);
        topTitle.setBackgroundColor(getResources().getColor(R.color.white));
        ImageView titleLeft = (ImageView) topTitle.findViewById(R.id.title_left);
        titleLeft.setImageResource(R.mipmap.icon_back_black);
        TextView titleTv = (TextView) topTitle.findViewById(R.id.title_tv);
        titleTv.setText("系统设置");
        titleTv.setTextColor(getResources().getColor(R.color.theme_black));
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AccountSettingActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_quite_login:
                SPUtils.clear();
//                PreferenceUtil.clear();
                JPushInterface.stopPush(getApplicationContext());
                startActivity(new Intent(getBaseContext(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                break;
            case R.id.rl_phone_type:
                Intent intent1 = new Intent(this, SettingDetailsActivity.class);
                intent1.putExtra(MyConstant.SETTINGS_DETAIL_TAG, MyConstant.SP_SETTINGS_BDFS);
                startActivity(intent1);
                break;
            case R.id.rl_sound_tip:
                Intent intent2 = new Intent(this, SettingDetailsActivity.class);
                intent2.putExtra(MyConstant.SETTINGS_DETAIL_TAG, MyConstant.SP_SETTINGS_AJSY);
                startActivity(intent2);
                break;
            case R.id.rl_modify_pwd:
                Intent intent3 = new Intent(this,ForgotPwdActivity.class);
                intent3.putExtra(AppConstant.TYPE_MODIFY_PWD,ForgotPwdActivity.TYPE_MODIFY_PWD);
                startActivity(intent3);
                break;
            case R.id.rl_update_software:

               // getServerAppinfor();

                break;
        }
    }


//    private void getServerAppinfor() {
//        final Dialog dialog = DialogUtil.getInstance().showDialog(this,"检查更新中");
//        Subscription subscribe = HttpClient.Builder.getAppService().getServerAppVersion()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpResultSubscriber<UpdateBean>() {
//                    @Override
//                    public void onSuccess(UpdateBean updateBean) {
//                        dialog.dismiss();
//                        //TODO 如果需要更新
//                        if(updateBean != null && !TextUtils.isEmpty(updateBean.getVersion())){
//                            getAPPLocalVersion(AccountSettingActivity.this);
//                            if(!localVersionName.equals(updateBean.getVersion())){
//                                updateApp(updateBean.getVersion(),updateBean.getSoft());
//                            }else{
//                                ToastUtils.showLongToast("最新状态不需要更新");
//                            }
//                        }else{
//                            ToastUtils.showLongToast("最新状态不需要更新");
//                        }
//                    }
//
//                    @Override
//                    public void _onError(String msg, int code) {
//                        dialog.dismiss();
//                        DebugUtil.error("msg:"+msg+"-------code"+code);
//                    }
//                });
//        addSubscription(subscribe);
//    }

    //获取apk的版本号 currentVersionCode
    private String localVersionName="";
    private  void getAPPLocalVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateApp(String versionName,String apkPath) {
        UpdateAppUtils.from(this)
                .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
//                .serverVersionCode(2)
                .serverVersionName(versionName)
                .apkPath(apkPath)
                .showNotification(true) //是否显示下载进度到通知栏，默认为true
//                .updateInfo(info)  //更新日志信息 String
//                .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER) //下载方式：app下载、手机浏览器下载。默认app下载
//                .isForce(true) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                .update();

    }

}
