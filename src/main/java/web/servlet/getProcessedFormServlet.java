package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ApplyForm;
import domain.ResultInfo;
import service.ApplyFormService;
import service.ApplyFormServiceImpl.ApplyFormServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "getProcessedFormServlet", value = "/getProcessedFormServlet")
public class getProcessedFormServlet extends HttpServlet {
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
            List<ApplyForm> applyForms = applyFormService.getAllFormByMutStatus("y","n");
            if (applyForms.size()!=0) {
                resultInfo.setFlag(true);
                resultInfo.setData(applyForms);
                mapper.writeValue(outputStream, resultInfo);
                outputStream.close();
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("未查询到已处理的申请单");
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("身份错误！");
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
