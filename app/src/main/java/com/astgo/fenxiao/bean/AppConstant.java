package com.astgo.fenxiao.bean;

/**
 * Created by ast009 on 2017/10/25.
 */

public class AppConstant {
    public static final String PAYJSON = "payjson";
    public static String WX_APP_ID = "wx4eeaa909776fb55a";//wx4eeaa909776fb55a
    //SP key Account infor
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_OS_VERSION = "key_os_version";
    public static final String KEY_DEVICE_MODEL = "key_device_model";
    public static final String KEY_JPUSH_ID = "key_jpush_id";
    public static final String KEY_SHOP_URL = "key_shop_url";
    public static final String KEY_SHOP_URL_MAIN = "key_shop_url_main";
    public static final Object KEY_RE_LOGIN = "key_re_login";
    public static String KEY_SHOP_URL_SUFFIX = "key_shop_url_suffix";

    //Cookie
    public static final String COOKIE = "cookie";

    //intent data key
    public static final String KEY_WEB_URL = "webUrl";
    public static final String INTENT_MESSAGE_ID = "intent_message_id";


    public static final String API_HOST_END = "/split";
//    public static final String API_HOST_END = "";



    public static String KEY_IS_LOGIN = "key_is_login";

    //  1 请求成功
    //  0 失败 错误信息 msg 字段中
    //  100 更换设备
    //  101 token 已过期
    //  102 请求中未带 token
    //Code constant
    public static int CODE_SUCCESS = 1;
    public static int CODE_FAILED = 0;
    public static int CODE_DEVICE_CHANGE = 100;
    public static int CODE_TOKEN_INVAILI = 101;
    public static int CODE_TOKEN_LOSS = 102;


    public static final int startResultTag = 4;
    public static final int finishTagForRefresh = -123;


    public static String INTENT_MESSAGE_DETAIL="intent_message_detail";
    public static String TYPE_MODIFY_PWD="type_modify_pwd";

    public static String KEY_VIEW_URL="key_view_url";
    public static String KEY_SHOP_URL_USERNAME="key_shop_url_username";
    public static String KEY_SHOP_URL_PWD="key_shop_url_pwd";
    public static String KEY_SHOP_URL_KEY="key_shop_url_key";
    public static String KEY_CALL_ADS_BG="key_call_ads_bg";
    public static String KEY_USER_INFOR="key_user_infor";
    public static Object EVENT_SHOW_COUNT_TIMER="event_show_count_timer";
    public static String KEY_JPUSH_FLAG="key_jpush_flag";
}
