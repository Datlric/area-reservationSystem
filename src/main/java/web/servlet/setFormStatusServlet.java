package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ApplyForm;
import domain.ResultInfo;
import service.ApplyFormService;
import service.ApplyFormServiceImpl.ApplyFormServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "setFormStatusServlet", value = "/setFormStatusServlet")
public class setFormStatusServlet extends HttpServlet {
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

        //data: {id: "admin", flag: true, method: "deny", applyFormId: applyFormId},
        String id = request.getParameter("id");
        String method = request.getParameter("method");
        String applyFormId = request.getParameter("applyFormId");

        if ("admin".equals(id)) {
            boolean flag = applyFormService.setApplyFormStatus(applyFormId, method);
            if (flag){
                resultInfo.setFlag(true);
            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("操作失败");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("身份错误！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
