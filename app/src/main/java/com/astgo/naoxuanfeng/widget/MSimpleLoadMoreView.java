package com.astgo.naoxuanfeng.widget;

import com.astgo.naoxuanfeng.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * Created by ast009 on 2017/12/15.
 */

public class MSimpleLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more_view;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
