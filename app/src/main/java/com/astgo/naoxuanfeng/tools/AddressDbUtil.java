//package com.astgo.fenxiao.tools;
//
//import android.util.Log;
//
//import com.astgo.fenxiao.MyApplication;
//import com.astgo.fenxiao.R;
//import com.astgo.fenxiao.bean.ContactsBean;
//import com.astgo.fenxiao.bean.ContactsTitleMenuBean;
//import com.astgo.fenxiao.bean.ContactsTitleMenuCityBean;
//import com.astgo.fenxiao.dao.PhoneAddInfo;
//import com.astgo.fenxiao.dao.PhoneAddInfoDao;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import de.greenrobot.dao.query.Query;
//
///**
// * Created by Administrator on 2016/2/23.
// * 归属地数据库操作工具
// */
//public class AddressDbUtil {
//    private static final String TAG = "归属地数据库操作工具AddressDbUtil";
//
//    /**
//     * 查询所有归属地信息
//     *
//     * @return list列表
//     */
//    public static List queryAddAllInfo() {
//
//        Query query = MyApplication.getInstance().getPhoneAddInfoDao().queryBuilder().build();
//        return query.list();
//    }
//
//    /**
//     * 批量插入归属地信息数据
//     */
//    public static void insertPhoneAddInfo(final List<PhoneAddInfo> list) {
//        if (list != null && !list.isEmpty()) {
//            PhoneAddInfoDao phoneAddInfoDao = MyApplication.getInstance().getPhoneAddInfoDao();
//            phoneAddInfoDao.getSession().runInTx(new Runnable() {
//                @Override
//                public void run() {
//                    for (PhoneAddInfo pai : list) {
//                        MyApplication.getInstance().getPhoneAddInfoDao().insertOrReplace(pai);
//                    }
//                    Log.d(TAG, "插入完成---" + list.size() + "条信息");
//                }
//            });
//        }
//
//    }
//
//    /**
//     * 联网获取号码归属地并更新数据库
//     */
//    public static void updatePhoneAddInfos(final List<String> mPhoneNums) {
//        JSONArray jsonArray = new JSONArray();
//        if (mPhoneNums != null) {
//            for (int i = 0; i < mPhoneNums.size(); i++) {
//                String temp = formatNum(mPhoneNums.get(i));
//                jsonArray.put(temp);
//            }
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("phoneList", jsonArray);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            WebServiceUtil.postJsonData(NetUrl.PHONE_ADDRESS, jsonObject, new Response.Listener<JSONObject>() {
////                @Override
////                public void onResponse(JSONObject jsonObject) {
////                    Log.i(TAG, jsonObject.toString());
////                    try {
////                        JSONArray list = jsonObject.getJSONArray("phoneList");
////                        List<PhoneAddInfo> phoneAddInfoList = new ArrayList<PhoneAddInfo>();
////                        for (int i = 0; i < list.length(); i++) {
////                            PhoneAddInfo phoneAddInfo = new PhoneAddInfo();
////                            JSONObject ob = (JSONObject) list.get(i);
////                            phoneAddInfo.setPhoneNum(formatNum(mPhoneNums.get(i)));
////                            phoneAddInfo.setCarrier(ob.getString("carrier"));
////                            phoneAddInfo.setCity(ob.getString("city"));
//////                            phoneAddInfo.setFriend(ob.getInt("status"));
////                            if ("".equals(ob.getString("province"))) {
////                                phoneAddInfo.setProvince("未知归属地");
////                            }else{
////                                phoneAddInfo.setProvince(ob.getString("province"));
////                            }
////                            phoneAddInfoList.add(phoneAddInfo);
////                        }
////                        insertPhoneAddInfo(phoneAddInfoList);
////                        Log.d(TAG, "数据库表长度---" + queryAddAllInfo().size());
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError volleyError) {
////                    Log.e(TAG, volleyError.toString());
////                }
////            });
//        }
//    }
//
//    /**
//     * 获取某个号码的归属地
//     *
//     * @param phoneNum 号码
//     */
//    public static String getPhoneAddress(String phoneNum) {
//        Query query = MyApplication.getInstance().getPhoneAddInfoDao().queryBuilder().where(PhoneAddInfoDao.Properties.PhoneNum.eq(formatNum(phoneNum))).build();
//        PhoneAddInfo phoneAddInfo = (PhoneAddInfo) query.unique();
//        if (phoneAddInfo != null) {//如果本地数据库存在
//            return phoneAddInfo.getProvince() + phoneAddInfo.getCity() + phoneAddInfo.getCarrier();
//        } else {//如果本地数据库不存在
//            List<String> phoneNums = new ArrayList<>();
//            phoneNums.add(phoneNum);
//            updatePhoneAddInfos(phoneNums);//联网获取号码归属地并更新数据库
//            return "未知归属地";
//        }
//    }
//
//    /**
//     * 获取某个号码是否是好友
//     *
//     * @param phoneNum 号码
//     */
//    public static int getPhoneFriend(String phoneNum) {
//        Query query = MyApplication.getInstance().getPhoneAddInfoDao().queryBuilder().where(PhoneAddInfoDao.Properties.PhoneNum.eq(formatNum(phoneNum))).build();
//        PhoneAddInfo phoneAddInfo = (PhoneAddInfo) query.unique();
//        if (phoneAddInfo != null) {//如果本地数据库存在
//            return phoneAddInfo.getFriend();
//        } else {//如果不存在
//            return 0;
//        }
//    }
//
//
//    /**
//     * 获取title菜单中的归属地分类数据
//     */
//    public static List<ContactsTitleMenuBean> getContactsTitleMenuData(List<ContactsBean> contactsBeans) {
//        List<ContactsTitleMenuBean> titleMenuBeanList = new ArrayList<>();
//        titleMenuBeanList.add(new ContactsTitleMenuBean(
//                R.drawable.contacts_title_menu_all, "全部联系人", 0, 0, null));//没有子分组，所以不需要展开图片
//        ContactsTitleMenuBean contactsTitleMenuBean = new ContactsTitleMenuBean(
//                R.drawable.contacts_title_menu_location, "城市分组", R.drawable.contacts_title_memu_arrow_n, R.drawable.contacts_title_memu_arrow_p, null);
//        List<ContactsBean> copyList = null;//当前联系人
//        try {
//            copyList = deepCopy(contactsBeans);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        List<ContactsTitleMenuCityBean> childList = new ArrayList<>();//存放归属地分组数据的列表
//        while (copyList.size() != 0) {
//            String currentCity = copyList.get(0).getContactsCity();//获取第一个号码归属地
//            ContactsTitleMenuCityBean child = new ContactsTitleMenuCityBean(currentCity, null);//创建此归属地列表
//            List<ContactsBean> childCbs = new ArrayList<>();//存放属于此归属地的联系人
//            childCbs.add(copyList.get(0));//将此联系人存放
//            copyList.remove(0);//从列表中移除这个联系人
//            List<ContactsBean> deleteList = new ArrayList<>();//存放需要删除的对象
//            for (ContactsBean cb : copyList) {//循环遍历列表剩下的联系人
//                if (currentCity.equals(cb.getContactsCity())) {//当剩下的联系人与第一个的归属地相同
//                    childCbs.add(cb);//将此联系人Id插入childIds
//                    deleteList.add(cb);//从列表中移除这个联系人
//                }
//            }
//            copyList.removeAll(deleteList);//删除已经添加的对象
//            child.setCbs(childCbs);//将属于此归属地联系人列表放入此归属地的对象中
//            childList.add(child);//将此归属地对象放入归属地分组列表中
//        }
//        Collections.sort(childList);
//        contactsTitleMenuBean.setChildren(childList);
//        titleMenuBeanList.add(contactsTitleMenuBean);
//        return titleMenuBeanList;
//    }
//
//    /**
//     * 格式化号码， 去掉“-” “+86”等
//     */
//    public static String formatNum(String num) {
//        return num.replaceAll("-", "").replaceAll("\\+86", "").replaceAll(" ", "");
//    }
//
//    /**
//     * 深拷贝ArrayList列表
//     * 列表中的对象必须序列化
//     *
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public static List deepCopy(List src) throws IOException, ClassNotFoundException {
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        ObjectOutputStream out = new ObjectOutputStream(byteOut);
//        out.writeObject(src);
//        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
//        ObjectInputStream in = new ObjectInputStream(byteIn);
//        List dest = (List) in.readObject();
//        return dest;
//    }
//
//}
