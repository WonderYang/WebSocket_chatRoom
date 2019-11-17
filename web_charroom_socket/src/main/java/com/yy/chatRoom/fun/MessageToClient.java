package com.yy.chatRoom.fun;

import lombok.Data;

import java.util.Map;

/**
 * @Author : YangY
 * @Description : 后端发送给前端的信息类
 * @Time : Created in 13:38 2019/8/6
 */
@Data
public class MessageToClient {
    // 聊天内容
    private String content;
    // 服务端登录的所有用户列表
    private Map<String, String> names;

    public void setContent(String msg) {
        this.content = msg;
    }
    //上面的重载方法
    public void setContent(String userName,String msg) {
        this.content = userName + "说:" + msg;
    }

    public void setNames(Map<String, String> names) {
        this.names = names;
    }
}
