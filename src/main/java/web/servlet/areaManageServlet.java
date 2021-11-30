package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AreaDao;
import domain.Area;
import domain.ResultInfo;
import org.apache.commons.beanutils.BeanUtils;
import service.AdminService;
import service.AdminServiceImpl.AdminServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "areaManageServlet", value = "/areaManageServlet")
public class areaManageServlet extends HttpServlet {
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

        if (request.getSession().getAttribute("adminLogin") != null){
            if ("admin".equals(request.getParameter("id"))){
                String method = request.getParameter("method");
                String areaId = request.getParameter("areaId");
                Area area = new Area();
                area.setAreaId(areaId);

                int flag = adminService.areaManage(area, method);

                if (flag==1){
                    resultInfo.setFlag(true);
                }else if ((flag==0)){
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("没有此区域的信息，请检查");
                }else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("操作出错！");
                }

            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("身份识别失败");
            }
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("管理员未登录，您没有权限访问");
        }

        mapper.writeValue(outputStream,resultInfo);
        outputStream.close();
    }
}
