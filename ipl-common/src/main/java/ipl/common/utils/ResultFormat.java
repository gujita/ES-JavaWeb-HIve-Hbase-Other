package ipl.common.utils;

/**
 * <p>Descirption:自定义的http响应结构</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.utils
 * @date 2018/3/20 11:10
 * @since api1.0
 */
public class ResultFormat {
    /**
     * 响应业务状态
     */
    private String status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     *  前端是否有必要读取msg中的数据
     */
    private int ifmsg;

    /**
     *   前端请求的数据
     */
    private Object data;

    /**
     *  前后端type相同，用于前端验证哪个接口返回数据了
     */
    private String type;

    public static ResultFormat build(String status, String msg, int ifmsg, String type, Object data) {
        return new ResultFormat(status, msg, ifmsg, type, data);
    }

    public ResultFormat() {
    }

    public ResultFormat(String status, String msg, int ifmsg, String type, Object data) {
        this.status = status;
        this.msg = msg;
        this.ifmsg = ifmsg;
        this.type = type;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIfmsg() {
        return ifmsg;
    }

    public void setIfmsg(int ifmsg) {
        this.ifmsg = ifmsg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
