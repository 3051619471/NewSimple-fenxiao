package com.astgo.fenxiao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.astgo.fenxiao.ConstantsRes;
import com.astgo.fenxiao.R;
import com.astgo.fenxiao.bean.ContactsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/28.
 * 联系人Recycler适配器
 */
public class ContactsRecyclerAdapter extends RecyclerView.Adapter implements SectionIndexer {
    private static final String TAG = "联系人Recycler适配器";

    private Context context;
    private List<ContactsBean> objects = new ArrayList<ContactsBean>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(ContactsBean cb, View view, int position);

        boolean onItemLongClick(ContactsBean cb, View view, int position);
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    public ContactsRecyclerAdapter(Context context, List<ContactsBean> cvList) {
        this.context = context;
        this.objects = cvList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder, i: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_contacts, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new CallRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder, i: " + position + ", viewHolder: " + holder);
        CallRecyclerViewHolder viewholder = (CallRecyclerViewHolder) holder;
        viewholder.cb = objects.get(position);
        viewholder.position = position;
        initAlphaView(viewholder, position, objects.get(position));//添加各字母的第一个联系人上的字母导航
        viewholder.headIv.setImageResource(R.drawable.contacts_head);//头像
        viewholder.headIv.setBackgroundDrawable(context.getResources().getDrawable(ConstantsRes.contatsHeadRes[objects.get(position).getContactsId() % 6]));
        viewholder.numTv.setText(objects.get(position).getContactsName());//姓名
        viewholder.arrowIv.setImageResource(ConstantsRes.contatsBgRes[objects.get(position).getContactsId() % 6]);
        List<String> tempNum = objects.get(position).getContactsNum();
        boolean isFriend = false;
        if(tempNum != null) {
//            for (int i = 0; i < tempNum.size() - 1; i++) {
//                if (AddressDbUtil.getPhoneFriend(tempNum.get(i)) == 1) {
//                    isFriend = true;//有一个号码是好友就显示这个联系人是好友
//                }
//            }
        }
        if(isFriend){
            viewholder.friendTv.setVisibility(View.VISIBLE);
        }else{
            viewholder.friendTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    //添加每个字母的第一个联系人上的字幕导航
    private void initAlphaView(CallRecyclerViewHolder holder,int position, ContactsBean object){
        // 根据position获取分类的首字母的Char ASCII值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置,则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.alphaTv.setVisibility(View.VISIBLE);
            holder.alphaTv.setText(object.getContactsAlpha());
        } else {
            holder.alphaTv.setVisibility(View.GONE);
        }
    }
    @Override
    public Object[] getSections() {
        return null;
    }

    // 根据分类的首字母的 Char ASCII 值获取其第一次出现该首字母的位置
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = objects.get(i).getContactsAlpha();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    // 根据ListView的当前位置获取分类的首字母的Char ASCII值
    @Override
    public int getSectionForPosition(int position) {
        return objects.get(position).getContactsAlpha()
                .charAt(0);
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
    }
    /**
     * 更新列表
     * @param tempList 新的数据列表
     */
    public void updateChanged(List<ContactsBean> tempList){
        objects = tempList;
        notifyDataSetChanged();
    }

    class CallRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public View rootView;
        public ImageView headIv, arrowIv;
        public TextView numTv, alphaTv, friendTv;
        public ContactsBean cb;
        public int position;

        public CallRecyclerViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.recycler_list_item);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
            headIv = (ImageView) itemView.findViewById(R.id.recycler_list_iv);
            numTv = (TextView) itemView.findViewById(R.id.recycler_list_tv);
            alphaTv = (TextView) itemView.findViewById(R.id.recycler_list_alpha);
            arrowIv = (ImageView) itemView.findViewById(R.id.recycler_list_arrow);
            friendTv = (TextView) itemView.findViewById(R.id.recycler_list_friend);
        }


        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(cb, view, position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemLongClick(cb, view, position);
            }
            return true;
        }
    }
}
