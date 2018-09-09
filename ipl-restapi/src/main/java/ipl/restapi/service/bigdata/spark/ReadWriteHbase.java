package ipl.restapi.service.bigdata.spark;

import ipl.restapi.util.HbaseApiUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/20 上午9:04</pre>
 */
public class ReadWriteHbase {

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf().setAppName("sparkHbase").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        Connection connection = HbaseApiUtils.getConnection();
//        config.addResource(new Path("/etc/hbase/conf/core-site.xml"));
//        config.addResource(new Path("/etc/hbase/conf/hbase-site.xml"));
        String tableName = "pappers_part";
        ResultScanner hbaseResult = null;
        // hbase数据，ResultScanner封装
        try {
            hbaseResult = HbaseApiUtils.scan(connection, TableName.valueOf(tableName), "pappers_info");
        } catch (IOException e) {
            System.out.println("读取Hbase数据出错");
            e.printStackTrace();
        }
        for (Result r : hbaseResult) {
            System.out.println(Bytes.toString(r.value()));
        }
    }
}