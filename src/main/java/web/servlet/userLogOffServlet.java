package web.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

//todo 10.28晚 现在基本的权限控制与登录注册什么的弄完了，接下来着重就是iframe里的列表查询什么的，明天先做所有活动区域一览的列表查询

@WebServlet(name = "userLogOffServlet", value = "/userLogOffServlet")
public class userLogOffServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("userLogOff已执行");
        session.removeAttribute("userLogin");
        if (session.getAttribute("userLogin")==null){
            System.out.println(session.getId()+" 中已无user对象，userLogOff执行成功");
        }else {
            System.out.println("userLogOff执行失败或未执行移除userLogOff属性");
        }
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
