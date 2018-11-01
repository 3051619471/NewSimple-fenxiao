package com.astgo.fenxiao.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.fenxiao.R;
import com.astgo.fenxiao.tools.DensityUtil;
import com.astgo.fenxiao.tools.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {
    private ViewPager viewPage;
    // 图片  
    private int[] imageView = { R.drawable.guide_01, R.drawable.guide_02,
            R.drawable.guide_03, R.drawable.guide_04 };
    private List<View> list;
    // 底部小点的图片  
    private LinearLayout llPoint;
    //立即进入按钮  
    private TextView textView;
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_guide);


        if (SPUtils.getBoolean("isFirst",true)){
            initview();
            initoper();
            addView();
            addPoint();
        }else {
            startActivity(new Intent(getBaseContext(), LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }

  
    }  
  
    private void initoper() {  
        // 进入按钮  
        textView.setOnClickListener(new View.OnClickListener() {
            @Override  
            public void onClick(View arg0) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }  
        });  
  
        // 2.监听当前显示的页面，将对应的小圆点设置为选中状态，其它设置为未选中状态  
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override  
            public void onPageSelected(int position) {  
                monitorPoint(position);  
            }  
  
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
            }  
  
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
                Log.e("","arg0--" + arg0);
            }  
        });  
  
    }  
  
    private void initview() {  
        viewPage = (ViewPager) findViewById(R.id.viewpage);  
        llPoint = (LinearLayout) findViewById(R.id.llPoint);  
        textView = (TextView) findViewById(R.id.guideTv);  
  
    }  
  
    /**  
     * 添加图片到view  
     */  
    private void addView() {  
        list = new ArrayList<View>();
        // 将imageview添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageView.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageView[i]);
            list.add(iv);
        }
        // 加入适配器  
        viewPage.setAdapter(new GuideViewAdapter(list));  
  
    }  
  
    /**  
     * 添加小圆点  
     */  
    private void addPoint() {  
        // 1.根据图片多少，添加多少小圆点

        for (int i = 0; i < imageView.length; i++) {
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = DensityUtil.dip2px(GuideActivity.this,10);
            pointParams.height = width;
            pointParams.width = width;
            if (i < 1) {
                pointParams.setMargins(0, 0, 0, 0);
            } else {
                pointParams.setMargins(width, 0, 0, 0);
            }
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(pointParams);
            iv.setBackgroundResource(R.drawable.dot_normal);
            llPoint.addView(iv);
        }
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.dot_focused);
  
    }  
  
    /**  
     * 判断小圆点  
     *   
     * @param position  
     */  
    private void monitorPoint(int position) {  
        for (int i = 0; i < imageView.length; i++) {
            if (i == position) {
                llPoint.getChildAt(position).setBackgroundResource(
                        R.drawable.dot_focused);
            } else {
                llPoint.getChildAt(i).setBackgroundResource(
                        R.drawable.dot_normal);
            }
        }
        // 3.当滑动到最后一个添加按钮点击进入，
        if (position == imageView.length - 1) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }


    public class GuideViewAdapter extends PagerAdapter {

        private List<View> list;

        public GuideViewAdapter(List<View> list) {
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

    }


}  