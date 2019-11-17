package com.yy.chatRoom.dao;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 11:24 2019/8/3
 */
public class BaseDaoTest {

    @Test
    //测试连接数据库
    public void getConnection() {
        BaseDao baseDao = new BaseDao();
        baseDao.getConnection();

    }
}