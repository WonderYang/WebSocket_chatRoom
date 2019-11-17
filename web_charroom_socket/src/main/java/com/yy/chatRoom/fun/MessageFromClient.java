package com.yy.chatRoom.fun;

import lombok.Data;

/**
 * @Author : YangY
 * @Description : 前端传给后台的信息类
 * @Time : Created in 13:42 2019/8/6
 */
@Data
public class MessageFromClient {
    // 聊天信息
    private String msg;
    // 聊天类别: 1表示群聊 2表示私聊
    private String type;
    // 私聊的对象SessionID
    private String to;

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public String getTo() {
        return to;
    }
}
