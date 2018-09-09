package ipl.restapi.util;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <p>package: ipl.restapi.util</p>
 * <p>
 * descirption:
 *
 * @author 王海
 * @version V1.0
 * @since <pre>2018/9/4 13:24</pre>
 */
public class kafkaUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger("kafka");

    public static KafkaProducer<String, String> getProducer() {
        // 加载配置文件(Properties extends Hashtable<Object,Object>)
        Properties props = new Properties();
        InputStream input;
        try {
            input = kafkaUtils.class.getResourceAsStream("/kafka_producer.properties");
            props.load(input);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            System.exit(-1);
        }
        return new KafkaProducer<>(props);
    }

    public static KafkaConsumer<String, String> getConsumer() {
        Properties props = new Properties();
        InputStream input;
        try {
            input = kafkaUtils.class.getResourceAsStream("/kafka_consumer.properties");
            props.load(input);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            System.exit(-1);
        }
        return new KafkaConsumer<>(props);
    }

    /**
     * 获取指定的topic的数据量
     *
     * @param topic 指定某个主题
     * @return 指定的topic的数据量
     */
    public int sizeOfOneTopic(String topic) {

        return 0;
    }

    /**
     * 获取主题list
     *
     * @param consumer 消费者对象
     * @return 主题list
     * @Deprecated 没必要的轮子
     */
    @Deprecated
    public static Set<String> topicList(KafkaConsumer<String, String> consumer) {
        return consumer.listTopics().keySet();
    }

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = kafkaUtils.getConsumer();
        for (String key : kafkaUtils.topicList(consumer)) {
            System.out.println(key);
        }
        List<PartitionInfo> partitionInfos = consumer.listTopics().get("papper_topic");
        System.out.println(partitionInfos.toString());
        consumer.partitionsFor("papper_topic");
//        consumer.poll()
        consumer.subscribe(Collections.singletonList("papper_topic"));
    }
}
