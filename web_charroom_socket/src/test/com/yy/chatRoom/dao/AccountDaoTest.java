package com.yy.chatRoom.dao;

import com.yy.chatRoom.fun.User;
import javafx.scene.control.Alert;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:18 2019/8/3
 */
public class AccountDaoTest {

    @Test
    public void userLogin() {
        AccountDao accountDao = new AccountDao();
        User user = accountDao.userLogin("yyy","123");
        System.out.println(user);

    }

    @Test
    public void userRegister() {
        AccountDao accountDao = new AccountDao();
        boolean res = accountDao.userRegister("yyy","123");
        Assert.assertEquals(true, res);
    }

    @Test
    public void getUserInfo() {
    }
}