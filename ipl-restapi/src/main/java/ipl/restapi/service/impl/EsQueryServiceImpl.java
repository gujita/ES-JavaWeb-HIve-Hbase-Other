package ipl.restapi.service.impl;

import ipl.restapi.service.EsQueryService;
import ipl.restapi.service.bigdata.es.BaseAndBool;
import ipl.restapi.util.EsOpenCloseUtils;
import ipl.restapi.util.EsPropertiesUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>pakage: ipl.restapi.service.impl</p>
 *
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/17 上午12:07</pre>
 */
@Service
public class EsQueryServiceImpl implements EsQueryService {
    @Override
    public String queryByAuthor(String authorName) {
        // 创建客户端
        Map<String, Object> hashMap = EsPropertiesUtils.getConf();
        TransportClient client = EsOpenCloseUtils.getInstance(hashMap);
        // 创建查询体
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("index", "mapper");
        queryParams.put("type", "automatic");
        queryParams.put("query", authorName);
        queryParams.put("fields", new String[]{"authors"});
        queryParams.put("mode", "bool");
        String result = BaseAndBool.query(queryParams, client);
        System.out.println(result);
        EsOpenCloseUtils.closeClient(client);
        return result;
    }
    @Override
    public String queryByYear(String year) {
        // TODO
        return "";
    }
}
