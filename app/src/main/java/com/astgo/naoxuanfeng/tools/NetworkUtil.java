package com.astgo.naoxuanfeng.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2015/2/2.
 * 网络检查工具类，判断网络是否连接，和Wifi是否连接
 */
public class NetworkUtil {

    public static boolean isConnection(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public static boolean isWifiConnection(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info != null){
            int networkType = info.getType();
            if(networkType == ConnectivityManager.TYPE_WIFI){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
