package ipl.restapi.service.bigdata;

import ipl.restapi.util.GetDataUtils;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * <p> Desciption
 * 程序运行顺序：
 * 1、structureDataRange()接受6个时间参数，构造数据的日期范围条件，用于2；structureDataRange()只运行一次
 * 2、在search()中，获取当前页所有数据，我们需要"多调用"一次该方法，以获得该时间段的总数据量，方便求出pageSize；
 * 3、对pageSize+时间段进行请求，获得包含各种字段的Json数据，需要解析该数据，之后放入HDFS；
 * 4、根据parseJsonAndCount()中填充好的keyWordList，获取全文数据，之后放入HDFS
 * 5、分了多少页，就执行多少次2、3、4。
 * </P>
 *
 * @author 原之安，王海
 * @version V1.0
 * @package ipl.restapi.service.bigdata
 * @date 2018/7/7 11:34
 * @date 2018/7/8 19:50
 * @since api1.0
 * TODO:增加一个解析函数，因为search()返回的数据的所有字段，也都应该存储。
 */
public class GetAllData {
    private static final String I_NEED_THIS_FIELDS = "TI,AB,PA,LS,AN,PN,AD,PD,ZYFT";
    public static final int PER_PAGE_NUM = 350;

    /**
     * 计算两个日期之间的天数
     *
     * @param date1 开始时间
     * @param date2 结束时间
     * @return 相差天数
     */
    public int daysBetween(LocalDate date1, LocalDate date2) {
        Period period = new Period(date1, date2, PeriodType.days());
        return period.getDays();
    }

    /**
     * 检索，获取当前页所有数据
     *
     * @param dataRange 检索数据的时间段
     * @param dp        页码
     *                  TODO：解决连接超时
     */
    public String search(String dataRange, int dp) {
        /*
         * 检索串编码
         */
        try {
            dataRange = URLEncoder.encode(dataRange, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String searchUrl = "http://172.21.201.131:8200/search?dp=" + dp + "&pn=" + PER_PAGE_NUM + "&fl=" + I_NEED_THIS_FIELDS + "&q=" + dataRange;
        System.out.println(searchUrl);
        // TODO：简单计算下大小，以及StringBuilder的容量上限（网友说由内存定，所以pagenum可以大一些，测试吞吐量再定）。
        return GetDataUtils.getData(searchUrl);
    }

    /**
     * 解析JSON串
     *
     * @param jsonStr serch()中所检索出来的数据
     */
    public ArrayList<String[]> parseJsonPrepareForFullText(String jsonStr) throws JSONException {
        /**
         * 用来存放PN，AN，PD，MID,在获取全文数据时使用
         * 换数据结构(无限扩容),避免溢出
         */
        ArrayList<String[]> keyWordList = new ArrayList<>();
        JSONObject jsonData;
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray object = jsonObject.getJSONArray("RESULT");
        for (int i = 0; i < object.length(); i++) {
            String[] keywords = new String[4];
            // System.out.println("====" + object.length());
            jsonData = object.getJSONObject(i);
            keywords[0] = jsonData.getString("PN");
            keywords[1] = jsonData.getString("AN");
            keywords[2] = jsonData.getString("PD");
            keywords[3] = jsonData.getString("mid");
            keyWordList.add(keywords);
        }
        return keyWordList;
    }

    public int count(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getInt("FOUNDNUM");
    }

    /**
     * 为查询接口构造数据范围条件
     * 为了避免直接输入参数20120115这样的日期串时输错，故采用6个参数的形式，先生成合法日期，再转换
     */
    public String structureDataRange(int... args) throws JSONException {
        // 需要配置JVM参数：-ea
        assert (args.length == 6 || args.length == 3) : "期望获得6个或3个时间参数，实际获得：\t" + args.length;

        LocalDate start = new LocalDate(args[0], args[1], args[2]);
        LocalDate end;
        if (args.length == 3) {
            // 默认一次调取一个月的数据
            end = start.plusMonths(1);
        } else {
            end = new LocalDate(args[3], args[4], args[5]);
        }
        // TODO,保证end在start之后
//        if (end)

        return "((PD>=" + start.toString().replaceAll("-", "") + ") AND (PD<=" + end.toString().replaceAll("-", "") + "))";
    }

    public Object getFullText(String keyword[], String cookie) {
        String fk = "TI,AB,CLM,FT,PA,IPC,AN,PN,AU,AD,PD,PR,ADDR,PC,AGC,AGT,QWFT,PCTF,IAN,IPN";
        String dataUrl = "http://172.21.201.131/search/pub/ApiDocinfo?fk=" + fk + "&dk=[{\"DCK\":\"" + keyword[1] + "@" + keyword[0] + "@" + keyword[2] + "\",\"MID\":\"" + keyword[3] + "\"}]";
        System.out.println(dataUrl);
        // TODO:拒绝参数为null
        return GetDataUtils.getDataWithCookie(dataUrl, cookie);
    }
}
