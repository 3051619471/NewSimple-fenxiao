package com.astgo.naoxuanfeng.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/1/21.
 * 正则表达式验证号码
 */
public class RegexUtil {

    public static Pattern pattern;
    public static Matcher matcher;

    private static Pattern PATTERN_MOBILEPHONE;
    private static Pattern PATTERN_FIXEDPHONE;
    private static Pattern PATTERN_ZIPCODE;


    //用于匹配手机号码
     private final static String REGEX_MOBILEPHONE = "^0?1\\d{10}$";
        
    //private final static String REGEX_MOBILEPHONE = "^0?1[3458]\\d{9}$";
    //用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
    //用于获取固定电话中的区号
    private final static String REGEX_ZIPCODE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";

    //用于匹配URL
    private final static String REGEX_URL = "(?i)(http:|https:)//[^\\u4e00-\\u9fa5]+?(?=\\s+)";

    public static String extractURL(String in) {
        try {
            String regex = REGEX_URL;// 提取 url 以空格结束
            String url = null;
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(in);
            while (matcher.find()) {
                url = matcher.group();
            }
            return url;
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }


    static {
        PATTERN_FIXEDPHONE = Pattern.compile(REGEX_FIXEDPHONE);
        PATTERN_MOBILEPHONE = Pattern.compile(REGEX_MOBILEPHONE);
        PATTERN_ZIPCODE = Pattern.compile(REGEX_ZIPCODE);
    }


    /**
     * 判断是否为手机号码
     *
     * @param number 手机号码
     * @return
     */

    public static boolean isCellPhone(String number) {
        Matcher match = PATTERN_MOBILEPHONE.matcher(number);
        return match.matches();
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isFixedPhone(String number) {
        Matcher match = PATTERN_FIXEDPHONE.matcher(number);
        return match.matches();
    }

    /**
     * 判断是否为固定电话号码中的区号
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isZipCode(String number) {
        Matcher match = PATTERN_ZIPCODE.matcher(number);
        return match.matches();
    }
}
