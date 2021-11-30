package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.AreaShow;
import domain.ResultInfo;
import service.UserService;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "getAllAreaServlet", value = "/getAllAreaServlet")
public class getAllAreaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码与响应数据格式与编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        ServletOutputStream outputStream = response.getOutputStream();

        UserService userService = new UserServiceImpl();
        ArrayList<AreaShow> allUseableAreas = userService.getAllUseableAreas();

        if (allUseableAreas.size()!=0){
            resultInfo.setFlag(true);
            resultInfo.setData(allUseableAreas);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("暂时没有可用信息！");
        }

        mapper.writeValue(outputStream,resultInfo);
        outputStream.close();
    }
}
