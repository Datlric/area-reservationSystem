package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "emailCheckServlet", value = "/emailCheckServlet")
public class emailCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.设置请求编码与响应数据格式与编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        String email = request.getParameter("email");
        ResultInfo resultInfo = new ResultInfo();
        UserServiceImpl userService = new UserServiceImpl();
        int count = userService.checkEmailRegistry(email);

        if (count == 0) {
            resultInfo.setFlag(true);
            ServletOutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, resultInfo);
            outputStream.close();
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("邮箱已被注册，请换一个");
            ServletOutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, resultInfo);
            outputStream.close();
        }

    }
}
