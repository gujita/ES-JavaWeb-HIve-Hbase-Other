package ipl.restapi.service.bigdata.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Map;

/**
 * <p>Descirption:默认查询和bool查询</p>
 *
 * @author 王海
 * @version V1.0
 * @since <pre>2018/5/18 22:23</pre>
 */
public class BaseAndBool {
    public static String query(Map<String, Object> queryParams, TransportClient esClient) {
        // 检索词
        String queryWords = queryParams.get("query").toString();
        // 检索模式
        String queryMode = queryParams.get("mode").toString();
        // 检索字段
        String[] queryFields = (String[]) queryParams.get("fields");
        // index
        String index = queryParams.get("index").toString();
        // type
        String type = queryParams.get("type").toString();
        int from = 0;
        // TODO:更改查询逻辑
        int size = 10000;
        if (queryParams.containsKey("from")) {
            from = (int) queryParams.get("from");
        }
        if (queryParams.containsKey("size")) {
            size = (int) queryParams.get("size");
        }

        // org.elasticsearch.index.query.QueryBuilder
        QueryBuilder queryBuilder = null;
        if ("MultiMatchQuery".equalsIgnoreCase(queryMode)) {
            // 基础查询，默认使用OR
            queryBuilder = QueryBuilders.multiMatchQuery(queryWords, queryFields);
        } else {
            // 使用bool查询，可使用must\should\mustNot等
            String[] terms = queryWords.split("\\s+");
            System.out.println("使用bool");
            for (String term : terms) {
                if (queryBuilder == null) {
                    queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(term, queryFields));
                } else {
                    queryBuilder = QueryBuilders.boolQuery().must(queryBuilder).must(QueryBuilders.multiMatchQuery(term, queryFields));
                }
            }
        }
        // QueryBuilders构建完毕，下面才是真正执行查询的地方
        SearchResponse searchResponse;
        // 在执行get()之前，返回的都是SearchRequestBuilder对象,SearchType.DEFAULT为QUERY_THEN_FETCH
        // 不设置setTypes则默认index下的所有type
        searchResponse = esClient.prepareSearch(index).setTypes(type).setSearchType(SearchType.DEFAULT).setQuery(queryBuilder).setFrom(from).setSize(size).get();
        searchResponse = esClient.prepareSearch(index).setTypes(type).setSearchType(SearchType.DEFAULT).setQuery(queryBuilder).setFrom(from).setSize(size).get();
        return searchResponse.toString();
    }
}
