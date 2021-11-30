package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Admin;
import domain.ResultInfo;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "getUserInfoServlet", value = "/getUserInfoServlet")
public class getUserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //data: {id:"user",flag:true}
        //todo 11.1 用户查看密码这个功能有问题
        String id = request.getParameter("id");

        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        ServletOutputStream outputStream = response.getOutputStream();

        if (id.equals("user")) {
            //功能太简单，直接把session里存储的userLogin对象拿出来扔进resultInfo就行了
            User userLogin = (User) request.getSession().getAttribute("userLogin");
            System.out.println(userLogin);
            resultInfo.setFlag(true);
            resultInfo.setData(userLogin);

        } else if (id.equals("admin")) {
            //功能太简单，直接把session里存储的adminLogin对象拿出来扔进resultInfo就行了
            Admin adminLogin = (Admin) request.getSession().getAttribute("adminLogin");
            resultInfo.setFlag(true);
            resultInfo.setData(adminLogin);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("400");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
