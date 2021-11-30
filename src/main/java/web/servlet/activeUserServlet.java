package web.servlet;

import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "activeUserServlet", value = "/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String acode = request.getParameter("acode");
        UserServiceImpl userService = new UserServiceImpl();
        //激活是否成功的标记
        boolean flag = userService.activeUser(acode);

        String msg=null;
        if (flag){
            //激活成功
            msg="激活成功，请<a href='login.html'>登录</a>";
        }else {
            //激活失败
            msg="激活失败，请联系管理员！";
        }

        System.out.println("done");
        //todo还有点问题
        response.getWriter().write(msg);
    }
}
