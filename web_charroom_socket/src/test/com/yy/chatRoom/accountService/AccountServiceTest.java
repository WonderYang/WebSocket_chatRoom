package com.yy.chatRoom.accountService;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 23:10 2019/8/3
 */
public class AccountServiceTest {

    @Test
    public void userRegister() {
        AccountService accountService = new AccountService();
        boolean res = accountService.userRegister("yyg","1111");
        Assert.assertEquals(true,res);
    }

    @Test
    public void userLogin() {
        AccountService accountService = new AccountService();
        boolean res = accountService.userLogin("yyy", "123");
        Assert.assertEquals(true, res);
    }
}