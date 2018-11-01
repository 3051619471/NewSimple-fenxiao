package com.astgo.fenxiao.tools.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 2017/12/21.
 */

public class CommonRCViewHolder extends RecyclerView.ViewHolder {
    public View mItemView;
    public Context mContext;
    public SparseArray<View> mViews;
    public CommonRCViewHolder(Context context,View itemView) {
        super(itemView);
        mItemView = itemView;
        mContext = context;
        mViews = new SparseArray<>();
    }
    public static CommonRCViewHolder getHolder(Context context, ViewGroup parent,int resID){
       // View itemView = LayoutInflater.from(context).inflate(resID,null);// 此方法宽高失效
        View itemView = LayoutInflater.from(context).inflate(resID,parent,false);
        CommonRCViewHolder holder = new CommonRCViewHolder(context,itemView);
        return holder;
    }
    public View getmItemView(){
        return mItemView;
    }
    public <T extends View> T getView(int viewID){
        View view  = mViews.get(viewID);
        if (view == null){
            view = mItemView.findViewById(viewID);
            mViews.put(viewID,view);
        }
        return (T) view;
    }
    public CommonRCViewHolder setText(int id,String textStr){
        TextView tv = getView(id);
        tv.setText(textStr);
        return this;
    }
    public CommonRCViewHolder setImage(int id,int resID){
        ImageView iv = getView(id);
        iv.setImageResource(resID);
        return this;
    }
}
