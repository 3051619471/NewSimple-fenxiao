package com.astgo.naoxuanfeng.studyvideo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.astgo.naoxuanfeng.R;

//小视频
public class Small_Video_Activity extends AppCompatActivity implements View.OnClickListener {
    /**
     * <
     */
    private ImageView mSmallFanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_video);
        initView();


    }

    private void initView() {
        mSmallFanhui = (ImageView) findViewById(R.id.small_fanhui);
        mSmallFanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.small_fanhui:
                finish();
                break;
        }
    }
}
