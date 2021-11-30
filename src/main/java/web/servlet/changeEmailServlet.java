package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Admin;
import domain.ResultInfo;
import domain.User;
import service.AdminService;
import service.AdminServiceImpl.AdminServiceImpl;
import service.UserService;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "changeEmailServlet", value = "/changeEmailServlet")
public class changeEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        ServletOutputStream outputStream = response.getOutputStream();

        /*data: {id:"user",flag:true,oldEmail:oldEmail,newEmail:newEmail}*/
        String id = request.getParameter("id");
        String oldEmail = request.getParameter("oldEmail");
        String newEmail = request.getParameter("newEmail");

        if (id.equals("user")) {
            UserService userService = new UserServiceImpl();
            User userLogin = (User) request.getSession().getAttribute("userLogin");
            //flag是执行是否成功的标志，true是成功更新了邮箱且重设了激活码，且用户状态被重设为yes
            boolean changeEmailFlag = userService.userChangeEmail(userLogin, oldEmail, newEmail);

            if (changeEmailFlag) {
                resultInfo.setFlag(true);
                //给用户发送激活邮件
                userService.sendActiveMailToUser(userLogin.getStudentnumber());

                //把修改成功的新邮箱回写到前端以供信息提示
                resultInfo.setData(newEmail);

                //注销用户，使其登出
                HttpSession session = request.getSession();
                System.out.println("userLogOff已执行  from-changeEmailServlet");
                session.removeAttribute("userLogin");
                if (session.getAttribute("userLogin") == null) {
                    System.out.println(session.getId() + " 中已无user对象，userLogOff执行成功  from-changeEmailServlet");
                } else {
                    System.out.println("userLogOff执行失败或未执行移除userLogOff属性  from-changeEmailServlet");
                }

            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("邮箱更新操作失败");
            }
        } else if (id.equals("admin")) {
            AdminService adminService = new AdminServiceImpl();
            Admin adminLogin = (Admin) request.getSession().getAttribute("adminLogin");

            //flag是执行是否成功的标志，true是成功更新了邮箱
            boolean changeEmailFlag = adminService.changeAdminEmail(adminLogin, oldEmail, newEmail);

            if (changeEmailFlag) {
                resultInfo.setFlag(true);
                resultInfo.setData(newEmail);
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("更新管理员邮箱失败！");
            }

        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户身份不明！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
