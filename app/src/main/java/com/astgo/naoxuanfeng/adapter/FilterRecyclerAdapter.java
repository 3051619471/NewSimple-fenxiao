package com.astgo.naoxuanfeng.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 * 检索查询列表适配器
 */
public class FilterRecyclerAdapter extends RecyclerView.Adapter{
    private static final String TAG = "检索查询列表适配器";

    private Context context;
    private List<ContentValues> objects = new ArrayList<ContentValues>();
    private String filter;//检索key
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(ContentValues cb, View view, int position);

        boolean onItemLongClick(ContentValues cb, View view, int position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    public FilterRecyclerAdapter(Context context, List<ContentValues> cvList) {
        this.context = context;
        this.objects = cvList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder, i: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_filter, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FilterRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder, i: " + position + ", viewHolder: " + holder);
        FilterRecyclerViewHolder viewholder = (FilterRecyclerViewHolder) holder;
        viewholder.cv = objects.get(position);
        viewholder.position = position;
//        viewholder.nameTv.setText(objects.get(position).getContactsName());//姓名
//        viewholder.numTv.setText(objects.get(position).getContactsNum().get(0));//号码
        initializeViews(objects.get(position), viewholder);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    /**
     * 添加item并更新界面
     * @param position item位置
     */
    public void addData(int position) {
        objects.add(position+1, objects.get(position));
        notifyItemInserted(position+1);
    }
    /**
     * 移除item并更新界面
     * @param position item位置
     */
    public void removeData(int position) {
        objects.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    /**
     * 更新列表
     * @param tempList 新的数据列表
     */
    public void updateChanged(List<ContentValues> tempList, String filter){
        objects = tempList;
        this.filter = filter;
        notifyDataSetChanged();
    }

    /**
     * 初始化姓名与号码控件并设置变色颜色
     */
    private void initializeViews(ContentValues object, FilterRecyclerViewHolder holder) {
        //TODO implement
        //为部分字体设置颜色的方法
        if (object.getAsInteger(MyConstant.CONTACT_STATE_NUM) == 1) {//如果号码匹配则设置号码变色
            SpannableStringBuilder builder = new SpannableStringBuilder(object.getAsString(MyConstant.CONTACT_NUMBER));
//            Log.e(TAG,object.getContactsNum().get(0).indexOf(filter)+"");
//            Log.e(TAG,object.getContactsNum().get(0)+"");
            builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.theme_color)),
                    object.getAsString(MyConstant.CONTACT_NUMBER).indexOf(filter),
                    object.getAsString(MyConstant.CONTACT_NUMBER).indexOf(filter) + filter.length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.numTv.setText(builder);
        } else {
            holder.numTv.setText(object.getAsString(MyConstant.CONTACT_NUMBER));
        }
        if (object.getAsInteger(MyConstant.CONTACT_STATE_NAME) == 1) {//如果名字匹配则设置名字变色
            SpannableStringBuilder builder = new SpannableStringBuilder(object.getAsString(MyConstant.CONTACT_NAME));
            builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.theme_color)),
                    object.getAsString(MyConstant.CONTACT_INITIAL).indexOf(filter),
                    object.getAsString(MyConstant.CONTACT_INITIAL).indexOf(filter) + filter.length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.nameTv.setText(builder);
        } else {
            holder.nameTv.setText(object.getAsString(MyConstant.CONTACT_NAME));
        }
    }

    class FilterRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public View rootView;
        public TextView nameTv;
        public TextView numTv;
        public LinearLayout long_press_btn;
        public ImageView detailsIv, arrowIv;
        public ContentValues cv;
        public int position;

        public FilterRecyclerViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.filter_item);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
            nameTv = (TextView) itemView.findViewById(R.id.filter_item_name);
            numTv = (TextView) itemView.findViewById(R.id.filter_item_num);
            long_press_btn = (LinearLayout) itemView.findViewById(R.id.filter_item_long_press);
            detailsIv = (ImageView) itemView.findViewById(R.id.filter_item_details);
            arrowIv = (ImageView) itemView.findViewById(R.id.filter_list_arrow);
            long_press_btn.setOnClickListener(this);
            detailsIv.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(cv, view, position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemLongClick(cv, view, position);
            }
            return true;
        }
    }
}
