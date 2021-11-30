package web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ResultInfo;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import service.UserServiceImpl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//todo 明儿把前端页面回调一写一并进行测试

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
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
            User loginUser = new User();
            try {
                //3.用BeanUtils将参数封装进loginUser中
                BeanUtils.populate(loginUser, map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            //4.创建一个新的userService操作
            UserService userService = new UserServiceImpl();
            User user = userService.login(loginUser);
            System.out.println(user + " from loginServlet");

            //5.做一些登陆失败的业务逻辑的判断
            //5.1判断用户是否为null
            if (user == null) {
                //用户名密码或错误
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("学号或密码错误");
            }
            //5.2判断用户是否被激活或被锁定,"yes".equals(user.getIslocked())代表未激活
            if (user != null && "yes".equals(user.getIslocked())) {
                //用户未激活，请先检查注册邮箱进行激活，或联系管理员
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户未激活，请先检查注册邮箱进行激活，或联系管理员");
            } else if (user != null && "locked".equals(user.getIslocked())) {
                //"locked".equals(user.getIslocked())代表用户已被锁定
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户已被锁定，请联系管理员解锁");
            }
            //6.判断登陆成功,"no"代表帐户正常使用，已激活并且未被锁定
            if (user != null && "no".equals(user.getIslocked())) {
                //登录成功标记，将user存储到session中
                request.getSession().setAttribute("userLogin", user);

                //登陆成功
                resultInfo.setFlag(true);
            }

            //7.响应数据
            ObjectMapper mapper = new ObjectMapper();
            ServletOutputStream outputStream = response.getOutputStream();
            mapper.writeValue(outputStream, resultInfo);
            outputStream.close();

        } else {
            //验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误!");

            //响应数据
            ObjectMapper mapper = new ObjectMapper();
            String resultInfo_json = mapper.writeValueAsString(resultInfo);
            System.out.println(resultInfo_json + " from loginServlet");
            //开启servlet输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //将json写入输出流中
            mapper.writeValue(outputStream, resultInfo);
            //关闭输出流
            outputStream.close();
        }

    }
}
