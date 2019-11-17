package com.yy.chatRoom.dao;

import com.yy.chatRoom.fun.User;

import java.sql.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:00 2019/8/3
 */
//得继承自BaseDao，才能获取到数据库连接；
public class AccountDao extends BaseDao{
    //用户登陆
    public User userLogin(String username, String password) {
        Connection connection = getConnection();
        //预编译过的SQL，能防止SQL注入；
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        String sql = "select * from user where username=? and password=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            //表明查询结果不为空，登陆成功
            //这里有个大坑，不能写成if(resultSet!=null),这里肯定不会为空，它会返回表头
            if(resultSet.next()) {
                user = getUserInfo(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //1.关闭数据源
            closeResources(connection,statement,resultSet);
        }
        return user;
    }

    //用户注册  (数据库插入操作)
    public boolean userRegister(String username, String password) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement;
        boolean res = false;
        String sql = "INSERT INTO user(username, password) VALUES(?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            int x = preparedStatement.executeUpdate();
            res = (x==1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    //将登陆用户信息封装到user表当中，如果不封装，上面执行完登陆操作后就关闭了数据源
    //就查询不到这些信息了
    public User getUserInfo(ResultSet resultSet) throws Exception{
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

}
