package com.yy.chatRoom.utils;

import org.junit.Assert;
import org.junit.Test;

import java.net.StandardSocketOptions;
import java.sql.*;

import static org.junit.Assert.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 19:55 2019/7/30
 */
public class JDBCUtilsTest {

    @Test
    public void getConnection() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
             statement = connection.createStatement();
            String sql = "select * from user";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println(id+"   "+username+"   "+password);
            }
            //System.out.println(connection);
//            String sql = "select * from user where id = ?";
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1,2);
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String userName = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                System.out.println("id = "+id+",userName = "+userName+",password = "+password);
//            }
//            //Assert.assertNotNull(connection);
        }catch (SQLException e) {

        }finally {
            //关闭JDBC的连接资源
            JDBCUtils.closeResources(connection,statement,resultSet);
        }
    }


}