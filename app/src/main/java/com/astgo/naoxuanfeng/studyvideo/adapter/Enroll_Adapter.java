package com.astgo.naoxuanfeng.studyvideo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astgo.naoxuanfeng.ConstantURL;
import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.studyvideo.bean.Enroll_Title_Bean;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/11/26.
 */

public class Enroll_Adapter extends RecyclerView.Adapter<Enroll_Adapter.ViewHolder> {
    List<Enroll_Title_Bean.DataBean> list;
    Context context;
    ConstantURL constantURL = new ConstantURL();
    String ip_url = constantURL.IP_url;
    private String id;

    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean onBind;
    private int checkedPosition = -1;
    public Enroll_Adapter(List<Enroll_Title_Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.enroll_name_item, null);
       ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide
                .with(context)
                .load(ip_url+list.get(position).getPic())
                .into(holder.enroll_img);

        holder.enroll_start_time.setText("开始时间："+list.get(position).getStar_time());
        holder.enroll_end_time.setText("结束时间："+list.get(position).getEnd_time());
        holder.enroll_title.setText("标题："+list.get(position).getTitle());
/*holder.radio_but_enroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            id = list.get(position).getId();
            //保存数据
            SharedPreferences pref = context.getSharedPreferences("checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id",id);
            editor.commit();
            Toast.makeText(context, "您已经选中啦"+id, Toast.LENGTH_SHORT).show();
        }
        //由选中状态到未选中状态时候执行这里：
        else {
            Toast.makeText(context, "您已经取消了", Toast.LENGTH_SHORT).show();
            System.out.println("swim is unchecked");
        }
    }
});*/
        holder.checkbox_but_enroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                //未选中到选中状态是执行这里：
                if(isChecked) {
                    id = list.get(position).getId();
                    //保存数据
                    SharedPreferences pref = context.getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id",id);
                    editor.commit();
                    Toast.makeText(context, "您已经选中了", Toast.LENGTH_SHORT).show();
                }
                //由选中状态到未选中状态时候执行这里：
                else {
                    Toast.makeText(context, "您已经取消了", Toast.LENGTH_SHORT).show();
                    System.out.println("swim is unchecked");
                }
            }
        });
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
    }}

    @Override
    public int getItemCount() {
        return list.size();
    }
    private OnItemClickListenerEnroll mOnItemClickListener;//声明接口

    public interface OnItemClickListenerEnroll {
        void onClick(int position);
    }

    public void OnItemClickListenerEnroll(OnItemClickListenerEnroll onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
  class ViewHolder extends RecyclerView.ViewHolder {

      private final ImageView enroll_img;
      private final TextView enroll_start_time;
      private final TextView enroll_end_time;
      private final TextView enroll_title;
     private final CheckBox checkbox_but_enroll;
      //private final RadioButton radio_but_enroll;

      public ViewHolder(View itemView) {
          super(itemView);
          enroll_img = itemView.findViewById(R.id.enroll_img);
          enroll_start_time = itemView.findViewById(R.id.enroll_start_time);
          enroll_end_time = itemView.findViewById(R.id.enroll_end_time);
          enroll_title = itemView.findViewById(R.id.enroll_title);
         checkbox_but_enroll = itemView.findViewById(R.id.checkbox_but_enroll);
        //  radio_but_enroll = itemView.findViewById(R.id.radio_but_enroll);
      }
  }
}
