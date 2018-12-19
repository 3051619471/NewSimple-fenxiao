package com.astgo.naoxuanfeng.classdomel.class_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.classdomel.class_bean.Resource_Bean;

import java.util.List;


/**
 * Created by Administrator on 2018/11/2.
 */

public class Resource_Adapter extends RecyclerView.Adapter<Resource_Adapter.ViewHolder> {
    List<Resource_Bean.DataBean> list;
    Context context;
    String url="http://phthwiy7h.bkt.clouddn.com/";
    String urll="?vframe/jpg/offset/5";
    public Resource_Adapter(List<Resource_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.resource_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String status = list.get(position).getStatus();


       /* holder.title_shop_resource.setText(list.get(position).getTitle());
        holder.vider_palyer.setWidthAndHeightProportion(16, 14);   //设置宽高比
        holder.vider_palyer.setIsNeedBatteryListen(true);         //设置电量监听
        holder.vider_palyer.setIsNeedNetChangeListen(true);       //设置网络监听
        //第二次播放调用：
        holder.vider_palyer.playVideo(list.get(position).getUrl(), "标题1");*/
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

  class ViewHolder extends RecyclerView.ViewHolder {
      private final TextView title_shop_resource;


      public ViewHolder(View itemView) {
          super(itemView);
          title_shop_resource = itemView.findViewById(R.id.title_shop_resource);
          //vider_palyer = itemView.findViewById(R.id.vider_palyer);
      }
  }
}
