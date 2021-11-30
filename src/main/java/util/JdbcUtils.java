package util;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
	1. 声明静态数据源成员变量
	2. 创建连接池对象
	3. 定义公有的得到数据源的方法
	4. 定义得到连接对象的方法
	5. 定义关闭资源的方法
 */
public class JdbcUtils {
    //1.申明静态成员变量
    private static DataSource ds;

    //2.静态代码块加载创建连接池对象
    static {
        //用字节输入流加载properties配置文件
        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();

        try {
            properties.load(is);
            //创建druid连接池，并引用配置文件
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.定义共有得到数据源的方法
    public static DataSource getDataSource() {
        return ds;
    }

    //4.定义得到连接对象的方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //5.定义关闭资源的方法
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //6.重载close方法，适用于insert，delete操作，无返回结果集的操作
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }

}
