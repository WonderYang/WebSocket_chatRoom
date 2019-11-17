package com.yy.chatRoom.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description : 封装基础的工具方法，如加载资源文件的方法
 * @Time : Created in 19:04 2019/7/30
 */
public class CommUtils {
    public static final Gson gson = new GsonBuilder().create();
    private CommUtils() {

    }
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        //获取数据库配置文件的输入流
        InputStream in = CommUtils.class.getClassLoader().getResourceAsStream(fileName);
        //加载配置文件中的所有内容
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //把任意对象Json序列化为一个Json字符串
    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }
    //反序列化
    public static Object jsonToObject(String jsonStr,Class objClass) {
        return gson.fromJson(jsonStr,objClass);
    }

    //判断字符串是否为空
    public static boolean isNull(String str) {
        //str==null一定要写在前面，否则可能出现NullPointer异常
        return str==null || str.equals("");
    }

}
