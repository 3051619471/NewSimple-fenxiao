package com.astgo.fenxiao.tools;

import android.content.SharedPreferences;

import com.astgo.fenxiao.MyApplication;

/**
 * Created by Administrator on 2015/2/6.
 *
 */
public class PreferenceUtil {

    public static void setValue(String key, String value) {
        SharedPreferences.Editor editor = MyApplication.mSpInformation.edit();
        editor.putString(key, value);
        //        editor.commit();
        editor.apply();
    }

    public static void setValue(String key, int value) {
        SharedPreferences.Editor editor = MyApplication.mSpInformation.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setValue(String key, boolean value) {
        SharedPreferences.Editor editor = MyApplication.mSpInformation.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setValue(String key, float value) {
        SharedPreferences.Editor editor = MyApplication.mSpInformation.edit();
        editor.putFloat(key, value);
        editor.apply();
    }


    /**
     * 获取String的value
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = MyApplication.mSpInformation;
        return sharedPreference.getString(key, defValue);
    }


    /**
     * 获取int的value
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreference = MyApplication.mSpInformation;
        return sharedPreference.getInt(key, defValue);
    }




    public static void clear() {
        SharedPreferences.Editor editor = MyApplication.mSpInformation.edit();
        editor.clear().apply();
    }

}
