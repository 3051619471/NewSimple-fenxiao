package com.astgo.naoxuanfeng.classdomel.class_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_bean.Resource_Bean;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.jzvd.JzvdStd;


/**
 * Created by Administrator on 2018/11/3.
 */

public class Resource_Shop_Adapter extends RecyclerView.Adapter<Resource_Shop_Adapter.ViewHolder> {
    List<Resource_Bean.DataBean> list;
    Context context;
    String urll="?vframe/jpg/offset/25";
    private String url;

    public Resource_Shop_Adapter(List<Resource_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.resoure_shop_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String status = list.get(position).getStatus();
        url = list.get(position).getUrl();


            if (status.equals("1")){
                holder.title_shop_resource.setText(list.get(position).getTitle());
                holder.title_shop_resource.setSingleLine();
                holder.image_shop_resource.TOOL_BAR_EXIST = false;
                holder.image_shop_resource.setUp(list.get(position).getUrl()
                        ,"" ,JzvdStd.SCREEN_WINDOW_LIST);
//        jcVideoPlayerStandard.loop  = true;//是否循环播放
                Glide.with(context).load(list.get(position).getUrl()+urll)
                        .into(holder.image_shop_resource.thumbImageView);
                holder.image_shop_resource.widthRatio = 4;//播放比例
                holder.image_shop_resource.heightRatio = 3;
            }else {
                holder.title_shop_resource.setText(list.get(position).getTitle());
                holder.image_shop_resource.TOOL_BAR_EXIST = false;

                holder.image_shop_resource.setUp(""
                        ,"该视频需要开通会员" ,JzvdStd.SCREEN_WINDOW_LIST);
//        jcVideoPlayerStandard.loop  = true;//是否循环播放
                Glide.with(context).load(list.get(position).getUrl()+urll)
                        .into(holder.image_shop_resource.thumbImageView);
                holder.image_shop_resource.widthRatio = 4;//播放比例
                holder.image_shop_resource.heightRatio = 3;
            }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private OnItemClickListenerRShop mOnItemClickListener;//声明接口

    public interface OnItemClickListenerRShop {
        void onClick(int position, String id);
    }

    public void setOnItemClickListenerRShop(OnItemClickListenerRShop onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
  class ViewHolder extends RecyclerView.ViewHolder{

      private final TextView title_shop_resource;
      private final JzvdStd image_shop_resource;

      public ViewHolder(View itemView) {
          super(itemView);
          title_shop_resource = itemView.findViewById(R.id.title_shop_resource);
          image_shop_resource = itemView.findViewById(R.id.image_shop_resource);
      }
  }

}
