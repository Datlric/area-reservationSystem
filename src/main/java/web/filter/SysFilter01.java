package web.filter;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.proxy.DruidDriver;
import com.alibaba.druid.util.DruidDataSourceUtils;
import util.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebFilter(filterName = "SysFilter01", urlPatterns = {"/*"})
public class SysFilter01 implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //todo 把过滤器这里记得弄一下，做权限相关的控制
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        //1.获取资源请求路径
        String uri = request.getRequestURI();
        //2.判断是否包含登录相关资源路径,要注意排除掉 css/js/图片/验证码等资源
        if (uri.contains("/index") || uri.contains("/login.html") || uri.contains("/regist.html") || uri.contains("/test.html") ||
                uri.contains("/layui/") || uri.contains("/js/") || uri.contains("/img/") || uri.contains("/icon/") || uri.contains("/fonts") ||
                uri.contains("/css/") || uri.contains("/error/") || uri.contains("/loginServlet") || uri.contains("/registUserServlet") ||
                uri.contains("/emailCheckServlet") || uri.contains("/checkCodeServlet") || uri.contains("/activeUserServlet") ||
                uri.contains("/adminLoginServlet") || uri.contains("/adminlogin.html") || uri.contains("/testServlet")) {
            //包含，用户就是想登录。放行
            chain.doFilter(req, resp);
        } else {
            //不包含，需要验证用户是否登录
            //从获取session中获取user
            Object user = request.getSession().getAttribute("userLogin");
            Object admin = request.getSession().getAttribute("adminLogin");

            if (user != null) {
                if (admin == null) {
                    //用户登陆了，管理员没登陆,限制部分资源访问权限
                    if (uri.contains("/HomepageForAdmin.html") || uri.contains("/iframe_use_admin/")) {
                        System.out.println("访问了限制的资源：/HomepageForAdmin.html或/iframe_use_admin/" + " uri:" + uri + "  " + simpleDateFormat.format(date.getTime()));
                        response.sendRedirect(request.getContextPath() + "/error/600error.html");
                    } else {
                        System.out.println("user != null、admin==null，用户登陆了，管理员没登陆," + "  " + simpleDateFormat.format(date.getTime()));
                        chain.doFilter(req, resp);
                    }
                } else {
                    //user != null、admin！=null 都登陆了，放行
                    System.out.println("user != null、admin！=null 都登陆了，放行" + "  " + simpleDateFormat.format(date.getTime()));
                    chain.doFilter(req, resp);
                }

            } else {
                //已经处于user==null
                if (admin != null) {
                    //user==null、admin!=null,表明admin已登录
                    System.out.println("user==null、admin!=null,表明admin已登录" + "  " + simpleDateFormat.format(date.getTime()));
                    chain.doFilter(req, resp);
                } else {
                    //已处于user==null、admin==null
                    //都没登录，重定向登录页面
                    System.out.println("session里面啥都没有"+ "  " + simpleDateFormat.format(date.getTime()));
                    response.sendRedirect(request.getContextPath() + "/login.html");
                }
            }
        }


    }
}
