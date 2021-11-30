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

@WebServlet(name = "changePasswordServlet", value = "/changePasswordServlet")
public class changePasswordServlet extends HttpServlet {
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

        String id = request.getParameter("id");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        if (id.equals("user")) {
            User userLogin = (User) request.getSession().getAttribute("userLogin");
            //检验旧密码和登录时用户用的旧密码是否一致,改邮箱那里没意识到，还去Dao层操作了，实属不应该，应该直接从session里取信息传给service方法就ok了,不过现在已经修改了
            UserService userService = new UserServiceImpl();
            //调用用户修改密码的方法
            boolean changePasswordFlag = userService.userChangePassword(userLogin, oldPassword, newPassword);

            //修改成功要做的操作
            if (changePasswordFlag) {

                resultInfo.setFlag(true);
                //回写新密码给前端以供展示
                resultInfo.setData(newPassword);
                //注销用户，使其登出
                //注销用户
                HttpSession session = request.getSession();
                System.out.println("userLogOff已执行   from-changePasswordServlet");
                session.removeAttribute("userLogin");
                if (session.getAttribute("userLogin") == null) {
                    System.out.println(session.getId() + " 中已无user对象，userLogOff执行成功  from-changePasswordServlet");
                } else {
                    System.out.println("userLogOff执行失败或未执行移除userLogOff属性  from-changePasswordServlet");
                }

            } else {
                //todo 11.3 22:57 用户修改密码操作没做完，明儿接着做
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户密码修改失败！");
            }

        } else if (id.equals("admin")) {
            AdminService adminService = new AdminServiceImpl();
            Admin adminLogin = (Admin) request.getSession().getAttribute("adminLogin");

            boolean changePasswordFlag = adminService.changeAdminPassword(adminLogin, oldPassword, newPassword);

            if (changePasswordFlag){
                resultInfo.setFlag(true);
                resultInfo.setData(newPassword);

                //在这里让管理员强制登出
                HttpSession session = request.getSession();
                System.out.println("adminLogOff已执行");
                session.removeAttribute("adminLogin");
                if (session.getAttribute("adminLogin")==null){
                    System.out.println(session.getId()+" adminLogOff已执行成功，session中已无admin对象 from-changePasswordServlet");
                }else {
                    System.out.println("adminLogOff执行失败或未执行移除adminLogin属性 from-changePasswordServlet");
                }

            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("更改管理员密码失败");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户身份不明！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
