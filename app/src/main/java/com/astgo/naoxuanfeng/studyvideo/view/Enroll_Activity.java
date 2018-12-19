package com.astgo.naoxuanfeng.studyvideo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.fragment.My_Enroll_Fragment;
import com.astgo.naoxuanfeng.studyvideo.fragment.Name_Enroll_Fragment;

import java.util.ArrayList;
import java.util.List;

public class Enroll_Activity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPage;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        initView();
        initMinum();
        MyAdapterpager myAdapterpager = new MyAdapterpager( getSupportFragmentManager() );
        mViewPage.setAdapter( myAdapterpager );
        mViewPage.setOffscreenPageLimit( list.size() );
        mTabLayout.setupWithViewPager( mViewPage );
    }

    private void initMinum() {
        list = new ArrayList<>( );
        list.add( "报名" );
        list.add( "我的报名" );
    }
    class MyAdapterpager extends FragmentPagerAdapter {
        public MyAdapterpager(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                //报名
                Name_Enroll_Fragment name_enroll_fragment = new Name_Enroll_Fragment();
                return name_enroll_fragment;
            }else if (position==1){
                //我的报名
                My_Enroll_Fragment my_enroll_fragment = new My_Enroll_Fragment();
                return my_enroll_fragment;
            }
                return null;
            }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }



    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout_enroll);
        mViewPage = (ViewPager) findViewById(R.id.viewPage_enroll);
    }
}
