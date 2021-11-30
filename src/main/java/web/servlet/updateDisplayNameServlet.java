package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Admin;
import domain.ResultInfo;
import domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "updateDisplayNameServlet", value = "/updateDisplayNameServlet")
public class updateDisplayNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.设置请求编码与响应数据格式与编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        String nameDisplay = request.getParameter("nameDisplay");
        String id = request.getParameter("id");
        String flag = request.getParameter("flag");
        System.out.println(nameDisplay+"  "+flag+"  "+id);

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(true);

        if (flag.equals("true")){
            switch (id){
                case "user":{
                    User userLogin= (User) request.getSession().getAttribute("userLogin");
                    String username = userLogin.getUsername();
                    if (username==null){
                        username="我";
                    }
                    resultInfo.setData(username);
                    break;
                }
                case "admin":{
                    Admin adminLogin= (Admin) request.getSession().getAttribute("adminLogin");
                    String adminName = adminLogin.getAdminname();
                    if (adminName==null){
                        adminName="我";
                    }
                    resultInfo.setData(adminName);
                    break;
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            ServletOutputStream outputStream = response.getOutputStream();
            mapper.writeValue(outputStream,resultInfo);
            outputStream.close();
        }
    }
}
