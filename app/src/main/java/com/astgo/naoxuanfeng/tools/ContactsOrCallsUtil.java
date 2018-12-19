//package com.astgo.fenxiao.tools;
//
//import android.content.ContentProviderOperation;
//import android.content.ContentProviderResult;
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.OperationApplicationException;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.RemoteException;
//import android.provider.CallLog;
//import android.provider.ContactsContract;
//import android.support.v4.content.CursorLoader;
//import android.util.Log;
//
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.MyConstant;
//import com.astgo.fenxiao.activity.call.CallBackActivity;
//import com.astgo.fenxiao.activity.call.CallDirectActivity;
//import com.astgo.fenxiao.bean.CallsBean;
//import com.astgo.fenxiao.bean.ContactsBean;
//import com.astgo.fenxiao.dao.PhoneAddInfo;
//import com.astgo.fenxiao.dao.PhoneAddInfoDao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import de.greenrobot.dao.query.Query;
//
///**
// * Created by Administrator on 2016/2/27.
// * 查询联系人或者查询通话记录相关工具类
// */
//public class ContactsOrCallsUtil {
//    private static final String TAG = "ContactsOrCallsUtil工具类";
//    public static ContactsOrCallsUtil cocUtil;//工具类对象
//    //联系人相关
//    public static final String[] PROJECTION1 =
//            new String[]{ContactsContract.Contacts.DISPLAY_NAME,//姓名
//                    ContactsContract.CommonDataKinds.Phone.NUMBER,//号码
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,//ID
//                    ContactsContract.CommonDataKinds.Phone.PHOTO_ID,//头像ID
//                    ContactsContract.Contacts.SORT_KEY_PRIMARY};
//
//    //通话记录相关
//    public static final String[] PROJECTION2 =
//            new String[]{
//                    CallLog.Calls._ID,//id
//                    CallLog.Calls.NUMBER,//电话号码
//                    CallLog.Calls.CACHED_NAME,//姓名
//                    CallLog.Calls.DATE,//通话时间
//                    CallLog.Calls.TYPE,//通话类型
//                    CallLog.Calls.DURATION//通话时长
//            };
//
//    public static ContactsOrCallsUtil getInstance() {
//        if (cocUtil == null) {
//            cocUtil = new ContactsOrCallsUtil();
//        }
//        return cocUtil;
//    }
//
//    /**
//     * 更新联系人数据
//     */
//    public void updateContactsData(Context context) {
//        // 初始化联系人 Contact ID 索引（用来去重）
//        MyApplication.mIDs = new ArrayList<>();
//        // 初始化数据集
//        MyApplication.mDataSet1 = new ArrayList<>();
//        MyApplication.mPhoneNum = new ArrayList<>();
//        CursorLoader cl = new CursorLoader(
//                context,
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                PROJECTION1,
////                "account_type!='com.tencent.mm.account'",
//                null,
//                null,
//                ContactsContract.Contacts.SORT_KEY_PRIMARY
//        );
//
//        Cursor cursor = cl.loadInBackground();
//        if (cursor != null) {
//            int i = 0;
//            while (cursor.moveToNext()) {
//                ContactsBean contactsBean = new ContactsBean();
//                contactsBean.setContactsId(cursor.getInt(2));
//                contactsBean.setContactsAlpha(Pinyin4JUtil.getAlpha(Pinyin4JUtil.getPinYinHeadChar(cursor.getString(0))));
//                contactsBean.setInitialNum(initialToNum(Pinyin4JUtil.getPinYinHeadChar(cursor.getString(0))));
//                contactsBean.setContactsName(cursor.getString(0));
//                contactsBean.addContactsNum(cursor.getString(1));
//                contactsBean.setContactsPhotoId(cursor.getLong(3));
//                Query query = MyApplication.getInstance().getPhoneAddInfoDao().queryBuilder().where(
//                        PhoneAddInfoDao.Properties.PhoneNum.eq(AddressDbUtil.formatNum(cursor.getString(1)))).build();
//                PhoneAddInfo phoneAddInfo = (PhoneAddInfo) query.unique();
////                此处为保证速度暂不联网请求
//                if (phoneAddInfo != null) {//如果本地数据库存在此归属地
//                    contactsBean.setContactsCity(phoneAddInfo.getCity());
//                } else {//为空
//                    contactsBean.setContactsCity("");
//                }
//                if (!MyApplication.mIDs.contains(cursor.getInt(2))) {
//                    MyApplication.mDataSet1.add(contactsBean);
//                } else {//一个联系人多个号码
//                    MyApplication.mDataSet1.get(MyApplication.mDataSet1.size() - 1).addContactsNum(cursor.getString(1));//将号码插入上个联系人
//                }
//                MyApplication.mIDs.add(cursor.getInt(2));
//                MyApplication.mPhoneNum.add(AddressDbUtil.formatNum(cursor.getString(1)));//所有号码
//                // 联系人存放数据集(每个联系人只有一个号码，并且号码已经去除+86等字符，用于检索)
//                /**
//                 * 保存为contentValues是为了减少查询时的时间，
//                 * contentValues只能保存基本类型，所以检索的效率能大大增加
//                 */
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(MyConstant.CONTACT_ID, contactsBean.getContactsId());
//                contentValues.put(MyConstant.CONTACT_NAME, contactsBean.getContactsName());
//                contentValues.put(MyConstant.CONTACT_NUMBER, AddressDbUtil.formatNum(contactsBean.getContactsNum().get(0)));
//                contentValues.put(MyConstant.CONTACT_INITIAL, contactsBean.getInitialNum());
//                contentValues.put(MyConstant.CONTACT_STATE_NAME, 0);
//                contentValues.put(MyConstant.CONTACT_STATE_NUM, 0);
//                MyApplication.mDataSet2.add(contentValues);
//            }
//            cursor.close();
//        }
//        Log.d(TAG, "updateContactsData");
//        Log.d(TAG, "mDataSet1--" + MyApplication.mDataSet1.size());
//        Log.d(TAG, "mDataSet2--" + MyApplication.mDataSet2.size());
//        Log.d(TAG, "mPhoneNum--" + MyApplication.mPhoneNum.size());
//        Log.d(TAG, "mIDs--" + MyApplication.mIDs.size());
//    }
//
//    /**
//     * 通过号码获取id
//     *
//     * @param phoneNum 通话记录号码
//     * @return 联系人id 为""时说明为陌生联系人
//     */
//    public int fromPhoneNumToContactsId(String phoneNum) {
//        if (MyApplication.mPhoneNum != null) {
//            for (int i = 0; i < MyApplication.mPhoneNum.size(); i++) {
//                if (MyApplication.mPhoneNum.get(i).equals(phoneNum)) {
//                    return MyApplication.mIDs.get(i);
//                }
//            }
//        }
//        return 0;
//    }
//
//    /**
//     * 通过号码获取名字
//     *
//     * @param phoneNum 通话记录号码
//     * @return 联系人名字 为null时说明为陌生联系人
//     */
//    public String fromPhoneNumToContactsName(String phoneNum) {
//        if (MyApplication.mDataSet2 != null) {
//            for (int i = 0; i < MyApplication.mDataSet2.size(); i++) {
//                if (MyApplication.mDataSet2.get(i).getAsString(MyConstant.CONTACT_NUMBER).equals(AddressDbUtil.formatNum(phoneNum))) {
//                    return MyApplication.mDataSet2.get(i).getAsString(MyConstant.CONTACT_NAME);
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 查询出通话记录数据
//     */
//    public List<CallsBean> getCallsData(Context context) {
//        List<String> mNums = MyApplication.mPhoneNum;//先查询出以保存的号码用于之后判断此通话记录是否是陌生号码
//        List<CallsBean> mDataSet2 = new ArrayList<CallsBean>();
//        CursorLoader cl = new CursorLoader(
//                context,
//                CallLog.Calls.CONTENT_URI,
//                PROJECTION2,
//                null,
//                null,
//                CallLog.Calls.DEFAULT_SORT_ORDER
//        );
//
//        Cursor cursor = cl.loadInBackground();
//        if (cursor != null) {
//            CallsBean temp = new CallsBean();//存放临时变量用来合并相邻且号码相同的记录
//            if (cursor.moveToFirst()) {//将第一条通话记录其保存到临时变量里面不显示
//                temp.setCallsId(cursor.getInt(0));
//                temp.setCallsNum(cursor.getString(1));
//                temp.setCallsName(cursor.getString(2));
//                temp.setCallsTime(cursor.getLong(3));
//                temp.setCallsType(cursor.getInt(4));
//                temp.setCallsDurations(cursor.getLong(5));
//                if (mNums != null) {
//                    //判断是否为陌生号码
//                    if (cursor.getString(2) != null || mNums.contains(AddressDbUtil.formatNum(temp.getCallsNum()))) {
//                        temp.setCallsNewNum(false);
//                    } else {
//                        temp.setCallsNewNum(true);
//                    }
//                }
//                if (cursor.isLast()) {//当记录即为第一条又是最后一条时（仅一条记录）就显示
//                    mDataSet2.add(temp);
//                }
//            }
//            while (cursor.moveToNext()) {
//                if (!temp.getCallsNum().isEmpty() && temp.getCallsNum().equals(cursor.getString(1))) {//当此条记录号码与上一条相同时
//                    temp.addCallsSameId(cursor.getInt(0));//将此条记录id写入上条记录的callsSameId属性中
//                } else {//如果不相同
//                    CallsBean callsBean = new CallsBean();
//                    callsBean.setCallsId(cursor.getInt(0));
//                    callsBean.setCallsNum(cursor.getString(1));
//                    callsBean.setCallsName(cursor.getString(2));
//                    callsBean.setCallsTime(cursor.getLong(3));
//                    callsBean.setCallsType(cursor.getInt(4));
//                    callsBean.setCallsDurations(cursor.getLong(5));
//                    if (mNums != null) {
//                        //判断是否为陌生号码
//                        if (cursor.getString(2) != null || mNums.contains(AddressDbUtil.formatNum(callsBean.getCallsNum()))) {
//                            callsBean.setCallsNewNum(false);
//                        } else {
//                            callsBean.setCallsNewNum(true);
//                        }
//                    }
//                    mDataSet2.add(temp);//将临时变量放入显示列表中
//                    temp = callsBean;//更新临时变量为此条记录
//                    if (cursor.isLast()) {//如果此条记录为最后一条则将临时变量也加入显示列表
//                        mDataSet2.add(temp);
//                    }
//                }
//            }
//            cursor.close();
//        }
//        return mDataSet2;
//    }
//
//
//    /**
//     * 根据Id删除通话记录
//     *
//     * @param ids
//     */
//    public void deleteCalls(List<Integer> ids, Context context) {
//        ContentResolver resolver = context.getContentResolver();
//        for (Integer id : ids) {
//            resolver.delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[]{id + ""});
//        }
//        Log.d(TAG, "删除Calls---" + ids.size());
//    }
//
//    /**
//     * 删除所有通话记录
//     */
//    public void deleteCallsAll(Context context) {
//        ContentResolver resolver = context.getContentResolver();
//        resolver.delete(CallLog.Calls.CONTENT_URI, null, null);
//        Log.d(TAG, "删除Calls---all");
//    }
//
//    /**
//     * 根据Id删除联系人
//     */
//    public void deleteContacts(int id, Context context) {
//        ContentResolver resolver = context.getContentResolver();
//        resolver.delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id + ""});
//        Log.d(TAG, "删除Contacts---" + id);
//    }
//
//    /**
//     * 根据联系人id跳转编辑联系人界面
//     *
//     * @param context
//     * @param id
//     */
//    public void editContacts(Context context, int id) {
//        Intent editIntent = new Intent(Intent.ACTION_EDIT);
//        Uri content_lookup_uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//        //注意：当既要给Intent设置Data属性又要设置Type属性时，必须使用setDataAndType()方法
//        //否则会得到异常：
//        //（Error loading the contact: null java.lang.IllegalArgumentException: uri must not null）
//        //例如，使用setData()然后再使用setType()，后者方法会清除前者设置的属性。或者在new Intent对象时，使用的构造方法是：
//        // Intent(String action, Uri uri)或者
//        //Intent(String action, Uri uri,Context packageContext, Class<?> cls)然后使用setType()设置的属性
//        editIntent.setDataAndType(content_lookup_uri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
//        context.startActivity(editIntent);
//    }
//
//    /**
//     * 跳转添加联系人界面
//     */
//    public void addContacts(Context context, String name, String phone) {
//        Intent intent = new Intent(Intent.ACTION_INSERT);
//        intent.setType("vnd.android.cursor.dir/person");
//        intent.setType("vnd.android.cursor.dir/contact");
//        intent.setType("vnd.android.cursor.dir/raw_contact");
//        context.startActivity(intent);
//    }
//
//    /**
//     * 跳转发送短信界面
//     */
//    public void sendMessage(Context context, String number) {
//        Uri smsToUri = Uri.parse("smsto:" + number);
//        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
//        context.startActivity(mIntent);
//    }
//
//    /**
//     * 将用户对应的首字母变成拨号键盘对应的数字
//     * 用于联系人检索
//     *
//     * @return 对应的拨号键盘数字
//     */
//    private String initialToNum(String initial) {
//        StringBuffer strTemp = new StringBuffer();
//        for (int i = 0; i < initial.length(); i++) {
//            switch (initial.charAt(i)) {
//                case 'a':
//                case 'b':
//                case 'c':
//                    strTemp.append(2);
//                    break;
//                case 'd':
//                case 'e':
//                case 'f':
//                    strTemp.append(3);
//                    break;
//                case 'g':
//                case 'h':
//                case 'i':
//                    strTemp.append(4);
//                    break;
//                case 'j':
//                case 'k':
//                case 'l':
//                    strTemp.append(5);
//                    break;
//                case 'm':
//                case 'n':
//                case 'o':
//                    strTemp.append(6);
//                    break;
//                case 'p':
//                case 'q':
//                case 'r':
//                case 's':
//                    strTemp.append(7);
//                    break;
//                case 't':
//                case 'u':
//                case 'v':
//                    strTemp.append(8);
//                    break;
//                case 'w':
//                case 'x':
//                case 'y':
//                case 'z':
//                    strTemp.append(9);
//                    break;
//            }
//        }
//        return strTemp.toString();
//    }
//
//    //拨打电话
//    public void mCall(Context context, String name, String num) {
//        Intent mIntent;
//        if (MyApplication.mSpInformation.getInt(MyConstant.SP_SETTINGS_BDFS, MyConstant.CALL_BACK_TAG) == 0) {
//            mIntent = new Intent(context, CallDirectActivity.class);
//        } else {
//            mIntent = new Intent(context, CallBackActivity.class);
//        }
//        context.startActivity(mIntent);
//        MyConstant.cName = name;
//        MyConstant.cNum = AddressDbUtil.formatNum(num);
//    }
//
//    // 操作通话记录(添加)
//    public static void addCalls(Context context, String name, String number, long date, long duration) {
//        ContentValues cv = new ContentValues();
//        cv.put(CallLog.Calls.CACHED_NAME, name);
//        cv.put(CallLog.Calls.NUMBER, number);
//        cv.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
//        cv.put(CallLog.Calls.DATE, date);
//        cv.put(CallLog.Calls.DURATION, duration);
//        //保存
//        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, cv);
//        Log.e(TAG, "add---call");
//    }
//
//    /**
//     * 批量添加联系人，处于同一个事务中
//     * @param context 上下文
//     * @param name 要添加的联系人姓名
//     * @param phones 要添加的电话
//     * @throws Throwable
//     */
//    public static void addContacts(Context context, String name, List<String> phones) {
//        deleteContact(context, name);
//        //文档位置：reference\android\provider\ContactsContract.RawContacts.html
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//        int rawContactInsertIndex = 0;
//        //设置谷歌账号 默认为空
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());
//
//        //文档位置：reference\android\provider\ContactsContract.Data.html
//        //设置姓名
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
//                .withYieldAllowed(true)
//                .build());
//
//        if(phones.size() > 0){
//            for(int i = 0; i < phones.size(); i++){
//                //设置号码，类别为TYPE_OTHER，如果添加多个号码类别均设置为TYPE_OTHER,则不会覆盖
//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phones.get(i))
//                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_OTHER)
//                        .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "")
//                        .withYieldAllowed(true)
//                        .build());
//            }
//            Log.d("AddContacts", rawContactInsertIndex+"");
//        }
////        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
////                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
////                .build());
//
//        ContentProviderResult[] results = new ContentProviderResult[0];
//        try {
//            results = context
//                    .getContentResolver().applyBatch(ContactsContract.AUTHORITY,ops);
//            ops.clear();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (OperationApplicationException e) {
//            e.printStackTrace();
//        }
//        for (ContentProviderResult result : results) {
//            Log.i("AddContacts", result.uri.toString());
//        }
//    }
//
//
//    /**
//     * 根据Id删除联系人
//     */
//    /**
//     * Delete contacts who's name equals contact.getName();
//     */
//    public static void deleteContact(Context context, String name) {
//        Log.w("deleteContact", "**delete start**");
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
//        String id = getContactID(context, name);
//        //delete contact
//        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
//                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=" + id, null)
//                .build());
//        //delete contact information such as phone number,email
//        ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
//                .withSelection(ContactsContract.Data.CONTACT_ID + "=" + id, null)
//                .build());
//        Log.d("deleteContact", "delete contact: " + name);
//
//        try {
//            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//            Log.d("deleteContact", "delete contact success");
//        } catch (Exception e) {
//            Log.d("deleteContact", "delete contact failed");
//            Log.e("deleteContact", e.getMessage());
//        }
//        Log.w("deleteContact", "**delete end**");
//    }
//
//    /**
//     *
//     * @param name The contact who you get the id from. The name of
//     * the contact should be set.
//     * @return 0 if contact not exist in contacts list. Otherwise return
//     * the id of the contact.
//     */
//    public static String getContactID(Context context, String name) {
//        String id = "0";
//        Cursor cursor = context.getContentResolver().query(
//                android.provider.ContactsContract.Contacts.CONTENT_URI,
//                new String[]{android.provider.ContactsContract.Contacts._ID},
//                android.provider.ContactsContract.Contacts.DISPLAY_NAME +
//                        "='" + name + "'", null, null);
//        if(cursor.moveToNext()) {
//            id = cursor.getString(cursor.getColumnIndex(
//                    android.provider.ContactsContract.Contacts._ID));
//        }
//        return id;
//    }
//}
