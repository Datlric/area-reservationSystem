package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import service.AdminService;
import service.AdminServiceImpl.AdminServiceImpl;
import service.UserService;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "userManageServlet", value = "/userManageServlet")
public class userManageServlet extends HttpServlet {
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
        AdminService adminService = new AdminServiceImpl();

        String id = request.getParameter("id");
        String studentNumber = request.getParameter("studentNumber");
        String method = request.getParameter("method");


        if ("admin".equals(id)) {
            //todo 11.11 我其实觉得这里可有可无，明儿尽力写吧，能写完就往完写
            if ("getUserInfo".equals(method)) {
                User manageUser = adminService.userManage(studentNumber, method);
                if (manageUser != null) {
                    resultInfo.setFlag(true);
                    resultInfo.setData(manageUser);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("未查找到此用户");
                }
            } else {
                User manageUser = adminService.userManage(studentNumber, method);
                //这里很无奈，拿Acode当状态结果表示码吧
                if (manageUser != null && "1".equals(manageUser.getAcode())) {
                    resultInfo.setFlag(true);
                } else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("操作失败");
                }
            }

        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("身份不明！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
