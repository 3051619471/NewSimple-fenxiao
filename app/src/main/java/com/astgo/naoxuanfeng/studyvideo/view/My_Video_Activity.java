package com.astgo.naoxuanfeng.studyvideo.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.video_fragment.My_Upload_Fragment;
import com.astgo.naoxuanfeng.studyvideo.video_fragment.Release_Fragment;
import com.astgo.naoxuanfeng.studyvideo.video_fragment.Small_Fragment;
import com.astgo.naoxuanfeng.studyvideo.video_fragment.Whale_Fragment;

import java.util.ArrayList;
import java.util.List;

//我的视频
public class My_Video_Activity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout myTab;
    private ViewPager viewPager;
    private List<String> meuns;
    /**
     * <
     */
    private ImageView mMyFanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        initView();
        myTab = findViewById(R.id.myTab);
        viewPager = findViewById(R.id.vp);
        initMenus();
        MyAdaprwr myAdaprwr = new MyAdaprwr(getSupportFragmentManager());
        viewPager.setAdapter(myAdaprwr);
        viewPager.setOffscreenPageLimit(meuns.size());
        myTab.setupWithViewPager(viewPager);
    }

    private void initMenus() {
        meuns = new ArrayList<>();
        meuns.add("全部");
        meuns.add("我的上传");
        meuns.add("小视频");
        meuns.add("发布");
    }

    private void initView() {
        mMyFanhui = (ImageView) findViewById(R.id.my_fanhui);
        mMyFanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_fanhui:
                finish();
                break;
        }
    }

    class MyAdaprwr extends FragmentPagerAdapter {

        public MyAdaprwr(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Whale_Fragment whale_fragment = new Whale_Fragment();
                return whale_fragment;
            } else if (position == 1) {
                My_Upload_Fragment my_upload_fragment = new My_Upload_Fragment();
                return my_upload_fragment;
            } else if (position == 2) {
                Small_Fragment small_fragment = new Small_Fragment();
                return small_fragment;
            } else if (position == 3) {
                Release_Fragment release_fragment = new Release_Fragment();
                return release_fragment;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return meuns.get(position);
        }

        @Override
        public int getCount() {
            return meuns.size();
        }
    }
}
