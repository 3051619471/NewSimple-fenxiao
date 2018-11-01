package com.astgo.fenxiao.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.ShopListBean;

import java.util.List;

/**
 * Created by ast009 on 2017/12/13.
 */

public class CommodityAdapter extends BaseQuickAdapter<ShopListBean.DataBean.ListBean,BaseViewHolder> {

    public boolean isTicket;


    public boolean isTicket() {
        return isTicket;
    }

    public void setTicket(boolean ticket) {
        isTicket = ticket;
    }

    public CommodityAdapter(@LayoutRes int layoutResId, @Nullable List<ShopListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListBean.DataBean.ListBean item) {
        if(item != null){
            ImageView ivCommodityImg = helper.getView(R.id.iv_commodity_img);
            TextView tvTitle = helper.getView(R.id.tv_title);
            TextView tvPrice = helper.getView(R.id.tv_price);
            TextView tvCoupon = helper.getView(R.id.tv_coupon);
            TextView tvSale = helper.getView(R.id.tv_sale);
            TextView tv_is_ticket_buy = helper.getView(R.id.tv_is_ticket_buy);
            if (isTicket){
                tv_is_ticket_buy.setText("兑换购买");
            }else {
                tv_is_ticket_buy.setText("立即购买");
            }
            Glide.with(mContext).load(item.getImg()).into(ivCommodityImg);
            tvTitle.setText(item.getTitle());
            tvPrice.setText("淘宝价: ¥"+item.getPrice());
            tvCoupon.setText(item.getCoupon());
            tvSale.setText("销量: "+item.getSale());

            switch (helper.getItemViewType()) {
                case LOADING_VIEW:
                    ((TextView) helper.getView(R.id.loading_text)).setText("正在加载中ppppp");
                    break;
            }
        }

    }
}
