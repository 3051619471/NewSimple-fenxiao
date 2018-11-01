package com.astgo.fenxiao.activity.register;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.astgo.fenxiao.R;
import com.astgo.fenxiao.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends FragmentActivity implements View.OnClickListener {

    //    private BaseTitle baseTitle;
    private RadioGroup rgRegMethod;
    private TabLayout tlRegister;
    private ViewPager vpRegister;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

//        baseTitle = (BaseTitle) findViewById(R.id.base_title);
//        baseTitle.setText("选择注册方式");
//        baseTitle.setDLVisibility(false);
//        initRadioGroup();
        initView();
//        initTitleView();
//        replaceFragment(new OtherNewFreeRegisterFragment());
    }

    private void initView() {
        tlRegister = (TabLayout) findViewById(R.id.tl_register);
        vpRegister = (ViewPager) findViewById(R.id.vp_register);
        initFragmentList();
        initTabLayout();
    }



    private void initFragmentList() {
        mTitleList.add("免费注册");
       // mTitleList.add("充值卡注册");
        mFragments.add(new OtherNewFreeRegisterFragment());
       // mFragments.add(new CardRegisterFragment());
    }

    private void initTabLayout() {
        vpRegister.setAdapter(new MyFragmentPagerAdapter(this.getSupportFragmentManager(),mFragments,mTitleList));
        tlRegister.setTabMode(TabLayout.MODE_FIXED);
        tlRegister.setupWithViewPager(vpRegister);
        vpRegister.setCurrentItem(1);
    }

    private void initTitleView() {
        ((TextView) findViewById(R.id.title_tv)).setText(getString(R.string.register));
        ((ImageView) findViewById(R.id.title_left)).setImageResource(R.drawable.back_selector);
        findViewById(R.id.title_left).setOnClickListener(this);
    }


    private void initRadioGroup() {
        rgRegMethod = (RadioGroup) findViewById(R.id.rg_reg_method);
//        rgRegMethod.setOnCheckedChangeListener(this);
        RadioButton rbLeft = (RadioButton) findViewById(R.id.rb_left);
        RadioButton rbRight = (RadioButton) findViewById(R.id.rb_right);
//        rbLeft.setOnClickListener(this);
//        rbRight.setOnClickListener(this);
        // 初始化主页显示的 fragment
        // 如果存在 fragment 容器,加载第一个 fragment 页面
        if (findViewById(R.id.reg_fl_container) != null) {
            initRegFragment();
        }
    }

    // 初始化注册 fragment
    private void initRegFragment() {
        // 程序启动默认加载 DialFragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //这里设置每次启动主页默认显示 HomeFragment
        transaction.add(R.id.reg_fl_container, new FreeRegisterFragment()).commit();
        // 设置对应的 tab 为选中状态
        rgRegMethod.check(R.id.rb_left);
    }

    /**
     * 替换 fragment 方法
     *
     * @param fragment 准备替换的 fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.reg_fl_container, fragment);
//        因为主页取消了回退键的功能，所以切换 fragment 不添加到堆中
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.title_left){
            this.finish();
        }
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.rb_left:  //免费注册
//                if (MyApplication.NEED_VERIFY) {
//                    replaceFragment(new OtherNewFreeRegisterFragment());
//                } else {
//                    replaceFragment(new FreeRegisterFragment());
//                }
//                break;
//            case R.id.rb_right: //充值卡注册
//                replaceFragment(new CardRegisterFragment());
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.rb_left:  //免费注册
//                rgRegMethod.check(R.id.rb_left);
//                break;
//            case R.id.rb_right: //充值卡注册
//                rgRegMethod.check(R.id.rb_right);
//                break;
//            default:
//                break;
//        }
//    }
}

