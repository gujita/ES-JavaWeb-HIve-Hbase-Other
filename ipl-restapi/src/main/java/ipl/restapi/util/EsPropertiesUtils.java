package ipl.restapi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>package: util,descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @since <pre>2018/5/30 20:03</pre>
 */
public class EsPropertiesUtils {
    // 配置文件封装于map
    private static Map<String, Object> properties = new HashMap<>();

    /**
     * 获取配置
     *
     * @param key 配置信息的键
     * @return 配置值
     */
    static Object getProperty(String key) {
        return properties.get(key);
    }

    // static块，项目启动时执行
    static {
        // 加载配置文件
        Properties prop = new Properties();
        InputStream input;
        try {
            // 获取ES的配置信息，然后加载到prop中
            input = EsPropertiesUtils.class.getResourceAsStream("/es.properties");
            prop.load(input);

            // 配置
            properties.put("cluster.name", prop.getProperty("cluster.name"));
            properties.put("server", prop.getProperty("server"));
            properties.put("port", prop.getProperty("port"));
//            properties.put("transport.type","netty3");
//            properties.put("http.type", "netty3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<String,Object> getConf(){
        return properties;
    }
}
