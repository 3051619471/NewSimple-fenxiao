package com.astgo.naoxuanfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.JpushMessageBean;

import java.util.List;

/**
 * Created by ast009 on 2017/11/30.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageRecyclerViewHolder>{

    private List<JpushMessageBean> jpushMessageBeanList;
    private MessageAdapter.OnRecyclerViewListener onRecyclerViewListener;


    public void setJpushMessageBeanList(List<JpushMessageBean> jpushMessageBeanList) {
        this.jpushMessageBeanList = jpushMessageBeanList;
        notifyDataSetChanged();
    }

    public MessageAdapter(List<JpushMessageBean> jpushMessageBeanList) {
        this.jpushMessageBeanList = jpushMessageBeanList;
    }

    public static interface OnRecyclerViewListener {
        void onItemClick(JpushMessageBean jpushMessageBean, View view, int position);
    }

    public void setOnRecyclerViewListener(MessageAdapter.OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public MessageRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new MessageRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageRecyclerViewHolder holder, int position) {
        if(jpushMessageBeanList != null && jpushMessageBeanList.size()>0){
            JpushMessageBean jpushMessageBean = jpushMessageBeanList.get(position);
            if(jpushMessageBean != null){
                holder.tvTitle.setText(jpushMessageBean.getTitle());
                holder.tvOutContent.setText(jpushMessageBean.getDesc());
                holder.tvTime.setText(jpushMessageBean.getAdd_time());
            }
        }
    }

    @Override
    public int getItemCount() {
        return jpushMessageBeanList == null?0:jpushMessageBeanList.size();
    }

    class MessageRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvTime;
        public TextView tvDetail;
        public TextView tvTitle;
        public TextView tvOutContent;
        public int position;
        public JpushMessageBean jpushMessageBean;

        public MessageRecyclerViewHolder(View itemView) {
            super(itemView);
            LinearLayout llContent  = (LinearLayout) itemView.findViewById(R.id.ll_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvOutContent = (TextView) itemView.findViewById(R.id.tv_out_content);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            llContent.setOnClickListener(this);
//            tvDetail.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(jpushMessageBean, view, position);
            }
        }
    }


}
