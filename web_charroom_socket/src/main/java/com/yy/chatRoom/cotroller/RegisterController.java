package com.yy.chatRoom.cotroller;

import com.yy.chatRoom.accountService.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @Author : YangY
 * @Description :
 * @Time : Created in 10:42 2019/8/3
 */

@WebServlet(urlPatterns = "/doRegister")
public class RegisterController extends HttpServlet {
    private AccountService  accountService= new AccountService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从前端的表单中（即请求中）获取信息用getParameter方法
        String userName = req.getParameter("username");
        String passWord = req.getParameter("password");
        //防止页面乱码
        resp.setContentType("text/html;charset=utf8");
        //Servlet给前端输出信息就需要用到PrintWriter这个类；即获取页面的输出流
        PrintWriter writer = resp.getWriter();
        if(accountService.userRegister(userName, passWord)) {
            // 用户注册成功
            // 弹框提示，返回登陆界面，在输出流中我们使用的是js语言
            writer.println("<script>\n" +
                    "    alert(\"注册成功\");\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");
        }else {
            // 弹框提示失败，保留原页面
            writer.println("<script>\n" +
                    "    alert(\"注册失败\");\n" +
                    "    window.location.href = \"/registration.html\";\n" +
                    "</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
