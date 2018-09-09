package ipl.restapi.service.bigdata.es;

import ipl.restapi.util.EsOpenCloseUtils;
import ipl.restapi.util.EsPropertiesUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>package: PACKAGE_NAME,descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @since <pre>2018/5/30 19:32</pre>
 */
public class CsvToES {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvToES.class);

    public void csvToES(String[] fields, File[] files) {
        Map<String, Object> hashMap = EsPropertiesUtils.getConf();
        TransportClient client = EsOpenCloseUtils.getInstance(hashMap);
        int idNum = 0;
        int error = 0;
        // 一、读取文件
        for (File f : files) {
            // 绝对路径读取文件
            System.out.println(f.getAbsolutePath());
            File file = new File(f.getAbsolutePath());
            // 二、开始一行一行的写
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                BulkRequestBuilder bulkRequest = client.prepareBulk();
                Map<String, Object> valuesMap = new HashMap<>(64, 0.6f);
                // 跳过文件第一行
                System.out.println("略过文件第一行==========" + br.readLine());
                String line;
                while ((line = br.readLine()) != null) {
                    String[] filedValues = line.split(",");
                    if (filedValues.length != 34) {
                        error = error + 1;
                        continue;
                    }
                    for (int filedNum = 0; filedNum < filedValues.length; filedNum++) {
                        valuesMap.put(fields[filedNum], filedValues[filedNum]);
                    }
                    // 导入数据有很多种方式，比如：1.手动构建JSON风格的字符串；2.使用map；3.使用JackSon等工具包序列化Beans；3.使用ES的XContentBuilder ；4.BulkRequestBuilder  ; 5.https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.1/java-docs-index.html#java-docs-index
                    bulkRequest.add(client.prepareIndex("xueli", "new_orders", Integer.toString(idNum)).setSource(valuesMap));
                    valuesMap.clear();
                /* 3.jsonBuilder
                bulkRequest.add(client.prepareIndex("xueli", "new_orders", Integer.toString(count)).setSource(jsonBuilder()
                        .startObject()
                        .field(fields[0], filedValues[0])
                        .field(fields[1], filedValues[1])
                        .endObject()));*/
                    if (idNum % 2000 == 0) {
                        System.out.println("++++++++++++++++++++++++++++++++++++");
                        // 三、批量导入
                        bulkRequest.get();
                        // 与上面的语法效果相同：bulkRequest.execute().actionGet();
                    }
                    idNum++;
                }
                // 四、导入每个文件最后不足2000条的数据（但是必须得有数据，否则报错: no requests added）
                // 查阅源码可知，可通过拿到父类requests属性，判断其size来解决[子类调用父类方法获得属性]
                if (bulkRequest.request().requests().size() > 0 ) {
                    bulkRequest.get();
                }
                // 五、操作下一个文件
            } catch (IOException e) {
                LOGGER.error("：ERROR:堆栈信息====={}", e.getMessage());
            }
        }
        EsOpenCloseUtils.closeClient(client);
    }

    public static void main(String[] args) {
        CsvToES csvToES = new CsvToES();
        String[] fields = {"order_id", "buyer_nickname", "member_level", "alipay", "xiaoer_wangwang", "order_time", "pay_time", "deliver_time", "item_name", "item_id", "item_price", "item_count", "item_seller_id", "item_sku_id", "order_should_paid", "order_actual_paid", "order_status", "order_type", "deal_time", "order_type", "customer_phone_num", "receiver_name", "order_address_prov", "order_address_city", "order_address_district", "Title", "ItemType", "ItemPattern", "ItemAge", "ItemSeason", "Class", "SubClass", "Style", "Season"};
        File file = new File("D:\\U_pan\\work\\new_data\\code\\data_utf8\\");
        // 目录下只有我需要读取的文件，故不再进行进一步处理
        File[] files = file.listFiles();
        csvToES.csvToES(fields, files);
    }
}