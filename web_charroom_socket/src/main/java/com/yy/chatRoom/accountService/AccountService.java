package com.yy.chatRoom.accountService;

import com.yy.chatRoom.dao.AccountDao;
import com.yy.chatRoom.fun.User;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:38 2019/8/3
 */
public class AccountService {
    private AccountDao accountDao = new AccountDao();
    //用户登录
    public boolean userLogin(String username, String password) {
        User user = accountDao.userLogin(username, password);
        if(user == null) {
            return false;
        }
        return true;
    }

    //用户注册
    public boolean userRegister(String username, String password) {
        boolean res = accountDao.userRegister(username, password);
        return res;
    }
}
