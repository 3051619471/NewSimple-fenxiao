package com.astgo.fenxiao.video;

import java.util.regex.Pattern;

/**
 * Created by ast009 on 2018/1/20.
 */

public class CommonUrlUtils {
    public static final int QQVideoURL = 1;
    public static final int IQiYiVideoUrl = 2;
    public static final int YouKuVideoUrl = 3;
    public static final int PPVideoUrl = 4;
    public static boolean isQQVideoUrl(String paramString) {//http://m.v.qq.com //v.qq.com/x/cover
        return (paramString.contains("://m.v.qq.com/x/cover"))
                || (paramString.contains("://m.v.qq.com/cover"))
                || (paramString.contains("://m.v.qq.com/play"))
                ||(paramString.contains("://m.v.qq.com/x/page"));
    }

    public static boolean isIQiYiVideoUrl(String paramString) {
        return (paramString.contains("://m.iqiyi.com/v_")) || (paramString.contains("://m.iqiyi.com/w_"));
    }

    public static boolean isYouKuVideoUrl(String paramString) {
        //http://m.youku.com/video/id_XMzQ4MDUyMzUwNA==.html?spm=a2hmv.20009921.m_86993.5~5!2~5~5~5~5~5~A
        //https://m.youku.com/video/id_XMzQ4MDUyMzUwNA==.html?spm=a2hmv.20009921.m_86993.5~5!2~5~5~5~5~5~A&ishttps=1
        return paramString.contains("//m.youku.com/video/id_");
    }

    public static boolean isPPVideoUrl(String paramString) {
        return paramString.contains("://m.pptv.com/show/");
    }

    public static boolean isLetvVideoUrl(String paramString) {

        return paramString.contains("://m.le.com/vplay_");
    }

    public static boolean is1905Movie(String paramString)
    {
        return (paramString.contains(".1905.com/"));
    }
    public static boolean is1905Movie2(String paramString)
    {
        return (paramString.contains(".1905.com/")) && (paramString.contains("play"));
    }
    public static boolean isMgTV(String paramString)
    {
        return (paramString.contains("m.mgtv.com/b")&&!paramString.endsWith("0.html"));
    }

    public static int getUrlType(String url) {
        if (isQQVideoUrl(url)){
            return QQVideoURL;
        }else if (isIQiYiVideoUrl(url)) {
            return IQiYiVideoUrl;
        }else if (isYouKuVideoUrl(url)){
            return YouKuVideoUrl;
        }else if (isPPVideoUrl(url)){
            return PPVideoUrl;
        }
        return 0;
    }
}
