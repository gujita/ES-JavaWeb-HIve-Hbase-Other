package ipl.common.utils;

/**
 * <p> Desciption</P>
 *
 * @author 原之安
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/31 21:27
 * @since api1.0
 */
public class ResultObjectFromApi {
    private int code;
    private String count;
    private Result data;

    public static ResultObjectFromApi build(int code,String count,Result data) {
        return new ResultObjectFromApi(code,count,data);
    }

    public ResultObjectFromApi() {

    }

    public ResultObjectFromApi(int code,String count,Result data) {
        this.code = code;
        this.count = count;
        this.data = data;

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }
}
