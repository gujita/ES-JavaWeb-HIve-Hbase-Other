package ipl.restapi.service.bigdata;

import ipl.restapi.controller.LoginCookie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * GetAllData Tester.
 *
 * @author <wang>
 * @version 1.0
 * @since <pre>07/09/2018</pre>
 */
public class GetAllDataTest {
    GetAllData getAllData = new GetAllData();

    @Before
    public void before() throws Exception {
        System.out.println("开始数据获取测试！");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: daysBetween(LocalDate date1, LocalDate date2)
     */
    @Test
    public void testDaysBetween() throws Exception {
//TODO:
    }

    /**
     * Method: search(String dataRange, int dp)
     */
    @Test
    public void testSearch() throws Exception {
        System.out.println(getAllData.search(getAllData.structureDataRange(2017, 11, 21, 2017, 11, 22), 1));
    }

    /**
     * Method: parseJsonPrepareForFullText(String jsonStr, int dp)
     */
    @Test
    public void testParseJsonPrepareForFullText() throws Exception {
//TODO:
    }

    /**
     * Method: count(String jsonStr)
     */
    @Test
    public void testCount() throws Exception {
//TODO:
    }

    /**
     * Method: structureDataRange(int... args)
     */
    @Test
    public void testStructureDataRange() throws Exception {
        Assert.assertEquals("((PD>=20171121) AND (PD<=20171122))", getAllData.structureDataRange(2017, 11, 21, 2017, 11, 22));
        Assert.assertEquals("((PD>=20171121) AND (PD<=20171221))", getAllData.structureDataRange(2017, 11, 21));
        // TODO:测试错误时间
    }

    /**
     * Method: getFullText(String keyword[])
     */
    @Test
    public void testGetFullText() throws Exception {

        String dataRange = getAllData.structureDataRange(2017, 10, 14, 2017, 10, 15);
        int FOUNDNUM = getAllData.count(getAllData.search(dataRange, 1));
        // 检索到多少条数据（非全文）
        System.out.println("size  " + FOUNDNUM);
        // 分了多少页，就执行多少次2、3、4
        // 页数向上取整，TODO：解决只有docid有值的哪些得分末尾的数据
//        if (FOUNDNUM == 0) {
//            // TODO:打日志，并结束本次检索
//        }
        int forControl = FOUNDNUM % GetAllData.PER_PAGE_NUM == 0 ? FOUNDNUM / GetAllData.PER_PAGE_NUM : FOUNDNUM / GetAllData.PER_PAGE_NUM + 1;
        for (int j = 1; j <= forControl; j++) {
            // 获取第dp页检索数据
            String jsonStr = getAllData.search(dataRange, j);
            System.out.println("jsonStr:   " + jsonStr);
            // 解析jsonStr获得参数列表
            ArrayList<String[]> keyWordList;
            // 遍历参数列表，存储并且请求全文数据
            keyWordList = getAllData.parseJsonPrepareForFullText(jsonStr);
            String cookie = LoginCookie.ConnectTheNet();
            for (String[] keywords : keyWordList) {
                // 请求全文,TODO：存储逻辑
                System.out.println("quanwen:   " + getAllData.getFullText(keywords, cookie));
                for (String words : keywords) {
                    // 单独存一个库,2G
                    System.out.println("===" + words);
                }
            }
        }
    }
} 
