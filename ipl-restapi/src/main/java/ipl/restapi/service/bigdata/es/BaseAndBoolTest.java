package ipl.restapi.service.bigdata.es;

import ipl.restapi.util.EsOpenCloseUtils;
import ipl.restapi.util.EsPropertiesUtils;
import org.elasticsearch.client.transport.TransportClient;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseAndBool Tester.
 *
 * @author <wanghai>
 * @version 1.0
 * @since <pre>05/18/2018</pre>
 */
public class BaseAndBoolTest {

    public static void testQuery(){
        // 创建客户端
        Map<String, Object> hashMap = EsPropertiesUtils.getConf();
        TransportClient client = EsOpenCloseUtils.getInstance(hashMap);
        // 创建查询体
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("index", "mapper");
        queryParams.put("type", "automatic");
        queryParams.put("query", "1930");
        queryParams.put("fields", new String[]{"year","category_name"});
        queryParams.put("from", 1);
        queryParams.put("size", 10);
        //
        queryParams.put("mode", "MultiMatchQuery");
        String result_multi = BaseAndBool.query(queryParams, client);
        System.out.println(result_multi);
        // bool
        queryParams.put("mode", "bool");
        String result_bool = BaseAndBool.query(queryParams, client);
        System.out.println(result_bool);
        // 关闭连接
        EsOpenCloseUtils.closeClient(client);
    }

    public static void main(String[] args) {
        BaseAndBoolTest.testQuery();
    }
} 
