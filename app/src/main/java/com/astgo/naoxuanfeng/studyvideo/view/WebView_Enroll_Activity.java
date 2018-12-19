package com.astgo.naoxuanfeng.studyvideo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.zzhoujay.richtext.RichText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WebView_Enroll_Activity extends AppCompatActivity implements View.OnClickListener {

    private String content;

    /**
     * 返回
     */
    private TextView mFanhui;
    ConstantURL constantURL = new ConstantURL();
    String ip_url = constantURL.IP_url;
    private TextView mTvHeadlineContent;
    private String contentde;
    private String decode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_enroll);
        RichText.initCacheDir(this);
        Intent intent = getIntent();
        initView();
        content = intent.getStringExtra("content");
        try {
            decode = URLDecoder.decode(content, "UTF-8");
            RichText into = RichText.fromHtml(decode).into(mTvHeadlineContent);
            Log.d("rzj1",decode+"");
            Log.d("rzj2",into+"");
           // mTvHeadlineContent.setText(decode);
           //System.out.print("1111");
          //  RichText.from(decode).bind(this).showBorder(false).size(ImageHolder.MATCH_PARENT,ImageHolder.WRAP_CONTENT).into(mTvHeadlineContent);
// 设置为Html

// 设置为Markdown
            //RichText.fromMarkdown(decode).into(mTvHeadlineContent);
           // RichText.from(decode).type(RichText.TYPE_MARKDOWN).into(mTvHeadlineContent);
        } catch (UnsupportedEncodingException e) {

        }
        }

    private void initView() {

        mFanhui = (TextView) findViewById(R.id.fanhui);
        mFanhui.setOnClickListener(this);
        mTvHeadlineContent = findViewById(R.id.tv_headline_content);

    }


   @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
    }


}
