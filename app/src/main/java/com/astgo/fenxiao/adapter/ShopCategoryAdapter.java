package com.astgo.fenxiao.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.CategoryBean;

import java.util.List;

/**
 * Created by ast009 on 2017/12/13.
 */

public class ShopCategoryAdapter extends BaseQuickAdapter<CategoryBean.DataBean,BaseViewHolder> {


    public ShopCategoryAdapter(@LayoutRes int layoutResId, @Nullable List<CategoryBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean.DataBean item) {

        if(item != null){
            ImageView ivCatoegory = helper.getView(R.id.iv_item);
            Glide.with(mContext).load(item.getImg()).into(ivCatoegory);

            TextView tvCatoegory = helper.getView(R.id.tv_item);
            tvCatoegory.setText(item.getTitle());
        }
    }
}
