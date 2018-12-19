package com.astgo.naoxuanfeng.studyvideo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.bean.My_Enroll_Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class My_Enroll_Adapter extends RecyclerView.Adapter<My_Enroll_Adapter.ViewHolder> {
    List<My_Enroll_Bean.DataBean> list;
        Context context;

    public My_Enroll_Adapter(List<My_Enroll_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.my_enroll_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.my_enroll_text.setText("会议："+"\t\t\t"+list.get(position).getTitle());
        holder.my_enroll_name.setText("姓名："+"\t\t\t"+list.get(position).getName());
        holder.my_enroll_phone.setText("电话："+"\t\t\t"+list.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView my_enroll_name;
        private final TextView my_enroll_phone;
        private final TextView my_enroll_text;

        public ViewHolder(View itemView) {
            super(itemView);
            my_enroll_text = itemView.findViewById(R.id.my_enroll_text);
            my_enroll_name = itemView.findViewById(R.id.my_enroll_name);
            my_enroll_phone = itemView.findViewById(R.id.my_enroll_phone);

        }
    }
}
