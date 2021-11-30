package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "adminLogOffServlet", value = "/adminLogOffServlet")
public class adminLogOffServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("adminLogOff已执行");
        session.removeAttribute("adminLogin");
        if (session.getAttribute("adminLogin")==null){
            System.out.println(session.getId()+" adminLogOff已执行成功，session中已无admin对象");
        }else {
            System.out.println("adminLogOff执行失败或未执行移除adminLogin属性");
        }
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
