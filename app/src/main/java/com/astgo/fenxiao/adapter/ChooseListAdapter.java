package com.astgo.fenxiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astgo.fenxiao.R;

/**
 * Created by Administrator on 2016/3/7.
 * 无网络拨号列表适配器
 */
public class ChooseListAdapter extends BaseAdapter {
    private Context context;
    private String[] datas;
    private int chooseId;//被选中item

    public ChooseListAdapter(Context context, String[] datas, int chooseId) {
        this.context = context;
        this.datas = datas;
        this.chooseId = chooseId;
    }

    @Override
    public int getCount() {
        return this.datas != null ? this.datas.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 获得组件，实例化组件
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_choose,
                    null);
            viewHolder.rootView = (LinearLayout) convertView.findViewById(R.id.no_net_call_item);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.list_item_choose_tv);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.list_item_choose_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(datas[position]);
        if (chooseId == position) {//如果此item被选中
            viewHolder.iv.setVisibility(View.VISIBLE);//显示对勾
        } else {
            viewHolder.iv.setVisibility(View.INVISIBLE);//隐藏对勾
        }
        return convertView;
    }

    /**
     * 选则哪个item
     */
    public void chooseItem(int i) {
        this.chooseId = i;
        notifyDataSetChanged();
    }

    /**
     * list_item中的组件集合
     */
    public final class ViewHolder {
        public LinearLayout rootView;
        public TextView tv;
        public ImageView iv;
    }
}
