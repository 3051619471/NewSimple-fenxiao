package com.astgo.naoxuanfeng.update;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.utils.ToastUtils;


/**
 * Created by Teprinciple on 2016/11/15.
 */
public class UpdateAppUtils {

    private final String TAG = "UpdateAppUtils";
    public static final int CHECK_BY_VERSION_NAME = 1001;
    public static final int CHECK_BY_VERSION_CODE = 1002;
    public static final int DOWNLOAD_BY_APP = 1003;
    public static final int DOWNLOAD_BY_BROWSER = 1004;

    private Activity activity;
    private int checkBy = CHECK_BY_VERSION_CODE;
    private int downloadBy = DOWNLOAD_BY_APP;

    private String apkPath="";
    private int serverVersionCode = 0;
    private String serverVersionName="";
    private int localVersionCode = 0;
    private String localVersionName="";
    private boolean isForce = false; //是否强制更新

    public static boolean needFitAndroidN = true; //提供给 整个工程不需要适配到7.0的项目 置为false
    public static boolean showNotification = true;
    private String updateInfo = "";




    public UpdateAppUtils needFitAndroidN(boolean needFitAndroidN) {
        UpdateAppUtils.needFitAndroidN = needFitAndroidN;
        return this;
    }

    private UpdateAppUtils(Activity activity) {
        this.activity = activity;
        getAPPLocalVersion(activity);
    }

    public static UpdateAppUtils from(Activity activity){
        return new UpdateAppUtils(activity);
    }

    public UpdateAppUtils checkBy(int checkBy){
        this.checkBy = checkBy;
        return this;
    }

    public UpdateAppUtils apkPath(String apkPath){
        this.apkPath = apkPath;
        return this;
    }

    public UpdateAppUtils downloadBy(int downloadBy){
        this.downloadBy = downloadBy;
        return this;
    }

    public UpdateAppUtils showNotification(boolean showNotification){
        this.showNotification = showNotification;
        return this;
    }

    public UpdateAppUtils updateInfo(String updateInfo){
        this.updateInfo = updateInfo;
        return this;
    }



    public UpdateAppUtils serverVersionCode(int serverVersionCode){
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    public UpdateAppUtils serverVersionName(String  serverVersionName){
        this.serverVersionName = serverVersionName;
        return this;
    }

    public UpdateAppUtils isForce(boolean  isForce){
        this.isForce = isForce;
        return this;
    }

    //获取apk的版本号 currentVersionCode
    private  void getAPPLocalVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = info.versionName; // 版本名
            localVersionCode = info.versionCode; // 版本号

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(){

        switch (checkBy){
            case CHECK_BY_VERSION_CODE:
                Log.d("RZJCODE","CODE");
                if (serverVersionCode >localVersionCode){
                    toUpdate();
                }else {
                    Log.i(TAG,"当前版本是最新版本"+serverVersionCode+"/"+serverVersionName);
                }
                break;

            case CHECK_BY_VERSION_NAME:
                Log.d("RZJCNAMEE","NAME");
                if (TextUtils.isEmpty(serverVersionName)){
                    ToastUtils.showShortToast("服务器版本异常！");
                    Log.d("RZ1","123");
                    return;
                }
                Log.d("RZ2","123");
                long versionNameServer = Long.parseLong(serverVersionName.replace(".",""));
                Log.d("RZ3",serverVersionName+"1");
                Log.d("RZ3333",localVersionName+"1");
                long versionNameLocal = Long.parseLong(localVersionName.replace(".",""));
                Log.d("RZ4",localVersionName+"123");
                if (versionNameServer>versionNameLocal){
                    Log.d("RZ5","123");
                    toUpdate();
                    Log.d("RZ6","123");
                }else {
                    Log.d("RZ7","123");
                    ToastUtils.showShortToast("当前版本是最新版本"+localVersionName);
                    Log.i(TAG,"当前版本是最新版本"+serverVersionCode+"/"+serverVersionName);
                    Log.d("RZ8","123");
                }
                break;
        }

    }

    private void toUpdate() {
        Log.d("RZ9","123");
        realUpdate();
        Log.d("RZ10","123");
    }

    private void realUpdate() {

        ConfirmDialog dialog = new ConfirmDialog(activity, new Callback() {
            @Override
            public void callback(int position) {
                switch (position){

                    case 0:  //cancle
                        Log.d("RZ11","123");
                        if (isForce)System.exit(0);
                        Log.d("RZ12","123");
                        break;

                    case 1:  //sure
                        if (downloadBy == DOWNLOAD_BY_APP) {
                            Log.d("RZ13","123");
                            if (isWifiConnected(activity)){
//                                DownloadAppUtils.downloadForAutoInstall(activity, apkPath, "demo.apk", serverVersionName);
                                DownloadAppUtils.download(activity, apkPath, serverVersionName);
                            }else {
                                new ConfirmDialog(activity, new Callback() {
                                    @Override
                                    public void callback(int position) {
                                        if (position==1){
                                            Log.d("RZ14","123");
                                            DownloadAppUtils.download(activity, apkPath, serverVersionName);
                                            //DownloadAppUtils.downloadForAutoInstall(activity, apkPath, "demo.apk", serverVersionName);
                                        }else {
                                            if (isForce)activity.finish();
                                        }
                                    }
                                }).setContent("目前手机不是WiFi状态\n确认是否继续下载更新？").show();
                            }

                        }else if (downloadBy == DOWNLOAD_BY_BROWSER){
                            Log.d("RZ15","123");
                            DownloadAppUtils.downloadForWebView(activity,apkPath);
                        }
                        break;
                }
            }
        });
        Log.d("RZ16","123");
        String content = "发现新版本:"+serverVersionName+"\n是否下载更新?";
        Log.d("RZ17","123");
        if (!TextUtils.isEmpty(updateInfo)){
            content = "发现新版本:"+serverVersionName+"是否下载更新?\n\n"+updateInfo;
            Log.d("RZ18","123");
        }
        dialog .setContent(content);
        dialog.setCancelable(false);
        dialog.show();
    }


    /**
     * 检测wifi是否连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }


}