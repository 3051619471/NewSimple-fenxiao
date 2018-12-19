//package com.astgo.fenxiao.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.astgo.fenxiao.ConstantsRes;
//import com.astgo.fenxiao.R;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/2/25.
// * 联系人详情号码列表适配器
// */
//public class ContactsDetailsInfoAdapter  extends RecyclerView.Adapter{
//    private static final String TAG = "联系人详情号码列表适配器";
//
//    private Context context;
//    private List<String> phoneNums;//号码
//    private int contactsId;//联系人id
//    private OnRecyclerViewListener onRecyclerViewListener;
//
//    public static interface OnRecyclerViewListener {
//        void onItemClick(int position, View view);
//        boolean onItemLongClick(int position, View view);
//    }
//
//    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
//        this.onRecyclerViewListener = onRecyclerViewListener;
//    }
//    public ContactsDetailsInfoAdapter(Context context, List<String> cbList, int contactsId) {
//        this.context = context;
//        this.phoneNums = cbList;
//        this.contactsId = contactsId;
//    }
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contacts_details_info_item, null);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
//        return new ContactsDetailsInfoViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ContactsDetailsInfoViewHolder viewHolder = (ContactsDetailsInfoViewHolder) holder;
//        viewHolder.postion = position;
//        viewHolder.call_iv.setImageResource(ConstantsRes.contatsCallRes[contactsId%6]);
//        viewHolder.message_tv.setText(context.getString(R.string.contacts_details_message));
//        viewHolder.number_tv.setText(phoneNums.get(position));
//        viewHolder.address_tv.setText(AddressDbUtil.getPhoneAddress(phoneNums.get(position)));
//    }
//
//    @Override
//    public int getItemCount() {
//        return phoneNums.size();
//    }
//
//    /**
//     * 更新列表
//     * @param tempList 新的数据列表
//     */
//    public void updateChanged(List<String> tempList){
//        phoneNums = tempList;
//        notifyDataSetChanged();
//    }
//
//    class ContactsDetailsInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
//        public View rootView;
//        public ImageView call_iv;
//        public TextView number_tv, address_tv, message_tv;
//        public int postion;
//
//        public ContactsDetailsInfoViewHolder(View itemView) {
//            super(itemView);
//            rootView = itemView.findViewById(R.id.contacts_di_item);
//            call_iv = (ImageView) itemView.findViewById(R.id.contacts_di_call);
//            number_tv = (TextView) itemView.findViewById(R.id.contacts_di_num);
//            address_tv = (TextView) itemView.findViewById(R.id.contacts_di_address);
//            message_tv = (TextView) itemView.findViewById(R.id.contacts_di_msg);
//            rootView.setOnClickListener(this);
//            call_iv.setOnClickListener(this);
//            message_tv.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (null != onRecyclerViewListener) {
//                onRecyclerViewListener.onItemClick(postion, view);
//            }
//        }
//
//        @Override
//        public boolean onLongClick(View view) {
//            if (null != onRecyclerViewListener) {
//                onRecyclerViewListener.onItemLongClick(postion, view);
//            }
//            return true;
//        }
//    }
//}
