package com.astgo.naoxuanfeng.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.CallsBean;
import com.astgo.naoxuanfeng.tools.TimeUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 * 联系人详情通话记录适配器
 */
public class ContactsDetailsCallsAdapter extends RecyclerView.Adapter{
    private static final String TAG = "联系人详情通话记录适配器";

    private Context context;
    private List<CallsBean> callsBeans;//号码
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position, View view);
        boolean onItemLongClick(int position, View view);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
    public ContactsDetailsCallsAdapter(Context context, List<CallsBean> cbList) {
        this.context = context;
        this.callsBeans = cbList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contacts_details_calls_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ContactsDetailsInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactsDetailsInfoViewHolder viewHolder = (ContactsDetailsInfoViewHolder) holder;
        viewHolder.postion = position;
        switch (callsBeans.get(position).getCallsType()){//通话记录类型与字体颜色
            case CallLog.Calls.INCOMING_TYPE://接听电话
                viewHolder.call_iv.setImageResource(R.drawable.icon_calls_item_incoming);
                break;
            case CallLog.Calls.OUTGOING_TYPE://拨出电话
                viewHolder.call_iv.setImageResource(R.drawable.icon_calls_item_outgoing);
                break;
            case CallLog.Calls.MISSED_TYPE://未接来电
                viewHolder.call_iv.setImageResource(R.drawable.calls_new_item_missed);
                viewHolder.time_tv.setTextColor(context.getResources().getColor(R.color.new_theme_red));
                viewHolder.number_tv.setTextColor(context.getResources().getColor(R.color.new_theme_red));
                break;
            default://默认为拨出电话
                viewHolder.call_iv.setImageResource(R.drawable.icon_calls_item_outgoing);
                break;
        }
        viewHolder.time_tv.setText(TimeUtil.date1(context, callsBeans.get(position).getCallsTime()));
        viewHolder.number_tv.setText(callsBeans.get(position).getCallsNum());
        viewHolder.duration_tv.setText(TimeUtil.getDuration(context, callsBeans.get(position).getCallsDurations()));
    }

    @Override
    public int getItemCount() {
        return callsBeans.size();
    }

    /**
     * 更新列表
     * @param tempList 新的数据列表
     */
    public void updateChanged(List<CallsBean> tempList){
        callsBeans = tempList;
        notifyDataSetChanged();
    }

    class ContactsDetailsInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public View rootView;
        public ImageView call_iv;
        public TextView time_tv, number_tv, duration_tv;
        public int postion;

        public ContactsDetailsInfoViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.contacts_dc_item);
            call_iv = (ImageView) itemView.findViewById(R.id.contacts_dc_call);
            time_tv = (TextView) itemView.findViewById(R.id.contacts_dc_time);
            number_tv = (TextView) itemView.findViewById(R.id.contacts_dc_num);
            duration_tv = (TextView) itemView.findViewById(R.id.contacts_dc_duration);
        }

        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(postion, view);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemLongClick(postion, view);
            }
            return true;
        }
    }
}
