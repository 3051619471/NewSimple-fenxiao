package com.astgo.naoxuanfeng.studyvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.bean.Video_Study_Bean;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.jzvd.JzvdStd;


/**
 * Created by Administrator on 2018/11/2.
 */

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {
    List<Video_Study_Bean.DataBean> list;
    Context context;
    String urll="?vframe/jpg/offset/25";
    public Video_Adapter( List<Video_Study_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.video_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     String status = list.get(position).getStatus();
        String url = list.get(position).getUrl();
        if (status.equals("1")){
            holder.title_video_resource.setText(list.get(position).getTitle());
            holder.image_video_resource.TOOL_BAR_EXIST = false;
            holder.image_video_resource.setUp(list.get(position).getUrl()
                    ,"" ,JzvdStd.SCREEN_WINDOW_LIST);
//        jcVideoPlayerStandard.loop  = true;//是否循环播放
            Glide.with(context).load(list.get(position).getUrl()+urll)
                    .into(holder.image_video_resource.thumbImageView);
            holder.image_video_resource.widthRatio = 4;//播放比例
            holder.image_video_resource.heightRatio = 3;
        }else {
            holder.title_video_resource.setText(list.get(position).getTitle());
            holder.image_video_resource.TOOL_BAR_EXIST = false;
            holder.image_video_resource.setUp(""
                    ,"该视频需要开通会员" ,JzvdStd.SCREEN_WINDOW_LIST);
//        jcVideoPlayerStandard.loop  = true;//是否循环播放
            Glide.with(context).load(list.get(position).getUrl()+urll)
                    .into(holder.image_video_resource.thumbImageView);
            holder.image_video_resource.widthRatio = 4;//播放比例
            holder.image_video_resource.heightRatio = 3;
        }

        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title_video_resource;
        private final JzvdStd image_video_resource;

        public ViewHolder(View itemView) {
            super(itemView);
            title_video_resource = itemView.findViewById(R.id.title_video_resource);
            image_video_resource = itemView.findViewById(R.id.image_video_resource);

        }
    }

}
