package ipl.restapi.service.bigdata.kafka;

import ipl.restapi.service.bigdata.GetAllData;
import ipl.restapi.util.kafkaUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>pakage: ipl.restapi.service.bigdata.kafka</p>
 *
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/7/20 下午6:17</pre>
 */
public class KeyWordProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger("kafka");

    public static void dataTokafka() throws JSONException {
        Producer<String, String> kafkaProducer = kafkaUtils.getProducer();
        // 统计时间
        System.out.println("程序开始时间戳信息：" + new Date());
        final long startTime = System.currentTimeMillis();
        String topic = "papper_topic8";
        GetAllData getAllData = new GetAllData();

        int keyCount = 0;
        for (int i = 1; i < 2; i++) {
            String dataRange = getAllData.structureDataRange(1995, i, 1, 1995, i, 6);
            int foundNum = getAllData.count(getAllData.search(dataRange, 1));
            // 检索到多少条数据（非全文）
            LOGGER.info("{} 月，有记录 {}", i, foundNum);
            // 分了多少页，就执行多少次2、3、4
            // 页数向上取整，TODO：解决只有docid有值的哪些得分末尾的数据
            if (foundNum == 0) {
                LOGGER.warn("请核查！{} 月，是否无专利数据", i);
                continue;
            }
            int forControl = foundNum % GetAllData.PER_PAGE_NUM == 0 ? foundNum / GetAllData.PER_PAGE_NUM : foundNum / GetAllData.PER_PAGE_NUM + 1;
            for (int j = 1; j <= forControl; j++) {
                // 获取第dp页检索数据
                String jsonStr = getAllData.search(dataRange, j);
                // 解析jsonStr获得参数列表
                ArrayList<String[]> keyWordList;
                // 遍历参数列表，存储并且请求全文数据
                keyWordList = getAllData.parseJsonPrepareForFullText(jsonStr);
                for (String[] keywords : keyWordList) {
                    keyCount++;
                    // 请求全文,TODO：关键词发送给kafka，全文解析，并且将结果发给kafka
                    //String fullText = (String) getAllData.getFullText(keywords, LoginCookie.ConnectTheNet());
                    try {
                        // 在这里， producer.send() 方住先返回一个 Future对象，然后调用 Future对象的 get() 方曲等待 Kafka 响应。
                        // 如果服务器返回错误， get()方法会抛出异常。如果没有发生错误，我们会得到一个 RecordMetadata对象，可以用它获取消息的偏移量。
                        kafkaProducer.send(new ProducerRecord<>(topic, Integer.toString(keyCount), Arrays.toString(keywords)), new KeyWordProducerCallBack());
//                        System.out.println(keyCount + "    " + Arrays.toString(keywords));
                        // send方法的返回值是RecordMetadata类型，它含有消息将被投递的partition信息，该条消息的offset，以及时间戳。
                        // 因为send返回的是Future对象，因此在该对象上调用get()方法将阻塞，直到相关的发送请求完成并返回元数据信息；或者在发送时抛出异常而退出。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(keyCount);
            }
        }
        kafkaProducer.close();
        final long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("执行时长：" + excTime + "s");
    }

    static class KeyWordProducerCallBack implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                LOGGER.error("offset: {}", String.valueOf(recordMetadata.offset()));
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            KeyWordProducer.dataTokafka();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
