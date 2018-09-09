package ipl.restapi.util;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * <p>Descirption:打开Elastic客户端以及关闭客户端</p>
 *
 * @author 王海
 * @version V1.0
 * @package PACKAGE_NAME
 * @date 2018/5/18 14:55
 * @since api1.0
 */
public class EsOpenCloseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("es");
    private static TransportClient client = null;

    /**
     * 从Map中解析出参数来初始化客户端
     *
     * @param mapParms 参数Map
     */
    public static TransportClient getClientWithMap(Map<String, Object> mapParms) {
        // 初始化client通常需要ip地址，端口，集群名称三个参数
        // 获取集群名
        String clusterName = mapParms.get("cluster.name").toString();
        // 获取ip地址
        String addressMaster = mapParms.get("server").toString();
        //byte[] addressMaster = (byte[]) mapParms.get("server");
        // 获取端口（ES的默认传输端口为9300）
        int transport = Integer.valueOf(mapParms.get("port").toString());
        // 根据以上设置，初始化elasticsearch客户端
        // Builder是Settings中的一个静态内部类
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .build();
        try {
            client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(addressMaster), transport));
            //client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByAddress(addressMaster), transport));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            LOGGER.error("初始化client失败 ===== " + e.getMessage());
        }
        LOGGER.info("初始化client成功 ===== " + System.currentTimeMillis());
        return client;
    }

    public static synchronized TransportClient getInstance(Map<String, Object> mapParms) {
        if (client == null) {
            client = EsOpenCloseUtils.getClientWithMap(mapParms);
        }
        return client;
    }

    public static void closeClient(TransportClient client) {
        if (client != null) {
            client.close();
            LOGGER.info("关闭client成功 ===== " + System.currentTimeMillis());
        }
    }
}
