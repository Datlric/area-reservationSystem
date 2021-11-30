package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Admin;
import domain.ResultInfo;
import org.apache.commons.beanutils.BeanUtils;
import service.AdminService;
import service.AdminServiceImpl.AdminServiceImpl;
import service.UserService;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "adminLoginServlet", value = "/adminLoginServlet")
public class adminLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.设置请求编码与响应数据格式与编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //2.获取请求的所有参数
        Map<String, String[]> map = request.getParameterMap();
        ResultInfo resultInfo = new ResultInfo(); //给前端返回的东西

        //验证码判断前参数准备
        String[] checkcodes = map.get("checkcode");
        String userCheckCode = checkcodes[0];
        //从session中取出checkcode
        HttpSession session = request.getSession();
        String sessionCheckCode = (String) session.getAttribute("checkcode");

        //判断用户验证码是否正确
        if (userCheckCode.equalsIgnoreCase(sessionCheckCode)) {
            //验证码正确开启业务逻辑
            Admin adminLogin = new Admin();
            try {
                BeanUtils.populate(adminLogin,map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            //4.创建一个新的adminService操作
            AdminService adminService = new AdminServiceImpl();
            Admin admin = adminService.login(adminLogin);
            System.out.println(admin+" from loginServlet");

            //5.做一些登陆失败的业务逻辑的判断
            //5.1判断用户是否为null
            if (admin == null) {
                //用户名或密码错误
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("管理员id或密码错误");
            }else {
                //登录成功标记，将admin存储到session中
                request.getSession().setAttribute("adminLogin", admin);

                //登陆成功
                resultInfo.setFlag(true);
            }

            //7.响应数据
            ObjectMapper mapper = new ObjectMapper();
            ServletOutputStream outputStream = response.getOutputStream();
            mapper.writeValue(outputStream, resultInfo);
            outputStream.close();
        }else {
            //验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误!");

            //响应数据
            ObjectMapper mapper = new ObjectMapper();
            String resultInfo_json = mapper.writeValueAsString(resultInfo);
            System.out.println(resultInfo_json + " from adminLoginServlet");
            //开启servlet输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //将json写入输出流中
            mapper.writeValue(outputStream, resultInfo);
            //关闭输出流
            outputStream.close();
        }

    }
}
