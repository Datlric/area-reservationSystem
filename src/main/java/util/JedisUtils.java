package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedis工具类
 */
public class JedisUtils {
    private static JedisPool jedisPool;

    //静态代码块加载配置
    static {
        //读取配置文件
        InputStream is = JedisPool.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties properties = new Properties();
        //关联文件
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));

        //初始化JedisPool
        jedisPool = new JedisPool(config, properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));
    }

    //获取连接方法
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    //关闭连接方法
    public static void close(Jedis jedis) {
        if (jedis!=null){
            jedis.close();
        }
    }
}
