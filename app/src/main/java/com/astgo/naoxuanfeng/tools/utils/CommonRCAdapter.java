package com.astgo.naoxuanfeng.tools.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by admin on 2017/12/21.
 */

public abstract class CommonRCAdapter<T> extends RecyclerView.Adapter<CommonRCViewHolder> {
    public Context mContext;
    public List<T> mList;
    public int mItemResID;
    public OnRCItemOnClickListener mOnRCItemOnClickListener;

    public void setOnRCItemOnClickListener(OnRCItemOnClickListener mOnRCItemOnClickListener) {
        this.mOnRCItemOnClickListener = mOnRCItemOnClickListener;
    }

    public CommonRCAdapter(Context mContext, List<T> mList, int mItemResID) {
        this.mContext = mContext;
        this.mList = mList;
        this.mItemResID = mItemResID;
    }

    @Override
    public int getItemCount() {
        return mList == null? 0:mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public CommonRCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonRCViewHolder holder = CommonRCViewHolder.getHolder(mContext,parent,mItemResID);
        return holder;
    }
    @Override
    public void onBindViewHolder(final CommonRCViewHolder holder, final int position) {
            convert(holder,position,mList.get(position));
            holder.getmItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRCItemOnClickListener == null)return;
                    mOnRCItemOnClickListener.setOnRCItemOnClickListener(holder,position);
                }
            });
    }
    public abstract void  convert(CommonRCViewHolder holder,int position,T t);
    public interface OnRCItemOnClickListener{
        void setOnRCItemOnClickListener(CommonRCViewHolder holder, int position);
    }
}
