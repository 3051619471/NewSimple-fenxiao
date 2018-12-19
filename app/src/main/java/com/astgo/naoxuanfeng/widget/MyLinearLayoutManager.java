package com.astgo.naoxuanfeng.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by ast009 on 2017/12/13.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//        final int width = RecyclerView.LayoutManager.chooseSize(widthSpec,
//                getPaddingLeft() + getPaddingRight(),
//                ViewCompat.getMinimumWidth(mList));
//        final int height = RecyclerView.LayoutManager.chooseSize(heightSpec,
//                getPaddingTop() + getPaddingBottom(),
//                ViewCompat.getMinimumHeight(mList));
//        setMeasuredDimension(width, height * mTelList.size());
//    }
}
