package com.yy.chatRoom.accountService;

import com.yy.chatRoom.fun.MessageFromClient;
import com.yy.chatRoom.fun.MessageToClient;
import com.yy.chatRoom.utils.CommUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: yuisama
 * @Date: 2019-08-03 11:53
 * @Description: 这是我用来测试webSocket的一个类，与本次项目无关；
 */
//这个注解表示这个类是一个webSocket的后端服务器类（类似于控制类）
@ServerEndpoint("/websocket")
public class WebSocket {
    // 存储所有连接到后端的websocket，这是一个线程安全的set集合
    //注意这里一定是static修饰，必须是共享的，不然每打开一个窗口就对应一个这个集合对象；
    private static CopyOnWriteArraySet<WebSocket> clients =
            new CopyOnWriteArraySet<>();
    //这个Map集合用来存放用户列表，每当有用户上线或下线，每个客户端都需要更新自己的用户列表；
    private static Map<String, String> map = new ConcurrentHashMap<>();
    // 绑定当前websocket会话，每个窗口对应一个会话
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //将客户端实体保存到clients这个set集合；
        clients.add(this);
        //username=' + '${username},这是在chat.ftl中携带的参数格式，要获取名字，就得分割
        this.username = session.getQueryString().split("=")[1];
        map.put(session.getId(), username);
        //后台的打印日志
        System.out.println("有新的连接，当前SessiodId为:"+session.getId()
        +",当前聊天室共有"+clients.size()+"人");
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setContent(username + "上线啦！！！快去找他聊天吧！！！");
        messageToClient.setNames(map);

        //将该对象转化为Json字符串发送给前端，前后端首先要约定好一定格式的Json字符串
        String json = CommUtils.objectToJson(messageToClient);
        for(WebSocket webSocket:clients) {
            webSocket.sendMsg(json);
        }
    }

    @OnError
    public void onError(Throwable e) {
        System.err.println("websocket连接失败！");
        e.printStackTrace();
    }

    @OnMessage
    //下面是前后端Json字符串规定的格式
    //群聊:{"msg":"message","type":1}
    //私聊:{"to":"0-","msg":"message","type":2}
    public void onMessage(String msg) {
        //1.前后端交互采用的Json字符串，所以首先将前端传来的Json字符串转换为我们的MessageFromClient类，
        //  这个类是我们按照前后端自己的约定格式而创建的
        MessageFromClient messageFromClient = (MessageFromClient) CommUtils.
                jsonToObject(msg, MessageFromClient.class);
        //分情况讨论，根据type这个属性来判断群聊还是私聊
        if(messageFromClient.getType().equals("1")) {
            //群聊
            MessageToClient messageToClient = new MessageToClient();
            messageToClient.setContent(messageFromClient.getMsg());
            messageToClient.setNames(map);
            String json = CommUtils.objectToJson(messageToClient);
            for(WebSocket webSocket: clients) {
                webSocket.sendMsg(json);
            }
        }else {
            //私聊
            //to是前端发给我们的用来识别要私发给哪些用户的字符串
            String to = messageFromClient.getTo();
            int len = to.length();
            //因为私发规定的字符串为0-这种格式，所以需要取出最后一个‘-’再进行分割
            String[] arr = to.substring(0, len-1).split("-");
            List<String> list = Arrays.asList(arr);
            for(WebSocket webSocket: clients) {
                if(list.contains(webSocket.session.getId()) &&
                        webSocket.session.getId()!=this.session.getId()) {
                    MessageToClient messageToClient = new MessageToClient();
                    messageToClient.setContent(this.username,messageFromClient.getMsg());
                    messageToClient.setNames(map);
                    String json = CommUtils.objectToJson(messageToClient);
                    webSocket.sendMsg(json);
                }
            }
        }
    }

    @OnClose
    public void onClose() {
        clients.remove(this);
        map.remove(this.session.getId());
        //设置要传给前端的对象的参数
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setNames(map);
        messageToClient.setContent(this.username + "下线啦！！");
        //群发所有用户通知该用户下线
        String json = CommUtils.objectToJson(messageToClient);
        for(WebSocket webSocket:clients) {
            webSocket.sendMsg(json);
        }
        //后台打印部分
        System.out.println("有用户退出聊天室");
        System.out.println("当前聊天室还剩下:"+clients.size()+
                "人");
    }

    public void sendMsg(String msg) {
        try {
            //向浏览器发送消息
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
