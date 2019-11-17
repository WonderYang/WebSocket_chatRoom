package com.yy.chatRoom.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @Author : YangY
 * @Description :  加载属性文件中的内容的一个单元测试
 * @Time : Created in 19:12 2019/7/30
 */
public class CommUtilsTest {

    @Test
    public void loadProperties() {
        String name = "db.properties";
        Properties properties = CommUtils.loadProperties(name);
        System.out.println(properties);
        String url = properties.getProperty("url");
        //断言，判断这个内容是否为空；断言不止这一种方法，有很多
        Assert.assertNotNull(url);
    }
    @Test
    public void json() {

    }
}