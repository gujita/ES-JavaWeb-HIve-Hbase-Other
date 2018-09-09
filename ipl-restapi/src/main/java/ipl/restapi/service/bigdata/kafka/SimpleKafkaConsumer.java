package ipl.restapi.service.bigdata.kafka;

import ipl.restapi.util.kafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>pakage: ipl.restapi.service.bigdata.kafka</p>
 * <p>
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/7/28 下午6:17</pre>
 */
public class SimpleKafkaConsumer {
    private static KafkaConsumer<String, String> consumer = kafkaUtils.getConsumer();
    private final static String TOPIC = "papper_topic5";

    public void consume() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(System.getProperty("user.dir") + "/test5.txt")));
        // 订阅指定的主题，可以是正则匹配
        consumer.subscribe(Arrays.asList(TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                bufferedWriter.write("topic = " + record.topic() + ", partition = " + record.partition() + ", offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
                bufferedWriter.newLine();
            }
        }
    }

//    public void consumeToHive() throws IOException {
//        consumer.subscribe(Arrays.asList(TOPIC));
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(1000);
//            for (ConsumerRecord<String, String> record : records) {
//                bufferedWriter.write("topic = " + record.topic() + ", partition = " + record.partition() + ", offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
//                bufferedWriter.newLine();
//            }
//        }
//    }

    public static void main(String[] args) {
        try {
            new SimpleKafkaConsumer().consume();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}