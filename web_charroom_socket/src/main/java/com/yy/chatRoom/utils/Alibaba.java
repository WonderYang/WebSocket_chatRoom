package com.yy.chatRoom.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description :用阿里的数据源来管理创建数据库连接
 * @Time : Created in 20:59 2019/7/30
 */
public class Alibaba {
    private static DruidDataSource dataSource;
    static {
        Properties properties = CommUtils.loadProperties("datasource.properties");
        try {
            //注册驱动,加载数据源
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DruidPooledConnection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
