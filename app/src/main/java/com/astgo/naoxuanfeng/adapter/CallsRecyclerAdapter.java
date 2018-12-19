//package com.astgo.fenxiao.adapter;
//
//import android.content.Context;
//import android.provider.CallLog;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.bean.CallsBean;
//import com.astgo.fenxiao.tools.AddressDbUtil;
//import com.astgo.fenxiao.tools.TimeUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/2/15.
// * 通话记录Recycler适配器
// */
//public class CallsRecyclerAdapter extends RecyclerView.Adapter {
//    private static final String TAG = "通话记录Recycler适配器";
//
//    private Context context;
//    private List<CallsBean> objects = new ArrayList<CallsBean>();
//    private OnRecyclerViewListener onRecyclerViewListener;
//
//    public static interface OnRecyclerViewListener {
//        void onItemClick(CallsBean cb, View view, int position);
//        boolean onItemLongClick(CallsBean cb, View view, int position);
//    }
//
//    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
//        this.onRecyclerViewListener = onRecyclerViewListener;
//    }
//
//
//    public CallsRecyclerAdapter(Context context, List<CallsBean> cbList) {
//        this.context = context;
//        this.objects = cbList;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        Log.d(TAG, "onCreateViewHolder, i: " + viewType);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_calls, null);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
//        return new CallRecyclerViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////        Log.d(TAG, "onBindViewHolder, i: " + position + ", viewHolder: " + holder);
//        CallRecyclerViewHolder viewholder = (CallRecyclerViewHolder) holder;
//        viewholder.cb = objects.get(position);
//        viewholder.position = position;
//        switch (objects.get(position).getCallsType()){//通话记录类型与字体颜色
//            case CallLog.Calls.INCOMING_TYPE://接听电话
//                viewholder.typeIv.setImageResource(R.drawable.icon_calls_item_incoming);
//                viewholder.nameTv.setTextColor(context.getResources().getColor(R.color.theme_black));
//                break;
//            case CallLog.Calls.OUTGOING_TYPE://拨出电话
//                viewholder.typeIv.setImageResource(R.drawable.icon_calls_item_outgoing);
//                viewholder.nameTv.setTextColor(context.getResources().getColor(R.color.theme_black));
//                break;
//            case CallLog.Calls.MISSED_TYPE://未接来电
//                viewholder.typeIv.setImageResource(R.drawable.calls_new_item_missed);
//                viewholder.nameTv.setTextColor(context.getResources().getColor(R.color.new_theme_red));
//                break;
//            default://默认为拨出电话
//                viewholder.typeIv.setImageResource(R.drawable.icon_calls_item_outgoing);
//                viewholder.nameTv.setTextColor(context.getResources().getColor(R.color.theme_black));
//                break;
//        }
//        String sameNum = "";
//        if(objects.get(position).getCallsSameId() != null){
//            sameNum = "(" + (objects.get(position).getCallsSameId().size()+1) + ")";
//        }
//        if(objects.get(position).getCallsName() == null){//如果姓名为空
//            viewholder.nameTv.setText(objects.get(position).getCallsNum() + sameNum);//通话记录电话号码
//            viewholder.numTv.setText(AddressDbUtil.getPhoneAddress(objects.get(position).getCallsNum()));//通话记录归属地
//        }else{
//            viewholder.nameTv.setText(objects.get(position).getCallsName() + sameNum);//通话记录姓名
//            viewholder.numTv.setText(objects.get(position).getCallsNum());//通话记录电话号码
//        }
//        viewholder.timeTv.setText(TimeUtil.date(context, objects.get(position).getCallsTime()));//通话时间
//    }
//
//    @Override
//    public int getItemCount() {
//        return objects.size();
//    }
//
//    /**
//     * 添加item并更新界面
//     * @param position item位置
//     */
//    public void addData(int position) {
//        objects.add(position+1, objects.get(position));
//        notifyItemInserted(position+1);
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 移除item并更新界面
//     * @param position item位置
//     */
//    public void removeData(int position) {
//        objects.remove(position);
//        notifyItemRemoved(position);
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 清除列表数据
//     */
//    public void clearData(){
//        objects.clear();
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 更新列表
//     * @param tempList 新的数据列表
//     */
//    public void updateChanged(List<CallsBean> tempList){
//        objects = tempList;
//        notifyDataSetChanged();
//        Log.e("CallReceiver","联系人列表更新");
//    }
//
//    class CallRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
//
//        public View rootView;
//        public ImageView typeIv, detailsIv;
//        public LinearLayout longPressIv;
//        public TextView nameTv, numTv, timeTv;
//        public CallsBean cb;
//        public int position;
//
//        public CallRecyclerViewHolder(View itemView) {
//            super(itemView);
//            rootView = itemView.findViewById(R.id.calls_item);
//            rootView.setOnClickListener(this);
//            rootView.setOnLongClickListener(this);
//            typeIv = (ImageView) itemView.findViewById(R.id.calls_item_state);
//            nameTv = (TextView) itemView.findViewById(R.id.calls_item_name);
//            numTv = (TextView) itemView.findViewById(R.id.calls_item_num);
//            longPressIv = (LinearLayout) itemView.findViewById(R.id.calls_item_long_press);
//            timeTv = (TextView) itemView.findViewById(R.id.calls_item_time);
//            detailsIv = (ImageView) itemView.findViewById(R.id.calls_item_details);
//            longPressIv.setOnClickListener(this);
//            detailsIv.setOnClickListener(this);
//        }
//
//
//        @Override
//        public void onClick(View view) {
//            if (null != onRecyclerViewListener) {
//                onRecyclerViewListener.onItemClick(cb, view, position);
//            }
//        }
//
//        @Override
//        public boolean onLongClick(View view) {
//            if (null != onRecyclerViewListener) {
//                onRecyclerViewListener.onItemLongClick(cb, view, position);
//            }
//            return true;
//        }
//    }
//}
