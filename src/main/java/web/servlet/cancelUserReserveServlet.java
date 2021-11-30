package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ApplyForm;
import domain.ResultInfo;
import domain.User;
import service.ApplyFormService;
import service.ApplyFormServiceImpl.ApplyFormServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "cancelUserReserveServlet", value = "/cancelUserReserveServlet")
public class cancelUserReserveServlet extends HttpServlet {
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
        ApplyFormService applyFormService = new ApplyFormServiceImpl();

        String id = request.getParameter("id");
        User userLogin = (User) request.getSession().getAttribute("userLogin");
        if (id.equals("user")) {
            boolean cancelFlag = applyFormService.deleteApplyFormByStudentNumber(userLogin.getStudentnumber());
            if (cancelFlag){
                resultInfo.setFlag(true);
            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("删除预约单出错！");
            }

        } else if (id.equals("admin")){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("管理员暂未开通此功能");
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户身份识别失败");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
