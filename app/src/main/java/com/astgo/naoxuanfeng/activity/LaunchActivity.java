package com.astgo.naoxuanfeng.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 这是应用启动的倒计时的过场界面，可以展示自定义的图片
 * 如果集成了第三方的广告，可以在这里添加相应的开屏展示效果
 */
public class LaunchActivity extends AppCompatActivity {

    private static final String TAG = "开屏界面";

    @Override
    public void onBackPressed() {
        // 取消回退键功能
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getBaseContext(), GuideActivity.class));
        finish();
    }

}
