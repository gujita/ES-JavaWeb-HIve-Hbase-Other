package ipl.restapi.service.bigdata.hbase;


import ipl.restapi.util.HbaseApiUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * <p>pakage: ipl.restapi.service.bigdata.hbase</p>
 * <p>
 * descirption:
 *
 * @author wanghai
 * @version V1.0
 * @since <pre>2018/8/15 下午11:24</pre>
 */
public class HbaseTest {

    public static void main(String[] args) {
        new HbaseTest().testCrud();
    }

    private void testCrud() {
        HbaseApiUtils caller = new HbaseApiUtils();
        /*
         * 标注1：详解HBaseConfiguration
         */
        // 创建一个configuration对象 —— 告诉客户端必要的配置信息
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "172.21.201.140,172.21.201.141,172.21.201.142,172.21.201.143,172.21.201.144");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        // 创建一个连接到集群的connection
        Connection connection = null;
        /* Admin是一个接口类，其很多方法在子类HBaseAdmin中实现
         0.99版本开始:HBaseAdmin不再是客户端API。它被标记为InterfaceAudience.Private，
         表示是一个HBase内部类。
         使用Connection.getAdmin（）获取Admin的实例，而不是直接构建一个HBaseAdmin。
         可以用来create、drop、list、enabl、disable表；add、drop 表的column families，以及一些其他的管理操作。*/

        try{
            HBaseAdmin.checkHBaseAvailable(config);
        }catch(Exception e){
            System.err.println("Exception at " + e);
            System.exit(1);
        }
        try {
            connection = ConnectionFactory.createConnection(config);
            System.out.println(config.toString());
            //
            Admin admin = connection.getAdmin();
            caller.createTable(admin,TableName.valueOf("pappers_part"),"pappers_info");
            // 该方法传递一个String类型参数，返回TableName实例
//            TableName tableName = TableName.valueOf("myHBaseTable");

            // 获取lists of table
            caller.listTable(admin);
            // 创建HBase表
            //caller.createTable(admin, tableName, "papper", "papper_info");
            // rowkey要设计得尽量的短，数据的持久化文件HFile中是按照KeyValue存储的，
            // 如果rowkey过长，会极大影响HFile的存储效率

//            byte[] rowkey_bytes = Bytes.toBytes("ROW4");
//            /* 一行一行的插入数据,每一次put操作都是一次有效的RPC（
//             所以数据量大时不要这样使用， 而应该使用BufferedMutator来实现批量的异步写操作。）
//             这里两个列族，commonInfo列族两个“小列”，concelInfo一个“小列”*/
//            caller.putOneByOne(connection, tableName, rowkey_bytes, "commonInfo", "city", "Ziyang");
//            caller.putOneByOne(connection, tableName, rowkey_bytes, "commonInfo", "password", "000000");
//            caller.putOneByOne(connection, tableName, rowkey_bytes, "concelInfo", "age", "100");
//
//            // 删除表
////            caller.myDeleteTable(admin, tableName);
//
//            // 获取指定的数据
//            caller.getOneRow(connection, tableName, rowkey_bytes);
//
//            // 批量put数据
//            String[] str = new String[]{"ROW5", "commonInfo", "city", "Shanghai", "ROW5"
//                    , "concelInfo", "age", "35", "ROW6", "concelInfo", "age", "120", "Illegal_Value"};
//            caller.putList(connection, tableName, str);
//
//            // 删除两行数据
//            Delete delete1 = new Delete(Bytes.toBytes("ROW5"));
//            Delete delete = new Delete(Bytes.toBytes("ROW6"));
//            /*也可以定义删除的列族：
//            其中addCaddColumn是删除最新版本，addCaddColumns
//            是删除所有版本*/
//            Table table = connection.getTable(tableName);
//            table.delete(delete1);
//            table.delete(delete);
//
//            // scan表
//            caller.scan(connection, tableName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}