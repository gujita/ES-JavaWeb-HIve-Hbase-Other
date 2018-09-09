package ipl.restapi.service.bigdata.es;

import ipl.restapi.util.EsOpenCloseUtils;
import ipl.restapi.util.EsPropertiesUtils;
import ipl.restapi.util.HbaseApiUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>pakage: ipl.restapi.service.bigdata.es</p>
 * <p>
 * descirption: es检索某一所以全量数据，导入HBase
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/15 下午9:03</pre>
 */
public class ReadFromEs {
    private static final Logger LOGGER = LoggerFactory.getLogger("es");
    private static final int SCROLL_SIZE = 5000;
    private static final int HBASE_PUT_SIZE = 500;

    /**
     * es数据导入HBase
     * @param tableName 数据导入到HBase的那一张表
     */
    public void putAllDataToHbase(String tableName) {
        Map<String, Object> hashMap = EsPropertiesUtils.getConf();
        TransportClient esClient = EsOpenCloseUtils.getInstance(hashMap);
        // 创建查询体
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("index", "intell_property");
        queryParams.put("type", "text_info");
        // index
        String indexName = queryParams.get("index").toString();
        // type
        String type = queryParams.get("type").toString();
        scrollDataToHbase(esClient, indexName, type, tableName);
    }

    /**
     * 允许我们做一个初始搜索并且持续批量从Elasticsearch里拉取另一部分数据，结果直到没有结果剩下,类似于数据库的游标，
     * 为了避免数据量过大，每次从上次scroll的位置继续获取数据获取的数据写入HBase。
     *
     * @param esClient  es客户端
     * @param indexName 索引名
     * @param typeName  type
     * @param tableName  type
     */
    public void scrollDataToHbase(Client esClient, String indexName, String typeName, String tableName) {
        // TODO：通信过程中getScrollId丢失怎么办？比如说kafka，就有多种机制验证处理请求者的请求数据的偏移量
        int baseRowNum = 0;
        SearchResponse scrollResp = esClient.prepareSearch(indexName)
                .setTypes(typeName)
                .setScroll(new TimeValue(600000)) // 5 mins
                .setSize(SCROLL_SIZE).get(); // 每次返回10000条数据(如果够）
        // arraylist放1000个map，一个map为一条论文数据，map中存放key-value对，是存入HBase的列名和值
        ArrayList<Map<String, Object>> hit1000List = new ArrayList<>(1024);
        Connection connection = HbaseApiUtils.getConnection();
        do {
            SearchHits searchHits = scrollResp.getHits();
            long num = searchHits.getHits().length;
            System.out.println("数量：" + num);
            for (int i = 0; i < num; ) {
                hit1000List.add(searchHits.getAt(i).getSourceAsMap());
                i++;
                if (i % HBASE_PUT_SIZE == 0) {
                    baseRowNum++;
                    try {
                        // TODO:网络不好，1000条可以考虑异步读写
                        HbaseApiUtils.putListByMap(connection, tableName, hit1000List, baseRowNum, "pappers_info", "papper_", HBASE_PUT_SIZE);
                        hit1000List.clear();
                    } catch (IOException e) {
                        LOGGER.error("es批量插入hbase异常-1！");
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            System.out.println("目前完成  " + baseRowNum * HBASE_PUT_SIZE);
            // 处理不足1000的数据
            if (!hit1000List.isEmpty()) {
                baseRowNum++; // 避免不足1000的数据覆盖之前的
                try {
                    HbaseApiUtils.putListByMap(connection, tableName, hit1000List, baseRowNum, "pappers_info", "papper_", HBASE_PUT_SIZE);
                    hit1000List.clear();
                    System.out.println("down!");
                } catch (IOException e) {
                    LOGGER.error("es批量插入hbase异常-2！");
                    LOGGER.error(e.getMessage());
                }
            }
            scrollResp = esClient.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
        }
        while (scrollResp.getHits().getHits().length != 0);
    }

    public static void main(String[] args) {
        ReadFromEs readFromEs = new ReadFromEs();
        readFromEs.putAllDataToHbase("pappers_part");
    }
}
