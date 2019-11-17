package com.yy.chatRoom.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.yy.chatRoom.utils.CommUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description : 这个类用来封装一些基础的操作，数据源、获取连接、关闭资源
 * @Time : Created in 9:55 2019/8/3
 */
public class BaseDao {
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
    //protected权限，只有这个类的子类才能够获取到数据库连接；
    protected  DruidPooledConnection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("数据库获取连接失败！！！");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeResources(Connection connection,
                                      Statement statement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResources(Connection connection,
                                      Statement statement,
                                      ResultSet resultSet) {
        closeResources(connection,statement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
