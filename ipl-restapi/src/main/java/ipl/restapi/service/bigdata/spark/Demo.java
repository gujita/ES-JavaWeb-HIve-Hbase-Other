//package ipl.restapi.service.bigdata.spark;
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function2;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.api.java.function.VoidFunction;
//import scala.Tuple2;
//
///**
// * descirption:
// *
// * @author wanghai
// * @version V1.0
// * @since <pre>2018/8/20 上午8:55</pre>
// */
//import java.util.Arrays;
//import java.util.Iterator;
//
//public class Demo {
//
//    private static final String OUTPUTFILEPATH = "./dataout";
//
//    public static void main(String[] args) {
//        SparkConf conf = new SparkConf().setAppName("WordCountLocal").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaRDD<String> lines = sc.textFile("./data/wordcount.txt");
//        // flatMap将RDD的一个元素给拆分成多个元素；FlatMapFunction的两个参数分别是输入和输出类型
//        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterator<String> call(String line) throws Exception {
//                return Arrays.asList(line.split(" ")).iterator();
//            }
//        });
//
//        // 第五步：将每一个单词，映射为(单词, 1)的这种格式。为之后进行reduce操作做准备。
//        // mapToPair，就是将每个元素映射为一个(v1,v2)这样的Tuple2（scala里的Tuple类型）类型的元素
//        // mapToPair得与PairFunction配合使用，PairFunction中的第一个泛型参数代表输入类型
//        // 第二个和第三个泛型参数，代表的输出的Tuple2的第一个值和第二个值的类型
//        // JavaPairRDD的两个泛型参数，分别代表了tuple元素的第一个值和第二个值的类型
//
////        JavaPairRDD<String, Integer> pairs = words.mapToPair(
////                new PairFunction<String, String, Integer>() {
////                    private static final long serialVersionUID = 1L;
////                    @Override
////                    public Tuple2<String, Integer> call(String word) throws Exception {
////                        return new Tuple2<>(word, 1);
////                    }});
//
//        // 注：mapToPair的lambda版本：【详见注释"iii"】
//        JavaPairRDD<String, Integer> pairs = words.mapToPair(
//                (PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1)
//        );
//
//        // 第六步：reduce操作（原理同MapReduce的reduce操作一样）
//        // 以单词作为key，统计每个单词出现的次数
//        // 最后返回的JavaPairRDD中的元素，也是tuple，但是第一个值就是每个key，第二个值就是key出现的次数
////        JavaPairRDD<String, Integer> wordCounts = pairs.reduceByKey(
////                new Function2<Integer, Integer, Integer>() {
////                    private static final long serialVersionUID = 1L;
////                    @Override
////                    public Integer call(Integer v1, Integer v2) throws Exception {
////                        return v1 + v2;
////                    }});
//
//        JavaPairRDD<String, Integer> wordCounts = pairs.reduceByKey(
//                (Function2<Integer, Integer, Integer>) (x, y) -> (x + y)
//        );
//
//        // 第七步：触发程序执行
//        // 到这里为止，我们通过几个Spark算子操作，已经统计出了单词的次数
//        // 目前为止我们使用的flatMap、mapToPair、reduceByKey操作，都是transformation操作
//        // 现在我们需要一个action操作来触发程序执行（这里是foreach）
//        wordCounts.foreach(new VoidFunction<Tuple2<String, Integer>>() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public void call(Tuple2<String, Integer> wordCount) throws Exception {
//                System.out.println(wordCount._1 + " 出现了 " + wordCount._2 + " 次; ");
//            }
//        });
//
//        // 我们也可以通过将统计出来的结果存入文件（这也是一个action操作），来触发之前的transformation操作
//        try {
//            wordCounts.saveAsTextFile(OUTPUTFILEPATH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        sc.close();
//    }
//}