package com.astgo.naoxuanfeng.tools;


import android.text.TextUtils;
import android.util.Log;

import com.astgo.naoxuanfeng.MyConstant;
import com.astgo.naoxuanfeng.bean.RingPhoneBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leef on 2015/4/19.
 * XmlPullParser 解析 XML 工具类
 */
public class XmlUtil {

//    private static final String ENCODE = "UTF-8";

    // 获取 API 请求返回的 XML 字符串中的 Ret 节点值
    public static int getRetXML(String xml) {
        try {
            return Integer.parseInt(parserFilterXML(xml, MyConstant.RET));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param xmlData 需要解析的 XML 字符串
     * @param field   指定要解析的节点
     * @return 解析结果
     */
    public static String parserFilterXML(String xmlData, String field) {
        String result = null;
        if(!TextUtils.isEmpty(xmlData)){
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(xmlData));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String nodeName = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (field.equals(nodeName)) {
                                result = xpp.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        default:
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
            }
        }
        return result;
    }

    public static List<RingPhoneBean> parserRingPhoneXML(String xmlData){
        Log.d("parserRingPhoneXML", xmlData);
        List<RingPhoneBean> list = new ArrayList<>();
        RingPhoneBean ringPhoneBean = null;
        List<String> phones = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(getStringStream(xmlData) ,"utf-8");
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("Item".equals(xpp.getName())){
                            ringPhoneBean = new RingPhoneBean();
                        }else if("Name".equals(xpp.getName())){
                            ringPhoneBean.setPhoneName(xpp.nextText());
                        }else if("PhoneList".equals(xpp.getName())){
                            phones = new ArrayList<>();
                            int eventType1 = xpp.getEventType();
                            while(eventType1 != XmlPullParser.END_TAG || !"PhoneList".equals(xpp.getName())){
                                if(xpp.getEventType() == XmlPullParser.START_TAG && "Item".equals(xpp.getName())){
                                    phones.add(xpp.nextText());
                                }
                                eventType1 = xpp.next();
                            }
                            ringPhoneBean.setPhoneListNum(phones);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("Item".equals(xpp.getName())){
                            list.add(ringPhoneBean);
                            ringPhoneBean = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
        }
        return list;
    }

    //将流对象读取到内存中转换成字符串
    public static byte[] readInput(InputStream in ) throws IOException{
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        int len=0;
        byte[] buffer=new byte[1024];
        while((len=in.read(buffer))>0){
            out.write(buffer,0,len);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }
    //以流的方式从内存中读出
    public static InputStream getStringStream(String sInputString){
        ByteArrayInputStream tInputStringStream=null;
        if (sInputString != null && !sInputString.trim().equals("")){
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }
}
