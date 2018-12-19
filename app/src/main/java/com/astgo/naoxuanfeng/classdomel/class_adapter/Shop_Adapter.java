package com.astgo.naoxuanfeng.classdomel.class_adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_bean.Shop_Bean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


/**
 * Created by Administrator on 2018/11/2.
 */

public class Shop_Adapter extends RecyclerView.Adapter<Shop_Adapter.ViewHolder> {
    List<Shop_Bean.DataBean> list;
    Context context;
    ConstantURL constantURL = new ConstantURL();
    String IP_url = constantURL.IP_url;
    public Shop_Adapter(List<Shop_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.shop_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
       final String name = list.get(position).getName();

        holder.title_shop.setText(name);


        final String picUrl = IP_url+list.get(position).getPic();
        final Uri uri = Uri.parse(picUrl);
        holder.my_image_view.setImageURI(uri);
        final String id = list.get(position).getId();
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position,id,name);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private OnItemClickListenerShop mOnItemClickListener;//声明接口

    public interface OnItemClickListenerShop {
        void onClick(int position, String id,String name);
    }

    public void setOnItemClickListenerShop(OnItemClickListenerShop onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView my_image_view;
        private final TextView title_shop;

        public ViewHolder(View itemView) {
            super(itemView);
            my_image_view = itemView.findViewById(R.id.my_image_view);
            title_shop = itemView.findViewById(R.id.title_shop);
        }
    }
}
