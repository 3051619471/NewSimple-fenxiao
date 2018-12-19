package com.astgo.naoxuanfeng.webview.newwebview;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.astgo.naoxuanfeng.MyApplication;
import com.astgo.naoxuanfeng.R;

/**
 * Created by hasee on 2018/11/27.
 */

public class Utils  {

    public static Context context = MyApplication.getContext();

    /**
     * 获取应用当前版本代码
     *
     * @return
     */
   /* public static int getCurrentVerCode() {
        String packageName = context.getPackageName();
        int currentVer = -1;
        try {
            currentVer = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVer;
    }*/
    public static String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 获取应用当前版本名称
     *
     * @return
     */
    public static String getCurrentVerName() {
        String packageName = context.getPackageName();
        String currentVerName = "";
        try {
            currentVerName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVerName;
    }

    /**
     * 获取应用名称
     *
     * @return
     */
    public static String getAppName() {
        return context.getResources().getText(R.string.app_name).toString();
    }

    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName() {
        return MyApplication.getContext().getPackageName();
    }
}