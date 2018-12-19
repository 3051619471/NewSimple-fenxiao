package com.astgo.naoxuanfeng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.astgo.naoxuanfeng.R;
import com.astgo.naoxuanfeng.bean.ContactsTitleMenuBean;
import com.astgo.naoxuanfeng.bean.ContactsTitleMenuCityBean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 * Contacts侧滑出的多级列表适配器
 */
public class ContactsExapandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ContactsTitleMenuBean> parents;
    private int groupPosition, childPosition;//用来保存item选中状态
    private View groupView0;//全部联系人控件
    private ImageView childView;//当前被选择的子控件

    public ContactsExapandAdapter(Context context, List<ContactsTitleMenuBean> parents, int groupPosition, int childPosition) {
        this.context = context;
        this.parents = parents;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

    /**
     *更新数据
     */
    public void updateData(List<ContactsTitleMenuBean> list, int groupPosition, int childPosition){
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        parents = list;
        notifyDataSetChanged();
    }

    //父级列表的总数
    @Override
    public int getGroupCount() {
        return parents.size();
    }
    //子级列表的总数
    @Override
    public int getChildrenCount(int groupPosition) {
        return parents.get(groupPosition).getChildren() == null ? 0 : parents.get(groupPosition).getChildren().size();
    }
    //获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        return parents.get(groupPosition);
    }
    //得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parents.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    //设置父item组件
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_parent_item, null);
        }
        ImageView left_iv = (ImageView) convertView.findViewById(R.id.expand_parents_left_iv);
        TextView tv = (TextView) convertView.findViewById(R.id.expand_parents_tv);
        ImageView right_iv = (ImageView) convertView.findViewById(R.id.expand_parents_right_iv);
        left_iv.setImageResource(parents.get(groupPosition).getLeft_iv());
        tv.setText(parents.get(groupPosition).getTv());
        if(groupPosition != 0){//第一个分组为全部联系人 不需要展开状态
            if(isExpanded){
                right_iv.setImageResource(parents.get(groupPosition).getRight_iv_p());
            }else{
                right_iv.setImageResource(parents.get(groupPosition).getRight_iv_n());
            }
        }
        if(groupPosition == 0){
            groupView0 = convertView;
        }
        if(groupPosition == this.groupPosition){
            right_iv.setSelected(true);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        List<ContactsTitleMenuCityBean> children = parents.get(groupPosition).getChildren();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_child_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.expand_child_tv);
        ImageView iv = (ImageView) convertView.findViewById(R.id.expand_child_iv);
        if("".equals(children.get(childPosition).getCityName())){
            tv.setText("其他" + "(" + children.get(childPosition).getCbs().size() + ")");
        }else{
            tv.setText(children.get(childPosition).getCityName() + "(" + children.get(childPosition).getCbs().size() + ")");
        }
        if(groupPosition == this.groupPosition && childPosition == this.childPosition){
            childView = iv;
            iv.setImageResource(R.drawable.contacts_title_memu_choose_p);//手动设置图片状态为被选择
        }else{
            iv.setImageResource(R.drawable.contacts_title_menu_selector);//其他设置为默认选择器
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        if(childView != null){
            childView.setImageResource(R.drawable.contacts_title_menu_selector);//清除被选中状态
        }
            groupView0.findViewById(R.id.expand_parents_right_iv).setSelected(false);//清除全部联系人的选择效果
        return true;
    }
}
