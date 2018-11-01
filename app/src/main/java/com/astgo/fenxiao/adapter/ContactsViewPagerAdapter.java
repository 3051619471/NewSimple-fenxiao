//package com.astgo.fenxiao.adapter;
//
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.astgo.fenxiao.activity.ContactsDetailsActivity;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2016/2/22.
// * ContactsDetails中viewPager的适配器
// */
//public class ContactsViewPagerAdapter extends PagerAdapter {
//    public List<View> mListViews;
//
//    public ContactsViewPagerAdapter(List<View> mListViews) {
//        this.mListViews = mListViews;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mListViews.get(position));
//    }
//
//
//    @Override
//    public int getCount() {
//        return mListViews.size();
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        container.addView(mListViews.get(position), 0);
//        mListViews.get(position).setTag(position);
//        return mListViews.get(position);
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        View view = (View)object;
//        int currentPage = ContactsDetailsActivity.getCurrentPagerIdx(); // Get current page index
//        if(currentPage == (Integer)view.getTag()){
//            return POSITION_NONE;
//        }else{
//            return POSITION_UNCHANGED;
//        }
//    }
//
//    @Override
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        return arg0 == arg1;
//    }
//}
