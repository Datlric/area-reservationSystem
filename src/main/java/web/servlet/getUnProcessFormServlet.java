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

@WebServlet(name = "getUnProcessFormServlet", value = "/getUnProcessFormServlet")
public class getUnProcessFormServlet extends HttpServlet {
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
        if ("admin".equals(id)) {
            List<ApplyForm> applyForms = applyFormService.getAllFormByStatus("processing");
            if (applyForms.size()!=0) {
                resultInfo.setFlag(true);
                resultInfo.setData(applyForms);
                mapper.writeValue(outputStream, resultInfo);
                outputStream.close();
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("未查询到未处理的申请单");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("身份错误！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
