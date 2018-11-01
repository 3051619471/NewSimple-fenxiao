package com.astgo.fenxiao.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.BannerInfo;
import com.astgo.fenxiao.tools.DensityUtil;
import com.astgo.fenxiao.tools.WebServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewPagerScrollImage extends FrameLayout {

    private Context context;
    // 用来实现轮播图片
    private ViewPager imageViewPager;
    private ViewPagerAdapter imageAdapter;
    // 用来存放轮播图片和点的list列表
    private List<BannerInfo> bannerInfos;
    private List<View> dots;
    /**
     * 定时周期执行指定的任务
     */
    private ScheduledExecutorService scheduledExecutorService;
    /**
     * 存放点的容器
     */
    private LinearLayout viewGroup;
//	/** viewpager的标题 */
//	private TextView viewpagerTitle;
    /**
     * 轮播到哪个界面
     */
    private int currentItemIamge;
    /**
     * 记录上一次点的位置
     */
    private int oldPosition = 0;

    public ViewPagerScrollImage(Context context) {
        this(context, null, 0);
    }

    public ViewPagerScrollImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerScrollImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        // 在构造函数中将Xml中定义的布局解析出来。
        LayoutInflater.from(context).inflate(R.layout.message_viewpager_scroll_image, this,
                true);
        imageViewPager = (ViewPager) findViewById(R.id.vp_image);
//		viewpagerTitle = (TextView) findViewById(R.id.vp_title);
        viewGroup = (LinearLayout) findViewById(R.id.dot_layout);
    }

    @Override
    protected void onFinishInflate() {
//		Log.d("ViewPagerScroll", "onFinishInflate");
        super.onFinishInflate();
    }

    /**
     * 加载view界面
     */
    public void setViewData(List<BannerInfo> bannerInfos) {
        this.bannerInfos = bannerInfos;
        addViewDot(context, bannerInfos.size());
        imageAdapter = new ViewPagerAdapter(bannerInfos, context);
        imageViewPager.setAdapter(imageAdapter);
        imageViewPager.setOnPageChangeListener(new PagerListener(1));
    }

    public void startAutoScroll() {
//        if(bannerInfos.size() != 0){
        stopAutoScroll();
        currentItemIamge = 0;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(1), 5, 5, TimeUnit.SECONDS);
//        }
    }

    public void stopAutoScroll() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }

    /**
     * 添加图片下端的点
     *
     * @param context 上下文
     * @param size    点的个数
     */
    private void addViewDot(Context context, int size) {
        viewGroup.removeAllViews();
        // 计算出view的长和高(dp---px)
        int dotsSize = DensityUtil.dip2px(context, 5);
        // 计算出点之间的距离
        int dotsMargin = DensityUtil.dip2px(context, 3);
        dots = new ArrayList<View>();
        for (int i = 0; i < size; i++) {
            View view = new View(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    dotsSize, dotsSize);
            layoutParams.leftMargin = dotsMargin;
            layoutParams.rightMargin = dotsMargin;
            // view.setLayoutParams(new LayoutParams(10, 10));
            view.setLayoutParams(layoutParams);
            dots.add(view);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.dot_focused);
            } else {
                view.setBackgroundResource(R.drawable.dot_normal);
            }
            viewGroup.addView(view);
        }
    }

    // 用来实现滑动效果
    class PagerListener implements OnPageChangeListener {
        // 区别标识
        private int tag;


        public PagerListener(int tag) {
            this.tag = tag;
        }

        /**
         * @param arg0 此方法是在状态改变的时候调用 arg0==1的时辰默示正在滑动 arg0==2的时辰默示滑动完毕了
         *             arg0==0的时辰默示什么都没做
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        /**
         * 当页面在滑动的时候会调用此方法
         *
         * @param arg0 :当前页面，即你点击滑动的页面
         * @param arg1 :当前页面偏移的百分比
         * @param arg2 :当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        /**
         * 此方法是页面跳转完后得到调用
         *
         * @param position :当前界面的position
         */
        @Override
        public void onPageSelected(int position) {
            if (tag == 1) {
                if (bannerInfos.size() > 0 && dots.size() > 0) {
                    position = position % bannerInfos.size();
                    dots.get(position).setBackgroundResource(
                            R.drawable.dot_focused);
                    dots.get(oldPosition).setBackgroundResource(
                            R.drawable.dot_normal);

                    oldPosition = position;
                }
//				if (titles != null) {
//					viewpagerTitle.setText(titles[position]);
//				}
                // currentItem = position;
            }

        }

    }

    /**
     * 控制轮播的线程
     */
    private class ViewPageTask implements Runnable {
        // 区别标识
        private int tag;

        public ViewPageTask(int tag) {
            this.tag = tag;
        }

        @Override
        public void run() {
            if (tag == 1) {
//				currentItemIamge = (currentItemIamge + 1) % size;
                currentItemIamge++;
            }
            mHandler.sendEmptyMessage(tag);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (bannerInfos != null && bannerInfos.size() != 0) {
                        imageViewPager.setCurrentItem(currentItemIamge % bannerInfos.size());
                    }
                    break;
            }
        }

        ;
    };

    class ViewPagerAdapter extends PagerAdapter {

        private List<BannerInfo> bannerInfos;
        private Context context;
        private List<View> views;

        public ViewPagerAdapter(List<BannerInfo> bannerInfos, Context context) {
            // Log.d("ViewPagerAdapter", views.size()+"");
            this.context = context;
            this.bannerInfos = bannerInfos;
            views = new ArrayList<View>();
            if (bannerInfos.isEmpty() || bannerInfos.size() <= 0) {
                ImageView iv = new ImageView(context);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setImageResource(R.mipmap.icon_banner_loadding);
//                WebServiceUtil.setWebImage(context, iv, "");
                views.add(iv);
            } else {
                for (int i = 0; i < bannerInfos.size(); i++) {
                    ImageView iv = new ImageView(context);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    WebServiceUtil.setWebImage(context, iv, bannerInfos.get(i).getImg_url());
                    views.add(iv);
                }
            }
        }

        @Override
        public int getCount() {
            return views.size();
            // return Integer.MAX_VALUE;
        }

        // 这个方法用于判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // 这个方法，是从ViewGroup中移出当前View
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Log.d("destroyItem", position+"");
            container.removeView(views.get(position % views.size()));
        }

        // 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Log.d("instantiateItem", position+"");
            View v = views.get(position % views.size());
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(v);
            v.setOnClickListener(new ViewOnClickListener(position));
            return v;
        }

        /**
         * 监听ViewPager中View的点击事件
         */
        class ViewOnClickListener implements OnClickListener {
            private int position;

            public ViewOnClickListener(int position) {
                this.position = position % views.size();
            }

            @Override
            public void onClick(View arg0) {
//            Toast.makeText(context, bannerInfos.get(position).getBannerLink(), Toast.LENGTH_SHORT)
//                    .show();
//                WebServiceUtil.transNewActivity(context, bannerInfos.get(position).getBannerLink(), "");
            }

        }

    }
}
