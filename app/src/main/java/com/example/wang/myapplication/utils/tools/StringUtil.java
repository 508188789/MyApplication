package com.example.wang.myapplication.utils.tools;

/**
 * Created by ${151530502} on 2018/12/4.
 */
public class StringUtil {
    public static String getRealString(String body){
        String result = body.substring(0,body.length()-4).replaceAll("\\<[p]\\>","        ")
                .replaceAll("\\<\\/[p]\\>","\r\n\n") + "\r\n";
        return result;
    }
}
