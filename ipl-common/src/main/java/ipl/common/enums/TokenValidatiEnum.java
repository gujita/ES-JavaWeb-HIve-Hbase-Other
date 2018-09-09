package ipl.common.enums;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.enums
 * @date 2018/3/26 10:10
 * @since api1.0
 */
public enum TokenValidatiEnum {
    NO_TRUST("签名不合法的token，已拦截请求", "110"),
    EXPIRE("该用户的token已过期，需重新登录", "111"),
    OTHER_ERRORE("用户token验证出现未知问题，已拦截请求", "112");

    private String errorCode;
    private String desc;

    TokenValidatiEnum(String desc, String code) {
        this.errorCode = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
