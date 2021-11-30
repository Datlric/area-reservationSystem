package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.AreaDao;
import dao.AreaDaoImpl.AreaDaoImpl;
import domain.ApplyForm;
import domain.Area;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.ApplyFormService;
import service.ApplyFormServiceImpl.ApplyFormServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "subReserveFormServlet", value = "/subReserveFormServlet")
public class subReserveFormServlet extends HttpServlet {
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
        AreaDao areaDao = new AreaDaoImpl();
        Map<String, String[]> map = request.getParameterMap();

        //用beanUtils将数据封装为applyForm对象
        ApplyForm applyForm = new ApplyForm();
        try {
            BeanUtils.populate(applyForm, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        String areaId = applyForm.getAreaId();
        String startTime = applyForm.getStartTime();
        String endTime = applyForm.getEndTime();

        //检查预约时间是否超过一周,true表示没有，false表示超了
        boolean timeCrossedCheck = applyFormService.timeCrossedCheck(startTime, endTime);

        //检查用户所填写的时间是否与现有已通过的预约单有冲突
        boolean timeConflictCheck = applyFormService.reserveTimeConflictCheck(applyForm);

        //检查获取的areaId是否非法,false表示非法
        boolean areaFlag = areaDao.getAreaByAreaId(areaId) != null;

        //先检查当前用户是否为session中存储的登录用户，以及当前用户是否存在预约
        String studentNumber = request.getParameter("studentNumber");
        User userLogin = (User) request.getSession().getAttribute("userLogin");

        //调用ApplyFormService中检查用户是否有预约,true表示有预约，false表示没预约
        boolean checkFlag = applyFormService.checkUserReserveIsExist(userLogin);


        if (userLogin.getStudentnumber().equals(studentNumber) && !checkFlag && areaFlag && timeCrossedCheck && timeConflictCheck) {

            //调用areaService层方法将applyForm保存进数据库中,saveApplyForm方法里面有添加提交时间，设置申请表ID和设置IsPass为“processing”的方法
            boolean saveFormFlag = applyFormService.saveApplyForm(applyForm);

            if (saveFormFlag) {
                resultInfo.setFlag(true);
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("保存申请表失败！");
            }

        } else {
            resultInfo.setFlag(false);
            if (!areaFlag) {
                resultInfo.setErrorMsg("区域不存在！");
            } else if (!timeCrossedCheck) {
                resultInfo.setErrorMsg("不能预约超过一周！");
            } else if (!timeConflictCheck) {
                resultInfo.setErrorMsg("此时段中有时间已被预约，请换一个时段");
            } else {
                resultInfo.setErrorMsg("申请用户已存在预约！您可以在‘已预约’中取消或活动结束后注销预约");
            }
        }

        mapper.writeValue(outputStream, resultInfo);
        outputStream.close();
    }
}
