package com.yy.chatRoom.cotroller;

import com.yy.chatRoom.accountService.AccountService;
import com.yy.chatRoom.config.FreeMarkerListener;
import com.yy.chatRoom.utils.CommUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : YangY
 * @Description :  登陆页面的后台服务器
 * @Time : Created in 9:48 2019/8/6
 */
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码格式，防止乱码
        resp.setContentType("text/html;charset=utf8");
        PrintWriter writer = resp.getWriter();
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        //判空操作,前端页面已经做了判空处理，但这里还是实现一下，因为一般都是前后端分离，后端不知道前端是否有了处理；
        if(CommUtils.isNull(userName) || CommUtils.isNull(password)) {
            //登陆失败，重定向到登陆界面
            writer.println("    <script>\n" +
                    "        alert(\"用户名或密码为空!\");\n" +
                    "        window.location.href = \"/index.html\";\n" +
                    "    </script>");
        }
        AccountService accountService = new AccountService();
        //用户名密码不为空，接下来根据我写的方法来判断用户名密码是否与数据库中的数据存在和匹配
        if(accountService.userLogin(userName, password)) {
            //登陆成功,加载chat.ftl资源模板
            Template template = getTemplate(req, "chat.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("username", userName);
            try {
                //执行合并,会将合并后的文本输出到指定的地方，即将我们上面的信息传递给前端那个ftl页面
                template.process(map, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }else {
            //登陆失败，还是重定向到此界面
            writer.println("    <script>\n" +
                    "        alert(\"用户名不存在或密码错误！！！\");\n" +
                    "        window.location.href = \"/index.html\";\n" +
                    "    </script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private Template getTemplate(HttpServletRequest req, String fileName) {
        Configuration cfg = (Configuration)
                req.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
        try {
            return cfg.getTemplate(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
