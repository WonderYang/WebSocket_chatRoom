package com.yy.chatRoom.config;

import freemarker.template.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author : YangY
 * @Description :
 * 因为要使用chat.ftl文件，所以要配置监听器
 * 配置监听器，全局的这是，在类一加载就开始
 * @Time : Created in 10:03 2019/8/6
 */
@WebListener
public class FreeMarkerListener implements ServletContextListener {
    public static final String TEMPLATE_KEY = "_template_";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //1.Freemarker的起始类,要使用Freemarker功能必须通过该类，顺便配置版本
        Configuration conf = new Configuration(Configuration.VERSION_2_3_0);
        //2.配置加载ftl的绝对路径,右击chat.ftl，选择open in terminal，就可以看到它的全路径
        try {
            //从指定地方加载模板文件
            conf.setDirectoryForTemplateLoading
                    (new File("E:\\IDEA Project\\web_charroom_socket\\src\\main\\webapp"));

            // 配置页面编码
            conf.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
            sce.getServletContext().setAttribute(TEMPLATE_KEY,conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
