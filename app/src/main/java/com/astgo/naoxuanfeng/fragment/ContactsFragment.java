//package com.astgo.fenxiao.fragment;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ExpandableListView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.astgo.fenxiao.ConstantsRes;
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.activity.ContactsDetailsActivity;
//import com.astgo.fenxiao.adapter.ContactsExapandAdapter;
//import com.astgo.fenxiao.adapter.ContactsRecyclerAdapter;
//import com.astgo.fenxiao.bean.ContactsBean;
//import com.astgo.fenxiao.bean.ContactsTitleMenuBean;
//import com.astgo.fenxiao.tools.AddressDbUtil;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//import com.astgo.fenxiao.widget.Sidebar;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/1/28.
// * 联系人界面
// */
//public class ContactsFragment extends Fragment implements ContactsRecyclerAdapter.OnRecyclerViewListener, View.OnClickListener {
//    private static final String TAG = "联系人界面";
//    private Context context;
//    private TextView title_tv;//标题
//    private View title_left;//标题栏左侧控件
//    private PopupWindow popupWindowTitleMenu;//title弹出菜单
//    private ExpandableListView item_title_menu;//title弹出菜单的list控件
//    private int gourpPosition, childPosition;
//    private List<ContactsTitleMenuBean> titleMenuBeanList;//存放归属地分组数据
//    private ContactsExapandAdapter contactsExapandAdapter;//弹出菜单的list数据适配器
//    private PopupWindow popupWindowItemMenu;//item弹出菜单
//    private RecyclerView recyclerView;//联系人列表
//    private LinearLayoutManager layoutManager;
//    private ContactsRecyclerAdapter contactsRecyclerAdapter;
//    private TextView popupIndex;//悬浮字母
//    private Sidebar sidebar;//右侧悬浮导航
//    private List<ContactsBean> mDataSet = new ArrayList<>();// 存放联系人数据集
//    private ContactsBean contactsTemp;//存放当前弹出框对应item的ContactsBean
//    private int currentPosition;//当前点击的itemPosition
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        context = getActivity();//获取上下文
//        return inflater.inflate(R.layout.fragment_contacts, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initView(view);
////        gestureDetector = new GestureDetector(context, new MyOnGestureListener());
////        //为fragment添加OnTouchListener监听器
////        view.setOnTouchListener(new MyOnTouchListener());
//    }
//    @Subscribe
//    public void getMsg(String str){
//        if (str.equals("change")){
//           initData();
//            Log.e("SLH","收到活敢了");
//        }
//    }
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
//
//    private void initView(View view) {
//        popupIndex = (TextView) view.findViewById(R.id.popup_index);
//        sidebar = (Sidebar) view.findViewById(R.id.sidebar);
//        sidebar.setTextView(popupIndex);
//        initTitleView(view);
//        initRecyclerView(view);
//        initPopupWindowTitleMenu();
//        initPopupWindowItemMenu();
//        initData();
//        EventBus.getDefault().register(this);
//    }
//
//    /**
//     * 初始化fragmentTitle
//     */
//    private void initTitleView(View view) {
//        title_tv = ((TextView) view.findViewById(R.id.title_tv));
//        title_tv.setText(getString(R.string.title_contacts));
//        title_left = view.findViewById(R.id.title_left);
//        title_left.setOnClickListener(this);
//    }
//
//    /**
//     * 初始化RecyclerView定义的list列表
//     */
//    private void initRecyclerView(View view) {
//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list);
////        recyclerView.setOnTouchListener(new MyOnTouchListener());
//        recyclerView.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能。
//        layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
////        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
//        contactsRecyclerAdapter = new ContactsRecyclerAdapter(context, mDataSet);
//        contactsRecyclerAdapter.setOnRecyclerViewListener(this);
//        recyclerView.setAdapter(contactsRecyclerAdapter);
//        // 设置右侧触摸监听
//        sidebar.setOnTouchingLetterChangedListener(new Sidebar.OnTouchingLetterChangedListener() {
//
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                // 该字母首次出现的位置
//                int position = contactsRecyclerAdapter.getPositionForSection(s.charAt(0));
//                if (position != -1) {
//                    moveToPosition(position);//移动至相应item位置
////                    smoothMoveToPosition(position);//移动至相应item位置
//                }
//            }
//        });
//    }
//
//    /**
//     * 初始化title弹出菜单
//     */
//    private void initPopupWindowTitleMenu() {
//        View myView = LayoutInflater.from(context).inflate(R.layout.popuwindow_contacts_title_menu, null);
//        item_title_menu = (ExpandableListView) myView.findViewById(R.id.pop_contacts_title_menu);
//        item_title_menu.setGroupIndicator(null);
//        item_title_menu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            //            　　parent                    发生点击动作的ExpandableListView
////            v                    在expandable list/ListView中被点击的视图(View)
////            groupPosition                   包含被点击子元素的组(group)在ExpandableListView中的位置(索引)
////            childPosition                   被点击子元素(child)在组(group)中的位置
////            id                  被点击子元素(child)的行ID(索引)
////            @Override
//            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
//                ContactsFragment.this.gourpPosition = groupPosition;
//                ContactsFragment.this.childPosition = childPosition;
//                title_tv.setText(titleMenuBeanList.get(groupPosition).getChildren().get(childPosition).getCityName());
//                view.setSelected(true);
//                contactsRecyclerAdapter.updateChanged(titleMenuBeanList.get(groupPosition).getChildren().get(childPosition).getCbs());//显示对应联系人
//                popupWindowTitleMenu.dismiss();
//                return true;
//            }
//        });
//        item_title_menu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
//                if (groupPosition == 0) {//全部联系人
//                    ContactsFragment.this.gourpPosition = 0;
//                    title_tv.setText(getString(R.string.title_contacts));
//                    view.findViewById(R.id.expand_parents_right_iv).setSelected(true);
//                    contactsRecyclerAdapter.updateChanged(mDataSet);//显示全部联系人
//                    popupWindowTitleMenu.dismiss();
//                }
//                return false;
//            }
//        });
//        titleMenuBeanList = new ArrayList<>();
//        contactsExapandAdapter = new ContactsExapandAdapter(context, titleMenuBeanList, gourpPosition, childPosition);
//        item_title_menu.setAdapter(contactsExapandAdapter);//加载数据
//        popupWindowTitleMenu = new PopupWindow(myView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        //设置控件可获取焦点
//        popupWindowTitleMenu.setFocusable(true);
//        //PopupWindow以外的区域是可点击,点击即可使PopupWindow消失
//        popupWindowTitleMenu.setOutsideTouchable(true);
//        if (isAdded()) {
//            popupWindowTitleMenu.setBackgroundDrawable(getResources().getDrawable(R.color.theme_white));
//        }
//        popupWindowTitleMenu.setAnimationStyle(R.style.popwin_horizontal_anim_style);
//    }
//
//
//    /**
//     * 初始化Item弹出菜单
//     */
//    private void initPopupWindowItemMenu() {
//        View myView = LayoutInflater.from(context).inflate(R.layout.popuwindow_contacts_item_menu, null);
//        LinearLayout item_menu_call = (LinearLayout) myView.findViewById(R.id.contacts_item_call);
//        LinearLayout item_menu_message = (LinearLayout) myView.findViewById(R.id.contacts_item_message);
//        LinearLayout item_menu_delete = (LinearLayout) myView.findViewById(R.id.contacts_item_delete);
//        LinearLayout item_menu_edit = (LinearLayout) myView.findViewById(R.id.contacts_item_edit);
//        LinearLayout item_menu_collect = (LinearLayout) myView.findViewById(R.id.contacts_item_collect);
//        item_menu_call.setOnClickListener(this);
//        item_menu_message.setOnClickListener(this);
//        item_menu_delete.setOnClickListener(this);
//        item_menu_edit.setOnClickListener(this);
//        item_menu_collect.setOnClickListener(this);
//        //初始化popupwindow，绑定显示view，设置该view的宽度/高度
//        popupWindowItemMenu = new PopupWindow(myView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //设置控件可获取焦点
//        popupWindowItemMenu.setFocusable(true);
//        //PopupWindow以外的区域是可点击,点击即可使PopupWindow消失
//        popupWindowItemMenu.setOutsideTouchable(true);
//        popupWindowItemMenu.setAnimationStyle(R.style.popwin_vertical_anim_style);
//    }
//
//    private void initData() {
//        mDataSet = MyApplication.mDataSet1;
//        contactsRecyclerAdapter.updateChanged(mDataSet);//更新联系人数据
//    }
//   public void upDate(){
//       contactsRecyclerAdapter.updateChanged(MyApplication.mDataSet1);//更新联系人数据
//   }
//    /**
//     * 加载title菜单中的归属地数据
//
//     */
//    private void initDataPopupWindowTitle() {
//        titleMenuBeanList = AddressDbUtil.getContactsTitleMenuData(mDataSet);//获取title分类菜单数据
//        contactsExapandAdapter.updateData(titleMenuBeanList, gourpPosition, childPosition);//更新列表数据
//    }
//
//    /**
//     * 将列表移动到指定item位置
//     */
//    private void moveToPosition(int n) {
////先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
//        int firstItem = layoutManager.findFirstVisibleItemPosition();
//        int lastItem = layoutManager.findLastVisibleItemPosition();
//        //然后区分情况
//        if (n <= firstItem) {//当要置顶的项在当前显示的第一个项的前面时
//            recyclerView.scrollToPosition(n);
//        } else if (n <= lastItem) {//当要置顶的项已经在屏幕上显示时
//            //获取要置顶的项顶部离RecyclerView顶部的距离
//            int top = recyclerView.getChildAt(n - firstItem).getTop();
//            recyclerView.scrollBy(0, top);
//        } else {//当要置顶的项在当前显示的最后一项的后面时
//            recyclerView.scrollToPosition(n);
//            //这里这个变量是用在RecyclerView滚动监听里面的
////            move = true;
//        }
//    }
//
//    private void smoothMoveToPosition(int n) {
////先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
//        int firstItem = layoutManager.findFirstVisibleItemPosition();
//        int lastItem = layoutManager.findLastVisibleItemPosition();
//        //然后区分情况
//        if (n <= firstItem) {//当要置顶的项在当前显示的第一个项的前面时
//            recyclerView.smoothScrollToPosition(n);
//        } else if (n <= lastItem) {//当要置顶的项已经在屏幕上显示时
//            //获取要置顶的项顶部离RecyclerView顶部的距离
//            int top = recyclerView.getChildAt(n - firstItem).getTop();
//            recyclerView.smoothScrollBy(0, top);
//        } else {//当要置顶的项在当前显示的最后一项的后面时
//            recyclerView.smoothScrollToPosition(n);
//            //这里这个变量是用在RecyclerView滚动监听里面的
////            move = true;
//        }
//    }
//
//    /**
//     * 显示popupwindowTitle
//     */
//    private void showPopupWindowTitle() {
//        initDataPopupWindowTitle();//加载归属地数据
//        //PopupWindow消失时控制弹出框的arrow消失
//        popupWindowTitleMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                sidebar.setVisibility(View.VISIBLE);
//            }
//        });
//        if (!popupWindowTitleMenu.isShowing()) {
//            popupWindowTitleMenu.showAsDropDown(title_left, 0, 0);
//        }
//    }
//    /**
//     * 显示popupwindow
//     */
//    private void showPopupWindow(final View parents, ContactsBean cb, int position) {
//        contactsTemp = cb;
//        currentPosition = position;
//        //设置背景颜色与arrow箭头匹配
//        if (isAdded()) {
//            popupWindowItemMenu.setBackgroundDrawable(getResources().getDrawable(ConstantsRes.contactsBgColor[cb.getContactsId()%6]));
//        }
//        //PopupWindow消失时控制弹出框的arrow消失
//        popupWindowItemMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                parents.findViewById(R.id.recycler_list_arrow).setVisibility(View.GONE);
//                sidebar.setVisibility(View.VISIBLE);
//            }
//        });
//        if (!popupWindowItemMenu.isShowing()) {
//            popupWindowItemMenu.showAsDropDown(parents, 0, -10);
//        }
//    }
//
//    @Override
//    public void onItemClick(ContactsBean cb, View view, int position) {
////        Log.d(TAG, "onItemClick");
////        Toast.makeText(context, "onItemClick" + cb.getContactsId(), Toast.LENGTH_SHORT).show();
////            contactsRecyclerAdapter.addData(position);
//        Intent intent = new Intent();
//        intent.setClass(context, ContactsDetailsActivity.class);
//        intent.putExtra(MyConstant.CONTACTS_ID, cb.getContactsId());
//        intent.putExtra(MyConstant.CONTACTS_TAG, cb.getContactsName());
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean onItemLongClick(ContactsBean cb, View view, int position) {
////        Log.d(TAG, "onItemLongClick");
////        Toast.makeText(context, "onItemLongClick" + cb.getContactsId(), Toast.LENGTH_SHORT).show();
//        showPopupWindow(view, cb, position);
//        view.findViewById(R.id.recycler_list_arrow).setVisibility(View.VISIBLE);
//        sidebar.setVisibility(View.GONE);
//        return true;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.title_left://标题栏菜单
//                showPopupWindowTitle();
//                break;
//            /**
//             * 联系人 item弹出监听
//             */
//            case R.id.contacts_item_call://item弹出菜单 拨打电话
//                ContactsOrCallsUtil.getInstance().mCall(context, contactsTemp.getContactsName(), contactsTemp.getContactsNum().get(0));//拨打电话
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_message://item弹出菜单 发短信
//                //向指定号码发送短信
//                if (contactsTemp != null) {
//                    Uri smsToUri = Uri.parse("smsto:" + contactsTemp.getContactsNum());
//                    Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
//                    startActivity(mIntent);
//                }
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_delete://item弹出菜单 删除
//                //先new出一个监听器，设置好监听
//                DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case Dialog.BUTTON_POSITIVE:
//                                ContactsOrCallsUtil.getInstance().deleteContacts(contactsTemp.getContactsId(), context);
//                                contactsRecyclerAdapter.removeData(currentPosition);
//                                break;
//                            case Dialog.BUTTON_NEGATIVE:
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                };
//                //dialog参数设置
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
//                builder.setTitle(context.getString(R.string.TIPS)); //设置标题
//                builder.setMessage(context.getString(R.string.tips_whether_delete)); //设置内容
//                builder.setIcon(R.mipmap.ic_launcher_new);//设置图标，图片id即可
//                builder.setPositiveButton(context.getString(R.string.tips_confirm), dialogOnclicListener);
//                builder.setNegativeButton(context.getString(R.string.tips_cancle), dialogOnclicListener);
//                builder.create().show();
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_edit://item弹出菜单 编辑
////                Toast.makeText(context, "onClick----edit", Toast.LENGTH_SHORT).show();
//                ContactsOrCallsUtil.getInstance().editContacts(context, contactsTemp.getContactsId());
//                popupWindowItemMenu.dismiss();
//                break;
//            case R.id.contacts_item_collect://item弹出菜单 收藏
//                Toast.makeText(context, "onClick----collect", Toast.LENGTH_SHORT).show();
//                popupWindowItemMenu.dismiss();
//                break;
//        }
//    }
//
////    //设置手势识别监听器
////    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener
////    {
////        @Override//此方法必须重写且返回真，否则onFling不起效
////        public boolean onDown(MotionEvent e) {
////            return true;
////        }
////
////        @Override
////        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
////            if(e1 != null && e2 != null){
////                if((e1.getX()- e2.getX()>120)&&Math.abs(velocityX)>200){//右滑
////                    return true;
////                }else if((e2.getX()- e1.getX()>120)&&Math.abs(velocityX)>200){//左滑
////                    showPopupWindowTitle();
////                    Log.d(TAG, "----左滑");
////                    return true;
////                }
////            }
////            return false;
////        }
////    }
////
////    //
////    private class MyOnTouchListener implements View.OnTouchListener {
////
////        @Override
////        public boolean onTouch(View view, MotionEvent motionEvent) {
////            return gestureDetector.onTouchEvent(motionEvent);//返回手势识别触发的事件
////        }
////    }
//
//}
