package ipl.common.token;

import java.util.Date;

/**
 * <p>Descirption:Token加密的载荷工具类</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.token
 * @date 2018/3/23 10:19
 * @since api1.0
 */
public class PlayLoadHelper {
    /**
     * 发布者
     */
    private String iss;
    /**
     * 用户是谁(useremail可以暴露)
     */
    private String sub;
    /**
     * 创建token的时间
     */
    private Date iat;

    public PlayLoadHelper() {
    }

    public PlayLoadHelper(Date createDate, String uemail) {
        this.iss = "labipl/sso";
        this.sub = uemail;
        this.iat = createDate;
    }

    public static PlayLoadHelper build(Date datenow, String uemail) {
        return new PlayLoadHelper(datenow, uemail);
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
