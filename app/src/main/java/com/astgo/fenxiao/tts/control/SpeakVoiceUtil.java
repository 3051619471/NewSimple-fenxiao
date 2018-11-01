package com.astgo.fenxiao.tts.control;

import android.content.Context;
import android.media.AudioManager;
import android.os.Environment;
import android.util.Log;

import com.astgo.fenxiao.MyApplication;
import com.astgo.fenxiao.MyConstant;
import com.astgo.fenxiao.tts.util.FileUtil;
import com.astgo.fenxiao.tts.util.OfflineResource;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpeakVoiceUtil {
    // ================== 初始化参数设置开始 ==========================
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */

    protected String appId = "11501703";

    protected String appKey = "GH675tCNL2bKQi0wcEAnmHLG";

    protected String secretKey = "bxvhsL2vw8GEB8WliG8eD3juDmol3vmK";

    /**
     * TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
     */
    private TtsMode ttsMode = TtsMode.ONLINE;


    // ================选择TtsMode.ONLINE  不需要设置以下参数; 选择TtsMode.MIX 需要设置下面2个离线资源文件的路径
//    private static final String TEMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TTS"; // 重要！请手动将assets目录下的3个dat 文件复制到该目录
//
//    // 请确保该PATH下有这个文件
//    private static final String TEXT_FILENAME = TEMP_DIR + "/" + "bd_etts_text.dat";

    // 请确保该PATH下有这个文件 ，m15是离线男声
//    private static final String MODEL_FILENAME =
//            TEMP_DIR + "/" + "bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat";


    /**
     * ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================
     */

    protected SpeechSynthesizer mSpeechSynthesizer;

    // =========== 以下为UI部分 ==================================================

    private static SpeakVoiceUtil speakVoiceUtil;

    public synchronized static SpeakVoiceUtil getInstance(Context context) {
        if (speakVoiceUtil == null) {
            synchronized (SpeakVoiceUtil.class) {
                if (speakVoiceUtil == null) {
                    speakVoiceUtil = new SpeakVoiceUtil(context);
                }
            }
        }
        return speakVoiceUtil;
    }

    public SpeakVoiceUtil(final Context context) {
        initTTs(context);
    }

    /**
     * 注意此处为了说明流程，故意在UI线程中调用。
     * 实际集成中，该方法一定在新线程中调用，并且该线程不能结束。具体可以参考NonBlockSyntherizer的写法
     *
     * @param context
     */
    private void initTTs(Context context) {
        Log.e("slh", "111111111111");
        // 1. 获取实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);

        // 2. 设置listener
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                Log.e("slh", "onSynthesizeStart:" + s);
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

            }

            @Override
            public void onSynthesizeFinish(String s) {
                Log.e("slh", "onSynthesizeFinish:" + s);
            }

            @Override
            public void onSpeechStart(String s) {
                Log.e("slh", "onSpeechStart:" + s);
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {

            }

            @Override
            public void onSpeechFinish(String s) {
                Log.e("slh", "onSpeechFinish:" + s);
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.e("slh", "onError:" + s + ",speechError:" + speechError.description + "," + speechError.code);
            }
        });

        // 3. 设置appId，appKey.secretKey
        int result = mSpeechSynthesizer.setAppId(appId);
        checkResult(result, "setAppId");
        result = mSpeechSynthesizer.setApiKey(appKey, secretKey);
        checkResult(result, "setApiKey");

        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "2");
        // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");

        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer
                .MIX_MODE_HIGH_SPEED_SYNTHESIZE);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);


        // 6. 初始化
        result = mSpeechSynthesizer.initTts(ttsMode);
        checkResult(result, "initTts");

    }


    public void speak(String string) {
        /* 以下参数每次合成时都可以修改
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
         *  设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "5"); 设置合成的音量，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5"); 设置合成的语速，0-9 ，默认 5
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5"); 设置合成的语调，0-9 ，默认 5
         *
         *  mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer
         *  .MIX_MODE_DEFAULT);
         *  MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         *  MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
         */

        if (mSpeechSynthesizer == null) {
            print("[mSpeechSynthesizer == nullERROR], 初始化失败");
            return;
        }
        int result = mSpeechSynthesizer.speak(string);
        print("合成并播放:" + string);
        checkResult(result, "speak");
    }

    private void print(String message) {
        Log.e("slh", message);
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            print("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

//    public void cancelResource() {
//        if (mSpeechSynthesizer != null) {
//            mSpeechSynthesizer.stop();
//            mSpeechSynthesizer.release();
//            mSpeechSynthesizer = null;
//        }
//    }


}