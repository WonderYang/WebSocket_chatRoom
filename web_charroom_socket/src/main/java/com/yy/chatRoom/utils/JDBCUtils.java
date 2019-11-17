package com.yy.chatRoom.utils;

import java.sql.*;
import java.util.Properties;

/**
 * @Author : YangY
 * @Description : 关于操作JDBC的一些工具方法
 * @Time : Created in 19:34 2019/7/30
 */
public class JDBCUtils {
    private static String driverName;
    private static String url;
    private static String userName;
    private static String password;
    static {
        //下面这些属性只加载一次，全局的
        Properties properties = CommUtils.loadProperties("db.properties");
        driverName = properties.getProperty("driverName");
        url = properties.getProperty("url");
        userName = properties.getProperty("userName");
        password = properties.getProperty("password");
        try {
            //驱动的加载只需要一次，所以也放在静态代码块中
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("加载数据库驱动出错！！！");
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库的连接
     * @return Connection对象
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.err.println("JDBC获取连接失败！！！");
            e.printStackTrace();
        }
        return null;
    }

    //关闭连接资源
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

    //这个方法是上面方法的重载
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
