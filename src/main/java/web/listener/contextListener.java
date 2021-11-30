package web.listener;


import com.alibaba.druid.util.JdbcUtils;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.DriverManager;


/**
 * 此监听器为了解决tomcat关闭时，无法销毁JDBC注册驱动，为防止内存泄漏等危险，在此监听器中手动销毁
 */
@WebListener
public class contextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public contextListener() {
    }

    /**
     * 实现其中的初始化函数，当有事件发生时即触发
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        System.out.println("webService start");
    }

    /**
     *实现其中的销毁函数
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        System.out.println("webService stop");
        try {
            while (DriverManager.getDrivers().hasMoreElements()){
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            }
            System.out.println("jdbc Driver close");
            AbandonedConnectionCleanupThread.shutdown();
            System.out.println("clean thread success");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
