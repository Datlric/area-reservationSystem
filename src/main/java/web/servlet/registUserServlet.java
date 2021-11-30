package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "registUserServlet", value = "/registUserServlet")
public class registUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.设置请求编码与响应数据格式与编码
        //记得先做验证码校验
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //todo 记得对提交的email属性和studentnumber属性进行是否唯一校验，若已存在，直接返回给客户端false
        String email = request.getParameter("email");   //用于前期检查
        String studentnumber = request.getParameter("studentnumber"); //用于前期检查
        String checkcode = request.getParameter("checkcode");
        String sessionCheckCode = (String) request.getSession().getAttribute("checkcode");
        ResultInfo resultInfo = new ResultInfo();

        //校验验证码
        if (checkcode.equalsIgnoreCase(sessionCheckCode)){
            //向service层查询参数状态
            UserServiceImpl userService = new UserServiceImpl();
            int checkStudentNumber = userService.checkStudentNumberRegistry(studentnumber);
            int checkEmail = userService.checkEmailRegistry(email);

            //注册功能的业务逻辑，开启逻辑前检查邮箱与学号是否状态为0，即未被注册才允许继续
            if (checkEmail == 0 && checkStudentNumber == 0){
                Map<String, String[]> map = request.getParameterMap();
                User registUser = new User();

                try {
                    BeanUtils.populate(registUser,map);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                //数据库注册新用户
                int flag = userService.regist(registUser);

                if (flag==1){
                    //表明数据库注册操作已完成
                    //接下来就是向用户注册的邮箱发送激活邮件
                    userService.sendActiveMailToUser(registUser.getStudentnumber());
                    resultInfo.setFlag(true);
                    ServletOutputStream outputStream = response.getOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(outputStream,resultInfo);
                    outputStream.close();

                }else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("注册失败");
                    ServletOutputStream outputStream = response.getOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(outputStream,resultInfo);
                    outputStream.close();
                }

            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户名或邮箱已被注册");
                ServletOutputStream outputStream = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(outputStream,resultInfo);
                outputStream.close();
            }
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码有误");
            ServletOutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream,resultInfo);
            outputStream.close();
        }

    }
}
