//package com.astgo.fenxiao.activity;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.CallLog;
//import android.provider.ContactsContract;
//import android.support.v4.content.CursorLoader;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.astgo.fenxiao.ConstantsRes;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.adapter.ContactsDetailsCallsAdapter;
//import com.astgo.fenxiao.adapter.ContactsDetailsInfoAdapter;
//import com.astgo.fenxiao.adapter.ContactsDetailsRecordingAdapter;
//import com.astgo.fenxiao.adapter.ContactsViewPagerAdapter;
//import com.astgo.fenxiao.bean.CallsBean;
//import com.astgo.fenxiao.tools.ContactsOrCallsUtil;
//import com.astgo.fenxiao.tools.FileUtil;
//import com.astgo.fenxiao.tools.MyLayoutManager;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.List;
///**
// * Created by Administrator on 2016/2/22.
// * 联系人详情界面
// */
//public class ContactsDetailsActivity extends AppCompatActivity implements View.OnClickListener, ContactsDetailsInfoAdapter.OnRecyclerViewListener {
//    private static final String TAG = "联系人详情界面";
//    //传递过来的数据
//    private int contactsId;//联系人Id
//    private String contactsTitle;//界面标题
//    //联系人的电话号码
////    private List<String> phones;
////    上部背景
//    private RelativeLayout contacts_details_bg;
//    //界面中部选项卡
//    private TextView contacts_details_calls, contacts_details_info, contacts_details_callrecorder;
//    private View contacts_tab_bar_subScript;
//    private int screenW = 0;// 屏幕宽度
//    private int offsetW = 50;//tab_bar下的view偏移量
//    private static int currIndex = 1;// 当前页卡编号
//    //可滑动ViewPager
//    private ViewPager mPager;
//    private List<View> listViews;
//    //详细信息界面
//    private List<String> phones;//存放详细信息数据
//    private RecyclerView recyclerViewInfo;//详细信息号码列表
//    private ContactsDetailsInfoAdapter contactsDetailsInfoAdapter;//详细信息号码列表适配器
//    // sdcard中的图片名称
//    private static final String FILE_NAME = "/share_pic.jpg";
//    public static String TEST_IMAGE;
//    //通话记录界面
//    private LinearLayout noDataView;
//    private List<CallsBean> callsBeans;//存放通话记录数据
//    private RecyclerView recyclerViewCalls;//详细信息通话记录列表
//    private ContactsDetailsCallsAdapter contactsDetailsCallsAdapter;//详细信息通话记录适配器
//    private LinearLayoutManager layoutManagerInfo;
//    //录音界面
//    private LinearLayout reNoData;
//    private List<File> files;//录音文件列表
//    private RecyclerView recyclerViewRecordings;
//    private ContactsDetailsRecordingAdapter contactsDetailsRecordingAdapter;//详细信息录音记录适配器
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contacts_details);
//        //接收传入的电话号码与标题
//        Intent intent = getIntent();
//        if (intent != null) {
//            contactsId = intent.getIntExtra(MyConstant.CONTACTS_ID, 0);
//            contactsTitle = intent.getStringExtra(MyConstant.CONTACTS_TAG);
//            Log.d(TAG, "contactsId" + contactsId);
//            Log.d(TAG, "contactsTitle" + contactsTitle);
//        }
//        initView();
//        EventBus.getDefault().register(this);
//    }
//    @Subscribe
//    public void getMsg(String str){
//        if (str.equals("change")){
//            initViewPager();
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
//
//    private void initView() {
//        initTitleView();
//        initTabBar();
//        initViewPager();
//        initImagePath();//初始化分享图片路径
//    }
//
//    /**
//     * 初始化Title
//     */
//    private void initTitleView() {
//        RelativeLayout base_title = (RelativeLayout) findViewById(R.id.base_title);
//        base_title.setBackgroundColor(getResources().getColor(ConstantsRes.contactsBgColor[contactsId % 6]));
//        TextView title_tv = ((TextView) findViewById(R.id.title_tv));
//        ImageView title_left = (ImageView) findViewById(R.id.title_left);
//        title_tv.setTextColor(getResources().getColor(R.color.theme_white));
//        title_tv.setText(contactsTitle);
//        title_left.setImageResource(R.drawable.contacts_details_back);
//        title_left.setOnClickListener(this);
//    }
//
//    /**
//     * 初始化选项卡切换栏
//     */
//    private void initTabBar() {
//        contacts_details_bg = (RelativeLayout) findViewById(R.id.contacts_details_bg);
//        contacts_details_bg.setBackgroundColor(getResources().getColor(ConstantsRes.contactsBgColor[contactsId % 6]));
//        contacts_details_calls = (TextView) findViewById(R.id.contacts_details_calls);
//        contacts_details_info = (TextView) findViewById(R.id.contacts_details_info);
//        contacts_details_callrecorder = (TextView) findViewById(R.id.contacts_details_callrecorder);
//        contacts_tab_bar_subScript = findViewById(R.id.contacts_details_tab_bar_subScript);
//        contacts_details_calls.setOnClickListener(this);
//        contacts_details_info.setOnClickListener(this);
//        contacts_details_callrecorder.setOnClickListener(this);
//        //获取屏幕宽度
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        screenW = dm.widthPixels;
//        //设置tab下标宽度
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) contacts_tab_bar_subScript.getLayoutParams();
//        params.width = screenW / 3 - offsetW * 2;
//        params.setMargins(offsetW + screenW / 3, 0, 0, 0);
//        contacts_tab_bar_subScript.setLayoutParams(params);
//    }
//
//    /**
//     * 初始化ViewPager
//     */
//    private void initViewPager() {
//        mPager = (ViewPager) findViewById(R.id.contacts_details_viewpager);
//        listViews = new ArrayList<>();
//        View view1 = View.inflate(getApplicationContext(), R.layout.view_contacts_details_calls, null);
//        View view2 = View.inflate(getApplicationContext(), R.layout.view_contacts_details_info, null);
//        View view3 = View.inflate(getApplicationContext(), R.layout.view_contacts_details_callrecorder, null);
//        listViews.add(view1);
//        listViews.add(view2);
//        listViews.add(view3);
//        mPager.setAdapter(new ContactsViewPagerAdapter(listViews));
//        mPager.setCurrentItem(1);
//        setTabBarText(1);
//        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
//        initViewDetailsCalls(view1);
//        initViewDetailsInfo(view2);
//        initViewDetailsCallRecorder(view3);
//        initDataDetailsCalls();
//        initDataDetailsInfo();
//        initDataDetailsRecording();
//    }
//
//    /**
//     * 初始化ViewPager通话记录界面
//     */
//    private void initViewDetailsCalls(View view) {
//        callsBeans = new ArrayList<>();
//        noDataView = (LinearLayout) view.findViewById(R.id.contacts_dc_nodata);
//        recyclerViewCalls = (RecyclerView) view.findViewById(R.id.recycler_list_calls);
//        recyclerViewCalls.setHasFixedSize(true);
//        layoutManagerInfo = new MyLayoutManager(this);
//        recyclerViewCalls.setLayoutManager(layoutManagerInfo);
//        contactsDetailsCallsAdapter = new ContactsDetailsCallsAdapter(getApplicationContext(), callsBeans);
//        recyclerViewCalls.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewCalls.setAdapter(contactsDetailsCallsAdapter);
//    }
//
//    /**
//     * 初始化ViewPager详细信息界面
//     */
//    private void initViewDetailsInfo(View view) {
//        phones = new ArrayList<>();
//        recyclerViewInfo = (RecyclerView) view.findViewById(R.id.recycler_list_info);
//        recyclerViewInfo.setHasFixedSize(true);
//        ImageView share_iv = (ImageView) view.findViewById(R.id.contacts_details_share);
//        share_iv.setImageResource(ConstantsRes.contatsShareRes[contactsId % 6]);
//        view.findViewById(R.id.contacts_details_share_ll).setOnClickListener(this);
//        layoutManagerInfo = new MyLayoutManager(this);
//        recyclerViewInfo.setLayoutManager(layoutManagerInfo);
//        contactsDetailsInfoAdapter = new ContactsDetailsInfoAdapter(getApplicationContext(), phones, contactsId);
//        recyclerViewInfo.setItemAnimator(new DefaultItemAnimator());
//        recyclerViewInfo.setAdapter(contactsDetailsInfoAdapter);
//        contactsDetailsInfoAdapter.setOnRecyclerViewListener(this);
//    }
//
//    /**
//     * 初始化ViewPager通话录音详情界面
//     */
//    private void initViewDetailsCallRecorder(View view) {
//        files = new ArrayList<>();
//        reNoData = (LinearLayout) view.findViewById(R.id.re_no_data);
//        recyclerViewRecordings = (RecyclerView) view.findViewById(R.id.recycler_list_recordings);
//        recyclerViewRecordings.setHasFixedSize(true);
//        layoutManagerInfo = new MyLayoutManager(this);
//        recyclerViewRecordings.setLayoutManager(layoutManagerInfo);
//        contactsDetailsRecordingAdapter = new ContactsDetailsRecordingAdapter(getApplicationContext(), files);
//        recyclerViewRecordings.setAdapter(contactsDetailsRecordingAdapter);
//    }
//
//    /**
//     * 加载ViewPager通话记录界面数据
//     */
//    private void initDataDetailsCalls() {
//        if (contactsId == 0) {//如果为空说明为陌生号码
//            callsBeans = searchCalls(CallLog.Calls.NUMBER + "=?", contactsTitle);//根据号码查询
//        } else {
//            callsBeans = searchCalls(CallLog.Calls.CACHED_NAME + "=?", contactsTitle);//根据姓名查询
//        }
//        if (callsBeans.size() == 0) {
//            noDataView.setVisibility(View.VISIBLE);
//        } else {
//            noDataView.setVisibility(View.GONE);
//            contactsDetailsCallsAdapter.updateChanged(callsBeans);
//        }
//    }
//
//    /**
//     * 加载ViewPager详细信息界面数据
//     */
//    private void initDataDetailsInfo() {
//        phones = searchContact(contactsId);
//        contactsDetailsInfoAdapter.updateChanged(phones);
//    }
//
//    /**
//     * 加载ViewPager录音信息界面数据
//     */
//    private void initDataDetailsRecording() {
//        if(phones .size() >0){
//            files = FileUtil.searchWav(getApplicationContext().getFilesDir().getAbsolutePath(), phones.get(0));
//            if (files == null || files.isEmpty()) {
//                reNoData.setVisibility(View.VISIBLE);
//            } else {
//                reNoData.setVisibility(View.GONE);
//            }
//            contactsDetailsRecordingAdapter.updateChanged(files);
//        }
//    }
//
//
//    // 给adpter返回当前页面position用于更新某一页面
//    public static int getCurrentPagerIdx() {
//        return currIndex;
//
//    }
//
//    /**
//     * 设置tab文字颜色
//     */
//    private void setTabBarText(int position) {
//        switch (position) {
//            case 0:
//                contacts_details_calls.setTextColor(getResources().getColor(R.color.theme_white));
//                contacts_details_info.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                contacts_details_callrecorder.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                break;
//            case 1:
//                contacts_details_calls.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                contacts_details_info.setTextColor(getResources().getColor(R.color.theme_white));
//                contacts_details_callrecorder.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                break;
//            case 2:
//                contacts_details_calls.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                contacts_details_info.setTextColor(getResources().getColor(R.color.theme_white_dark));
//                contacts_details_callrecorder.setTextColor(getResources().getColor(R.color.theme_white));
//                break;
//        }
//    }
//
//    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
//
//        int one = screenW / 3;// 页卡1 -> 页卡2 偏移量
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            Animation animation = null;
//            switch (position) {
//                case 0:
//                    if (currIndex == 1) {
//                        animation = new TranslateAnimation(0, -one, 0, 0);
//                    } else if (currIndex == 2) {
//                        animation = new TranslateAnimation(one, -one, 0, 0);
//                    }
//                    break;
//                case 1:
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(-one, 0, 0, 0);
//                    } else if (currIndex == 2) {
//                        animation = new TranslateAnimation(one, 0, 0, 0);
//                    }
//                    break;
//                case 2:
//                    if (currIndex == 0) {
//                        animation = new TranslateAnimation(-one, one, 0, 0);
//                    } else if (currIndex == 1) {
//                        animation = new TranslateAnimation(0, one, 0, 0);
//                    }
//                    break;
//            }
//            currIndex = position;
//            if (animation != null) {
//                animation.setFillAfter(true);// True:图片停在动画结束位置
//                animation.setDuration(100);
//                contacts_tab_bar_subScript.startAnimation(animation);
//                setTabBarText(position);
//                if (position == 0) {
//                    initDataDetailsCalls();
////                    mPager.getAdapter().notifyDataSetChanged();
//                }
//                if (position == 1) {
//                    initDataDetailsInfo();
////                    mPager.getAdapter();
//                }
//                if (position == 2) {
//
////                    mPager.getAdapter().notifyDataSetChanged();
//                }
//            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//
//        }
//
//    }
//
//    /**
//     * @param contactsId 通过联系人id查询联系人信息
//     */
//    private List<String> searchContact(int contactsId) {
//        List<String> phones = new ArrayList<>();
//        if (contactsId == 0) {//如果为空说明为陌生号码
//            phones.add(contactsTitle);
//            return phones;
//        } else {
//            CursorLoader cl = new CursorLoader(
//                    this,
//                    ContactsContract.Contacts.CONTENT_URI,
//                    new String[]{
//                            ContactsContract.Contacts._ID,
//                            ContactsContract.Contacts.DISPLAY_NAME,
//                            ContactsContract.Contacts.HAS_PHONE_NUMBER,//是否有号码
//                            ContactsContract.Contacts.LOOKUP_KEY//用来查询号码的key值（此值不会因为id联系人改变而改变 ）
//                    },
//                    ContactsContract.Contacts._ID + "=?",
//                    new String[]{
//                            contactsId + ""
//                    },
//                    null
//            );
//
//            Cursor cursor = cl.loadInBackground();
//
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    phones = getAllPhoneNumbers(cursor.getString(3));
//                }
//                cursor.close();
//            }
//            return phones;
//        }
//    }
//
//    /**
//     * 通过lookUp_key获取一个联系人下的所有号码
//     *
//     * @param lookUp_Key 联系人唯一标识
//     */
//    private List<String> getAllPhoneNumbers(String lookUp_Key) {
//        List<String> phones = new ArrayList<>();
//        // Phone info are stored in the ContactsContract.Data table
//        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        String[] project = {ContactsContract.CommonDataKinds.Phone.NUMBER};
//        // using lookUp key to search the phone numbers
//        String selection = ContactsContract.Data.LOOKUP_KEY + "=?";
//
//        Cursor cur = getContentResolver().query(phoneUri, project, selection, new String[]{lookUp_Key}, null);
//        if (cur != null) {
//            while (cur.moveToNext()) {
//                phones.add(cur.getString(0));
//            }
//            cur.close();
//        }
//        return phones;
//    }
//
//    /**
//     * 通话通话记录标识查询相关所有通话记录
//     *
//     * @param whereSql 根据哪个条件查询 例： CallLog.Calls.CACHED_NAME=？
//     * @param callsTag 查询条件的值
//     */
//    private List<CallsBean> searchCalls(String whereSql, String callsTag) {
//        List<CallsBean> callsBeans = new ArrayList<>();
//        CursorLoader cl = new CursorLoader(
//                getApplicationContext(),
//                CallLog.Calls.CONTENT_URI,
//                new String[]{
//                        CallLog.Calls._ID,//id
//                        CallLog.Calls.NUMBER,//电话号码
//                        CallLog.Calls.CACHED_NAME,//姓名
//                        CallLog.Calls.DATE,//通话时间
//                        CallLog.Calls.TYPE,//通话类型
//                        CallLog.Calls.DURATION//通话时长
//                },
//                whereSql,
//                new String[]{
//                        callsTag
//                },
//                CallLog.Calls.DEFAULT_SORT_ORDER
//        );
//        Cursor cursor = cl.loadInBackground();
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                CallsBean callsBean = new CallsBean();
//                callsBean.setCallsId(cursor.getInt(0));
//                callsBean.setCallsNum(cursor.getString(1));
//                callsBean.setCallsName(cursor.getString(2));
//                callsBean.setCallsTime(cursor.getLong(3));
//                callsBean.setCallsType(cursor.getInt(4));
//                callsBean.setCallsDurations(cursor.getLong(5));
//                callsBeans.add(callsBean);
//            }
//            cursor.close();
//        }
//        return callsBeans;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.title_left://返回按钮
//                finish();
//                break;
//            case R.id.contacts_details_calls://通话记录选项卡
//                mPager.setCurrentItem(0);
//                setTabBarText(0);
//                break;
//            case R.id.contacts_details_info://详细信息选项卡
//                mPager.setCurrentItem(1);
//                setTabBarText(1);
//                break;
//            case R.id.contacts_details_callrecorder://通话录音选项卡
//                mPager.setCurrentItem(2);
//                setTabBarText(2);
//                break;
//            case R.id.contacts_details_share_ll://分享按钮
//                String context = contactsTitle + ":" + phones.toString();
////                showShare(getApplicationContext(), context);
//                Uri uri = Uri.parse("smsto:" + "");
//                Intent sms = new Intent(Intent.ACTION_SENDTO, uri);
//                sms.putExtra("sms_body", context);
//                startActivity(sms);
//                break;
//        }
//    }
//
//    @Override
//    public void onItemClick(int position, View view) {
//        switch (view.getId()) {
//            case R.id.contacts_di_call:
//            case R.id.contacts_di_item:
//                String name;
//                if (contactsTitle.equals(phones.get(position))) {
//                    name = "";
//                } else {
//                    name = contactsTitle;
//                }
//                ContactsOrCallsUtil.getInstance().mCall(this, name, phones.get(position));//拨打电话
//                break;
//            case R.id.contacts_di_msg://向指定号码发送短信
//                Uri smsToUri = Uri.parse("smsto:" + phones.get(position));
//                Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//                startActivity(mIntent);
//                break;
//        }
//
//    }
//
//    @Override
//    public boolean onItemLongClick(int position, View view) {
//        return true;
//    }
//
////    /**
////     * 分享内容
////     */
////    private void showShare(Context context, String content) {
////        ShareSDK.initSDK(context);
////        OnekeyShare oks = new OnekeyShare();
////        //关闭sso授权
////        oks.disableSSOWhenAuthorize();
//////         分享时Notification的图标和文字
//////        oks.setNotification(R.drawable.ic_launcher_new, getString(R.string.app_name));
//////        oks.set
////        // title标题：微信、QQ（新浪微博不需要标题）
////        oks.setTitle(getString(R.string.app_name));
////        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
////        oks.setTitleUrl(MyApplication.mSpInformation.getString(MyConstant.SP_SHARE_URL, ""));
////        oks.setText(content);
////        oks.setImagePath(TEST_IMAGE);
//////        oks.setImagePath();
//////        oks.setImageUrl(NetURL.SHARE_IMG_URL);
//////         url仅在微信（包括好友和朋友圈）中使用
////        oks.setUrl(MyApplication.mSpInformation.getString(MyConstant.SP_SHARE_URL, ""));
//////         siteUrl是分享此内容的网站地址，仅在QQ空间使用
////        oks.setSiteUrl(MyApplication.mSpInformation.getString(MyConstant.SP_SHARE_URL, ""));
////        // 启动分享GUI
////        oks.show(context);
////    }
//
//    private void initImagePath() {
//        try {
//            if (Environment.MEDIA_MOUNTED.equals(Environment.
//                    getExternalStorageState())
//                    && Environment.getExternalStorageDirectory().exists()) {
//                TEST_IMAGE = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + FILE_NAME;
//            } else {
//                TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath()
//                        + FILE_NAME;
//            }
//            // 创建图片文件夹
//            File file = new File(TEST_IMAGE);
//            if (!file.exists()) {
//                file.createNewFile();
//                Bitmap pic = BitmapFactory.decodeResource(getResources(),
//                        R.mipmap.ic_launcher_new);
//                FileOutputStream fos = new FileOutputStream(file);
//                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                fos.flush();
//                fos.close();
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//            TEST_IMAGE = null;
//        }
//    }
//}
